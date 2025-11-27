package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Student;

@Mapper
public interface StudentMapper {

	// 生徒ページマッピング

	// 生徒一覧
	List<Student> selectAll();

	// 生徒ID取得
	Student selectById(Integer id);

	// 生徒追加
	void insert(Student student);

	// 生徒編集
	void update(Student student);

	// 生徒削除(倫理削除)
	void delete(Integer id);

	// 同ログインIDが存在するか判定 名前で件数をカウント
	int CountByLoginId(String loginId);

	// 指定ID以外で同ログインIDが存在するか判定
	int countByLoginIdExcludingId(@Param("loginId") String loginId,
			@Param("id") Integer id);

	// データの全件数を取得
	Long count();

	// ページごとのデータを取得
	List<Student> selectLimitedStudents(@Param("offset") int offset,
			@Param("limit") int limit);

	// 生徒ログインID選択
	Student selectByLoginId(@Param("loginId") String loginId);
	
	// DBのStudentを1件取得、パスワードが一致するかチェック
	Student findByLoginId(@Param("loginId") String loginId);

}
