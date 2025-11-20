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
	void insert (Student student);
	
	// 生徒編集
	void update (Student student);
	
	// データの全件数を取得
	Long count();
	
	// ページごとのデータを取得
	List<Student> selectLimitedStudents
	(@Param("offset") int offset,
			@Param("limit") int limit);

}
