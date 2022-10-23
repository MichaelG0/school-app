package com.capstone.schoolmanagement.runners;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.ECourse;
import com.capstone.schoolmanagement.model.EWeekDay;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.capstone.schoolmanagement.model.users.Admin;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Guest;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.repos.CourseInfoRepo;
import com.capstone.schoolmanagement.repos.CourseRepo;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.repos.MmoduleRepo;
import com.capstone.schoolmanagement.repos.WeeklyScheduleItemRepo;
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
	private final MmoduleRepo moduleRepo;
	private final CourseInfoRepo crsInfoRepo;
	private final CourseRepo crsRepo;
	private final WeeklyScheduleItemRepo wsiRepo;
	private final ObjectProvider<Teacher> teacherPrv;
	private final ObjectProvider<Student> studentPrv;
	private final ObjectProvider<Klass> klassPrv;
	private final ObjectProvider<Mmodule> modulePrv;
	private final ObjectProvider<CourseInfo> crsInfoPrv;
	private final ObjectProvider<Course> crsPrv;
	private final ObjectProvider<WeeklyScheduleItem> wsiPrv;
	private final ObjectProvider<Faker> fkrPrv;
	private final PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
		log.info("===== FIRST RUNNER =====");
		Faker fkr = fkrPrv.getObject();

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

		List<Mmodule> modules = new ArrayList<Mmodule>();
		for (int i = 0; i < 20; i++) {
			String[] subjects = { "Mathematics", "Science", "Health", "Art", "Music", "Literature", "Composition",
					"Algebra", "Geography", "Sociology", "Medicine", "Geology", "Physics", "Anthropology", "Genealogy",
					"Philosophy", "Critical Thinking", "Psychology", "Economics", "Rhetoric" };
			Mmodule module = modulePrv.getObject();
			module.setName(subjects[i]);
			modules.add(module);
		}
		moduleRepo.saveAll(modules);

		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		for (int i = 0; i < 10; i++) {
			String description = fkr.lorem().paragraph(3);
			if (description.length() > 250)
				description = description.substring(0, 249);

			Set<Mmodule> mdls = new HashSet<Mmodule>();
			for (int j = 0; j < 5; j++) {
				mdls.add(modules.get(fkr.random().nextInt(19) + 1));
			}
			String name = fkr.educator().course();

			CourseInfo crs = crsInfoPrv.getObject();
			crs.setName(name);
			crs.setDescription(description);
			crs.setImage("https://picsum.photos/id/4" + i + "/300");
			crs.setType(name.substring(0, 6).equals("Master") ? ECourse.GRADUATE : ECourse.UNDERGRADUATE);
			crs.setModules(mdls);
			courseInfos.add(crs);
		}
		crsInfoRepo.saveAll(courseInfos);

		List<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < 20; i++) {
			LocalDate startDate;
			LocalDate endDate;
			if (i < 7) {
				startDate = fkr.date()
						.past(548, TimeUnit.DAYS)
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();

				endDate = fkr.date()
						.future(548, TimeUnit.DAYS)
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
			} else {
				startDate = fkr.date()
						.future(365, TimeUnit.DAYS)
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();

				endDate = fkr.date()
						.future(1461, TimeUnit.DAYS)
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
			}

			Course crs = crsPrv.getObject();
			crs.setStartDate(startDate);
			crs.setEndDate(endDate);
			crs.setInfo(courseInfos.get(fkr.random().nextInt(10)));
			courses.add(crs);
		}
		crsRepo.saveAll(courses);

		List<Klass> klasses = new ArrayList<Klass>();
		List<WeeklyScheduleItem> weeklySchedule = new ArrayList<WeeklyScheduleItem>();
		EWeekDay[] weekDays = EWeekDay.values().clone();
		for (int i = 0; i < 10; i++) {
			int courseNumm = i == 0 ? i : fkr.random().nextInt(20);
			
			Klass klass = klassPrv.getObject();
			klass.setCourse(courses.get(courseNumm));
			klasses.add(klass);

			for (int j = 0; j < 10; j++) {
				Set<Mmodule> moduleSet = klasses.get(i).getCourse().getInfo().getModules();
				List<Mmodule> moduleArr = moduleSet.stream().toList();
				int modulesLength = moduleArr.size();

				WeeklyScheduleItem wsi = wsiPrv.getObject();
				wsi.setWeekDay(weekDays[j / 2]);
				wsi.setStartTime(LocalTime.of(9 + 5 * (j % 2), 0));
				wsi.setEndTime(LocalTime.of(13 + 5 * (j % 2), 0));
				wsi.setKlass(klasses.get(i));
				wsi.setModule(moduleArr.get(fkr.random().nextInt(modulesLength)));
				weeklySchedule.add(wsi);
			}
		}
		klsRepo.saveAll(klasses);
		wsiRepo.saveAll(weeklySchedule);

		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < 10; i++) {
			Set<Klass> klss = new HashSet<Klass>();
			for (int j = 0; j < 5; j++) {
				klss.add(klasses.get(fkr.random().nextInt(10)));
			}

			Teacher teacher = teacherPrv.getObject();
			teacher.setName(fkr.name().firstName());
			teacher.setSurname(fkr.name().lastName());
			teacher.setAvatar("https://i.pravatar.cc/300?img=" + fkr.random().nextInt(70));
			teacher.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			teacher.setEmail(fkr.internet().emailAddress());
			teacher.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			teacher.setRoles(staffRoles);
			teacher.setClasses(klss);
			teachers.add(teacher);
		}
		usrRepo.saveAll(teachers);

		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 50; i++) {
			int klassNum = fkr.random().nextInt(10);

			Student student = studentPrv.getObject();
			student.setName(fkr.name().firstName());
			student.setSurname(fkr.name().lastName());
			student.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			student.setAvatar("https://i.pravatar.cc/300?img=" + fkr.random().nextInt(70));
			student.setCourse(klasses.get(klassNum).getCourse());
			student.setKlass(klasses.get(klassNum));
			student.setEmail(fkr.internet().emailAddress());
			student.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			student.setRoles(studentRoles);
			students.add(student);
		}
		usrRepo.saveAll(students);

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
		Set<Klass> testKlss = new HashSet<Klass>();
		for (int j = 0; j < 5; j++)
			testKlss.add(klasses.get(fkr.random().nextInt(10)));
		teacherTest.setClasses(testKlss);
		usrRepo.save(teacherTest);

		Student studentTest = new Student();
		studentTest.setName("Student");
		studentTest.setSurname("Delonge");
		studentTest.setAvatar("https://i.pravatar.cc/300?img=32");
		studentTest.setEmail("student@student.com");
		studentTest.setPassword(encoder.encode("studentstudent"));
		studentTest.setRoles(studentRoles);
		studentTest.setCourse(klasses.get(0).getCourse());
		studentTest.setKlass(klasses.get(0));
		usrRepo.save(studentTest);

		Guest guestTest = new Guest();
		guestTest.setName("Guest");
		guestTest.setSurname("Skiba");
		guestTest.setAvatar("https://i.pravatar.cc/300?img=33");
		guestTest.setEmail("guest@guest.com");
		guestTest.setPassword(encoder.encode("guestguest"));
		guestTest.setRoles(guestRoles);
		usrRepo.save(guestTest);
	}

}
