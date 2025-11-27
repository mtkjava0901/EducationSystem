package com.example.app.service;

import java.util.List;

import com.example.app.domain.RentalRecord;

public interface RentalRecordService {

	// 貸し出し履歴一覧
	List<RentalRecord> getAllRentalRecords();

	// 貸し出し履歴1件取得(ID検索)
	RentalRecord getRentalRecordById(Integer id);

	// 登録(借り出し)
	void borrowMaterial(RentalRecord record);

	// 返却日時の更新
	void returnMaterial(RentalRecord record);

	// 削除
	void deleteRentalRecord(Integer id);

}
