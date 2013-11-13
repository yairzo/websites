<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="infoPageDetails" scope="page" class="huard3.actions.TabledInfoPageHandler"/>
<jsp:setProperty name="infoPageDetails" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<html>
  <head>
     <link href="js/style3.css" type="text/css" rel="stylesheet">
     <script src="js/coolmenus4.js" type="text/javascript"></script>
     <title> Edit Information Page Tabled Details </title>
  </head>
  <body>

  <script type="text/javascript" src="<%= infoPageDetails.isHebrewDoc() ? "js/infoPageDetailsMenuBarHebrew.js" : "js/infoPageDetailsMenuBarEnglish.js" %>"></script>



  <%if (identify.isAuthorized()){
      if((!(infoPageDetails.isInfoPageBusy()))||(infoPageDetails.isInfoPageStillHoldedByMe(identify.getUsername()))){%>





  <% infoPageDetails.getTabledInfoPageByArdNum();%>
  <% infoPageDetails.setInfoPageAsBusy(identify.getUsername());%>





<form name="form1" action="validateTabledInfoPageDetails.jsp" method="post">

    <a name="top">
    </a>
    <br><br><br>
<table border="0">

      <tbody><tr>
      <td>  <a href="#subSite">Submission Site</a>&nbsp;&nbsp;</td>

      <td>  <a href="#subDateFund">Submission Date - Fund</a>&nbsp;&nbsp;</td>
      <td>  <a href="#subDateDetails">Submission Date Details</a>&nbsp;&nbsp;</td>


       <td>  <a href="#deskAndContact">Desk and Contact</a>&nbsp;&nbsp;</td>

       </tr>
       <tr>
	<td>  <a href="#forms">Forms</a>&nbsp;&nbsp;</td>
	<td> <a href="#description">Description</a>&nbsp;&nbsp; </td>
	<td> <a href="#maxFundingPeriod">Maximum Funding Period</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td> <a href="#amountOfGrant">Amount of Grant</a>&nbsp;&nbsp; </td>

      </tr>
      <tr>

	<td> <a href="#eligibilityRequirements">Eligibility Requirements</a>&nbsp;&nbsp;</td>
	<td> <a href="#activityLocation">Activity Location</a>&nbsp;&nbsp;</td>
	<td> <a href="#possibleCollaboration">Possible Collaboration</a>&nbsp;&nbsp;</td>
        <td> <a href="#budgetDetails">Budget Details</a>&nbsp;&nbsp;</td>

      </tr>
      <tr>


	<td> <a href="#numOfCopies">Number Of Copies</a>&nbsp;&nbsp;</td>
	<td> <a href="#additionalInformation">Additional Information</a>&nbsp;&nbsp;</td>
	<td> <a href="#submit">Submit</a> &nbsp;&nbsp;</td>
	</tr>
	<tr>
	<td> <a href="/huard/fundDetails.jsp?fundNum=<%=infoPageDetails.getTabledInfoPageByArdNum().getFundNum()%>" target="view_window">Update Fund's Details</a>
	<td><a href="/huard/infoPagesList.jsp?username="<%=identify.getUsername()%>" target="_top">Back to InfoPagesList</a></td>
      </tr>
     </tbody>
</table>





     <br>

     <br>

     <br>




    <br>
<br>

<a name="subSite"></a>

<table border="0" valign="top">
<tbody>
<tr><td>
Submission Site:
<select name="subSite">
          <option <%= (infoPageDetails.getTabledInfoPageByArdNum().getSubSite().equals("ARD") ? "selected" : "") %> > ARD </option>
	   <option <%= (infoPageDetails.getTabledInfoPageByArdNum().getSubSite().equals("Fund") ? "selected" : "") %> > Fund </option>
</select>
</td><td>
<a href="#top">Back to list <br>of fields</a>
</td></tr>
</tbody>
</table>

<table border="0" valign="top">
<tbody>
<tr><td>
Send copy by email:

<input type="checkbox" name="sendDigitalCopy" <%=infoPageDetails.getTabledInfoPageByArdNum().isSendDigitalCopy() ? "checked=\"yes\"" : ""%>/>

</td><td>
<a href="#top">Back to list <br>of fields</a>
</td></tr>
</tbody>
</table>




<a name="subDateFund"></a>

<font color="red"> Remember: </font>if submission site is Fund you don't have to enter any additional date<br>
if submission site is ARD, you can enter the Fund submission date (not obligatory)<br>

<table border="0" valign="top">



          <tbody><tr>

		  <td> Submission date at Fund: </td>
		   <td><input type="text" name="subDateFund" size="8" value="<%=infoPageDetails.getTabledInfoPageByArdNum().getSubDateFund()%>" onClick="accessInputControl(this)"/>
		  </td>

	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
	</tbody>
</table>

<a name="subDateDetails"></a>

<table border="0" valign="top" >



          <tbody><tr>

		  <td> Submission date details: </td>
		   <td><textarea name="subDateDetails" onClick="accessInputControl(this)" rows="2" cols="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> ><%= infoPageDetails.getTabledInfoPageByArdNum().getSubDateDetails() %> </textarea>
		  </td>

	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
	</tbody>
</table>






    <br>
<br>
<a name="deskAndContact">
    </a>
<table>

	<tbody><tr>
         <td>
	           Desk + Contact (free text): </td>
		   <td> <textarea name="deskAndContact" rows="2" cols="75" onClick="accessInputControl(this)" align="left" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %>><%= infoPageDetails.getTabledInfoPageByArdNum().getDeskAndContact() %>
		   </textarea>
	 </td>



	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
    </tbody>
</table>


<a name="forms">
    </a>
<table>

        <tbody>
	<tr>
		<td> </td>
		<td> <font color="red">free text (optional) * link text (obligatory) * form file (obligatory) * free text (optional)  ~ </font></td>
	</tr>

	<tr>
           <td>
                   Forms (free text): </td>
		   <td><textarea name="forms" onClick="accessInputControl(this)" rows="3" cols="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %>><%= infoPageDetails.getTabledInfoPageByArdNum().getForms() %>
                   </textarea>
           </td>
	   <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
	</tbody>
</table>

<% if ( infoPageDetails.getFundsFormsFileNames().length >0 ) { %>
<table >
	<tbody>
		<tr>
			<td> Forms Files: </td>
			<td> <% for (int i = 0; i < infoPageDetails.getFundsFormsFileNames().length; i++) { %>
					 &nbsp;<%= infoPageDetails.getFundsFormsFileNames()[i] %>&nbsp; <% if ((i+1)%7==0) { %> <br> <%}%>
				<%}%>
			</td>
    	</tbody>
</table>

<%}%>

<a name="description">
</a>
<table>


	<tbody><tr>
	  <td>
	      Description:
	  </td>
	  <td>
	      <textarea name="description" onClick="accessInputControl(this)" rows="<%= infoPageDetails.getTabledInfoPageByArdNum().getDescription().length()/45 +10 %>" cols="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %>>	      <%= infoPageDetails.getTabledInfoPageByArdNum().getDescription() %>
	      </textarea>
	  </td>
	  <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
    </tbody>
</table>

<a name="maxFundingPeriod">
    </a>
<table>

	 <tbody><tr>
	 <td>
	     Maximum Funding Period:
	 </td>
	 <td>
	     <input type="text" name="maxFundingPeriod" onClick="accessInputControl(this)" size="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> value="<%= infoPageDetails.getTabledInfoPageByArdNum().getMaxFundingPeriod() %>">
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
    </tbody>
</table>

<a name="amountOfGrant">
</a>
<table>

	 <tbody><tr>
	 <td>
	     Amount of Grant:
	 </td>
	 <td>
	     <input type="text" name="amountOfGrant" onClick="accessInputControl(this)" size="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> value="<%= infoPageDetails.getTabledInfoPageByArdNum().getAmountOfGrant() %>">
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
    </tbody>
</table>
<a name="eligibilityRequirements">
    </a>
<table>

	 <tbody><tr>
	 <td>
	      Eligibility Requirements:
	 </td>
	 <td>
	      <input type="text" name="eligibilityRequirements" onClick="accessInputControl(this)" size="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> value="<%= infoPageDetails.getTabledInfoPageByArdNum().getEligibilityRequirements() %>">
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
     </tbody>
</table>
<a name="activityLocation">
     </a>
<table>

	 <tbody><tr>
	 <td>
	      Activity Location:
	 </td>
	 <td>
	     <input type="text" name="activityLocation" onClick="accessInputControl(this)" size="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> value="<%= infoPageDetails.getTabledInfoPageByArdNum().getActivityLocation() %>">
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
   </tbody>
</table>

<a name="possibleCollaboration">
   </a>
<table>

	 <tbody><tr>
	 <td>
	      Possible Collaboration:
	 </td>
	 <td>
	     <input type="text" name="possibleCollaboration" onClick="accessInputControl(this)" size="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %> value="<%= infoPageDetails.getTabledInfoPageByArdNum().getPossibleCollaboration() %>">
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
	 </tr>
   </tbody>
</table>
<a name="budgetDetails">
   </a>
<table>

	 <tbody><tr>
	 <td>
	     Budget Details:
	 </td>
	 <td>
	     <textarea name="budgetDetails" onClick="accessInputControl(this)" rows="<%= infoPageDetails.getTabledInfoPageByArdNum().getBudgetDetails().length()/45 +10 %>" cols="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %>>		   <%= infoPageDetails.getTabledInfoPageByArdNum().getBudgetDetails() %>
		   </textarea>
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
     <tr><td>
		Append 'Budget Officer' line:

		<input type="checkbox" name="appendBudgetOfficerLine" <%=infoPageDetails.getTabledInfoPageByArdNum().isAppendBudgetOfficerLine() ? "checked=\"yes\"" : ""%>/>

</td><td>
<a href="#top">Back to list <br>of fields</a>
</td></tr>
   </tbody>
</table>

<a name="numOfCopies">
   </a>
<table>

       <tbody><tr>
          <td> Number of Copies </td> <td><input type="text" name="numOfCopies" size="2" value="<%=infoPageDetails.getTabledInfoPageByArdNum().getNumOfCopies()%>"> </td>
       </tr>
   </tbody>
</table>
<br>
<br>
<br>
<a name="additionalInformation">


<table>

	<tbody><tr><td>
	     Additional Information:
	 </td>
	 <td>
	     <textarea name="additionalInformation" onClick="accessInputControl(this)" rows="<%= infoPageDetails.getTabledInfoPageByArdNum().getAdditionalInformation().length()/45 +10 %>" cols="75" <%= infoPageDetails.isHebrewDoc() ? "dir=\"rtl\"" : "dir=\"ltr\"" %>> <%= infoPageDetails.getTabledInfoPageByArdNum().getAdditionalInformation() %>
		   </textarea>
	 </td>
	 <td>
	 <a href="#top">Back to list <br>of fields</a>
	 </td>
        </tr>
   </tbody>
</table>



<center>
<a name="submit"> </a>        <input type="submit" value="Update">
	 <input onclick="window.history.back()" type="button" value="Cancel">
</center>





     <input type="hidden" name="ardNum" value="<%= infoPageDetails.getArdNum() %>">

</form>







<%}
    else{%>
               <font size="+2">Sorry, infoPage is being updated by another user,<br>
                                   try again later</font>
               <%}
}
else {%>

<font size="+2">Sorry, you are not authorized using this tool.</font>

<%}%>

<br>

</body></html>
