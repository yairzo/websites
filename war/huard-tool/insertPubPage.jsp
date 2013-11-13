<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="pubPageUpdater" scope="page" class="huard3.actions.PubPageUpdater"/>
<jsp:setProperty name="pubPageUpdater" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />

<html>
	<head>
		<title> Updating PubPage </title>
	</head>
	<body>
		
		<form action="pubPagesList.jsp" target="_top" method="post">
		
		<% if (pubPageUpdater.insertOrUpdatePubPageToDatabase (identify.getUsername())){ %>
			Publication Page Was Inserted / Updated In Database
		<%}else{%>
			No Luck, There Were Problems Inserting /Updating the Publication Page to Database
		<%}%>
		
		<% if (pubPageUpdater.isMessage()){%>
			<input type="hidden" name="messages" value="true">
		<%}%>
		
		<input type="submit" value="O.K">
		</form>
	</body>
</html>