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
	
	// 指定ページのデータ取得
	List<RentalRecord> getRentalRecordsByPage(int page, int numPerPage);
	
	// 全件数を取得
	int getTotalCount();
	
	// 貸し出し中だけ取得
	List<RentalRecord> getBorrowingRecords();

	// 貸し出し中の教材を取得
	List<RentalRecord> getBorrowingRecordsByStudent(Integer studentId);
	
	//教材IDごとの最新貸し出し履歴を取得（上限件数指定）
	List<RentalRecord> getRecentRecordsByMaterial(Integer materialId, int limit);


}
