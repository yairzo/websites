<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="pubPageDetails" scope="page" class="huard3.actions.PubPageHandler"/>
<jsp:setProperty name="pubPageDetails" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<html>
<head>
<title> Information Page Details Handling</title>
</head>


<frameset rows="50%,*" >


	<frame src="viewExternalDocumentForm.html" name="frame1">

<frameset >

   	<frame src="pubPageDetails1.jsp?ardNum=<%= pubPageDetails.getArdNum()%>&hebrew=<%= pubPageDetails.isHebrew()%>&message=<%= pubPageDetails.isMessage()%> " name="frame3" scrolling="yes" >


</frameset>

</html>