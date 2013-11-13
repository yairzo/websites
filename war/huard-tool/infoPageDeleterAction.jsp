<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="infoPageDeleter" scope="page" class="huard3.actions.InfoPageDeleter"/>
<jsp:setProperty name="infoPageDeleter" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<html>
 <head>
 </head>

<body>
<form action="infoPagesList.jsp" method="post">
  <center>
   <% infoPageDeleter.deleteInfoPage(identify.getUsername());%>
      <font size=+2>Document <%=infoPageDeleter.getArdNum()%> was marked as Deleted<br>

      <input type="submit" value="O.K">

  </center>
</form>
</body>


<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

</html>
