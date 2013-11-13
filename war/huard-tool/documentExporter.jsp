<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="docExporter" scope="page" class="huard3.actions.DocumentExporter"/>
<jsp:setProperty name="docExporter" property="*"/>

<html>
  <head>
   <title>Document Exporting</title>
  </head>
  
  <body>
    <% if (docExporter.exportHtml()) { %>
         <font size=+1>Document was Exported to /home/<%=docExporter.getUsername()%>/internet/resend.html <br>
	 Edit the file than upload it.</font><br>
	  <input OnClick="window.history.back()" type="button" value="Back">
    <%}
        else{%>
	
	There where problems exprting the document <br>
	Please contact the administrator.<br>.
	<form>
	<input OnClick="window.history.back()" type="button" value="Back">
	</form>
	
	<%}%>
	
    </body>
</html>