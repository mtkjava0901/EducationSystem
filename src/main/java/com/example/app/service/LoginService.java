package com.example.app.service;

import org.springframework.validation.Errors;

import com.example.app.domain.Admin;
import com.example.app.domain.Student;

public interface LoginService {
	
	/*
	 * 管理者ログイン認証
	 * @param admin Adminオブジェクト
	 * @param errors バリデーションエラー格納用
	 * @return 認証成功ならtrue
	 */
	
	boolean authenticateAdmin(Admin admin, Errors erros);
	
	/*
	 * 生徒ログイン認証
	 * @param student Studentオブジェクト
	 * @param errors バリデーションエラー格納用
	 * @return 認証成功ならtrue
	 */
	
	boolean authenticateStudent(Student student, Errors erros);

}
