<%@ page language="java" contentType="text/html;charset=UTF-8" info="HUARD test page" import="huard3.actions.*" %>
<%@ page pageEncoding="UTF-8"%> 
<jsp:useBean id="oldInfoPagesList" scope="page" class="huard3.actions.OldInfoPagesList" />
<jsp:setProperty name="oldInfoPagesList" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){%>

<html>
 <head>
   <meta http-equiv="Refresh" content="180" />

   <title>Old InfoPages Page</title>
 </head>

 <body>

   <a href="infoPagesList.jsp"><font size=+1>InfoPages List</font></a>
   <a href="oldInfoPagesList.jsp"><font size=+1>Sort By ARD Number</font></a>
   <br>
   <center>
   <font size=+2>Old InfoPages List</font>
   <br>
   <br>

   <table border=1 width=700>
     <%for (int i=0;i<=(oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId()).length-1);i++){%>
            <tr>
              <td><a href="viewInfoPageDetails.jsp?ardNum=<%=oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId())[i].getArdNum()%>&table=OldInfoPages"><%=oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId())[i].getArdNum()%></a></td>
	      <td><%=oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId())[i].getTitle()%></td>
              <td><%=oldInfoPagesList.getFormatedSubDate(oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId())[i].getSubDate())%></td>
              <td><a href="infoPageReviver.jsp?ardNum=<%=oldInfoPagesList.getInfoPagesSortedByTitleByDesk(identify.getUsersDeskId())[i].getArdNum()%>">revive</a></td>

            </tr>



     <%}%>
   </table>
   </center>
 </body>
</html>

<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>
