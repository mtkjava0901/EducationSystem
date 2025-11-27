package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.RentalRecord;
import com.example.app.mapper.RentalRecordMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalRecordServiceImpl implements RentalRecordService {

	private final RentalRecordMapper mapper;

	// 貸し出し履歴一覧
	@Override
	public List<RentalRecord> getAllRentalRecords() {
		return mapper.selectAll();
	}

	// 貸し出し履歴1件取得(ID検索)
	@Override
	public RentalRecord getRentalRecordById(Integer id) {
		return mapper.selectById(id);
	}

	// 登録(借り出し)
	@Override
	public void borrowMaterial(RentalRecord record) {
		mapper.insert(record);
	}

	// 返却日時の更新
	@Override
	public void returnMaterial(RentalRecord record) {
		mapper.updateReturn(record);
	}

	// 削除
	@Override
	public void deleteRentalRecord(Integer id) {
		mapper.delete(id);
	}

}
