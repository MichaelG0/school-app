package com.capstone.schoolmanagement.runners;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import com.capstone.schoolmanagement.model.Assignment;
import com.capstone.schoolmanagement.model.CompletedAssignment;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.ECourse;
import com.capstone.schoolmanagement.model.EWeekDay;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.model.TeacherModulesPerKlass;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.capstone.schoolmanagement.model.users.Admin;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Guest;
import com.capstone.schoolmanagement.model.users.Staff;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.repos.AssignmentRepo;
import com.capstone.schoolmanagement.repos.CompletedAssignmentRepo;
import com.capstone.schoolmanagement.repos.CourseInfoRepo;
import com.capstone.schoolmanagement.repos.CourseRepo;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.repos.MmoduleRepo;
import com.capstone.schoolmanagement.repos.RegisterRepo;
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
	private final AssignmentRepo assRepo;
	private final CompletedAssignmentRepo complAssRepo;
	private final RegisterRepo registerRepo;
	private final ObjectProvider<Guest> guestPrv;
	private final ObjectProvider<Student> studentPrv;
	private final ObjectProvider<Staff> staffPrv;
	private final ObjectProvider<Teacher> teacherPrv;
	private final ObjectProvider<Admin> adminPrv;
	private final ObjectProvider<Klass> klassPrv;
	private final ObjectProvider<Mmodule> modulePrv;
	private final ObjectProvider<CourseInfo> crsInfoPrv;
	private final ObjectProvider<Course> crsPrv;
	private final ObjectProvider<WeeklyScheduleItem> wsiPrv;
	private final ObjectProvider<Register> registerPrv;
	private final ObjectProvider<TeacherModulesPerKlass> teacherMPKPrv;
	private final ObjectProvider<Assignment> assPrv;
	private final ObjectProvider<CompletedAssignment> complAssPrv;
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
			String[] colors = { "aqua", "coral", "crimson", "cadetblue", "chartreuse", "cornflowerblue", "chocolate",
					"darkcyan", "darkgoldenrod", "yellow", "mediumpurple", "darkorange", "deepskyblue", "forestgreen",
					"gold", "hotpink", "lightslategray", "mediumaquamarine", "olivedrab", "royalblue" };
			Mmodule module = modulePrv.getObject();
			module.setName(subjects[i]);
			module.setRenderColor(colors[i]);
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
				mdls.add(modules.get(fkr.random().nextInt(20)));
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

		Teacher teacherTest = teacherPrv.getObject();
		teacherTest.setName("Teacher");
		teacherTest.setSurname("Barker");
		teacherTest.setGender(EGender.FEMALE);
		teacherTest.setAvatar("https://i.pravatar.cc/300?img=31");
		teacherTest.setAddress(fkr.address().fullAddress());
		teacherTest.setPhone(fkr.phoneNumber().cellPhone());
		teacherTest.setBio(fkr.lorem().paragraph(5));
		teacherTest.setEmail("teacher@teacher.com");
		teacherTest.setPassword(encoder.encode("teacherteacher"));
		teacherTest.setRoles(teacherRoles);
		List<Mmodule> mdlsForTcrTest = modules.stream()
				.filter(mdl -> mdl.getId() == 2 || mdl.getId() == 3 || mdl.getId() == 11 || mdl.getId() == 14
						|| mdl.getId() == 15 || mdl.getId() == 17 || mdl.getId() == 18)
				.toList();
		teacherTest.setModules(new HashSet<Mmodule>(mdlsForTcrTest));

		List<Teacher> teachers = new ArrayList<Teacher>();
		teachers.add(teacherTest);
		for (int i = 0; i < 19; i++) {
			Set<Mmodule> mdls = new HashSet<Mmodule>();
			mdls.add(modules.get(i));
			mdls.add(modules.get(i + 1));
			for (int j = -1; j < fkr.random().nextInt(19); j++)
				mdls.add(modules.get(fkr.random().nextInt(20)));

			Teacher teacher = teacherPrv.getObject();
			teacher.setName(fkr.name().firstName());
			teacher.setSurname(fkr.name().lastName());
			teacher.setAvatar("https://i.pravatar.cc/300?img=" + fkr.random().nextInt(70));
			teacher.setAddress(fkr.address().fullAddress());
			teacher.setPhone(fkr.phoneNumber().cellPhone());
			teacher.setBio(fkr.lorem().paragraph(5));
			teacher.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			teacher.setEmail(teacher.getName().toLowerCase() + "." + teacher.getSurname().toLowerCase() + "@marsu.com");
			teacher.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			teacher.setModules(mdls);
			teacher.setRoles(staffRoles);
			teachers.add(teacher);
		}
		usrRepo.saveAll(teachers);

		List<Klass> klasses = new ArrayList<Klass>();
		List<WeeklyScheduleItem> weeklySchedule = new ArrayList<WeeklyScheduleItem>();
		EWeekDay[] weekDays = EWeekDay.values().clone();
		for (int i = 0; i < 10; i++) {
			int courseNumm = i == 0 ? i : fkr.random().nextInt(20);

			Klass klass = klassPrv.getObject();
			klass.setCourse(courses.get(courseNumm));
			klasses.add(klass);

			Set<Mmodule> moduleSet = klass.getCourse().getInfo().getModules();
			List<Mmodule> moduleArr = moduleSet.stream().toList();

			List<TeacherModulesPerKlass> tcrMPKList = new ArrayList<TeacherModulesPerKlass>();
			for (int j = 0; j < moduleArr.size(); j++) {
				final int index = j;
				Mmodule taughtModule = moduleArr.get(index);
				Teacher chosenTcr;

				if (i == 0 && (j == 0 || j == 1) || i == 2 && j == 0 || i == 7 && j == 0) {
					teacherTest.getModules().add(taughtModule);
					chosenTcr = teacherTest;
				} else {
					List<Teacher> filteredTeachersList = teachers.stream()
							.filter(tcr -> tcr.getModules().contains(taughtModule))
							.toList();
					// List is immutable, so the remove method used later wouldn't work.
					// Thus I need to work with an ArrayList
					ArrayList<Teacher> filteredTeachers = new ArrayList<>(filteredTeachersList);
					if (i > 4)
						filteredTeachers.remove(teacherTest);
					chosenTcr = filteredTeachers.get(fkr.random().nextInt(filteredTeachers.size()));
				}

				Optional<TeacherModulesPerKlass> tcrMPKOpt = tcrMPKList.stream()
						.filter(tcrMPKItem -> tcrMPKItem.getTeacher().equals(chosenTcr))
						.findFirst();
				if (tcrMPKOpt.isPresent()) {
					TeacherModulesPerKlass tcrMPK = tcrMPKOpt.get();
					tcrMPK.getModules().add(taughtModule);
				} else {
					TeacherModulesPerKlass tcrMPK = teacherMPKPrv.getObject();
					tcrMPK.setKlass(klass);
					tcrMPK.setTeacher(chosenTcr);
					Set<Mmodule> taughtMdls = new HashSet<Mmodule>();
					taughtMdls.add(taughtModule);
					tcrMPK.setModules(taughtMdls);
					tcrMPKList.add(tcrMPK);
				}
			}
			klass.setTeachers(tcrMPKList);

			for (int j = 0; j < 10; j++) {
				WeeklyScheduleItem wsi = wsiPrv.getObject();
				wsi.setWeekDay(weekDays[j / 2]);
				int startTimeHour = 9 + 5 * (j % 2);
				wsi.setStartTime(LocalTime.of(startTimeHour, 0));
				wsi.setEndTime(LocalTime.of(startTimeHour + (fkr.random().nextInt(3) + 1), 0));
				wsi.setKlass(klass);
				int moduleIndex = j < moduleArr.size() ? j : fkr.random().nextInt(moduleArr.size());
				wsi.setModule(moduleArr.get(moduleIndex));
				for (TeacherModulesPerKlass tcrMPKItem : klass.getTeachers()) {
					for (Mmodule mdlItem : tcrMPKItem.getModules()) {
						if (mdlItem.equals(wsi.getModule())) {
							Teacher tcr = tcrMPKItem.getTeacher();
							while (!wsi.setTeacher(tcr) || wsi.getStartTime().getHour() == 13) {
								wsi.setEndTime(wsi.getStartTime().plusHours(2));
								wsi.setStartTime(wsi.getStartTime().plusHours(1));
							}
							tcr.getWeeklySchedule().add(wsi);
							break;
						}
					}
				}
				weeklySchedule.add(wsi);
			}
		}
		klsRepo.saveAll(klasses);
		wsiRepo.saveAll(weeklySchedule);

		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 100; i++) {
			int klassNum = fkr.random().nextInt(10);

			Student student = studentPrv.getObject();
			student.setName(fkr.name().firstName());
			student.setSurname(fkr.name().lastName());
			student.setGender((i % 2 == 0) ? EGender.MALE : EGender.FEMALE);
			student.setAvatar("https://i.pravatar.cc/300?img=" + fkr.random().nextInt(70));
			student.setAddress(fkr.address().fullAddress());
			student.setPhone(fkr.phoneNumber().cellPhone());
			student.setBio(fkr.lorem().paragraph(5));
			student.setCourse(klasses.get(klassNum).getCourse());
			student.setKlass(klasses.get(klassNum));
			student.setEmail(student.getName().toLowerCase() + "." + student.getSurname().toLowerCase() + "@marsu.com");
			student.setPassword(encoder.encode(fkr.internet().password(8, 10)));
			student.setRoles(studentRoles);
			students.add(student);
		}
		usrRepo.saveAll(students);

		////////////////////////////////////////////////////////////////////////////

		Klass klass = klasses.get(0);

		Admin admin = adminPrv.getObject();
		admin.setName("Admin");
		admin.setSurname("Hoppus");
		admin.setGender(EGender.MALE);
		admin.setAvatar("https://i.pravatar.cc/300?img=30");
		admin.setAddress(fkr.address().fullAddress());
		admin.setPhone(fkr.phoneNumber().cellPhone());
		admin.setBio(fkr.lorem().paragraph(5));
		admin.setEmail("admin@admin.com");
		admin.setPassword(encoder.encode("adminadmin"));
		admin.setRoles(adminRoles);
		usrRepo.save(admin);

		Student studentTest = studentPrv.getObject();
		studentTest.setName("Student");
		studentTest.setSurname("DeLonge");
		studentTest.setGender(EGender.MALE);
		studentTest.setAvatar("https://i.pravatar.cc/300?img=32");
		studentTest.setAddress(fkr.address().fullAddress());
		studentTest.setPhone(fkr.phoneNumber().cellPhone());
		studentTest.setBio(fkr.lorem().paragraph(5));
		studentTest.setEmail("student@student.com");
		studentTest.setPassword(encoder.encode("studentstudent"));
		studentTest.setRoles(studentRoles);
		studentTest.setCourse(klass.getCourse());
		studentTest.setKlass(klass);
		usrRepo.save(studentTest);

		Staff staffTest = staffPrv.getObject();
		staffTest.setName("Staff");
		staffTest.setSurname("Raynor");
		staffTest.setGender(EGender.FEMALE);
		staffTest.setAvatar("https://i.pravatar.cc/300?img=33");
		staffTest.setAddress(fkr.address().fullAddress());
		staffTest.setPhone(fkr.phoneNumber().cellPhone());
		staffTest.setBio(fkr.lorem().paragraph(5));
		staffTest.setEmail("staff@staff.com");
		staffTest.setPassword(encoder.encode("staffstaff"));
		staffTest.setRoles(staffRoles);
		usrRepo.save(staffTest);

		Guest guestTest = guestPrv.getObject();
		guestTest.setName("Guest");
		guestTest.setSurname("Skiba");
		guestTest.setAvatar("https://i.pravatar.cc/300?img=34");
		guestTest.setEmail("guest@guest.com");
		guestTest.setPassword(encoder.encode("guestguest"));
		guestTest.setRoles(guestRoles);
		usrRepo.save(guestTest);

		////////////////////////////////////////////////////////////////////////////

		List<Register> registers = new ArrayList<Register>();
		for (int i = 0; i < 20; i++) {
			List<Mmodule> mdls = klass.getCourse().getInfo().getModules().stream().toList();

			Register register = registerPrv.getObject();
			register.setDate(
					fkr.date().past(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			register.setModule(modules.get(fkr.random().nextInt(mdls.size())));
			register.setKlass(klass);
			if (i == 0)
				register.setAbsent(new HashSet<Student>(Arrays.asList(studentTest)));
			registers.add(register);
		}
		registerRepo.saveAll(registers);

		List<Assignment> assignments = new ArrayList<Assignment>();
		List<TeacherModulesPerKlass> tcrMPKs = klass.getTeachers().stream().toList();
		int tcrMPKsSize = tcrMPKs.size();
		for (int i = 0; i < 100; i++) {
			TeacherModulesPerKlass tcrMPK = tcrMPKs.get(fkr.random().nextInt(tcrMPKsSize));
			List<Mmodule> mdls = tcrMPK.getModules().stream().toList();

			Assignment ass = assPrv.getObject();
			ass.setIssueDate(
					fkr.date().past(15, 6, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			ass.setDueDate(i % 3 == 0
					? fkr.date().past(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					: fkr.date().future(15, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			ass.setTitle(fkr.lorem().sentence(2, 2).replace('.', ' '));
			ass.setCaption(fkr.lorem().paragraph(10));
			ass.setKlass(klass);
			ass.setTeacher(tcrMPK.getTeacher());
			ass.setModule(mdls.get(fkr.random().nextInt(mdls.size())));
			assignments.add(ass);
		}
		assRepo.saveAll(assignments);

		List<CompletedAssignment> complAsss = new ArrayList<CompletedAssignment>();
		List<Student> klass1Stds = students.stream().filter(std -> std.getKlass().equals(klass)).toList();
		int klass1StdsSize = klass1Stds.size();
		int assignmentsSize = assignments.size();
		for (int i = 0; i < 100; i++) {
			CompletedAssignment ass = complAssPrv.getObject();
			ass.setSubmittedDate(
					fkr.date().past(15, 6, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			ass.setStudent(i % 3 == 0 ? studentTest : klass1Stds.get(fkr.random().nextInt(klass1StdsSize)));
			ass.setLink(fkr.internet().url());
			if (i % 2 == 0)
				ass.setGrade(fkr.random().nextInt(5) + 6);
			ass.setAssignment(assignments.get(fkr.random().nextInt(assignmentsSize)));
			complAsss.add(ass);
		}
		complAssRepo.saveAll(complAsss);
	}

}
