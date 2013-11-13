<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="Windows-1255" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="infoPageValidator" scope="page" class="huard3.actions.InfoPageValidator"/>
<jsp:setProperty name="infoPageValidator" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){
     if (infoPageValidator.isInfoPageStillHoldedByMe(identify.getUsername())){%>



<head>
  <title>Insert Details Into Database </title>
</head>

<body>


    <%if (infoPageValidator.updateInfoPageDetailsInDatabase(identify.getUsername())){%>
       <%infoPageValidator.insertInfoPageKeywordsIntoDatabase();%>
       <%infoPageValidator.insertInfoPageAdditionalSubDatesIntoDatabase();%>
       <% if (infoPageValidator.isNewInfoPage() && infoPageValidator.getInfoPage().isHasLocalWebPage()) { %>
             <%= (infoPageValidator.sendHtmlToSite(identify.getUsername()) ? "We are lucky, Document was moved to webSite successfully" : "There were problems moving document to site<br>please rename your html manualy<br>and notify system administrator ") %>
        <%}
      }%>
    <%infoPageValidator.releaseInfoPage();%>
   <form action="infoPagesList.jsp" method="post">

     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=+2>Details were updated in database</font>
     <br>
     <br>
     <input type="submit" value="O.K">
   </form>

</body>

      <%}
      else{%><html>
               <body><font size=+2>Sorry, page has expired, details may have been changed by<br>
                          another user, you will have to check the details and update again (if needed).</font>
               </body>
            </html>

    <%}
}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>


