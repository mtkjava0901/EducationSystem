package com.example.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.RentalRecord;
import com.example.app.service.RentalRecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RentalRecordController {

	private final RentalRecordService rentalService;

	// 貸し出し履歴一覧
	@GetMapping("/list")
	public String listRentalRecords(Model model) {
		List<RentalRecord> records = rentalService.getAllRentalRecords();
		model.addAttribute("records", records);
		return "rental/list"; // Thymeleafテンプレート rental/list.html
	}

	// 貸し出し登録
	@GetMapping("/borrow")
	public String showBorrowForm(Model model) {
		model.addAttribute("rentalRecord", new RentalRecord());
		return "rental/borrowForm"; // rental/borrowForm.html
	}

	@PostMapping("/borrow")
	public String borrowMaterial(@ModelAttribute RentalRecord rentalRecord) {
		rentalRecord.setBorrowedAt(LocalDateTime.now());
		rentalService.borrowMaterial(rentalRecord);
		return "redirect:/rental/list";
	}

	// 返却処理
	@PostMapping("/return/{id}")
	public String returnMaterial(@PathVariable Integer id) {
		RentalRecord record = rentalService.getRentalRecordById(id);
		if (record != null) {
			record.setReturnedAt(LocalDateTime.now());
			rentalService.returnMaterial(record);
		}
		return "redirect:/rental/list";
	}

	// 削除処理
	@PostMapping("/delete/{id}")
	public String deleteRentalRecord(@PathVariable Integer id) {
		rentalService.deleteRentalRecord(id);
		return "redirect:/rental/list";
	}

}