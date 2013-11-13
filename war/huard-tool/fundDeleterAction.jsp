<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="fundDeleter" scope="page" class="huard3.actions.FundDeleter"/>
<jsp:setProperty name="fundDeleter" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<body>
<form action="fundsList.jsp" method="post">
  
   
   <% if (fundDeleter.deleteFund()){%>
   	<font size=+2>Fund <%=fundDeleter.getFundNum()%> was deleted from the database</font>   
   <%}else{%>
   	<font size=+2>Fund <%=fundDeleter.getFundNum()%> still has Information Pages, you'll have to delete them first
	 </font>
	 <br>
	 The ARD Numbers of the Information Pages that belongs to this Fund:<br>
	 <% for (int i=0; i<fundDeleter.getFundsArdNums().length;i++){%>
	 	<%=fundDeleter.getFundsArdNums()[i]%>
		<br>
	<%}%>
	  
   <%}%>
   
   <input type="submit" value="O.K">
  
</form>
</body>

<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

