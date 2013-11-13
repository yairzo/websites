<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>

<%request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="infoPageValidator" scope="page" class="huard3.actions.TabledInfoPageValidator"/>
<jsp:setProperty name="infoPageValidator" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){
     if (infoPageValidator.isInfoPageStillHoldedByMe(identify.getUsername())){%>
        <html>
          <head>
	    <title>Validating Information Page TabledDetails</title>
	    <link href="style_ard.css" rel="stylesheet" type="text/css">
          </head>
          <body>
	  <a name="top">
	   <form target="_top" action="updateTabledInfoPageDetailsToDatabase.jsp" method="post">

            <div style="text-align: center;"><big><big>Validating Call for Proposal Details<br></big>
	                                                 for document: <%=infoPageValidator.getTabledInfoPage().getTitle()%>
							 ARD number: <%=infoPageValidator.getTabledInfoPage().getArdNum()%></big><br></div>
             General Information
	     <table border="1" width="550">
  	       <tbody>

		   <tr>
      			<td>Submission Site</td>
      			<td><%=infoPageValidator.getTabledInfoPage().getSubSite()%></td>

  		  </tr>

	<% if (infoPageValidator.getTabledInfoPage().getSubSite().equals("Fund")){ %>

    <tr>
      <td> Submission Date Funding Agency </td>
      <td>  <%=infoPageValidator.getFormatedDate(infoPageValidator.getTabledInfoPage().getSubDate())%></td>
    </tr>
    <tr>
    	<td>	Submission Date Details </td>
	<td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>">  <%=infoPageValidator.getTabledInfoPage().getSubDateDetails()%></td>
    </tr>

    <tr>
    <%}%>
    <% if (infoPageValidator.getTabledInfoPage().getSubSite().equals("ARD")){ %>
      <tr>
      <td> Submission Date at the <br> Authority for Research & Development </td>
      <td> <%=infoPageValidator.getFormatedDate(infoPageValidator.getTabledInfoPage().getSubDate())%></td>
    </tr>
     <tr>
     	<td>Submission Date at <br>Funding Agency</td>
     	<td><%=infoPageValidator.getTabledInfoPage().getSubDateFund()%> </td>
     </tr>
     <tr>
    	<td>	Submission Date Details </td>
	<td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>">  <%=infoPageValidator.getTabledInfoPage().getSubDateDetails()%></td>
    </tr>
    <tr>
    	<td>	Ask to send a copy by email </td>
    	<td	dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>">  <%=infoPageValidator.getTabledInfoPage().isSendDigitalCopy() ? "Yes" : "No"%></td>

     <%}%>

    		<tr>
      			<td> Desk + contact </td>
      			<td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getDeskAndContact()%> </td>
    		</tr>
    		<tr>
      			<td> Forms </td>
		      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%= infoPageValidator.getForms() %></td>
    		</tr>
	    </tbody>
	   </table>
<a href="#top">Back to top of page</a>
<br>
<br>
<br>
	    Description
	  <table border="1" width="550">
  	<tbody>
    	<tr>
    	</tr>
    	<tr>
      		<td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getDescription()%> </td>
    </tr>
  </tbody>
</table>
<a href="#top">Back to top of page</a>
<br>
<br>
<br>
Funding Details
<table border="1" width="550">
  <tbody>
    <tr>
    </tr>
    <tr>
      <td> Maximum Funding Period </td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getMaxFundingPeriod()%> </td>
    </tr>
    <tr>
      <td> Amount of Grant </td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getAmountOfGrant()%></td>
    </tr>
    <tr>
      <td> Eligibility Requirements </td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getEligibilityRequirements()%> </td>
    </tr>
    <tr>
      <td> Activity Location </td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getActivityLocation()%></td>
    </tr>
    <tr>
      <td> Possible Collaboration </td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getPossibleCollaboration()%></td>
    </tr>
    <tr>
      <td> Link to Call for Proposal </td>
      <td> <a href="http://ard.huji.ac.il/pageExecuter.jsp?ardNum=<%=infoPageValidator.getTabledInfoPage().getArdNum()%>&table=InfoPages">Call for Proposal Full Text </a></td>
    </tr>
  </tbody>
</table>
<a href="#top">Back to top of page</a> <br>
<br>
<br>
Budget Details
<table border="1" width="550">
  <tbody>
    <tr>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getBudgetDetails()%></td>
    </tr>
    <tr>
      <td> Budget officer automatic line will <%if (!infoPageValidator.isAppendBudgetOfficerLine()){%> NOT <%}%> be appended</td>
  </tbody>
</table>
<a href="#top">Back to top of page</a> <br>
<br>
<br>
Funding Agency
<table border="1" width="550">
  <tbody>
    <tr>
      <td>Link to Funding Agency Page or Funding Agency Website</td>
    </tr>
  </tbody>
</table>
<a href="#top">Back to top of page</a> <br>
<br>
<br>
Additional Information
<table border="1" width="550">
  <tbody>

    <% if (infoPageValidator.getTabledInfoPage().getNumOfCopies()>0){%>
    <tr>

      <td> Number Of Copies</td>
      <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getNumOfCopies()%> </td>
    </tr>
    <%}%>
  </tbody>
</table>
<table border=1 width="550">
   <tbody>
      <tr>
        <td dir="<%= infoPageValidator.isHebrew() ? "rtl" : "ltr" %>"> <%=infoPageValidator.getTabledInfoPage().getAdditionalInformation()%> </td>
     </tr>
   </tbody>
</table>
<a href="#top">Back to top of page</a>

 <br>
 <br>
 <input type="hidden" name="ardNum" value="<%=infoPageValidator.getTabledInfoPage().getArdNum()%>"/>

 <input type="hidden" name="subSite" value="<%=infoPageValidator.getTabledInfoPage().getSubSite()%>"/>

  <% if (infoPageValidator.getTabledInfoPage().getSubSite().equals("ARD")){ %>
  	<input type="hidden" name="subDateFund" value="<%=infoPageValidator.getTabledInfoPage().getSubDateFund()%>"/>
  <%}%>

  <input type="hidden" name="subDateDetails" value="<%=infoPageValidator.getTabledInfoPage().getSubDateDetails()%>"/>
 <input type="hidden" name="deskAndContact" value="<%=infoPageValidator.getTabledInfoPage().getDeskAndContact()%>"/>
 <input type="hidden" name="forms" value="<%=infoPageValidator.getTabledInfoPage().getForms()%>"/>
 <input type="hidden" name="description" value="<%=infoPageValidator.getTabledInfoPage().getDescription()%>"/>
 <input type="hidden" name="maxFundingPeriod" value="<%=infoPageValidator.getTabledInfoPage().getMaxFundingPeriod()%>"/>
 <input type="hidden" name="amountOfGrant" value="<%=infoPageValidator.getTabledInfoPage().getAmountOfGrant()%>"/>
 <input type="hidden" name="eligibilityRequirements" value="<%=infoPageValidator.getTabledInfoPage().getEligibilityRequirements()%>"/>
 <input type="hidden" name="activityLocation" value="<%=infoPageValidator.getTabledInfoPage().getActivityLocation()%>"/>
 <input type="hidden" name="possibleCollaboration" value="<%=infoPageValidator.getTabledInfoPage().getPossibleCollaboration()%>"/>
 <input type="hidden" name="budgetDetails" value="<%=infoPageValidator.getTabledInfoPage().getBudgetDetails()%>"/>
 <input type="hidden" name="numOfCopies" value="<%=infoPageValidator.getTabledInfoPage().getNumOfCopies()%>"/>
 <input type="hidden" name="additionalInformation"  value="<%=infoPageValidator.getTabledInfoPage().getAdditionalInformation()%>"/>
 <input type="hidden" name="sendDigitalCopy"  value="<%=infoPageValidator.getTabledInfoPage().isSendDigitalCopy()%>"/>
 <input type="hidden" name="appendBudgetOfficerLine"  value="<%=infoPageValidator.getTabledInfoPage().isAppendBudgetOfficerLine()%>"/>



 <input type="submit" value="Update">
 <input OnClick="window.history.back()" type="button" value="Cancel">
 </form>

</body>
</html>

 <%}
     else{%><html>
               <body><font size=+2>Sorry, page has expired, details may have been change by<br>
                          another user, you will have to update the details again.</font>
               </body>
            </html>

   <%}
   }
 else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>
