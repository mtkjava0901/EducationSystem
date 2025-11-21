package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Admin {
	
	private Integer id; // 管理者主キー
	
	private String name; // 管理者フルネーム
	
	@NotBlank(message="{admin.loginId.NotBlank}")
	private String loginId; // 管理者ログインID
	
	@NotBlank(message="{admin.loginPass.NotBlank}")
	private String loginPass; // 管理者パスワード:pass(BCrypt使用)

}
