<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="fundValidator" scope="page" class="huard3.actions.FundValidator"/>
<jsp:setProperty name="fundValidator" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%fundValidator.setANewFund(request.getParameter("aNewFund"));%>

<%if (identify.isAuthorized()){
     if (fundValidator.isFundStillHoldedByMe(identify.getUsername())){%>

<head>
  <title>Fund Details Validation</title>
</head>

<body>



  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=+2>Validating Your Enteries</font>
  <br>
  <br>
  You Have entered this Information:
  <br>
  <br>
  Fund Number: <%=fundValidator.getFundNum()%>
  <br>
  <br>
  Fund Short Name: <%=fundValidator.getShortName()%>
  <br>
  <br>
  Fund Full Name: <%=fundValidator.getFullName()%>
  
  <br>
  <br>
  
  Web Address: <%=fundValidator.getWebAddress()%>
  <br>
  <br>
  Phone Number: <%=fundValidator.getPhoneNum()%>
  <br>
  <br>
  Fax: <%=fundValidator.getFax()%>
  <br>
  <br>
  Contact: <%=fundValidator.getContact()%>
  <br>
  <br>
  Mail Address: <%=fundValidator.getMailAddress()%>
  <br>
  <br>
  Desk Id: <%=fundValidator.getDeskId()%>
  <br>
  <br>
  Document Owner: <%=fundValidator.getDocOwner()%>
  <br>
  <br>
  Budget Officer: <%=fundValidator.getBudgetOfficer()%>
  <br>
  <br>
  Financial Reporter: <%=fundValidator.getFinancialReporter()%>
  
   Keywords: <%for(int i=0;i<fundValidator.getKeywordsArray().length;i++){%>
                 <%=fundValidator.getKeywordsArray()[i]%>,
            <%}%>
  <br>
  <br>

  <%if(fundValidator.isDetailsAreAllright()){%>


      <form action="updateFundDetailsToDatabase.jsp" method="post">
        <input type="hidden" name="originalFundNum" value="<%=fundValidator.getOriginalFundNum()%>">
        <input type="hidden" name="fundNum" value="<%=fundValidator.getFundNum()%>">
        <input type="hidden" name="shortName" value="<%=fundValidator.getShortName()%>">
        <input type="hidden" name="fullName" value="<%=fundValidator.getFullName()%>">
        
        <input type="hidden" name="webAddress" value="<%=fundValidator.getWebAddress()%>">
        <input type="hidden" name="phoneNum" value="<%=fundValidator.getPhoneNum()%>">
        <input type="hidden" name="fax" value="<%=fundValidator.getFax()%>">
        <input type="hidden" name="contact" value="<%=fundValidator.getContact()%>">
        <input type="hidden" name="mailAddress" value="<%=fundValidator.getMailAddress()%>">
        <input type="hidden" name="docOwner" value="<%=fundValidator.getDocOwner()%>">
        <input type="hidden" name="deskId" value="<%=fundValidator.getDeskId()%>">
        <input type="hidden" name="keywords" value="<%=fundValidator.getKeywords()%>">
	<input type="hidden" name="financialReporter" value="<%=fundValidator.getFinancialReporter()%>">
	<input type="hidden" name="budgetOfficer" value="<%=fundValidator.getBudgetOfficer()%>">
	
        <%if (fundValidator.isANewFund()){%>
           <input type="hidden" name="aNewFund" value="true">
        <%}%>
        <input type="submit" value="Update Database">
        <input OnClick="window.history.back()" type="button" value="Back">
      </form>
  <%}
   else{%>
      <form><input OnClick="window.history.back()" type="button" value="Go Fix The Details">
      </form>
  <%}%>

  <br>
  <br>



</body>
    <%}
     else{%><html>
               <body><font size=+2>Sorry, page has expired, details may have been changed by<br>
                          another user, you will have to update the details again.</font>
               </body>
            </html>

   <%}


 }
 else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>






