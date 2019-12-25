package com.internousdev.django.action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.django.dao.DestinationInfoDAO;
import com.internousdev.django.dto.DestinationInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementConfirmAction extends ActionSupport implements SessionAware{

	private boolean listFlg;
	private Map<String, Object> session;
	private DestinationInfoDAO destinationDao = new DestinationInfoDAO();
	private List<DestinationInfoDTO> destinationList =new  ArrayList<DestinationInfoDTO>();

	//ログインを判定しています。
	public String execute() throws SQLException{

		String tempLoginFlg = String.valueOf(session.get("loginFlg"));
		int loginFlg = "null".equals(tempLoginFlg)? 0 :Integer.parseInt(tempLoginFlg);
		if (!session.containsKey("loginFlg") || loginFlg != 1) {
			return "loginError";
		}else{
			//userIdを使い宛先情報を取得しリストを作成
			destinationList=destinationDao.getDestinationInfo(session.get("userId").toString());
			int list_add_all=destinationList.size();
			/*
			 * カートの中身があるかどうか判定できるようにする。
			 */
			if(list_add_all==0){
				//jspではカート情報なしを表示する
				listFlg=false;
			}else{
				//jspではカート情報を表示する
				listFlg=true;
			}
			return SUCCESS;
		}
	}

	//getterとsetter
	public Map<String,Object> getSession() {
		return session;
	}

	public void setSession(Map<String ,Object> session) {
		this.session= session;
	}

	public List<DestinationInfoDTO> getDestinationList() {
		return this.destinationList;
	}

	public void setDestinationList(List<DestinationInfoDTO> DestinationList) {
		this.destinationList=DestinationList;
	}

	public boolean getListFlg() {
		return listFlg;
	}

	public void setListFlg(boolean listFlg) {
		this.listFlg=listFlg;
	}

}
