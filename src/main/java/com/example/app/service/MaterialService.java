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

	// ページごとの教材取得（管理画面用）
	List<Material> getMaterialListByPage(int page, int numPerPage);

	// 全件取得（ページネーションなし）
	List<Material> getAvailableMaterials();

	// ページごとの貸し出し可能な教材を取得
	List<Material> getAvailableMaterialsByPage(int page, int numPerPage);

	// 貸し出し可能教材の総件数(ページネーション用)
	int getAvailableTotalCount();

	// データの全件数を取得
	int getTotalPages(int numPerPage);
	
	// borrowフラグ更新
	void updateBorrowStatus(Integer materialId, boolean borrow);
}
