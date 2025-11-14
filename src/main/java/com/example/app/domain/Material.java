package com.example.app.domain;

import lombok.Data;

@Data
public class Material {

	// フィールド 教材カラム
	private Integer id; // id番号
	private String name; // 教材名
	private Integer materialType; // 教材の種類
	private Integer publisher; // 出版元
	private String note; // 備考欄
}
