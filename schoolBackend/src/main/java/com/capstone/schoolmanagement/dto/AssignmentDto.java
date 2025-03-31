package com.capstone.schoolmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentDto {
  @NotBlank
  private String dueDate;
  @NotBlank
  private String title;
  @NotBlank
  private String caption;
  @NotNull
  private Long klassId;
  @NotNull
  private Long teacherId;
  @NotBlank
  private String moduleName;
}
