package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	// 生徒一覧（post無し）
	@GetMapping("/list")
	public String studentList(
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) {
		List<Student> students = service.getStudentListByPage(page, NUM_PER_PAGE);
		model.addAttribute("list", students);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
		return "admin/student/list";
	}

	// 生徒追加（post有り）
	@GetMapping("/add")
	public String addGetStudent(Model model) {
		model.addAttribute("title", "生徒の追加");
		model.addAttribute("student", new Student());
		return "admin/student/save";
	}

	/*
	@PostMapping("/add")
	public String addPostStudent(RedirectAttributes rd,
			Model model) {
		service.addStudent(student);
		rd.addFlashAttribute("statusMessage", "生徒を追加しました。");
		return "redirect:/admin/student/list";
	}
	*/

	// 生徒編集（post有り）
	@GetMapping("/edit/{id}")
	public String editGetStudent(
			@PathVariable Integer id, Model model) {
		model.addAttribute("title", "生徒の編集");
		model.addAttribute("student", service.getStudentById(id));
		return "admin/student/save";
	}

	// 生徒倫理削除(post有り)

	@GetMapping("/delete/{id}")
	public String deleteGetStudent(
			@PathVariable Integer id,
			RedirectAttributes rd) {
		service.deleteStudent(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/student/list";
	}

	@PostMapping("/delete/{id}")
	public String deletePostStudent(
			@PathVariable Integer id,
			RedirectAttributes rd) {
		service.deleteStudent(id);
		rd.addFlashAttribute("statusMessage", "教材を削除しました");
		return "redirect:/admin/student/list";
	}

}
