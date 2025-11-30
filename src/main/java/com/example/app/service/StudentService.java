package com.example.app.service;

import java.util.List;

import com.example.app.domain.Student;

public interface StudentService {

	// 生徒一覧
	List<Student> getStudentList();

	// 生徒ID取得
	Student getStudentById(Integer id);

	// データの全件数を取得
	int getTotalPages(int numPerPage);

	// ページごとのデータを取得
	List<Student> getStudentListByPage(int page, int numPerPage);

	// 生徒追加
	void addStudent(Student student);

	// 生徒編集
	void editStudent(Student student);

	// 生徒倫理削除
	void deleteStudent(Integer id);

	// 同ログインIDが存在するか判定
	boolean existsByLoginId(String loginId);

	// 指定ID以外で同ログインIDが存在するか判定
	boolean existsByLoginIdExcludingId(String loginId, Integer id);

	// パスワード認証
	public boolean isCorrectIdAndPassword(String loginId, String loginPass);

	// BCrypt.checkpw
	String getPasswordHashByLoginId(String loginId);

	// DBのStudentを1件取得、パスワードが一致するかチェック
	Student findByLoginId(String loginId);

}
