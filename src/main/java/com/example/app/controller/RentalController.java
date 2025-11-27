package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.Student;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RentalController {

	private final HttpSession session;

	// 教材貸し出しページ
	@GetMapping({ "/", "/rental" })
	public String showRentalList(Model model) {
		// セッションからユーザー取得
		Student student = (Student) session.getAttribute("student");

		if (student != null) {
			model.addAttribute("username", student.getName()); // 名前を渡す
		}
		return "rental";
	}

}
