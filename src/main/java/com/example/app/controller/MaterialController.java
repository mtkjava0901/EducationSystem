package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.domain.Material;
import com.example.app.domain.RentalRecord;
import com.example.app.service.MaterialService;
import com.example.app.service.RentalRecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/material")
public class MaterialController {

	// 1ページの表示数
	private final int NUM_PER_PAGE = 5;
	
	
	private final MaterialService materialService;
	private final RentalRecordService rentalService;
	private final HttpSession session;

	@GetMapping("/list")
	public String showList(
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) {
		// 1ページ文の教材のみ取得
		List<Material> materials = materialService.getMaterialListByPage(page, NUM_PER_PAGE);
		// Null安全性担保
		if (materials == null) {
			materials = new ArrayList<>();
		}

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("list", materials);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", materialService.getTotalPages(NUM_PER_PAGE));
		return "admin/material/list";
	}

	// 教材詳細(post無し)
	@GetMapping("/show/{id}")
	public String showDetail(
			@PathVariable Integer id, Model model) {

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}
		
		Material material = materialService.getMaterialById(id);
		model.addAttribute("material", material);

		// 最新貸し出し履歴(上限5件)を取得、modelに追加
		List<RentalRecord> recentRecords = rentalService.getRecentRecordsByMaterial(id, 5);
		model.addAttribute("recentRecords", recentRecords);
		
		return "admin/material/show";
	}

	// 教材追加(post有り)
	@GetMapping("/add")
	public String addGetMaterial(Model model) {

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("title", "視聴覚教材の追加");
		model.addAttribute("material", new Material());
		model.addAttribute("type", materialService.getMaterialTypeList());
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
			if (materialService.existsByName(material.getName())) {
				// 既にあればerrorsにnameフィールドエラーを追加
				errors.rejectValue("name", "error.name.notunique");
			}
		}
		if (errors.hasErrors()) {
			model.addAttribute("title", "視聴覚教材の追加");
			model.addAttribute("type", materialService.getMaterialTypeList());
			return "admin/material/add";
		}

		materialService.addMaterial(material);
		rd.addFlashAttribute("statusMessage", "教材を追加しました。");
		return "redirect:/admin/material/list";
	}

	// 教材編集(post有り)
	@GetMapping("/edit/{id}")
	public String editGetMaterial(
			@PathVariable Integer id, Model model) {

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("title", "視聴覚教材の編集");
		model.addAttribute("material", materialService.getMaterialById(id));
		model.addAttribute("type", materialService.getMaterialTypeList());
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
		String originalMaterialName = materialService.getMaterialById(id).getName();

		if (!material.getName().isBlank()) {
			if (!originalMaterialName.equals(material.getName()) && materialService.existsByName(material.getName())) {
				errors.rejectValue("name", "error.name.notunique");
			}
		}

		if (errors.hasErrors()) {
			model.addAttribute("title", "視聴覚教材の編集");
			model.addAttribute("type", materialService.getMaterialTypeList());
			return "admin/material/edit";
		}

		material.setId(id);
		materialService.editMaterial(material);
		rd.addFlashAttribute("statusMessage", "教材を編集しました。");
		return "redirect:/admin/material/list";
	}

	// 教材倫理削除(post有り)
	@GetMapping("/delete/{id}")
	public String deleteGetMaterial(
			@PathVariable Integer id, RedirectAttributes rd) {
		materialService.setChangeByStatus(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/material/list";
	}

	@PostMapping("/delete/{id}")
	public String deletePostMaterial(
			@PathVariable Integer id, RedirectAttributes rd) {
		materialService.setChangeByStatus(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/material/list";
	}

}
