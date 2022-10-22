package com.capstone.schoolmanagement.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.CourseDto;
import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.ECourse;
import com.capstone.schoolmanagement.repos.CourseRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	private final CourseRepo crsRepo;

	public CourseInfo create(CourseDto crsDto) {
		CourseInfo crs = new CourseInfo();
		BeanUtils.copyProperties(crsDto, crs);
		return crsRepo.save(crs);
	}

	public List<CourseInfo> getAll() {
		return (List<CourseInfo>) crsRepo.findAll();
	}

	public List<CourseInfo> getByType(String type) {
		return crsRepo.findByType(ECourse.valueOf(type)).orElseThrow(() -> new EntityNotFoundException("No courses of type " + type));
	}
	
	public CourseInfo getById(Long id) {
		return crsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
	}

	public CourseInfo getByName(String name) {
		return crsRepo.findByName(name).orElseThrow(() -> new EntityNotFoundException("Course not found"));
	}
	
	public CourseInfo update(Long id, CourseDto crsDto) {
		CourseInfo crs = getById(id);
		BeanUtils.copyProperties(crsDto, crs);
		return crsRepo.save(crs);
	}

	public void delete(Long id) {
		if (!crsRepo.existsById(id))
			throw new EntityNotFoundException("Course not found");
		crsRepo.deleteById(id);
	}

}
