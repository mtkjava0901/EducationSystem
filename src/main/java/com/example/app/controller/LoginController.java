package com.example.app.controller;

import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.domain.Student;
import com.example.app.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final AdminService service;
	private final HttpSession session;

	// 管理者ログイン(post有り)
	@GetMapping("/admin/login")
	public String showAdminLogin(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/login";
	}

	@PostMapping("/admin/login")
	public String adminLogin(
			@Valid Admin admin,
			Errors errors,
			RedirectAttributes rd
			) {
		// 入力不備の場合
		if (errors.hasErrors()) {
			// 入力チェックエラーをメッセージにまとめる
			// rd.addFlashAttribute("errorMessage", "error.incorrect_id_password");
			String errorMsg = errors.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.joining("<br>"));
			rd.addFlashAttribute("errorMessage", errorMsg);
			return "redirect:/admin/login";
		}
		String loginId = admin.getLoginId();
		String loginPass = admin.getLoginPass();
		// IDまたはPASSが正しくない
		if (!service.isCorrectIdAndPassword(loginId, loginPass)) {
			// rd.addFlashAttribute("errorMessage", "error.incorrect_id_password");
			// errors.rejectValue("loginId", "error.incorrect_id_password");
			rd.addFlashAttribute("errorMessage", "ログインIDまたはパスワードが正しくありません。");
			return "redirect:/admin/login";
		}

		// 正しいID/PASSだったらセッションにIDを格納、リダイレクト
		session.setAttribute("loginId", loginId);
		return "redirect:/admin/material/list";
		// (生徒のログインセッションを破棄)

	}

	// 生徒ログイン(post有り)
	@GetMapping("/login")
	public String showStudentLogin(Model model) {
		model.addAttribute("student", new Student());
		return "login";
	}

	// 教材貸し出しページ
	@GetMapping("/rental")
	public String showRentalList() {
		return "rental";
	}

}
