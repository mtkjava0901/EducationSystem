package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class StudentLoginForm {
	
	@NotBlank(message="{student.loginId.NotBlank}")
	@Size(max = 30, message="{student.loginId.Size}")
	private String loginId;
	
	@NotBlank(message="{student.loginPass.NotBlank}")
	private String loginPass;

}
