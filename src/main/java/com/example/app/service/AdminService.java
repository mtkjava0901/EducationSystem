package com.example.app.service;

public interface AdminService {

	// パスワード認証
	boolean isCorrectIdAndPassword(String loginId, String loginPass);

	// BCrypt.checkpw
	String getPasswordHashByLoginId(String loginId);

}
