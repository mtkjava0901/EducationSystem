package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.RentalRecord;

@Mapper
public interface RentalRecordMapper {

	// 貸し出し履歴一覧
	List<RentalRecord> selectAll();

	// 貸し出し履歴1件取得
	RentalRecord selectById(Integer id);

	// 登録(借り出し)
	void insert(RentalRecord record);

	// 返却日時の更新
	void updateReturn(RentalRecord record);

	// 削除
	void delete(Integer id);

}
