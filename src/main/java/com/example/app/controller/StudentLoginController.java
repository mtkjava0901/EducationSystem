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

import com.example.app.domain.Student;
import com.example.app.domain.StudentLoginForm;
import com.example.app.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentLoginController {

	private final LoginService service;
	private final HttpSession session;

	// 生徒ログイン(post有り)
	@GetMapping("/login")
	public String showStudentLogin(
			Model model) {
		
		// loginFormをモデルに追加
		if (!model.containsAttribute("loginForm")) {
			model.addAttribute("loginForm", new StudentLoginForm());
		}
		
		// 管理者セッションが存在する場合は破棄して生徒ログインページへ遷移(未)
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
			@Valid 
			@ModelAttribute("loginForm")
			StudentLoginForm form,
			Errors errors,
			Model model) {
		// System.out.println(errors);

		Student student = service.authenticateStudent(form.getLoginId(), form.getLoginPass(), errors);
		
		if (errors.hasErrors()) {
			return "login";
		}

		// 成功したら逆側のセッションを破棄
		session.removeAttribute("admin");
		session.setAttribute("student", student);
		return "redirect:/rental";
	}

	// ログアウト
	@GetMapping("/logout")
	public String logout(RedirectAttributes rd) {
		// セッションを破棄してトップページへ遷移
		session.invalidate();
		rd.addFlashAttribute("loginMessage", "ログアウトしました。");
		return "redirect:/login";
	}

	// 教材貸し出しページ
	@GetMapping({ "/", "/rental" })
	public String showRentalList() {
		return "rental";
	}
}
