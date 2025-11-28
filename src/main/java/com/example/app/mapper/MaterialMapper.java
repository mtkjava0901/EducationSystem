package com.example.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Material;

@Mapper
public interface MaterialMapper {

	// 教材マッピング
	// throws Exception;(一旦使わない)

	// 教材一覧
	List<Material> selectAll();

	// 教材詳細
	Material selectById(Integer id);

	// 教材詳細
	Material selectByName(String name);

	// 教材追加
	void insert(Material material);

	// 教材編集
	void update(Material material);

	// 教材削除(倫理削除)
	void delete(Integer id);

	// 同名が存在するか判定 名前で件数をカウント
	int countByName(String name);

	// 指定ID以外で同名が存在するか判定(編集時)
	int countByNameExcludingId(@Param("name") String name,
			@Param("id") Integer id);

	// データの全件数を取得
	Long count();

	// ページごとのデータを取得
	List<Material> selectLimitedMaterials(@Param("offset") int offset,
			@Param("limit") int limit);

	// 貸し出し可能な教材を全件取得
	List<Material> selectAvailableMaterials();

	// 貸し出し可能な教材をページネーション付きで取得
	List<Material> selectAvailableMaterialsByPage(@Param("offset") int offset,
			@Param("limit") int limit);

	// 貸し出し可能教材の件数（ページネーション用）
	int countAvailable();

	// borrowフラグ更新(貸出/返却)
	void updateBorrow(Map<String, Object> params);
}

// 教材倫理削除(statusを'DEL'に変更、安全に扱えるように)(未使用)
// @Update("UPDATE materials SET status = 'DEL' WHERE id = #{id}")
// int setChangeByStatus(@Param("id") Integer id);
