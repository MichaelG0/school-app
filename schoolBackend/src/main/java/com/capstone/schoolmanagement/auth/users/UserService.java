package com.capstone.schoolmanagement.auth.users;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.auth.login.LoginRequest;
import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.auth.roles.ERole;
import com.capstone.schoolmanagement.auth.roles.RoleRepository;
import com.capstone.schoolmanagement.dto.KlassDto;
import com.capstone.schoolmanagement.dto.users.StudentDto;
import com.capstone.schoolmanagement.dto.users.UserRolesDto;
import com.capstone.schoolmanagement.email.EmailService;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Guest;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.StudentConfirmationToken;
import com.capstone.schoolmanagement.repos.CourseRepo;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.repos.StudentConfirmationTokenRepo;
import com.capstone.schoolmanagement.services.KlassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository usrRepo;
	private final RoleRepository roleRepo;
	private final StudentConfirmationTokenRepo studentTokenRepo;
	private final CourseRepo crsRepo;
	private final KlassRepo klsRepo;
	private final KlassService klsSrv;
	private final EmailService mailSrv;
	private final PasswordEncoder encoder;

	public LoginRequest create(UserDto usrDto) {
		if (usrRepo.existsByEmail(usrDto.getEmail()))
			throw new EntityExistsException("A user with this email has already been registered");
		AppUser usr = new Guest();
		BeanUtils.copyProperties(usrDto, usr);
		usr.setPassword(encoder.encode(usrDto.getPassword()));
		AppRole role = roleRepo.findByRoleName(ERole.ROLE_GUEST)
				.orElseThrow(() -> new EntityNotFoundException("Role not found"));
		usr.getRoles().add(role);
		usrRepo.save(usr);

		LoginRequest request = new LoginRequest();
		request.setEmail(usrDto.getEmail());
		request.setPassword(usrDto.getPassword());
		return request;
	}

	public UserResponse studentApplication(StudentDto studentDto) {
		// cerco l'utente e se non esiste lancio un errore
		// genero token che collego all'account
		// invio email con link di conferma
		// nel frattempo aggiorno l'utente (che rimane un GUEST)
		// quando il link viene cliccato parte
		// la funzione enrolStudent che trasforma il GUEST in STUDENT
		AppUser usr = usrRepo.findByEmail(studentDto.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		BeanUtils.copyProperties(studentDto, usr);
		usr.setGender(EGender.valueOf(studentDto.getGender()));
		usr = usrRepo.save(usr);

		Optional<StudentConfirmationToken> tokenOpt = studentTokenRepo.getByUserId(usr.getId());
		StudentConfirmationToken token = tokenOpt.isPresent() ? tokenOpt.get() : new StudentConfirmationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setExpirationDate(LocalDate.now().plusDays(30));
		token.setUser(usr);
		token.setCourse(crsRepo.findById(studentDto.getCourseId())
				.orElseThrow(() -> new EntityNotFoundException("Course not found")));
		studentTokenRepo.save(token);

		String link = "http://localhost:4200/" + token.getToken() + "/confirmation";
		mailSrv.send(usr.getEmail(), buildEmail(usr.getName(), link));

		return UserResponse.buildUserResponse(usr);
	}

	public UserResponse enrolStudent(String token) {
		StudentConfirmationToken tokenObj = studentTokenRepo.getByToken(token)
				.orElseThrow(() -> new EntityNotFoundException("Token not found"));
		AppUser usr = usrRepo.findById(tokenObj.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		AppRole role = roleRepo.findByRoleName(ERole.ROLE_STUDENT)
				.orElseThrow(() -> new EntityNotFoundException("Role not found"));
		Course course = tokenObj.getCourse();
		List<Klass> availableKlasses = klsRepo.findByCourse(course)
				.get()
				.stream()
				.filter(kls -> kls.getStudents().size() < 20)
				.toList();
		Klass klass = availableKlasses.isEmpty() ? klsSrv.create(new KlassDto(course)) : availableKlasses.get(0);

		Student student = new Student();
		BeanUtils.copyProperties(usr, student);
		student.getRoles().add(role);
		student.setCourse(course);
		student.setKlass(klass);

		usrRepo.deleteById(usr.getId());
		student = usrRepo.save(student);
		return UserResponse.buildUserResponse(student);
	}

	public Page<UserResponse> getAll(Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "name");
		Page<AppUser> usersPage = usrRepo.findAll(pgb);

		List<UserResponse> users = usersPage.getContent()
				.stream()
				.map(user -> UserResponse.buildUserResponse(user))
				.toList();

		return new PageImpl<UserResponse>(users, pgb, usersPage.getTotalElements());
	}

	public UserResponse getById(Long id) {
		AppUser user = usrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		return UserResponse.buildUserResponse(user);
	}

	public UserResponse update(Long id, UserDto usrDto) {
		AppUser usr = usrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		BeanUtils.copyProperties(usrDto, usr);
		usr.setPassword(encoder.encode(usrDto.getPassword()));
		usr.setGender(EGender.valueOf(usrDto.getGender()));
		usrRepo.save(usr);
		return UserResponse.buildUserResponse(usr);
	}

	public void delete(Long id) {
		if (!usrRepo.existsById(id))
			throw new EntityNotFoundException("User not found with id: " + id);
		usrRepo.deleteById(id);
	}

	public List<UserResponse> editUsersRoles(List<UserRolesDto> userRolesList) {
		Map<Long, List<String>> userRolesMap = userRolesList.stream()
				.collect(Collectors.toMap(UserRolesDto::getUserId, UserRolesDto::getRoles));
		List<Long> userIds = userRolesMap.keySet().stream().toList();
		List<AppUser> users = usrRepo.findAllById(userIds);

		for (AppUser user : users) {
			List<ERole> rolesEnum = userRolesMap.get(user.getId())
					.stream()
					.map(roleStr -> ERole.valueOf(roleStr))
					.toList();
			List<AppRole> roles = roleRepo.findAllByRoleNameIn(rolesEnum);
			user.getRoles().clear();
			user.getRoles().addAll(roles);
		}

		usrRepo.saveAll(users);
		return users.stream().map(UserResponse::buildUserResponse).toList();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	private String buildEmail(String name, String link) {
		return "<div style=\"font-family: 'Roboto', sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" + "\n"
				+ "<table role=\"presentation\" width=\"100%\" style=\"border-collapse: collapse; min-width: 100%; width: 100%!important;\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "        <td width=\"100%\" height=\"53\" bgcolor=\"#1E3D59\">\n"
				+ "            \n"
				+ "            <table role=\"presentation\" width=\"100%\" style=\"border-collapse: collapse; max-width: 580px;\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "                <tbody><tr>\n"
				+ "                    <td width=\"70\" bgcolor=\"#1E3D59\" valign=\"middle\">\n"
				+ "                        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">\n"
				+ "                            <tbody><tr>\n"
				+ "                                <td style=\"padding-left: 10px;\">\n"
				+ "                                    \n" + "                                </td>\n"
				+ "                                <td style=\"font-size: 28px; line-height: 1.315789474; Margin-top: 4px; padding-left: 10px;\">\n"
				+ "                                    <span style=\"font-family: 'Roboto', sans-serif; font-weight: 700; color: #ffffff; text-decoration: none; vertical-align: top; display: inline-block;\">Confirm your email</span>\n"
				+ "                                </td>\n" + "                            </tr>\n"
				+ "                        </tbody></table>\n" + "                    </a>\n"
				+ "                </td>\n" + "            </tr>\n" + "        </tbody></table>\n" + "        \n"
				+ "    </td>\n" + "</tr>\n" + "</tbody></table>\n"
				+ "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; max-width: 580px; width: 100%!important;\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "        <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "        <td>\n" + "            \n"
				+ "            <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">\n"
				+ "                <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1E3D59\" width=\"100%\" height=\"10\"></td>\n"
				+ "                </tr>\n" + "            </tbody></table>\n" + "            \n" + "        </td>\n"
				+ "        <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "</tbody></table>\n" + "\n"
				+ "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; max-width: 580px; width: 100%!important;\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "        <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "        <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "        <td style=\"font-family: 'Roboto', sans-serif; font-size: 19px; line-height: 1.5; max-width: 560px; color: #0b0c0c;\">\n"
				+ "            <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\">Hi "
				+ name + ",</p>\n"
				+ "            <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\">Thank you for applying for our course. We're excited to have you on board!</p>\n"
				+ "            <blockquote style=\"Margin: 0 0 20px 0; border-left: 3px solid #1E3D59; padding: 15px 0 0.1px 15px; font-size: 19px; line-height: 25px; color: #0b0c0c;\">\n"
				+ "                <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\">To complete your enrollment, please click the button below:</p>\n"
				+ "                <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\"><a href=\""
				+ link
				+ "\" style=\"text-decoration: none; color: #ffffff; background-color: #1E3D59; padding: 10px 20px; border-radius: 5px; display: inline-block;\">Enroll Now</a></p>\n"
				+ "            </blockquote>\n"
				+ "            <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\">This link will expire in 30 days.</p>\n"
				+ "            <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c;\">We look forward to seeing you in class!</p>\n"
				+ "        </td>\n" + "        <td width=\"10\" valign=\"middle\"><br></td>\n" + "    </tr>\n"
				+ "    <tr>\n" + "        <td height=\"30\"><br></td>\n" + "    </tr>\n" + "</tbody></table>\n"
				+ "<div class=\"yj6qo\"></div>\n" + "<div class=\"adL\">\n" + "</div>\n" + "</div>";
	}

}
