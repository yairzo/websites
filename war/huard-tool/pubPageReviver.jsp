<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*" %>
<jsp:useBean id="pubPageReviver" scope="page" class="huard3.actions.PubPageReviver" />
<jsp:setProperty name="pubPageReviver" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<html>
 <head>
   <title>Revive PubPage</title>
 </head>

 <body>
   <center>
   <form action="pubPagesList.jsp" method="post">
     <% pubPageReviver.revivePubPage(identify.getUsername());%>
      <font size=+2>Document: <%=pubPageReviver.getArdNum()%> was revived.<br>
      <input type="submit" value="O.K">
   </form>
   </center>


<%}
else{%>Sorry, you are not authorized
<%}%>

</body>

</html>