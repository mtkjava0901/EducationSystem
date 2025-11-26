package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Student;
import com.example.app.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentMapper mapper;

	// 生徒一覧
	@Override
	public List<Student> getStudentList() {
		return mapper.selectAll();
	}

	// 生徒ID取得
	@Override
	public Student getStudentById(Integer id) {
		return mapper.selectById(id);
	}

	// 生徒追加
	@Override
	public void addStudent(Student student) {
		mapper.insert(student);
	}

	// 生徒編集
	@Override
	public void editStudent(Student student) {
		mapper.update(student);
	}

	// 生徒倫理削除
	@Override
	public void deleteStudent(Integer id) {
		mapper.delete(id);
	}

	// データの全件数を取得
	@Override
	public int getTotalPages(int numPerPage) {
		double totalNum = (double) mapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	// ページごとのデータを取得
	@Override
	public List<Student> getStudentListByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return mapper.selectLimitedStudents(offset, numPerPage);
	}

	// 同ログインIDが存在するか判定
	@Override
	public boolean existsByLoginId(String loginId) {
		return mapper.CountByLoginId(loginId) > 0;
	}

	// 指定ID以外で同ログインIDが存在するか判定(編集時)
	@Override
	public boolean existsByLoginIdExcludingId(String loginId, Integer id) {
		return mapper.countByLoginIdExcludingId(loginId, id) > 0;
	}

	// パスワード認証
	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) {
		// 
		Student student = mapper.selectByLoginId(loginId);
		// 
		if (student == null) return false;
		return BCrypt.checkpw(loginPass, student.getLoginPass());
	}

	// BCrypt.checkpw
	@Override
	public String getPasswordHashByLoginId(String loginId) {
		Student student = mapper.selectByLoginId(loginId);
		return (student != null) ? student.getLoginPass() : null;
	}

}
