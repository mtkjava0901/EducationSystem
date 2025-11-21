package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.Admin;
import com.example.app.domain.Student;
import com.example.app.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final AdminService service;
	private final HttpSession session;
	
	// 管理者ログイン(post有り)
	@GetMapping("/admin/login")
	public String showAdminLogin(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/login";
	}
	// @PostMapping
	
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
