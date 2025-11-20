package com.example.app.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class Material {

	// フィールド 教材カラム
	private Integer id; // id番号
	
	@NotBlank
	@Size(max = 30)
	private String name; // 教材名
	
	@Size(max = 30)
	private String publisher; // 出版元
	
	@Size(max = 100)
	private String note; // 備考欄
	
	private MaterialType materialType; // 教材の種類
	private LocalDateTime created; // 登録日
	private String status; // アクティブ
}
