package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Material;
import com.example.app.mapper.MaterialMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
	
	private final MaterialMapper mapper;

	// 教材一覧
	@Override
	public List<Material> getMaterialList() {
		return mapper.selectAll();
	}

	// 教材詳細
	@Override
	public Material getMaterialById(Integer id) {
		return mapper.selectById(id);
	}

	// 教材追加
	@Override
	public void addMaterial(Material material) {
		mapper.insert(material);
	}

	// 教材編集
	@Override
	public void editMaterial(Material material) {
		mapper.update(material);
	}

}
