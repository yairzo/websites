<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="pubPageDeleter" scope="page" class="huard3.actions.PubPageDeleter"/>
<jsp:setProperty name="pubPageDeleter" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<%if (identify.isAuthorized()){%>

<html>
	<head>
		<title>
			Delete a Publication Page
		</title>
	</head>
	<body>
		<form action="pubPagesList.jsp" method="post">
  		<center>
		   <%pubPageDeleter.deletePubPage(identify.getUsername());%>
      			<font size=+2>Publication <%=pubPageDeleter.getArdNum()%> was moved to Old Documents Table<br>
      			<input type="submit" value="O.K">

  		</center>
		</form>


<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

</body>
</html>