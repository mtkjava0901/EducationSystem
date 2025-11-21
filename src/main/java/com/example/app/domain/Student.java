package com.example.app.domain;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class Student {

	private Integer id; //id,主キー
	
	@NotBlank
	@Size(max = 30)
	private String name; // 生徒名

	@NotBlank
	private LocalDate birthday; // 誕生日

	@NotBlank
	@Size(max = 30)
	// ユニークな教材名か？(Validation.properties)
	private String loginId; // ログインID(重複不可)
	
	@NotBlank
	private String loginPass; // パスワード(BCrypt使用)
	
	private String status; // 状態('ACT'or'DEL')

}
