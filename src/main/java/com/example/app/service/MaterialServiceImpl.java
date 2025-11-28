package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Material;
import com.example.app.domain.MaterialType;
import com.example.app.mapper.MaterialMapper;
import com.example.app.mapper.MaterialTypeMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

	private final MaterialMapper mapper;
	private final MaterialTypeMapper typeMapper;

	// 教材一覧
	@Override
	public List<Material> getMaterialList() {
		return mapper.selectAll();
	}

	@Override
	public List<MaterialType> getMaterialTypeList() {
		return typeMapper.selectAll();
	}

	// 教材詳細
	@Override
	public Material getMaterialById(Integer id) {
		return mapper.selectById(id);
	}

	// 教材追加
	@Override
	public void addMaterial(Material material) {
		material.setBorrow(false); // 初期値を明示
		mapper.insert(material);
	}

	// 教材編集
	@Override
	public void editMaterial(Material material) {
		mapper.update(material);
	}

	// 教材削除
	@Override
	public void setChangeByStatus(Integer id) {
		mapper.delete(id);
	}

	// 同名が存在するか判定
	@Override
	public boolean existsByName(String name) {
		return mapper.countByName(name) > 0;
	}

	// 指定ID以外で同名が存在するか判定(編集時)
	@Override
	public boolean existsByNameExcludingId(String name, Integer id) {
		return mapper.countByNameExcludingId(name, id) > 0;
	}

	// ページごとのデータを取得
	@Override
	public List<Material> getMaterialListByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return mapper.selectLimitedMaterials(offset, numPerPage);
	}

	// 貸し出し可能教材（全件）
	@Override
	public List<Material> getAvailableMaterials() {
		return mapper.selectAvailableMaterials();
	}

	// 貸し出し可能教材（ページネーション対応）
	@Override
	public List<Material> getAvailableMaterialsByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return mapper.selectAvailableMaterialsByPage(offset, numPerPage);
	}

	@Override
	public int getAvailableTotalCount() {
		return mapper.countAvailable();
	}

	// データの全件数を取得
	@Override
	public int getTotalPages(int numPerPage) {
		double totalNum = (double) mapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

}
