<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="Windows-1255" %>

<jsp:useBean id="handler" scope="page" class="huard3.actions.InfoPageHandler"/>
<jsp:setProperty name="handler" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<html>
<head>
  <title>Copying a InfoPage </title>
</head>

<body>


<%if (identify.isAuthorized()){%>




	<% int newArdNum = handler.copyInfoPage(identify.getUsername()); %>
	You've copied InfoPage <%=handler.getArdNum() %> to InfoPage <%=newArdNum %>.



   <form action="infoPagesList.jsp" method="post">


     <br>
     <br>
     <input type="submit" value="O.K">
   </form>




<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

</body>
</html>
