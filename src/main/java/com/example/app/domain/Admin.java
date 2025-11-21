package com.example.app.domain;

import lombok.Data;

@Data
public class Admin {
	
	private Integer id; // 管理者ID
	
	private String name; // 管理者フルネーム
	
	private String loginId; // 管理者ログインID
	
	private String loginPass; // 管理者パスワード:pass(BCrypt使用)

}
