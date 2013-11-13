<%@ page language="java" contentType="text/html; charset=UTF-8" import="huard3.actions.InfoPage"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="infoPagesList" scope="page" class="huard3.actions.InfoPagesList" />
<jsp:setProperty name="infoPagesList" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<%session.setMaxInactiveInterval(3600);%>

<%if (identify.isAuthorized()){
     infoPagesList.releaseInfoPagesHoldedToLong();%>

<html>
 <head>
   <meta http-equiv="Refresh" content="180" />


   <title>Call for Proposals List</title>
 </head>

 <body>


   <a href="infoPageDetails.jsp"><font size=+1>New InfoPage</font></a>&nbsp;
   <a href="fundsList.jsp"><font size=+1>Funds List</font></a>&nbsp;
   <a href="oldInfoPagesList.jsp"><font size=+1>Old InfoPages List</font></a>&nbsp;
   <a href="logFileViewer.jsp"><font size=+1>View My Log File</font></a>&nbsp;
   <a href="viewDoc/viewDocForm.jsp"><font size=+1>View tabled Document</font></a>&nbsp;
   <a href="http://ard.huji.ac.il/tablesHandler/tableViewer.jsp?databaseName=<%=infoPagesList.getDatabaseName()%>&tableName=Workers"><font size=+1>Workers List</font></a>
   <a href="pubPagesList.jsp?messages=false"><font size=+1>Information Pages List</font></a>&nbsp;
   <a href="pubPagesList.jsp?messages=true"><font size=+1>Messages List</font></a>&nbsp;
   <a href="fileManager.jsp"><font size=+1>Manage Docs</font></a>&nbsp;


   <center>
   <font size=+2>Call for Proposals List</font>
   <br>
   <br>




   <table border=1 width=900>
     <tr>
       <td width=50>Ard Number</td><td width=650><font size=+2>Title</td><td width=70><font size=+2>First<br>Submission</td><td width=40 >Tends to<br>Repeat</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td>
     </tr>

       <% for (InfoPage infoPage: infoPagesList.getInfoPagesByDesk(identify.getUsersDeskId(), "ardNum")){%>
       	<%  if((!infoPagesList.isInfoPageBusy(infoPage.getArdNum())) || infoPagesList.isInfoPageStillHoldedByMe(infoPage.getArdNum(),identify.getUsername())){%>



	    <tr>
	    <td width=50><a href="infoPageDetails.jsp?ardNum=<%=infoPage.getArdNum()%>">
            <%= infoPage.getArdNum()%></a></td>
	    <td width=650><%=infoPage.isDeleted() ? "<font color=grey>" : "<b>"%><%= infoPage.getTitle()%><%=infoPage.isDeleted() ? "<font color=grey>" : "<b>"%></td>
	    <td width=70>
	        <% if(infoPage.getSubDate()==0){%>All Year
              <%}
                else{%><%= infoPagesList.getFormatedSubDate(infoPage.getSubDate())%>
		<%}%>
		</td>
	    <td width=40><%= ( infoPage.isRepetitive() ? " <center><b>*</b></center>" : "&nbsp;")%></td>
            <td width=30><a href="infoPageTabledDetailsIdx.jsp?ardNum=<%=infoPage.getArdNum()%>" title="edit tabled details" alt="edit tabled details">det</a></td>
	    <td width=30>
	    	<% if (infoPage.isDeleted()){%>
	    	   <a href="infoPageReviver.jsp?ardNum=<%=infoPage.getArdNum()%>">revive</a>
	    	  <%}else{%>
	    	   <a href="infoPageDeleterValidation.jsp?ardNum=<%=infoPage.getArdNum()%>">del</a>
	    	   <%}%>

			</td>
			<td>
				<a href="copyInfoPage.jsp?ardNum=<%=infoPage.getArdNum()%>&newInfoPage=true" title="create a new document like this one" alt="create a new document like this one" >copy</a>
            </tr>

         <%}else {%>
           <tr>
	     <td width=50><%= infoPage.getArdNum()%></td>
	     <td width=650><%= infoPage.getTitle()%></td>
	     <td width=70><%= infoPagesList.getFormatedSubDate(infoPage.getSubDate())%></td>
	     <td width=40><%= ( infoPage.isRepetitive() ? " <center><b>*</b></center>" : "")%></td>
             <td width=30 colspan="3"><%=infoPagesList.getUserOccupyingInfoPage(infoPage.getArdNum())%></td>
	  </tr>
         <%}
     }%>
   </table>



<%}

else {%>

<font size="+2">Sorry, you are not authorized using this tool.</font>

<%}%>

</center>
 </body>
</html>