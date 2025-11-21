package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Admin;
import com.example.app.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper mapper;

	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) {
		// adminにログインIDを代入
		Admin admin = mapper.selectByLoginId(loginId);

		// ログインチェック
		// IDが存在しなければデータは取得されない
		if (admin == null) {
			return false;
		}

		// パスが正しくなければデータは取得されない
		if (!BCrypt.checkpw(loginPass, admin.getLoginPass())) {
			return false;
		}
		// 両方突破したらtrueを返す
		return true;
	}

}
