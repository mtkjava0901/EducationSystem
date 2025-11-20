package com.example.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Student {

	private Integer id; //id,主キー
	private String name; // 生徒名
	private LocalDate birthday; // 誕生日
	private String loginId; // ログインID(重複不可)
	private String loginPass; // パスワード(BCrypt使用)
	private String status; // 状態('ACT'or'DEL')

}
