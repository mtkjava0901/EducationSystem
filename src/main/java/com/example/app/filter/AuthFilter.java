package com.example.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false); // 新たにセッションを作らない

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();

		// ログインページ、ログアウトページはフィルタ対象外
		//if (uri.equals(contextPath + "/login")
		//		|| uri.equals(contextPath + "/logout")
		//				|| uri.equals(contextPath + "/admin/login")
		//				|| uri.equals(contextPath + "/admin/logout")) {
		//			chain.doFilter(request, response);
		//			return;
		//		}

		// 管理者ページ
		if (uri.startsWith(contextPath + "/admin")) {
			if (!uri.equals(contextPath + "/admin/login")
					&& (session == null || session.getAttribute("admin") == null)) {

				res.sendRedirect(contextPath + "/admin/login");
				return;
			}
		}

		// 生徒ページ
		boolean studentProtected = uri.equals(contextPath + "/") ||
				uri.startsWith(contextPath + "/rental");

		if (studentProtected) {
			if (!uri.equals(contextPath + "/login")
					&& (session == null || session.getAttribute("student") == null)) {

				res.sendRedirect(contextPath + "/login");
				return;
			}
		}

		// 認証済みなら次のFilter / Controllerへ
		chain.doFilter(request, response);
	}

}

/*		
		if (uri.startsWith(contextPath + "/admin/")) {
			if (session == null || session.getAttribute("admin") == null) {
				// リダイレクト前にchain.doFilterは呼ばなfい
				res.sendRedirect(contextPath + "/admin/login");
				return;
			}
		}

		// Studentページの場合
		else if (uri.equals(contextPath + "/rental")
				|| uri.equals(contextPath + "/rental/")) {
			if (session == null || session.getAttribute("student") == null) {
				// リダイレクト前にchain.doFilterは呼ばない
				res.sendRedirect(contextPath + "/login");
				return;
			}
		}
*/
