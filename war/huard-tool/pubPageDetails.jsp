<%@ page language="java" contentType="text/html; UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="pubPageDetails" scope="page" class="huard3.actions.PubPageHandler"/>
<jsp:setProperty name="pubPageDetails" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />


<html>
	<head>
			<script type="text/javascript" src="js/jquery-1.2.6.js"></script>
			<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
			<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>
			<script type="text/javascript">
			$(document).ready(function() {
				$( 'textarea#html' ).ckeditor( function() {}, { language: 'he' } );
			});
			</script>

     		<link href="js/style3.css" type="text/css" rel="stylesheet">
     		<title> Edit Publication Page Html </title>
  	</head>
  	<body>

	<%if (identify.isAuthorized()){
      		if((!(pubPageDetails.isPubPageBusy()))||(pubPageDetails.isPubPageStillHoldedByMe(identify.getUsername()))){%>

  	<% pubPageDetails.setPubPageAsBusy(identify.getUsername());%>
  	<br>
	<%= pubPageDetails.getPubPageByArdNum().isOnSite() ? " <font color=red size=+2> This Page is on site, the right way to do major editing is to create a new page, copy the html from old page, change it, and then link the new page instead of this one</font> " : "" %>



	<form name="form1" action="insertPubPage.jsp" method="post">

		<input type="hidden" name="ardNum" value="<%= pubPageDetails.getPubPageByArdNum().getArdNum()%>">
		<br>
		<br>

		<%= (pubPageDetails.isMessage() ? "Message Page Handeling" : "Information Page Handeling") %>
		<br>

		<br>
		Ard Number: <%= pubPageDetails.getPubPageByArdNum().getArdNum() %>
		<br>
		<br>
		Title:
		<% if (pubPageDetails.getPubPageByArdNum().isOnSite()){ %>
			<%= pubPageDetails.getPubPageByArdNum().getTitle() %>
			<input type="hidden" name="title" value="<%= pubPageDetails.getPubPageByArdNum().getTitle() %>"/>
		<%}else{%>
			<input name="title" value="<%= pubPageDetails.getPubPageByArdNum().getTitle() %>" size=80
			<%= pubPageDetails.isHebrew() ? "dir=\"rtl\"" : "\"\"" %> onClick="accessInputControl(this)">
		<%}%>
		<br>
		<br>


		html:
		<br>
		<textarea id="html" name="html"  rows="<%= pubPageDetails.getPubPageByArdNum().getHtml().length()/30 +10 %>" cols="75" <%= pubPageDetails.isHebrew() && (pubPageDetails.getPubPageByArdNum().isHebrewEdit() || pubPageDetails.isNewPubPage()) ? "dir=\"rtl\"" : "\"\"" %> onClick="accessInputControl(this)" >

			<%= pubPageDetails.getPubPageByArdNum().getHtml().replaceAll("\\*(.*?)\\*(.*?)\\*", "<a href=\"$2\">$1</a>").trim() %>

		</textarea>


		<br>
		<br>

		Desk Id: <select name="deskId" size="1">
	                       <% for (int i=0;i<=pubPageDetails.getAllDeskIds().length-1;i++){%>
        	                      <option <%=(pubPageDetails.getAllDeskIds()[i].equals(pubPageDetails.getPubPageByArdNum().	getDeskId()) ? "selected" : "")%>><%=pubPageDetails.getAllDeskIds()[i]%></option>
                              <%}%>
                          </select>
		<br>
		<br>
       		Document Owner: <select name="docOwner" size="1">

                              	<% for (int i=0;i<=pubPageDetails.getAllDocOwners().length-1;i++){%>
                              	<option <%=(pubPageDetails.getAllDocOwners()[i].equals(pubPageDetails.getPubPageByArdNum().getDocOwner()) ? "selected" : "")%>><%=pubPageDetails.getAllDocOwners()[i]%></option>
                               <%}%>
                          	</select>

		<br>
		<br>
		<input type="checkbox" <% if(pubPageDetails.getPubPageByArdNum().isRestricted()){%> checked="checked" <%}%> name="restricted" value="restricted"/>&nbsp; The Document is Restricted to HUJI Members Only<br>

		<br>
		Insert Keywords:
		 <br>
		 <input type="text" size="60" name="keywords" value="<%=pubPageDetails.getPubPageByArdNum().getKeywords()%>">
		 <br>
		 <br>

		 <input type="checkbox" name="hasImage" <%= pubPageDetails.getPubPageByArdNum().isHasImage() ? "checked=\"checked\"" : "" %> >
		 Add an Image on the top of the page <br>
		 Image Filename: <input type="text" name="imageFilename" value="<%= pubPageDetails.getPubPageByArdNum().getImageFilename() %>" size=30 >
		 <br>
		 <br>

		 <% if (pubPageDetails.isMessage()) {%>

		 	Message Type: <select name="docType" size="1">


                              	<option <%=(pubPageDetails.getPubPageByArdNum().getDocType().equals("Funding") ? "selected" : "")%>>Funding</option>
                               <option <%=(pubPageDetails.getPubPageByArdNum().getDocType().equals("ARD") ? "selected" : "")%>>ARD</option>
                          	</select>
			<br>
			<br>


			Expiration Date: <input type="text" name="expDate" value="<%=pubPageDetails.getPubPageByArdNum().getFormatedExpDate()%>" size=8/>
			<br>
			<br>
			<input type="checkbox" name="toRollingMessages" <%= (pubPageDetails.getPubPageByArdNum().isToRollingMessages() ? "checked=\"checked\"" : "" ) %>" />&nbsp; The Message will appear in the Rolling Messages Window
			<br>
			<br>
			Will dissapear from the Rolling Messages Window on: <input type="text" name="stopRollingDate" value="<%=pubPageDetails.getPubPageByArdNum().getFormatedStopRollingDate()%>" size=8/>
			<br>
			<br>
			<input type="hidden" name="message" value="true">
		<%}else{%>

		<input type="checkbox" name="fileRepresentation" <%= pubPageDetails.getPubPageByArdNum().isFileRepresentation() ? "checked=\"checked\"" : "" %> >
		 This is not a page it's only a file representation <br>

		 The web address of the file this page represents: <input type="text" name="link" value="<%= pubPageDetails.getPubPageByArdNum().getLink() %>" size=30 ><br>

		 <input type="hidden" name="docType" value="Information">

		<%}%>

		Reference File: <input type="text" name="referenceFile" value="<%= pubPageDetails.getPubPageByArdNum().getReferenceFile() %>" size=30 ><br>

		Source: <input type="text" name="source" value="<%= pubPageDetails.getPubPageByArdNum().getSource() %>" size=30 ><br>

		Internal Use Description: <br>
		<textarea name="internalUseDescription" rows="<%= pubPageDetails.getPubPageByArdNum().getInternalUseDescription().length()/30 +10 %>"   cols="75" <%= pubPageDetails.isHebrew() ? "dir=\"rtl\"" : "\"\"" %> onClick="accessInputControl(this)" >

		<%= pubPageDetails.getPubPageByArdNum().getInternalUseDescription()%>

		</textarea>
		<br>

		<input type="checkbox" name="wraper" <%= pubPageDetails.getPubPageByArdNum().isWraper() ? "checked=\"checked\"" : "" %> > This Pub Page wraps an external page<br>

		Source to wrap: <input type="text" name="sourceToWrap" value="<%= pubPageDetails.getPubPageByArdNum().getSourceToWrap() %>" size=60 ><br>

		<input type="checkbox" name="onSite" <%= pubPageDetails.getPubPageByArdNum().isOnSite() ? "checked=\"checked\"" : "" %> > Page is on site and will be indexed for search<br>

		<input type="hidden" name="pubDate" value="<%= pubPageDetails.getPubPageByArdNum().getPubDate()%>">

		 <%if(pubPageDetails.isNewPubPage()){%><input type="hidden" name="newPubPage" value="newPubPage">
    		<%}%>

		<br>


		<input type="submit" value="Update">
	 	<input onclick="window.history.back()" type="button" value="Cancel">

	</form>
















       <%}
    	else{%>
               <font size="+2">Sorry, pubPage is being updated by another user,<br>
                                   try again later</font>
       <%}
     }
     else {%>

	<font size="+2">Sorry, you are not authorized using this tool.</font>

   <%}%>

