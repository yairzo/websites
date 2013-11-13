<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="infoPageDetails" scope="page" class="huard3.actions.TabledInfoPageHandler"/>
<jsp:setProperty name="infoPageDetails" property="*"/>

<html>
<head>
<title> Information Page Details Handling</title>
</head>
<frameset rows="50%,*">

<% if ( infoPageDetails.getTabledInfoPageByArdNum().isHasLocalWebPage() ) {%>
	<frame src="pageExecuterFullText.jsp?ardNum=<%=infoPageDetails.getArdNum()%>"  >
<% }
else if (infoPageDetails.getInfoPageByArdNum().getArdNum() !=1990){ %>
	<frame src="http://<%= infoPageDetails.getTabledInfoPageByArdNum().getPageWebAddress() %>"  >
<%}%>

<frame src="infoPageTabledDetails.jsp?ardNum=<%=infoPageDetails.getArdNum()%>" scrolling="yes" >

</frameset>

</html>
