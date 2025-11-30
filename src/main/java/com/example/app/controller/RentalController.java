package com.example.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Material;
import com.example.app.domain.RentalRecord;
import com.example.app.domain.Student;
import com.example.app.service.MaterialService;
import com.example.app.service.RentalRecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RentalController {

	private final int NUM_PER_PAGE = 5;

	private final HttpSession session;
	private final RentalRecordService rentalService;
	private final MaterialService materialService;

	// /rentalへのリダイレクト
	@GetMapping("/")
	public String redirectToRental() {
		// sessionにstudentがいなければログインへ
		Student student = (Student) session.getAttribute("student");
		if (student == null) {
			return "redirect:/login";
		}

		// ログインしていたら/rentalへ
		return "redirect:/rental";
	}

	// 教材貸し出しページ
	@GetMapping("/rental")
	public String showRentalList(
			@RequestParam(name = "page", defaultValue = "1") int page,
			Model model) {

		// セッションからユーザー取得
		Student student = (Student) session.getAttribute("student");
		if (student != null) {
			model.addAttribute("username", student.getName()); // 名前を渡す
		}

		// Sessionのエラーメッセージを取得、modelに渡す
		String errorMessage = (String) session.getAttribute("borrowError");
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
			session.removeAttribute("borrowError"); // 1回表示したら消す
		}

		// 貸し出し中のみ表示
		List<RentalRecord> records = rentalService.getBorrowingRecords();
		model.addAttribute("records", records);

		// 貸し出し可能な教材一覧(ページネーション有り)
		List<Material> availableMaterials = materialService.getAvailableMaterialsByPage(page, NUM_PER_PAGE);
		model.addAttribute("availableMaterials", availableMaterials);

		// 総ページ数計算
		int totalCounts = materialService.getAvailableTotalCount(); // 総件数
		int totalPages = (int) Math.ceil((double) totalCounts / NUM_PER_PAGE);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);

		return "rental";
	}

	// 教材を借りる
	@PostMapping("/borrow/{materialId}")
	public String borrowMaterial(
			@PathVariable Integer materialId) {
		Student student = (Student) session.getAttribute("student");
		if (student != null) {
			// 既に借りている教材件数を取得
			int borrowedCount = rentalService.getBorrowingRecordsByStudent(student.getId()).size();
			// 3件以上なら借りられない
			if (borrowedCount >= 3) {
				session.setAttribute("borrowError", "一度に借りられる教材は3件までです。");
				return "redirect:/rental";
			}
			// 借りる処理
			RentalRecord record = new RentalRecord();
			record.setStudent(student);
			record.setMaterial(materialService.getMaterialById(materialId));
			record.setBorrowedAt(LocalDateTime.now());
			rentalService.borrowMaterial(record);

			// borrowフラグ更新
			materialService.updateBorrowStatus(materialId, true);
		}
		return "redirect:/rental";
	}

	// 教材を返却する
	@PostMapping("/return/{recordId}")
	public String returnMaterial(
			@PathVariable Integer recordId) {
		RentalRecord record = rentalService.getRentalRecordById(recordId);
		if (record != null) {
			record.setReturnedAt(LocalDateTime.now());
			rentalService.returnMaterial(record);

			// borrowフラグ更新
			materialService.updateBorrowStatus(record.getMaterial().getId(), false);
		}
		return "redirect:/rental";
	}

}
