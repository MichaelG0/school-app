package com.capstone.schoolmanagement.dto;

import java.util.List;
import com.capstone.schoolmanagement.model.ECourse;
import lombok.Data;

@Data
public class CourseInfoDto {
	private String name;
	private String description;
	private String image;
	private ECourse type;
	private List<Long> modulesIds;
}
