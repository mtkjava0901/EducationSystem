package com.example.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.domain.AdminLoginForm;
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
			Model model) {
		
		// loginFormをモデルに追加
		if (!model.containsAttribute("loginForm")) {
			model.addAttribute("loginForm", new AdminLoginForm());
		}
		
		// 生徒セッションが存在する場合は破棄して管理者ログインページへ遷移(未)
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
			@Valid
			@ModelAttribute("loginForm")
			AdminLoginForm form,
			Errors errors,
			Model model) {
		// System.out.println(errors);
		
		Admin admin = service.authenticateAdmin(form.getLoginId(), form.getLoginPass(), errors);
		
		if (errors.hasErrors()) {
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
		rd.addFlashAttribute("loginMessage", "ログアウトしました。");
		return "redirect:/admin/login";
	}
}
