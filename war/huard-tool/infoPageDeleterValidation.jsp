<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="infoPageDeleter" scope="page" class="huard3.actions.InfoPageDeleter"/>
<jsp:setProperty name="infoPageDeleter" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<head>
 <title> Delete Validation </title>
</head>

<body>
 <form action="infoPageDeleterAction.jsp" method="post">
    <center>
    <% if (identify.isAuthorizedToEdit(infoPageDeleter.getDeskId())){ %>
        <font size=+2>Are you sure you want to delete Information Page <%=infoPageDeleter.getArdNum()%> ?</font><br><br>
        <input type="hidden" name="ardNum" value="<%=infoPageDeleter.getArdNum()%>">
        <input type="submit" value="yes">
        <input OnClick="window.history.back()" type="button" value="No">
    <% }
       else{ %>
          <font size=+2>Ooops! you are not authorized to delete this document, it belongs to another desk</font><br><br>
           <input OnClick="window.history.back()" type="button" value="Back">
    <% } %>

  </center>
 </form>
</body>

<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

