package com.example.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {

	private final LoginService service;
	private final HttpSession session;

	// 管理者ログイン(post有り)
	@GetMapping("/admin/login")
	public String showAdminLogin(
			Admin admin,
			Model model) {
		// 生徒セッションが存在する場合は破棄して管理者ログインページへ遷移
		if (session.getAttribute("student") != null) {
			session.removeAttribute("student");
		}
		// 管理者が既にログインしている場合はリストヘリダイレクト
		if (session.getAttribute("admin") != null) {
			return "redirect:/admin/material/list";
		}
		return "admin/login";
	}

	@PostMapping("/admin/login")
	public String adminLogin(
			@Valid Admin admin,
			Errors errors,
			Model model) {
		/*
		// 認証が失敗した場合
		if (!service.authenticateAdmin(admin, errors)) {
			// バリデーションまたはログイン失敗のメッセージをまとめる
			if (errors.hasErrors()) {
				String errorMsg = errors.getAllErrors().stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.collect(Collectors.joining("<br>"));
				rd.addFlashAttribute("errorMessage", errorMsg);
				return "redirect:/admin/login";
			}
		}
		*/
		if (!service.authenticateAdmin(admin, errors)) {
			return "admin/login";
		}
		
		// 成功したら逆側のセッションを破棄
		session.removeAttribute("student");
		session.setAttribute("admin", admin);
		return "redirect:/admin/material/list";
	}
	
	@GetMapping("/admin/logout")
	public String logout(RedirectAttributes rd) {
		// セッションを破棄し、トップページへ遷移
		session.invalidate();
		rd.addFlashAttribute("", "");
		return "redirect:/admin/login";
	}
}
