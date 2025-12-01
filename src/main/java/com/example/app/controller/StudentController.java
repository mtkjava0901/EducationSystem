package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.domain.Student;
import com.example.app.service.StudentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/student")
@RequiredArgsConstructor
public class StudentController {

	// 1ページ当たりの表示人数
	private final int NUM_PER_PAGE = 5;

	private final StudentService service;
	private final HttpSession session;

	// 生徒一覧（post無し）
	@GetMapping("/list")
	public String studentList(
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) {
		List<Student> students = service.getStudentListByPage(page, NUM_PER_PAGE);
		// Null安全性担保
		if (students == null) {
			students = new ArrayList<>();
		}

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("list", students);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
		return "admin/student/list";
	}

	// 生徒追加（post有り）
	@GetMapping("/add")
	public String addGetStudent(Model model) {

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("title", "生徒の追加");
		model.addAttribute("student", new Student());
		return "admin/student/save";
	}

	@PostMapping("/add")
	public String addPostStudent(
			@Valid Student student,
			Errors errors,
			RedirectAttributes rd,
			Model model) {
		// studentのloginIdが空白でなければ・DBなどに同名が既に存在するか
		if (!student.getLoginId().isBlank()) {
			if (service.existsByLoginId(student.getLoginId())) {
				// 既にあればerrorsにloginIdフィールドエラーを追加
				errors.rejectValue("loginId", "error.loginid.notunique");
			}
		}
		if (errors.hasErrors()) {
			model.addAttribute("title", "生徒の追加");
			return "admin/student/save";
		}
		service.addStudent(student);
		rd.addFlashAttribute("statusMessage", "生徒を追加しました。");
		return "redirect:/admin/student/list";
	}

	// 生徒編集（post有り）
	@GetMapping("/edit/{id}")
	public String editGetStudent(
			@PathVariable Integer id, Model model) {

		// セッションからAdminユーザー取得
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("username", admin.getName()); // 名前を渡す
		}

		model.addAttribute("title", "生徒の編集");
		model.addAttribute("student", service.getStudentById(id));
		return "admin/student/save";
	}

	@PostMapping("/edit/{id}")
	public String editPostStudent(
			@PathVariable Integer id,
			@Valid Student student,
			Errors errors,
			RedirectAttributes rd,
			Model model) {
		// 編集画面に来た時元の名前を取得
		String originalStudentName = service.getStudentById(id).getLoginId();

		if (!student.getLoginId().isBlank()) {
			if (!originalStudentName.equals(student.getLoginId()) && service.existsByLoginId(student.getLoginId())) {
				errors.rejectValue("loginId", "error.loginid.notunique");
			}
		}
		if (errors.hasErrors()) {
			model.addAttribute("title", "生徒の編集");
			// これがあるとDBから新しくstudentを取得してmodelに入れてしまう
			// model.addAttribute("student", service.getStudentById(id));
			return "admin/student/save";
		}
		student.setId(id);
		service.editStudent(student);
		rd.addFlashAttribute("statusMessage", "生徒を編集しました。");
		return "redirect:/admin/student/list";
	}

	// 生徒倫理削除(post有り)
	@GetMapping("/delete/{id}")
	public String deleteGetStudent(
			@PathVariable Integer id,
			RedirectAttributes rd) {
		service.deleteStudent(id);
		rd.addFlashAttribute("statusMessage", "生徒を削除しました");
		return "redirect:/admin/student/list";
	}

	@PostMapping("/delete/{id}")
	public String deletePostStudent(
			@PathVariable Integer id,
			RedirectAttributes rd) {
		service.deleteStudent(id);
		rd.addFlashAttribute("statusMessage", "生徒を削除しました");
		return "redirect:/admin/student/list";
	}
	
	// 削除済み生徒一覧ページ
	@GetMapping("/deleted")
	public String deletedStudentList(
	        @RequestParam(name = "page", defaultValue = "1") Integer page,
	        Model model) {

	    List<Student> students = service.getDeletedStudentListByPage(page, NUM_PER_PAGE);
	    if (students == null) {
	        students = new ArrayList<>();
	    }

	    // セッションからAdminユーザー取得
	    Admin admin = (Admin) session.getAttribute("admin");
	    if (admin != null) {
	        model.addAttribute("username", admin.getName());
	    }

	    model.addAttribute("list", students);
	    model.addAttribute("page", page);
	    model.addAttribute("totalPages", service.getDeletedTotalPages(NUM_PER_PAGE));

	    return "admin/student/deleted"; // 新しいテンプレート名
	}
	
	// 削除済み生徒復活(post)
	@PostMapping("/restore/{id}")
	public String restoreStudent(
	        @PathVariable Integer id,
	        RedirectAttributes rd) {

	    service.restoreStudent(id);
	    rd.addFlashAttribute("statusMessage", "生徒を復活させました。");
	    return "redirect:/admin/student/deleted";
	}

}
