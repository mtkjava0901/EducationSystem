package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Material;
import com.example.app.service.MaterialService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MaterialController01 {
	
	private final MaterialService service;
	
	// /admin/material/list.htmlのマッピング
	// 教材一覧
	@GetMapping("/list-material")
	public String showList (Model model) {
		List<Material> materialList = service.getMaterialList();
		model.addAttribute("list", materialList);
		return "list-material";
	}
	
	// /show/{id}(教材ID)のマッピング
	// 教材詳細
	@GetMapping("/show-material")
	public String showDetail (@PathVariable Integer id, Model model) {
		model.addAttribute("material", service.getMaterialById(id));
		return "show-material";
	}
	
	// /addのgetマッピング 
	// 教材追加
	@GetMapping("/add-material")
	public String addGet(Model model) {
		model.addAttribute("title", "視聴覚教材の追加");
		model.addAttribute("material", new Material());
		return "add-material";
	}

	// /addのpostマッピング
	// 教材追加
	// @PostMapping("/add")
	// public String addPost {
	// 	return "add-material";
	// }
	
	// /editのgetマッピング
	// 教材編集
	@GetMapping("/edit-material")
	public String getEdit(
			@PathVariable Integer id, Model model) {
		model.addAttribute("title", "視聴覚教材の追加");
		model.addAttribute("material", service.getMaterialById(id));
		return "edit-material";
	}
	// /editのpostマッピング
	// 教材編集

	

}
