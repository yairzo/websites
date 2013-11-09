<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="pubPageViewer" scope="page" class="huard.website.viewer.page.PubPageViewer" />
<jsp:setProperty name="pubPageViewer" property="*"/>

<jsp:useBean id="pubPageUpdator" scope="page" class="huard.website.update.PubPageUpdator" />
<jsp:setProperty name="pubPageUpdator" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<jsp:useBean id="authorizer" scope="session" class="huard.website.session.Authorizer" />
<jsp:setProperty name="authorizer" property="*"/>

<% System.out.println("Viewing pubPageViewerBasic.jsp"); %>

<html>
<head>

</head>

<body leftMargin=0 topmargin="0" marginheight="0" >
<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>">
	<td valign="top">
		<table border="0" cellpadding="30" width="100%">
			<tbody>
				<tr>
               		<td>
                  		<table width="100%" style="background: #efe5dc;" cellpadding="10">
              	     		<tbody>
	                			<tr>
    			   					<th style="background-color: #efe5dc;" class="<%=sideMenu.getCategory(pubPageViewer.getCategory(),false).getStyleClass()%>" style="vertical-align: top; padding: 20;" dir="<%= pubPageViewer.getPubPage().isHebrew() ? "rtl" : "ltr" %>" align="<%=pubPageViewer.getPubPage().isHebrew()  ? "right" : "left"%>">
			      						<p>
			         						<%=pubPageViewer.getFormatedText(pubPageViewer.getPubPage().getHtml())%>
			      						</p>
			      					</th>
								</tr>
		     				</tbody>
	         	 		</table>
					</td>
      	    	</tr>
			</tbody>
		</table>
	</td>
</table>
</body>
</html>