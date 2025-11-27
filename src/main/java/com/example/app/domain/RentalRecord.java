package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalRecord {
		
	private Integer id; //貸し出し記録のID
	
	private Student student ; // 教材を借りた生徒のID //studentId(オブジェクト参照)
	private Material material ; // 貸し出しをする教材のID //materialId(オブジェクト参照)
	
	private LocalDateTime borrowedAt; // 教材が貸し出された日時
	private LocalDateTime returnedAt; // 教材が返却された日時

}
