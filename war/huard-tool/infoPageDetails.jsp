
<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="infoPageDetails" scope="page" class="huard3.actions.InfoPageHandler"/>
<jsp:setProperty name="infoPageDetails" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>




<%if (identify.isAuthorized()){
      if((!(infoPageDetails.isInfoPageBusy()))||(infoPageDetails.isInfoPageStillHoldedByMe(identify.getUsername()))){%>
		<html>
		<head>
			<title>InfoPage Details</title>
		</head>
		<body>
			<% infoPageDetails.getInfoPageByArdNum(); %>
  			<center>
				<font size=+2>InfoPage Details Handling &nbsp;</font><%= ((identify.isAuthorizedToEdit(infoPageDetails.getDeskId()) || infoPageDetails.isNewInfoPage()) ? "" : "You are only authorized to <b>watch</b> this document, it belongs to another desk") %>
			</center>
  			<br><br>
   			<form action="validateInfoPageDetails.jsp" method="get">

    			<table border=1 width=990 height=220>
     				<tr>
       					<td>
					ARD Number: <%=infoPageDetails.getInfoPageByArdNum().getArdNum()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   			<input type="hidden" name="ardNum" value="<%=infoPageDetails.getInfoPageByArdNum().getArdNum()%>"/>
                   			<%=(infoPageDetails.getInfoPageByArdNum().isDraft() ? " (Draft)" : "") %>
					</td>

       					<%infoPageDetails.setInfoPageAsBusy(identify.getUsername());%>
						<% if (infoPageDetails.isNewInfoPage() || infoPageDetails.getInfoPageByArdNum().isDraft()){ %>
       					<td>
						Title: <input type="text" size="50" name="title" value="<%=infoPageDetails.getInfoPageByArdNum().getTitle()%>" >
					</td>

       					<td>
						Publication<br> Date: <input type="text" size="8" name="pubDate" value="<%=infoPageDetails.getFormatedPubDate()%>">
					</td>
					<%}else{ %>
						<td>
						Title: <%=infoPageDetails.getInfoPageByArdNum().getTitle()%>
						<input type="hidden" name="title" value="<%=infoPageDetails.getInfoPageByArdNum().getTitle()%>"/>
					</td>

       					<td>
						Publication<br> Date: <%=infoPageDetails.getFormatedPubDate()%>
						<input type="hidden" name="pubDate" value="<%=infoPageDetails.getFormatedPubDate()%>"/>
					</td>
				   <%} %>

       					<td>
						Submission<br> Date: <input type="text" size="8" name="subDate" value="<%=infoPageDetails.getFormatedSubDate()%>"><br>
           					All Year <input type="checkbox" name="allYear" value="allYear" <%= ( (infoPageDetails.getInfoPageByArdNum().getSubDate()==0&&(!infoPageDetails.isNewInfoPage())) ? "checked=\"checked\"" : "" )%>"/>
					</td>
     				<tr>
				       <td>
				       		Fund:<br> <select name="fundShortName" size="1">
                              			<% for (int i=0; i<infoPageDetails.getAllFundsShortNames().length; i++){ %>
                                   			<option <%=                               				( infoPageDetails.getAllFundsShortNames()[i].equals( 		infoPageDetails.getFundShortName() ) ? "selected" : "" ) 	%>><%=infoPageDetails.getAllFundsShortNames()[i]%>
                                   			</option>
                               			<%}%>
                          			</select>
					</td>
				         <td>
					 	Document Type:<br> &nbsp;&nbsp;&nbsp;&nbsp;<select name="docType" size="1">
		                              	<% for (int i=0;i<=infoPageDetails.getAllDocTypes().length-1;i++){%>
                              				<option <%=(infoPageDetails.getAllDocTypes()[i].equals(infoPageDetails.getInfoPageByArdNum().getDocType()) ? "selected" : "")%>><%=infoPageDetails.getAllDocTypes()[i]%>
                              			<%}%>
                          			</select>
					</td>
					<td>
						Desk Id:<br> <select name="deskId" size="1">
		                              <% for (int i=0;i<=infoPageDetails.getAllDeskIds().length-1;i++){%>
                              				<option <%=(infoPageDetails.getAllDeskIds()[i].equals(infoPageDetails.getInfoPageByArdNum().getDeskId()) ? "selected" : "")%>><%=infoPageDetails.getAllDeskIds()[i]%></option>
                              		      <%}%>
                          			</select>
					</td>

       					<td>
						Document Owner:<br> <select name="docOwner" size="1">
		                              <% for (int i=0;i<=infoPageDetails.getAllDocOwners().length-1;i++){%>
                              				<option <%=(infoPageDetails.getAllDocOwners()[i].equals(infoPageDetails.getInfoPageByArdNum().getDocOwner()) ? "selected" : "")%>><%=infoPageDetails.getAllDocOwners()[i]%></option>
                              		      <%}%>
                          			</select>
					</td>
    				</tr>
    				<tr>
       					<td>
						Research Fields: <br>
						<% for (int i=0;i<=infoPageDetails.getResearchFieldsIntArray().length-1;i++){%>
                                      			<input type="checkbox"
                                         		<% if(infoPageDetails.getResearchFieldsIntArray()[i]==1){%> 		checked="checked"<%}%>
                                         		name="<%=infoPageDetails.getAllResearchFieldsOrderdByNum()[i].getResearchFieldShort()%>" value="<%=infoPageDetails.getAllResearchFieldsOrderdByNum()[i].getResearchFieldShort()%>"/>&nbsp;<%=infoPageDetails.getAllResearchFieldsOrderdByNum()[i].getResearchFieldName()%><br>
			                        <%}%>
       					</td>
				         <td>
					 	Additional Parameters:<br>
                      			        <% if (infoPageDetails.isHasRecordInTabledInfoPagesTable()){%>
		      					<input type="checkbox" <% if(infoPageDetails.getInfoPageByArdNum().isHasTabledVersion()){%> 	checked="checked"
							<%}%>
                            				name="hasTabledVersion" value="hasTabledVersion"/>&nbsp; Has Tabled Version
		      			        <%}else{%>
		      					'Has Tabled Version' checkbox will be enabled after<br>
							entering and verifying the table's details.<br>
							Marking this checkbox is a must for uploading<br>
							a document to the site<br>
		      				<%}%>
                      				<input type="checkbox" <% if(infoPageDetails.getInfoPageByArdNum().isRepetitive()){%> 			checked="checked"
						<%}%>
                            			name="repetitive" value="repetitive"/>&nbsp; The Document is Repetative <br>

						<input type="checkbox" <% if(infoPageDetails.getInfoPageByArdNum().isDescriptionOnly()){%> 			checked="checked"
						<%}%>
                            			name="descriptionOnly" value="descriptionOnly"/>&nbsp; View Only Description Field <br>

		      				<input type="checkbox" <% if(infoPageDetails.getInfoPageByArdNum().isRestricted()){%> 			checked="checked"
						<%}%>
                            			name="restricted" value="restricted"/>&nbsp; The Document is Restricted to HUJI Members Only<br>
						<input type="checkbox" <%= infoPageDetails.getInfoPageByArdNum().isHasLocalWebPage() ? "checked=\"checked\"" : "" %>
						name="hasLocalWebPage" value="hasLocalWebPage"/>&nbsp; Has Local Web Page <br> otherwise enter a full exact web address &nbsp; <input type="text" size=20 name="pageWebAddress" value="<%= infoPageDetails.getInfoPageByArdNum().getPageWebAddress() %>" />
					</td>
      					<td>
						Additional Submission Dates:<br>
					        &nbsp;&nbsp;<input type="text" size="8" name="secondSubDate" value="<%=infoPageDetails.getFormatedAdditionalSubDates()[0]%>"><br>
            					&nbsp;&nbsp;<input type="text" size="8" name="thirdSubDate" value="<%=infoPageDetails.getFormatedAdditionalSubDates()[1]%>"><br>
            					&nbsp;&nbsp;<input type="text" size="8" name="fourthSubDate" value="<%=infoPageDetails.getFormatedAdditionalSubDates()[2]%>"><br>
      					</td>
      					<td>
       						<a href="documentUploader.jsp?ardNum=<%=infoPageDetails.getArdNum()%>&username=<%=identify.getUsername()%>">Upload a new<br> Document</a><br><br>
       						<a href="documentExporter.jsp?ardNum=<%=infoPageDetails.getArdNum()%>&username=<%=identify.getUsername()%>">Export the<br> Document</a>
					</td>
			    </tr>
    		</table>
    		<table border=1 width=990 height=80>
      			    <tr>
      					<td>
						<input type="checkbox" <%= infoPageDetails.getInfoPageByArdNum().isKeepInRollingMessages() ? "checked=\"checked\"" : "" %>
						name="keepInRollingMessages" value="keepInRollingMessages"/>&nbsp; Should keep rolling in the<br>rolling messages window until the date entered below<br>
						Date to stop rolling: &nbsp; <input type="text" size=20 name="stopRollingDate" value="<%= infoPageDetails.getFormatedStopRollingDate() %>" />
		    			</td>
      			</tr>
			<tr>
          				  <td>
					  	<br>Insert Keywords: (As many as you like, separated by commas, that can help researchers find this page)
	  					<br> <input type="text" size="160" name="keywords" value="<%=infoPageDetails.getInfoPageByArdNum().getKeywords()%>"><br>
		     			</td>
      			</tr>
    		</table>
	    <%if(infoPageDetails.isNewInfoPage()){%>
        	 <iframe src="http://ard.huji.ac.il/huard/noDocInform.html" width=755 height=200 align=left>
	  	</iframe>
    	    <%}
      	    else{
      		if (infoPageDetails.getInfoPageByArdNum().isHasLocalWebPage()) {%>
       		          <iframe src="http://ard.huji.ac.il/huard/pageExecuterFullText.jsp?ardNum=<%=infoPageDetails.getInfoPageByArdNum().getArdNum()%>" width=755 height=200 align=left>
          		</iframe>
    		<%}
    		else if (infoPageDetails.getInfoPageByArdNum().getArdNum() !=1990) { %>
			<iframe src="http://<%=infoPageDetails.getInfoPageByArdNum().getPageWebAddress()%>" width=755 height=200 align=left>
          		</iframe>
		<%}
	   }%>
          <%if(infoPageDetails.isNewInfoPage()){%><input type="hidden" name="newInfoPage" value="true">
    	  <%}%>

    	  <table border=1 width=235 height=200>
       		<tr>
         		<td> <center>
		         	<% if (identify.isAuthorizedToEdit(infoPageDetails.getDeskId()) || infoPageDetails.isNewInfoPage()){ %>
	     				<input type="submit" value="Update">
	 			<% } %>
         			<input OnClick="window.history.back()" type="button" value="Cancel"></center>
			</td>
       		</tr>
     	</table>
	</form>


    <%}
    else{%><font size=+2>Sorry, infoPage is being updated by another user,<br>
                                   try again later</font>


    <%}
}
else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>

</body>
</html>
