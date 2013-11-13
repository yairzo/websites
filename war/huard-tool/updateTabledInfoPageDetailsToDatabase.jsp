<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="infoPageValidator" scope="page" class="huard3.actions.TabledInfoPageValidator"/>
<jsp:setProperty name="infoPageValidator" property="*"/>



<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<%if (identify.isAuthorized()){
     if (infoPageValidator.isInfoPageStillHoldedByMe(identify.getUsername())){%>
        <html>
	  <body>
	  	
	    <form action="infoPagesList.jsp" method="post">
	     <center>
	        <%infoPageValidator.updateTabledInfoPageDetailsInTabledInfoPagesTable(infoPageValidator.getTabledInfoPage());%>
	        <font size=+2> Details Where Updated in Database </font><br>
		<input type="submit" value="O.K" />
	     </center>
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
