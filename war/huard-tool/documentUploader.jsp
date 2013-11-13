<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="docUploader" scope="page" class="huard3.actions.DocumentUploader"/>
<jsp:setProperty name="docUploader" property="*"/>

<html>
  <head>
   <title>Document Uploading</title>
  </head>
  
  <body>
    <% if (docUploader.sendHtmlToSite()) { %>
         <font size=+1>Document was uploaded to site <br>
	 Please check for changes in: <a href="http://ard.huji.ac.il">website</a><br>
	   <form> or go <input OnClick="window.history.back()" type="button" value="Back">  if you have more details to change. </form><br>
	   Remember: if you go back you'll have to use the reload button to see the changes in the html document</font>
	   <br>
	   <br>
	   <a href="/huard/infoPagesList.jsp"> I finished with this Information Page take me to the Information Pages List</a>
	   
    <%}
        else{%>
	
	There where problems uploading the document to site<br>
	Please make sure you have a document resend.html in you /home/internet/ directory.
	<form>
	<input OnClick="window.history.back()" type="button" value="Back">
	</form>
	
	<%}%>
	
    </body>
</html>
    	
	
	    
