<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.PubPage" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="pubPagesList" scope="page" class="huard3.actions.PubPagesList" />
<jsp:setProperty name="pubPagesList" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<%session.setMaxInactiveInterval(3600);%>

<%if (identify.isAuthorized()){
     pubPagesList.releasePubPagesHoldedToLong();%>

<html>
 <head>
   <meta http-equiv="Refresh" content="180" />
   <link href="style_ard.css" rel="stylesheet" type="text/css">
   <title>PubPages Page</title>
 </head>

 <body>



 <a href="pubPageDetailsIdx.jsp?ardNum=0&hebrew=false&message=<%=pubPagesList.isMessages()%>"> new English Document</a>&nbsp;&nbsp;
 <a href="pubPageDetailsIdx.jsp?ardNum=0&hebrew=true&message=<%=pubPagesList.isMessages()%>"> new Hebrew Document</a>

 <h1> <%=pubPagesList.isMessages() ? "Messages List" : "Information Pages List"%> </h1>


 <table border=1 width=900 align=center>
     <tr>
       <th width=50><font size=+2>Ard Number</th><th width=720><font size=+2>Title</th><th width=50><font size=+2>Document Type</th><th width=30><font size=+2>On Site</th><th width=30>&nbsp;</th>
     </tr>

     <% for (PubPage pubPage: pubPagesList.getPubPagesSortedByArdNum()){%>

     		<%if((!pubPagesList.isPubPageBusy(pubPage.getArdNum()))||pubPagesList.isPubPageStillHoldedByMe(pubPage.getArdNum(),identify.getUsername())){%>



	<tr>
	    <td width=50><a href="pubPageDetails.jsp?ardNum=<%=pubPage.getArdNum()%>&message=<%= pubPage.isMessage() %>">
            <%= pubPage.getArdNum()%></a></td>
	    <td width=720><%=pubPage.isDeleted() ? "<font color=grey>" : "<b>"%><%= pubPage.getTitle()%><%=pubPage.isDeleted() ? "</font>" : "</b>"%></td>
	    <td width=50><%= pubPage.getDocType()%></td>
            <td width=30><%= pubPage.isOnSite() ? "*" : "" %></td>
	    <td width=30>
	    <% if (pubPage.isDeleted()){ %>
	    	<a href="pubPageReviver.jsp?ardNum=<%=pubPage.getArdNum()%>">revive</a>
	    <%}else{%>
	    	<a href="pubPageDeleter.jsp?ardNum=<%=pubPage.getArdNum()%>">delete</a>
	     <%}%>
	     </td>
    </tr>






         <%}
         else {%>
           <tr>
	     <td width=50><%= pubPage.getArdNum()%></td>
	     <td width=790><%= pubPage.getTitle()%></td>
	     <td width=30><%=pubPagesList.getUserOccupyingPubPage(pubPage.getArdNum())%></td>
	  </tr>
         <%}
     }%>




   </table>





<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

</body>
</html>