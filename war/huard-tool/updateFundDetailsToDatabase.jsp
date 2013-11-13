<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="fundValidator" scope="page" class="huard3.actions.FundValidator"/>
<jsp:setProperty name="fundValidator" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if(request.getParameter("aNewFund")!=null){
        fundValidator.setANewFund(request.getParameter("aNewFund"));
    }%>


<%if (identify.isAuthorized()){
     if ((fundValidator.isFundStillHoldedByMe(identify.getUsername()))||(fundValidator.isANewFund())){%>



<head>
  <title>Fund Details Validation</title>
</head>

<body>
  <%fundValidator.setANewFund(request.getParameter("aNewFund"));%>
  <%fundValidator.updateFundDetailsInDatabase();%>
  <%fundValidator.insertFundKeywordsIntoDatabase();%>
  <%fundValidator.releaseFund();%>

  <form action="fundsList.jsp" method="post">
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=+2>Details where inserted into database</font>
  <input type="submit" value="O.K">
  </form>
  <br>
  <br>
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
