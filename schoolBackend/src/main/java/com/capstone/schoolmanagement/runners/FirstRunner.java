package com.capstone.schoolmanagement.runners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.auth.roles.ERole;
import com.capstone.schoolmanagement.auth.roles.RoleRepository;
import com.capstone.schoolmanagement.auth.users.UserRepository;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.ECourse;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.users.Admin;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Guest;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.repos.CourseRepo;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class FirstRunner implements CommandLineRunner {
	private final RoleRepository roleRepo;
	private final UserRepository usrRepo;
	private final KlassRepo klsRepo;
	private final CourseRepo crsRepo;
	private final ObjectProvider<Teacher> teacherPrv;
	private final ObjectProvider<Student> studentPrv;
	private final ObjectProvider<Klass> klassPrv;
	private final ObjectProvider<Course> crsPrv;
	private final Faker fkr;
	private final PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
		log.info("===== FIRST RUNNER =====");

		AppRole adminRole = new AppRole();
		adminRole.setRoleName(ERole.ROLE_ADMIN);
		roleRepo.save(adminRole);
		
		AppRole teacherRole = new AppRole();
		teacherRole.setRoleName(ERole.ROLE_TEACHER);
		roleRepo.save(teacherRole);

		AppRole staffRole = new AppRole();
		staffRole.setRoleName(ERole.ROLE_STAFF);
		roleRepo.save(staffRole);

		AppRole studentRole = new AppRole();
		studentRole.setRoleName(ERole.ROLE_STUDENT);
		roleRepo.save(studentRole);

		AppRole guestRole = new AppRole();
		guestRole.setRoleName(ERole.ROLE_GUEST);
		roleRepo.save(guestRole);

		Set<AppRole> adminRoles = new HashSet<AppRole>();
		adminRoles.add(guestRole);
		adminRoles.add(teacherRole);
		adminRoles.add(studentRole);
		adminRoles.add(adminRole);

		Set<AppRole> teacherRoles = new HashSet<AppRole>();
		teacherRoles.add(guestRole);
		teacherRoles.add(teacherRole);
		
		Set<AppRole> staffRoles = new HashSet<AppRole>();
		staffRoles.add(guestRole);
		staffRoles.add(staffRole);

		Set<AppRole> studentRoles = new HashSet<AppRole>();
		studentRoles.add(guestRole);
		studentRoles.add(studentRole);

		Set<AppRole> guestRoles = new HashSet<AppRole>();
		guestRoles.add(guestRole);

		////////////////////////////////////////////////////////////////////////////

		Admin admin = new Admin();
		admin.setName("Admin");
		admin.setSurname("Hoppus");
		admin.setAvatar("https://i.pravatar.cc/300?img=30");
		admin.setEmail("admin@admin.com");
		admin.setPassword(encoder.encode("adminadmin"));
		admin.setRoles(adminRoles);
		usrRepo.save(admin);
		
		Teacher teacherTest = new Teacher();
		teacherTest.setName("Teacher");
		teacherTest.setSurname("Barker");
		teacherTest.setAvatar("https://i.pravatar.cc/300?img=31");
		teacherTest.setEmail("teacher@teacher.com");
		teacherTest.setPassword(encoder.encode("teacherteacher"));
		teacherTest.setRoles(teacherRoles);
		usrRepo.save(teacherTest);
		
		Student studentTest = new Student();
		studentTest.setName("Student");
		studentTest.setSurname("Delonge");
		studentTest.setAvatar("https://i.pravatar.cc/300?img=32");
		studentTest.setEmail("student@student.com");
		studentTest.setPassword(encoder.encode("studentstudent"));
		studentTest.setRoles(studentRoles);
		usrRepo.save(studentTest);
		
		Guest guestTest = new Guest();
		guestTest.setName("Guest");
		guestTest.setSurname("Skiba");
		guestTest.setAvatar("https://i.pravatar.cc/300?img=33");
		guestTest.setEmail("guest@guest.com");
		guestTest.setPassword(encoder.encode("guestguest"));
		guestTest.setRoles(guestRoles);
		usrRepo.save(guestTest);

		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < 2; i++) {
			Teacher teacher = teacherPrv.getObject();
			teacher.setName(fkr.name().firstName());
			teacher.setSurname(fkr.name().lastName());
			teacher.setAvatar("https://i.pravatar.cc/300?img=2" + i);
			teacher.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			teacher.setEmail(fkr.internet().emailAddress());
			teacher.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			teacher.setRoles(staffRoles);
			teachers.add(teacher);
		}
		usrRepo.saveAll(teachers);

		List<Klass> klasses = new ArrayList<Klass>();
		for (int i = 0; i < 10; i++) {
			Klass klass = klassPrv.getObject();
			klass.setTeacher(teachers.get(i % 2));
			klasses.add(klass);
		}
		klsRepo.saveAll(klasses);

		List<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < 10; i++) {
			String description = fkr.lorem().paragraph(3);
			if (description.length() > 250)
				description = description.substring(0, 249);

			Course crs = crsPrv.getObject();
			crs.setName(fkr.lorem().sentence(1));
			crs.setDescription(description);
			crs.setImage("https://picsum.photos/id/4" + i + "/300");
			crs.setType((i % 2 == 0) ? ECourse.UNDERGRADUATE : ECourse.GRADUATE);
			courses.add(crs);
		}
		crsRepo.saveAll(courses);

		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 5; i++) {
			Student student = studentPrv.getObject();
			student.setName(fkr.name().firstName());
			student.setSurname(fkr.name().lastName());
			student.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			student.setAvatar("https://i.pravatar.cc/300?img=1" + i);
			student.setCourse(courses.get(0));
			student.setKlass(klasses.get(0));
			student.setEmail(fkr.internet().emailAddress());
			student.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			student.setRoles(studentRoles);
			students.add(student);
		}
		usrRepo.saveAll(students);
	}

}
