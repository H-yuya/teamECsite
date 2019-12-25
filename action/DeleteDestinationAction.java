package com.internousdev.django.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.django.dao.DestinationInfoDAO;
import com.internousdev.django.dto.DestinationInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteDestinationAction  extends ActionSupport implements SessionAware {

	private Map<String,Object> session;
	private int id;
	private String userId;
	private boolean listFlg;
	private DestinationInfoDAO destinationDAO = new DestinationInfoDAO();
	private ArrayList<DestinationInfoDTO> destinationList = new ArrayList <DestinationInfoDTO>();

	public String execute() {

		String tempLoginFlg = String.valueOf(session.get("loginFlg"));
		int loginFlg = "null".equals(tempLoginFlg)? 0 :Integer.parseInt(tempLoginFlg);
		if (!session.containsKey("loginFlg") || loginFlg != 1) {
			return "loginError";
		}
		//デリートのメソッド
		int destinationId = getId();
		//System.out.println(destinationId);
		int result = destinationDAO.DestinationInfoDelete(destinationId);

		//再度宛先情報テーブルへ情報を確認する
		if (result > 0){
			destinationList = null;
			if (loginFlg == 1) {
				destinationList = destinationDAO.getDestinationInfo(session.get("userId").toString());
				int list_add_all = destinationList.size();
				if (list_add_all == 0) {
					//リストのためのフラグです ログインのフラグとは違います。
					listFlg=false;
					return SUCCESS;
				} else if (list_add_all > 0) {
					listFlg = true;
					return SUCCESS;
				}
			} else {
				return ERROR;
			}
		}
			return ERROR;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//destinationList の名前間違え注意
	public ArrayList<DestinationInfoDTO> getDestinationList() {
		return destinationList;
	}
	
	//destinationList の名前間違え注意
	public void setDestinationList( ArrayList<DestinationInfoDTO> destinationList) {
		this.destinationList = destinationList;
	}
	
	public Map<String,Object> getSession() {
		return session;
	}
	
	public void setSession(Map<String ,Object> session) {
		this.session= session;
	}
	
	public boolean getListFlg() {
		return listFlg;
	}
	
	public void setListFlg(boolean listFlg) {
		this.listFlg=listFlg;
	}
}