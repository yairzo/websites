<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*" %>
<%@ page pageEncoding="UTF-8"%>
<jsp:useBean id="logFileViewer" scope="page" class="huard3.actions.LogFileViewer" />
<jsp:setProperty name="logFileViewer" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<html>
 <head>
    <title> Viewing Personal Log File  </title>
 </head>
 
 <body>
 
  <%if (identify.isAuthorized()){ %>

     <center> <font size=+2> Actions Logged For Documents Owned By <%= identify.getUsername() %> </font> </center>
     <table border=1>
			<tr>
				<td> Username</td>
				<td> Action  </td>
				<td> ARD Number</td>
				<td> Title </td>
				<td> Document Type </td>
				<td> When </td>
				<td> New ARD Number </td>
 			</tr>

      <% for (int i=0; i<logFileViewer.getLogFile(identify.getUsername()).length; i++){ %>
		<tr>
		
                <td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getUsername() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getAction() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getArdNum() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getTitle() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getDocType() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getDate() %> </td>
		<td> <%= logFileViewer.getLogFile(identify.getUsername())[i].getNewArdNum() %> </td>

      		</tr>
      <% } %>
	</table>
  
  
  
  
  
  <% }

    else{ %>

    <font size =+2> Sorry, you probably don't have a personal log file </font>
    
  <% } %>
  
  </body>
  
 </html>
    

