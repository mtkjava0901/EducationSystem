package com.example.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Student;
import com.example.app.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentLoginController {

	private final LoginService service;

	// 生徒ログイン(post有り)
	@GetMapping("/login")
	public String showStudentLogin(
			Student student,
			HttpSession session,
			Model model) {
		// 管理者セッションが存在する場合は破棄して生徒ログインページへ遷移
		if (session.getAttribute("admin") != null) {
			session.removeAttribute("admin");
		}
		// 生徒が既にログインしている場合はレンタルへリダイレクト
		if (session.getAttribute("student") != null) {
			return "redirect:/rental";
		}
		return "login";
	}

	@PostMapping("/login")
	public String studentLogin(
			@Valid Student student,
			Errors errors,
			RedirectAttributes rd,
			HttpSession session,
			Model model) {
		if (!service.authenticateStudent(student, errors)) {
			if (errors.hasErrors()) {
				String errorMsg = errors.getAllErrors().stream()
						.map(e -> e.getDefaultMessage())
						.reduce((a, b) -> a + " / " + b)
						.orElse("ログインエラー");
				model.addAttribute("errorMessage", errorMsg);
			}
			return "login";
		}

		// 成功したら逆側のセッションを破棄
		session.removeAttribute("admin");
		session.setAttribute("student", student);
		return "redirect:/rental";
	}
}

/*
// 入力チェックエラーをメッセージにまとめる
String errorMsg = errors.getAllErrors().stream()
		.map(DefaultMessageSourceResolvable::getDefaultMessage)
		.collect(Collectors.joining("<br>"));
rd.addFlashAttribute("errorMessage", errorMsg);
return "redirect:/admin/login";
}
String loginId = student.getLoginId();
String loginPass = student.getLoginPass();
// ID/PASSが正しくない
if (!service.)

}

// 教材貸し出しページ
@GetMapping({"/", "/rental"})
public String showRentalList() {
return "rental";
}
}

*/