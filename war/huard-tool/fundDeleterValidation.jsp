<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="fundDeleter" scope="page" class="huard3.actions.FundDeleter"/>
<jsp:setProperty name="fundDeleter" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<body>
 <form action="fundDeleterAction.jsp" method="post">
  <center><font size=+2>Are you sure you want to delete fund number <%=fundDeleter.getFundNum()%> ?</font>
  <input type="hidden" name="fundNum" value="<%=fundDeleter.getFundNum()%>">
  <input type="submit" value="yes">
  <input OnClick="window.history.back()" type="button" value="No">

  </center>
 </form>
</body>


<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>
