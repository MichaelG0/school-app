package com.capstone.schoolmanagement.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.RandomStringUtils;

import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Klass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Course course;
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass", cascade = CascadeType.ALL)
	private List<TeacherModulesPerKlass> teachers = new ArrayList<TeacherModulesPerKlass>();
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
	private Set<Student> students = new HashSet<Student>();
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
	private Set<WeeklyScheduleItem> weeklySchedule = new HashSet<WeeklyScheduleItem>();
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
	private Set<Register> registers = new HashSet<Register>();
	private String renderColor;

	public Klass() {
		this.renderColor = generateRandomColor();
	}

	private String generateRandomColor() {
		// Define ranges for each RGB component to avoid bright colors
		int minRGBValue = 50; // Minimum value for R, G, and B components
		int maxRGBValue = 200; // Maximum value for R, G, and B components

		// Generate random values for each RGB component within the defined range
		int red = (int) (minRGBValue + Math.random() * (maxRGBValue - minRGBValue + 1));
		int green = (int) (minRGBValue + Math.random() * (maxRGBValue - minRGBValue + 1));
		int blue = (int) (minRGBValue + Math.random() * (maxRGBValue - minRGBValue + 1));

		// Convert RGB values to hexadecimal format and concatenate them
		String hexRed = Integer.toHexString(red);
		String hexGreen = Integer.toHexString(green);
		String hexBlue = Integer.toHexString(blue);

		// Ensure that each component has two digits in hexadecimal representation
		hexRed = hexRed.length() == 1 ? "0" + hexRed : hexRed;
		hexGreen = hexGreen.length() == 1 ? "0" + hexGreen : hexGreen;
		hexBlue = hexBlue.length() == 1 ? "0" + hexBlue : hexBlue;

		// Concatenate the hexadecimal values to form the color code
		return "#" + hexRed + hexGreen + hexBlue;
	}
}
