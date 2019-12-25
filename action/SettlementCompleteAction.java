package com.internousdev.django.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.django.dao.CartInfoDAO;
import com.internousdev.django.dao.PurchaseHistoryInfoDAO;
import com.internousdev.django.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;
public class SettlementCompleteAction  extends ActionSupport implements SessionAware {

	private String userId;
	private int id;
	private Map<String, Object> session;
	public CartInfoDAO cartdao = new CartInfoDAO();
	PurchaseHistoryInfoDAO purchasedao = new PurchaseHistoryInfoDAO();
	List<CartInfoDTO> cartInfoDTOList =new ArrayList<CartInfoDTO>();
	public CartInfoDTO cartinfoDTO = new CartInfoDTO();


	public String execute() {
		/*
		 * ログインしているかの判定をしています。
		 */
		String tempLoginFlg = String.valueOf(session.get("loginFlg"));
		int loginFlg = "null".equals(tempLoginFlg)? 0 :Integer.parseInt(tempLoginFlg);
		if (!session.containsKey("loginFlg") || loginFlg != 1) {
			return "loginError";
		}
		//cartから情報をとってきています。
		String userId = session.get("userId").toString();
		int addressId = getId();
		int count = 0;
		cartInfoDTOList = cartdao.getCartList(userId);

		//cartのデータを一件ずつまとめカウントに追加していく。
		for (CartInfoDTO c : cartInfoDTOList) {

			count += purchasedao.PurchaseHistoryInsert( userId, addressId,
					c.getProductId(), c.getProductCount(), c.getPrice());

		}
		//購入できたらカート情報を空にしている。
		if(count > 0) {
			int result = 0;
			result =  cartdao.deleteAll(userId);
			if(result>0) {
				return SUCCESS;
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}
	//getterとsetter
	public String  getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this. id= id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session =session;
	}

}
