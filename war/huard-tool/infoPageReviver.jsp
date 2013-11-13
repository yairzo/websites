<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*" %>
<jsp:useBean id="infoPageReviver" scope="page" class="huard3.actions.InfoPageReviver" />
<jsp:setProperty name="infoPageReviver" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<html>
 <head>
   <title>Revive InfoPage</title>
 </head>

 <body>
   <center>
   <form action="infoPagesList.jsp" method="post">
     <% infoPageReviver.reviveInfoPage(identify.getUsername());%>
      <font size=+2>Info Page: <%=infoPageReviver.getArdNum()%> was revived.<br>


      <input type="submit" value="O.K">
   </form>


<%}
else{%>Sorry, you are not authorized
<%}%>

</center>
  </body>

</html>