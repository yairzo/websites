<%@ page language="java" contentType="text/html"  import="huard3.actions.Worker" %>
<jsp:useBean id="fundDetails" scope="page" class="huard3.actions.FundHandler" />
<jsp:setProperty name="fundDetails" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<head>
	<link href="js/style3.css" type="text/css" rel="stylesheet">
	<script src="js/coolmenus4.js" type="text/javascript"></script>
  	<title>Funds Details</title>
</head>

<%if (identify.isAuthorized()){
     if((!(fundDetails.isFundBusy()))||(fundDetails.isFundStillHoldedByMe(identify.getUsername()))){%>

<body>
	<script type="text/javascript" src="js/pubPageDetailsMenuBarEnglishMOZ.js"></script>

  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=+2>Fund Details Handler</font>
   <form action="validateFundDetails.jsp" method="post">

  <table border=1 width=990 >
    <tr>

      <td>Fund ARD Number: <%=fundDetails.getFundByFundNum().getFundNum()%><br>

          <%fundDetails.setFundAsBusy(identify.getUsername());%>
          <input type="hidden" size=4 name="fundNum" value="<%=fundDetails.getFundByFundNum().getFundNum()%>">

      </td>



      <td>Fund Short Name:<br>
          <input type="text" size=20 name="shortName" value="<%=fundDetails.getFundByFundNum().getShortName()%>">
      </td>
      <td>Fund Full Name:<br>
          <input type="text" size=50 name="fullName" value="<%=fundDetails.getFundByFundNum().getFullName()%>">
      </td>
     </tr>
     <tr>
       <td>
         Fund KSF Number:<input type="text" size=4 name="ksfNum" value="<%=fundDetails.getFundByFundNum().getKsfNum()%>">
       </td>
       <td>Parent Fund Short Name:<br>
          <input type="text" size=50 name="parentFund" value="<%=fundDetails.getFundByFundNum().getParentFund()%>">
      </td>


       <td>
	&nbsp;
       </td>
      </tr>
      <tr>
       <td>
         Web Address:<br>
           <input type="text" size="50" name="webAddress" value="<%=fundDetails.getFundByFundNum().getWebAddress()%>">
       </td>

       <td>
         Phone Number:<br>
           <input type="text" size="20" name="phoneNum" value="<%=fundDetails.getFundByFundNum().getPhoneNum()%>">
       </td>


       <td>
         Fax:<br>
           <input type="text" size="20" name="fax" value="<%=fundDetails.getFundByFundNum().getFax()%>">
       </td>
     </tr>
     <tr>
       <td>
         Contact:<br>
           <input type="text" size="32" name="contact" value="<%=fundDetails.getFundByFundNum().getContact()%>">
       </td>
       <td>
         Mail Address:<br>
           <input type="text" size="32" name="mailAddress" value="<%=fundDetails.getFundByFundNum().getMailAddress()%>">
       </td>
       <td> Financial Reporter: <br><select name="financialReporter" size="1">
				<option></option>
                              <% for (int i=0;i<=fundDetails.getWorkersByTitle("Financial").length-1;i++){%>
                              <option <%=(fundDetails.getWorkersByTitle("Financial")[i].getEnglishName().equals(fundDetails.getFundByFundNum().getFinancialReporter()) ? "selected" : "")%>><%=fundDetails.getWorkersByTitle("Financial")[i].getEnglishName()%></option>
                              <%}%>
                          </select>
       </td>
   </tr>
   <tr>
   	<td> Budget Officer: <br><select name="budgetOfficer" size="1">
				<option></option>
                              <% for (int i=0;i<=fundDetails.getWorkersByTitle("Budget").length-1;i++){%>
                              <option <%=(fundDetails.getWorkersByTitle("Budget")[i].getEnglishName().equals(fundDetails.getFundByFundNum().getBudgetOfficer()) ? "selected" : "")%>><%=fundDetails.getWorkersByTitle("Budget")[i].getEnglishName()%></option>
                              <%}%>
                          </select>
       </td>
       <td>Desk Id:<br> <select name="deskId" size="1">

                              <% for (int i=0;i<=fundDetails.getAllDeskIds().length-1;i++){%>
                              <option <%=(fundDetails.getAllDeskIds()[i].equals(fundDetails.getFundByFundNum().getDeskId()) ? "selected" : "")%>><%=fundDetails.getAllDeskIds()[i]%></option>
                              <%}%>
                          </select>
       </td>

       <td>Document Owner:<br> <select name="docOwner" size="1">

                              <% for (int i=0;i<=fundDetails.getAllDocOwners().length-1;i++){%>
                              <option <%=(fundDetails.getAllDocOwners()[i].equals(fundDetails.getFundByFundNum().getDocOwner()) ? "selected" : "")%>><%=fundDetails.getAllDocOwners()[i]%></option>

                              <%}%>
                          </select>
      </td>
    </tr>
   </table>
   <table border=1 width=990 height=80>
      <tr>
          <td>Insert Keywords: (As many as you like but no more than 10, separated by commas, that can help researchers find this page) <br>
                     <input type="text" size="100" name="keywords" value="<%=fundDetails.getFundByFundNum().getKeywords()%>"><br>
      </tr>
    </table>

    <%if(fundDetails.isANewFund()){%>
      <input type="hidden" name="aNewFund" value="true">
    <%}%>
    <br>
    Free Text (html):<br>
    <textarea cols=120 rows=10 value="<%=fundDetails.getFundByFundNum().getHtml()%>"></textarea>
    <br>

    <iframe src="http://ard.huji.ac.il/f<%=fundDetails.getFundByFundNum().getFundNum()%>.htm" width=755 height=250 align=left>
    </iframe>

    <table border=1 width=235 height=250>
       <tr>

         <td> <center> <input type="submit" value="Update">
        <input OnClick="window.history.back()" type="button" value="Cancel"></center></td>
        </tr>
     </table>
  </form>

</body>

     <%}
      else{%><html>
               <body><font size=+2>Sorry, fund details are being updated by another user,<br>
                                   try again later</font>
               </body>
           </html>
      <%}
  }
  else{%><font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

