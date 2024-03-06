package com.capstone.schoolmanagement.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KlassBasicResponse {
	private Long id;
	private Course course;
	private String renderColor;

	public static KlassBasicResponse buildKlassBasicResponse(Klass klass) {
		return KlassBasicResponse.builder()
				.id(klass.getId())
				.course(klass.getCourse())
				.renderColor(klass.getRenderColor())
				.build();
	}
}
