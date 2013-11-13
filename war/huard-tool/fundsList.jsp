<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*" %>
<jsp:useBean id="fundsListQuery" scope="page" class="huard3.actions.FundsListQuery" />
<jsp:setProperty name="fundsListQuery" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<%if (identify.isAuthorized()){
     fundsListQuery.releaseFundsHoldedToLong();%>


<html>
 <head>
   <meta http-equiv="Refresh" content="180" />

   <title>Funds List Page</title>
 </head>

 <body>

   <a href="fundsListSortedByName.jsp"><font size=+1>Sort by Name</font></a>&nbsp;
   <a href="fundDetails.jsp"><font size=+1>New Fund</font></a>&nbsp;
   <a href="infoPagesList.jsp"><font size=+1>Information Pages List</font></a>&nbsp;
   <br>
   <center>
   <font size=+2>Funds List</font>
   <br>
   <br>

   <table border=1>
     <%for (int i=0;i<=(fundsListQuery.getFundsSortedByFundNum().length-1);i++){
        if ((!fundsListQuery.isFundBusy(fundsListQuery.getFundsSortedByFundNum()[i].getFundNum()))||(fundsListQuery.isFundStillHoldedByMe(fundsListQuery.getFundsSortedByFundNum()[i].getFundNum(),identify.getUsername()))) {%>
          <tr>
            <td> <a href="fundDetails.jsp?fundNum=<%=fundsListQuery.getFundsSortedByFundNum()[i].getFundNum()%>">
                 <%= fundsListQuery.getFundsSortedByFundNum()[i].getFundNum()%>&nbsp;&nbsp;&nbsp;<%= fundsListQuery.getFundsSortedByFundNum()[i].getFullName()%></a></td>
            <td><a href="fundDeleterValidation.jsp?fundNum=<%=fundsListQuery.getFundsSortedByFundNum()[i].getFundNum()%>">delete</a></td>
          </tr>
      <%}
        else {%>
           <tr><td>
            <%= fundsListQuery.getFundsSortedByFundNum()[i].getFundNum()%>&nbsp;&nbsp;&nbsp;<%= fundsListQuery.getFundsSortedByFundNum()[i].getFullName()%></td>
           <td>in use</td></tr>
         <%}
     }%>



   </table>
   </center>
 </body>
</html>

<%}
else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>
