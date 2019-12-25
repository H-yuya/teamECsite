package com.internousdev.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.django.dto.PurchaseHistoryInfoDTO;
import com.internousdev.django.util.DBConnector;

public class PurchaseHistoryInfoDAO {

//	 購入履歴を取得するメソッド。
	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryInfoDTOList(String userId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList = new ArrayList<PurchaseHistoryInfoDTO>();

		String sql =
				"SELECT "
						+ "phi.id, " /*ID*/
						+ "phi.user_id, " /*ユーザーID*/
						+ "pi.product_name, " /*商品名*/
						+ "pi.product_name_kana, " /*商品名かな*/
						+ "pi.image_file_path, " /*画像ファイルパス*/
						+ "pi.image_file_name, " /*画像ファイル名*/
						+ " pi.release_company, " /*販売会社名*/
						+ "pi.release_date, " /*販売年月日*/
						+ "phi.price, " /*値段*/
						+ "phi.product_count, " /*個数*/
						+ "phi.price * phi.product_count As total_price, " /*合計金額*/
						+ "di. family_name, " /*姓*/
						+ "di.first_name, " /*名*/
						+ "di.user_address " /*住所*/
				+ "FROM "
						+ "purchase_history_info As phi "
				+ "LEFT JOIN "
						+ "destination_info As di "
				+ "ON "
						+ "phi.destination_id = di.id "
				+ "LEFT JOIN "
						+ "product_info As pi "
				+ "ON "
						+ "phi.product_id = pi.product_id "
				+ "WHERE "
						+ "phi.user_id = ? "
				+ "ORDER BY "
						+ "phi.regist_date DESC";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PurchaseHistoryInfoDTO dto = new PurchaseHistoryInfoDTO();
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setProductCount(rs.getInt("product_count"));
				dto.setTotalPrice(rs.getInt("total_price"));
				dto.setFamilyName(rs.getString("family_name"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setUserAddress(rs.getString("user_address"));
				purchaseHistoryInfoDTOList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return purchaseHistoryInfoDTOList;
	}
	
//履歴削除削除機能
	public int PurchaseHistoryDelete(String userId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql =
				"DELETE FROM "
						+ "purchase_history_info "
				+ "WHERE "
						+ "purchase_history_info.user_id = ?";

		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,userId);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int PurchaseHistoryInsert(String userId, int addressId, int productId, int productCount, int productPrice) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int count= 0;
		String sql = "INSERT INTO purchase_history_info  (user_id,product_id, product_count, price,"
				+ " destination_id,regist_date,update_date ) VALUES(?,?,?,?,?,now(),now())";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			ps.setInt(4, productPrice);
			ps.setInt(5, addressId);
			count = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
}