package com.capstone.schoolmanagement.auth.users;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import com.capstone.schoolmanagement.auth.roles.RoleRepository;
import com.capstone.schoolmanagement.email.EmailService;
import com.capstone.schoolmanagement.model.StudentConfirmationToken;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Guest;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.StudentDto;
import com.capstone.schoolmanagement.repos.StudentConfirmationTokenRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository usrRepo;
	private final RoleRepository roleRepo;
	private final StudentConfirmationTokenRepo studentTokenRepo;
	private final EmailService mailSrv;
	private final PasswordEncoder encoder;

	public LoginRequest create(UserDto usrDto) {
		if (usrRepo.existsByEmail(usrDto.getEmail()))
			throw new EntityExistsException("A user with this email has already been registered");
		AppUser usr = new Guest();
		BeanUtils.copyProperties(usrDto, usr);
		usr.setPassword(encoder.encode(usrDto.getPassword()));
		AppRole role = roleRepo.findById(4l).orElseThrow(() -> new EntityNotFoundException("Role not found"));
		usr.getRoles().add(role);
		usrRepo.save(usr);

		LoginRequest request = new LoginRequest();
		request.setEmail(usrDto.getEmail());
		request.setPassword(usrDto.getPassword());
		return request;
	}

	public StudentConfirmationToken studentApplication(StudentDto studentDto) {
		// cerco l'utente e se non esiste lancio un errore
		// genero token che collego all'account
		// invio email con link di conferma
		// nel frattempo salvo l'utente come GUEST. Quando il link viene cliccato parte
		// la funzione enrolStudent che trasforma il GUEST in STUDENT
		AppUser usr = usrRepo.findByEmail(studentDto.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		BeanUtils.copyProperties(studentDto, usr);
		usr.setGender(EGender.valueOf(studentDto.getGender()));
		AppRole role = roleRepo.findById(4l).orElseThrow(() -> new EntityNotFoundException("Role not found"));
		usr.getRoles().add(role);
		usr = usrRepo.save(usr);

		Optional<StudentConfirmationToken> tokenOpt = studentTokenRepo.getByUserId(usr.getId());
		StudentConfirmationToken token = tokenOpt.isPresent() ? tokenOpt.get() : new StudentConfirmationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setExpirationDate(LocalDate.now().plusDays(30));
		token.setUser(usr);

		String link = "http://localhost:8080/users/enrol?token=" + token.getToken();
		mailSrv.send(usr.getEmail(), buildEmail(usr.getName(), link));

		return studentTokenRepo.save(token);
	}

	public UserResponse enrolStudent(String token) {
		StudentConfirmationToken tokenObj = studentTokenRepo.getByToken(token)
				.orElseThrow(() -> new EntityNotFoundException("Token not found"));
		AppUser usr = usrRepo.findById(tokenObj.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		Student student = new Student();
		BeanUtils.copyProperties(usr, student);
		AppRole role = roleRepo.findById(3l).orElseThrow(() -> new EntityNotFoundException("Role not found"));
		student.getRoles().add(role);
		usrRepo.deleteById(usr.getId());
		student = usrRepo.save(student);
		return UserResponse.buildUserResponse(student);
	}

	public Page<UserResponse> getAllUsersBasicInformations(Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "id");
		Page<AppUser> usersPage = usrRepo.findAll(pgb);

		List<UserResponse> users = usersPage//
				.getContent()//
				.stream()//
				.map(user -> UserResponse.buildUserResponse(user))//
				.toList();

		return new PageImpl<UserResponse>(users);
	}

	public UserResponse getUserBasicInformations(Long id) {
		AppUser user = usrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return UserResponse.buildUserResponse(user);
	}

	public UserResponse update(Long id, UserDto usrDto) {
		AppUser usr = usrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		BeanUtils.copyProperties(usrDto, usr);
		usr.setPassword(encoder.encode(usrDto.getPassword()));
		usr.setGender(EGender.valueOf(usrDto.getGender()));
		usrRepo.save(usr);
		return UserResponse.buildUserResponse(usr);
	}

	public void delete(Long id) {
		if (!usrRepo.existsById(id))
			throw new EntityNotFoundException("User not found");
		usrRepo.deleteById(id);
	}

	public void addRole(Long userId, Long roleId) {
		AppUser usr = usrRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		AppRole role = roleRepo.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));
		usr.getRoles().add(role);
		usrRepo.save(usr);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	private String buildEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
				+ "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
				+ "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
				+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
				+ link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
				+ "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}

}
