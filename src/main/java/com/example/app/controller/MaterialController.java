package com.example.app.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Material;
import com.example.app.service.MaterialService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/material")
public class MaterialController {

	private final MaterialService service;

	// 教材一覧(post無し)
	@GetMapping("/list")
	public String showList(Model model) {
		List<Material> materialList = service.getMaterialList();
		model.addAttribute("list", materialList);
		return "admin/material/list";
	}

	// 教材詳細(post無し)
	@GetMapping("/show/{id}")
	public String showDetail(
			@PathVariable Integer id, Model model) {
		model.addAttribute("material", service.getMaterialById(id));
		return "admin/material/show";
	}

	// 教材追加(post有り)
	@GetMapping("/add")
	public String addGetMaterial(Model model) {
		model.addAttribute("title", "視聴覚教材の追加");
		model.addAttribute("material", new Material());
		model.addAttribute("type", service.getMaterialTypeList());
		return "admin/material/add";
	}

	@PostMapping("/add")
	public String addPostMaterial(
			@Valid Material material,
			Errors errors,
			RedirectAttributes rd,
			Model model) {
		// Materialのnameが空白でなければ・DBなどに同名が既に存在するか
		if (!material.getName().isBlank()) {
			if (service.existsByName(material.getName())) {
				// 既にあればerrorsにnameフィールドエラーを追加
				errors.rejectValue("name", "error.name.notunique");
			}
		}
		if (errors.hasErrors()) {
			model.addAttribute("title", "視聴覚教材の追加");
			model.addAttribute("type", service.getMaterialTypeList());
			return "admin/material/add";
		}

		service.addMaterial(material);
		rd.addFlashAttribute("statusMessage", "教材を追加しました。");
		return "redirect:/admin/material/list";
	}

	// 教材編集(post有り)
	@GetMapping("/edit/{id}")
	public String editGetMaterial(
			@PathVariable Integer id, Model model) {
		model.addAttribute("title", "視聴覚教材の編集");
		model.addAttribute("material", service.getMaterialById(id));
		model.addAttribute("type", service.getMaterialTypeList());
		return "admin/material/edit";
	}

	@PostMapping("/edit/{id}")
	public String editPostMaterial(
			@PathVariable Integer id,
			@Valid Material material,
			Errors errors,
			RedirectAttributes rd,
			Model model) {

		// 編集画面に来た時に元の名前を取得 
		String originalMaterialName = service.getMaterialById(id).getName();

		if (!material.getName().isBlank()) {
			if (!originalMaterialName.equals(material.getName()) && service.existsByName(material.getName())) {
				errors.rejectValue("name", "error.name.notunique");
			}
		}

		if (errors.hasErrors()) {
			model.addAttribute("title", "視聴覚教材の編集");
			model.addAttribute("type", service.getMaterialTypeList());
			return "admin/material/edit";
		}

		material.setId(id);
		service.editMaterial(material);
		rd.addFlashAttribute("statusMessage", "教材を編集しました。");
		return "redirect:/admin/material/list";
	}

	// 教材倫理削除(post有り)
	@GetMapping("/delete/{id}")
	public String deleteGetMaterial(
			@PathVariable Integer id, RedirectAttributes rd) {
		service.setChangeByStatus(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/material/list";
	}

	@PostMapping("/delete/{id}")
	public String deletePostMaterial(
			@PathVariable Integer id, RedirectAttributes rd) {
		service.setChangeByStatus(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/material/list";
	}

}
