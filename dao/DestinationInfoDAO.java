package com.internousdev.django.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.internousdev.django.dto.DestinationInfoDTO;
import com.internousdev.django.util.DBConnector;

public class DestinationInfoDAO {

	/*
	 * 				宛先情報の取得機能
	 */
	public ArrayList<DestinationInfoDTO> getDestinationInfo(String userId)  {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		ArrayList<DestinationInfoDTO> destinationList = new ArrayList<DestinationInfoDTO>();

		String sql = "SELECT id, user_id, family_name, first_name, family_name_kana, first_name_kana,"
				+ " user_address, tel_number, email,regist_date"
				+ " from destination_info WHERE user_id = ? ORDER BY regist_date ASC ";

		try{
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				DestinationInfoDTO dto = new DestinationInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getString("user_id"));
				dto.setFamilyName(rs.getString("family_name"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setFamilyNameKana(rs.getString("family_name_kana"));
				dto.setFirstNameKana(rs.getString("first_name_kana"));
				dto.setUserAddress(rs.getString("user_address"));
				dto.setTelNumber(rs.getString("tel_number"));
				dto.setEmail(rs.getString("email"));
				destinationList.add(dto);
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
		return destinationList;

	}

	/*
	 * 宛先登録機能
	 */
	public int createDestination(String userId, String familyName, String firstName,String familyNameKana,
			String firstNameKana,String email, String userAddress,String telNumber) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int count= 0;
		String sql = "INSERT INTO destination_info ( user_id, family_name, first_name, family_name_kana,"
				+ " first_name_kana, email, tel_number, user_address, regist_date, update_date)"
				+ " VALUES(?,?,?,?,?,?,?,?, now(), now())";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, familyName);
			ps.setString(3, firstName);
			ps.setString(4, familyNameKana);
			ps.setString(5, firstNameKana);
			ps.setString(6, email);
			ps.setString(7, telNumber);
			ps.setString(8, userAddress);
			count = ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/*
	 * 				宛先情報削除機能
	 */

	public int DestinationInfoDelete(int destinationId ) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "DELETE FROM destination_info  WHERE id=?";
		PreparedStatement ps;
		int result = 0;
		try {
			ps= con.prepareStatement(sql);
			ps.setInt(1, destinationId);
			result = ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

}
