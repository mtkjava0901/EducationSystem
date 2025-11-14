package com.example.app.service;

import java.util.List;

import com.example.app.domain.Material;

public interface MaterialService {
	
	// 教材一覧
	List<Material> getMaterialList();
	
	// 教材詳細
	Material getMaterialById(Integer id);
	
	// 教材追加
	void addMaterial (Material material);
	
	// 教材編集
	void editMaterial (Material material);

}
