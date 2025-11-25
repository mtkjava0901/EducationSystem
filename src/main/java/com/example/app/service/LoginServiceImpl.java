package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.example.app.domain.Admin;
import com.example.app.domain.Student;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final AdminService adminService;
	private final StudentService studentService;

	// Admin認証
	@Override
	public boolean authenticateAdmin(Admin admin, Errors errors) {
		String loginId = admin.getLoginId();
		String loginPass = admin.getLoginPass();

		// 必須チェック
		if (loginId == null || loginId.isBlank()) {
			errors.rejectValue("loginId", "error.required",
					"ログインIDは必須項目です。");
		}
		if (loginPass == null || loginPass.isBlank()) {
			errors.rejectValue("loginPass", "error.required",
					"パスワードは必須項目です。");
		}

		// 必須チェックにエラーがある場合は認証エラーは追加しない
		if (errors.hasErrors()) {
			return false;
		}

		// 認証チェック
		boolean authenticated = adminService.isCorrectIdAndPassword(loginId, loginPass);
		if (!authenticated) {
			rejectIfNotExists(errors, "loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
		}
		return authenticated;
	}

	// 重複登録防止ヘルパー
	private void rejectIfNotExists(Errors errors, String field, String code, String defaultMessage) {
		boolean alreadyExists = errors.getFieldErrors(field).stream()
				.anyMatch(fe -> code.equals(fe.getCode()));
		if (!alreadyExists) {
			errors.rejectValue(field, code, defaultMessage);
		}
	}

	// Student認証
	@Override
	public boolean authenticateStudent(Student student, Errors errors) {
		String loginId = student.getLoginId();
		String loginPass = student.getLoginPass();

		if (loginId == null || loginId.isBlank() || loginPass == null || loginPass.isBlank()) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
			return false;
		}

		boolean authenticated = studentService.isCorrectIdAndPassword(loginId, loginPass);
		if (!authenticated) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
		}
		return authenticated;
	}
}

/*
}
return authenticate(
		admin.getLoginId(),
		admin.getLoginPass(),
		errors,
		"loginId",
		adminService::getPasswordHashByLoginId);
}

// student
@Override
public boolean authenticateStudent(Student student, Errors errors) {
return authenticate(
		student.getLoginId(),
		student.getLoginPass(),
		errors,
		"loginId",
		studentService::getPasswordHashByLoginId);
}
*/

/*
 * 共通認証メソッド
 * 
 * @param loginId 入力ID
 * @param loginPass 入力パス
 * @param errors Errorsオブジェクト
 * @param fieldName フィールド名(rejectValueで使う)
 * @param hashProvider ハッシュ取得関数(Admin/StudentService)
 * @return 認証成功ならtrue
 */

/*
private boolean authenticate(
		String loginId,
		String loginPass,
		Errors errors,
		String fieldName,
		java.util.function.Function<String, String> hashProvider) {
	// ID入力チェック
	if (loginId == null || loginId.isBlank()) {
		errors.rejectValue(fieldName, "error.loginId.required");
	}
	// パスワード入力チェック
	if (loginPass == null || loginPass.isBlank()) {
		errors.rejectValue("loginPass", "error.loginPass.required");
	}
	if (errors.hasErrors()) {
		return false;
	}

	// ハッシュ化パスワードチェック
	String hashedPass = hashProvider.apply(loginId);
	if (hashedPass == null || !BCrypt.checkpw(loginPass, hashedPass)) {
		errors.reject("error.incorrect_id_password");
		return false;
	}
	return true;
}
}
*/

/*
		if (admin.getLoginId() == null || admin.getLoginId().isBlank()) {
			errors.rejectValue("loginId", "error.loginId.required");
		}

		// パスワード入力チェック
		if (admin.getLoginPass() == null || admin.getLoginPass().isBlank()) {
			errors.rejectValue("loginPass", "error.loginPass.required");
		}

		// まとめてfalse
		if (errors.hasErrors()) {
			return false;
		}

		// ハッシュ化されたパスのチェック
		String hashedPass = adminService.getPasswordHashByLoginId(admin.getLoginId());
		if (hashedPass == null ||
				!BCrypt.checkpw(admin.getLoginPass(), hashedPass)) {
			errors.rejectValue("LoginId", "error.incorrect_id_password");
			return false;
		}
		// 両方突破したらtrueを返す
		return true;
	}

	// Studentログイン
	@Override
	public boolean authenticateStudent(Student student, Errors errors) {
		// ID入力チェック
		if (student.getLoginId() == null || student.getLoginId().isBlank()) {
			errors.rejectValue("loginId", "error.loginId.required");
		}
		// パスワード入力チェック
		if (student.getLoginPass() == null || student.getLoginPass().isBlank()) {
			errors.rejectValue("loginPass", "error.loginPass.required");
		}

		// まとめてfalse
		if (errors.hasErrors()) {
			return false;
		}

		// ハッシュ化されたパスのチェック
		String hashedPass = studentService.getPasswordHashByLoginId(student.getLoginId());
		if (hashedPass == null ||
				!BCrypt.checkpw(student.getLoginPass(), hashedPass)) {
			errors.rejectValue("loginId", "error.incorrect_id_password");
			return false;
		}

		// 両方突破したらtrueを返す
		return true;
	}

}
*/
