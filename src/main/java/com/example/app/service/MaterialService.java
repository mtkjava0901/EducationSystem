package com.example.app.service;

import java.util.List;

import com.example.app.domain.Material;
import com.example.app.domain.MaterialType;

public interface MaterialService {

	// 教材一覧
	List<Material> getMaterialList();

	// 教材種別一覧
	List<MaterialType> getMaterialTypeList();

	// 教材詳細
	Material getMaterialById(Integer id);

	// 教材追加
	void addMaterial(Material material);

	// 教材編集
	void editMaterial(Material material);

	// 教材倫理削除
	void setChangeByStatus(Integer id);

	// 同名が存在するか判定
	boolean existsByName(String name);

	// 指定ID以外で同名が存在するか判定(編集時)
	boolean existsByNameExcludingId(String name, Integer id);

}
