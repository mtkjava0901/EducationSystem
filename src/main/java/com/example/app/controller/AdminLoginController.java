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
import com.example.app.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {

	private final LoginService service;

	// 管理者ログイン(post有り)
	@GetMapping("/admin/login")
	public String showAdminLogin(
			HttpSession session,
			Model model) {

		// 生徒セッションが存在する場合は破棄して管理者ログインページへ遷移
		if (session.getAttribute("student") != null) {
			session.removeAttribute("student");
		}
		// 管理者が既にログインしている場合はリストヘリダイレクト
		if (session.getAttribute("admin") != null) {
			return "redirect:/admin/material/list";
		}

		// モデルにAdminが存在しない場合は初期化
		if (!model.containsAttribute("admin")) {
			model.addAttribute("admin", new Admin());
		}
		return "admin/login";
	}

	@PostMapping("/admin/login")
	public String adminLogin(
			@Valid Admin admin,
			Errors errors,
			RedirectAttributes rd,
			HttpSession session,
			Model model) {
		boolean authenticated = service.authenticateAdmin(admin, errors);

		if (!authenticated) {
			// バリデーションエラーがある場合
			if (errors.hasErrors()) {
				// field errorをまとめて表示
				String errorMsg = errors.getAllErrors().stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.collect(Collectors.joining("<br>"));

				model.addAttribute("errorMessage", errorMsg);
				// post失敗時はredirectではなくforwardで返す
				return "admin/login";
			} else {
				// field errorがなくても認証失敗なら汎用エラー
				model.addAttribute("errorMessage", "ログインIDまたはパスワードが正しくありません。");
				return "admin/login";
			}
		}

		// 認証成功→セッションセット
		session.removeAttribute("student");
		session.setAttribute("admin", admin);
		return "redirect:/admin/material/list";
	}

}

/*		
		// 認証が失敗した場合
		if (!service.authenticateAdmin(admin, errors)) {
			// バリデーションまたはログイン失敗のメッセージをまとめる
			if (errors.hasErrors()) {
*/
/*
String errorMsg = errors.getAllErrors().stream()
		.map(e -> e.getDefaultMessage())
		.reduce((a, b) -> a + " <br> " + b)
		.orElse("ログインエラー");
rd.addFlashAttribute("errorMessage", errorMsg);
*/

/*
		String errorMsg = errors.getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining("<br>"));
		rd.addFlashAttribute("errorMessage", errorMsg);
		return "redirect:/admin/login";
	}
}
// 成功したら逆側のセッションを破棄
session.removeAttribute("student");
session.setAttribute("admin", admin);
return "redirect:/admin/material/list";
}
}
*/

/*
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

}
*/