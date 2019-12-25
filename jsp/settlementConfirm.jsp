<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<link rel="stylesheet" type="text/css" href="./css/table.css">
<title>決済確認画面</title>
<script src="./js/setAction.js"></script>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div id=main>
		<div id=top>
			<h1>決済確認画面</h1>
		</div>
		<s:if test="listFlg==true">
			<div>
				<div class=messageResult>宛先情報を選択してください。</div>
			</div>
			<div>
				<s:form id="form">
					<table  class="horizontal-list-table">
						<tr>
							<th><s:label value="#" /></th>
							<th><s:label value="姓" /></th>
							<th><s:label value="名" /></th>
							<th><s:label value="ふりがな" /></th>
							<th><s:label value="住所" /></th>
							<th><s:label value="電話番号" /></th>
							<th><s:label value="メールアドレス" /></th>
						</tr>
						<s:iterator value="destinationList">
						<tr>
							<td><input type="radio" name="id" checked="checked"
								value="<s:property value='id'/>" /></td>
							<td><s:property value="familyName" /></td>
							<td><s:property value="firstName" /></td>
							<td><s:property value="familyNameKana" />&emsp;<s:property
									value="firstNameKana" /></td>
							<td><s:property value="userAddress" /></td>
							<td><s:property value="telNumber" /></td>
							<td><s:property value="email" /></td>
						</tr>
						</s:iterator>
					</table>

					<div class="submit_btn_box">
						<s:submit value="決済" class="submit_btn"
							onClick="setAction('SettlementCompleteAction')" />
					</div>
					<div class="submit_btn_box">
						<s:submit value="削除" class="submit_btn"
							onClick="setAction('DeleteDestinationAction')" />
					</div>
					<div class="submit_btn_box">
						<s:submit value="新規宛先登録" class="submit_btn"
							onClick="setAction('CreateDestinationAction')" />
					</div>
				</s:form>
			</div>
		</s:if>
		<s:else>
			<s:form id="form">
				<div class=messageResult>宛先情報がありません。</div>
				<div class="submit_btn_box">
					<s:submit value="新規宛先登録" class="submit_btn"
						onClick="setAction('CreateDestinationAction')" />
				</div>
			</s:form>
		</s:else>
	</div>
</body>
</html>