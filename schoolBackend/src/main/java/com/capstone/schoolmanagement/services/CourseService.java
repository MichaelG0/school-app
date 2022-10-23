package com.capstone.schoolmanagement.services;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.CourseDto;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.repos.CourseRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	private final CourseRepo crsRepo;
	private final CourseInfoService infoSrv;

	public Course create(CourseDto crsDto) {
		Course crs = new Course();
		crs.setStartDate(LocalDate.parse(crsDto.getStartDate()));
		crs.setEndDate(LocalDate.parse(crsDto.getEndDate()));
		crs.setInfo(infoSrv.getById(crsDto.getCourseInfoId()));
		return crsRepo.save(crs);
	}

	public List<Course> getAll() {
		return (List<Course>) crsRepo.findAll();
	}

	public List<Course> getAllUpcoming() {
		List<Course> allCourses = (List<Course>) crsRepo.findAll();
		return allCourses.stream()
				.filter(course -> course.getStartDate().isAfter(LocalDate.now().plusDays(30)))
				.toList();
	}

	public Course getById(Long id) {
		return crsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
	}

	public Course update(Long id, CourseDto crsDto) {
		Course crs = getById(id);
		crs.setStartDate(LocalDate.parse(crsDto.getStartDate()));
		crs.setEndDate(LocalDate.parse(crsDto.getEndDate()));
		crs.setInfo(infoSrv.getById(crsDto.getCourseInfoId()));
		return crsRepo.save(crs);
	}

	public void delete(Long id) {
		if (!crsRepo.existsById(id))
			throw new EntityNotFoundException("Course not found");
		crsRepo.deleteById(id);
	}

}
