package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Material;

@Mapper
public interface MaterialMapper {
	
	// 教材マッピング
	
	// 教材一覧
	List<Material> selectAll();
	
	// 教材詳細
	Material selectById(Integer id);
	
	// 教材追加
	void insert (Material material);
	
	// 教材編集
	void update (Material material);

}
