package com.example.app.domain;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class Student {

	private Integer id; //id,主キー
	
	@NotBlank(message="{student.name.NotBlank}")
	@Size(max = 30, message="{student.name.Size}")
	private String name; // 生徒名

	@NotNull(message="{student.birthday.NotNull}")
	private LocalDate birthday; // 誕生日

	@NotBlank(message="{student.loginId.NotBlank}")
	@Size(max = 30, message="{student.loginId.Size}")
	// +ユニークな名か？(Validation.properties)
	private String loginId; // ログインID(重複不可)
	
	@NotBlank(message="{student.loginPass.NotBlank}")
	private String loginPass; // パスワード:pass(BCrypt使用)
	
	private String status; // 状態('ACT'or'DEL')

}
