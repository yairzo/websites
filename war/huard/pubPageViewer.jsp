<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

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

<%
response.setStatus(301);
response.setHeader( "Location", "https://ard.huji.ac.il" );
response.setHeader( "Connection", "close" );
%>

<% System.out.println("Viewing pubPageViewer.jsp"); %>


<% if (authorizer.isAuthorizedForLearningMode(request.getRemoteAddr())){
	pubPageUpdator.updatePubPageLocationDetails();
   }%>

<html>
<head>

<script language="JavaScript1.2" src="js/cookiesFuncs.js"/>
<script language="JavaScript1.2">
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
     boolean isHebrew = language.isHebrewVer();
	 pubPageViewer.logAccessToPage(language.isHebrewVer(), request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(pubPageViewer.getCategory(),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(pubPageViewer.getCategory(),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(pubPageViewer.getCategory(),isHebrew, language.isLanguageChanged());
%>

<script language="JavaScript1.2">
function openwindow(target)
{
window.open(target,
	"mywindow","menubar=1");
}
</script>
<title><%= language.isHebrewVer() ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%= pubPageViewer.getPubPage().getTitle()%></title>

<link href="style/style_ard.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">document.write(unescape("%3Cscript src='" + (("https:" == document.location.protocol) ? "https" : "http") + "://cdn.mouseflow.com/projects/c7bfcd1a-0fba-4cbc-9dac-af443d1c2be8.js' type='text/javascript'%3E%3C/script%3E"));</script>

<jsp:include page="include/menuHead.jsp"/>

</head>

<body leftMargin=0 topmargin="0" marginheight="0" >

<%// if (pubPageViewer.getPubPage().isFileRepresentation()){%><!--onLoad="javascript: openwindow('files/<%=pubPageViewer.getPubPage().getLink()%>')"--><%//}%>

<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />

<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>">
	<tbody>
  		<tr>
    			<td height="15" background="images/menu_bg.jpg">
    			</td>
    			<td class="<%=category.getClassColor(isHebrew)%>">
    				<p class="small" dir="<%=dir%> %>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=pubPageViewer.getPubPage().getTitle()%>
    			</td>
			</tr>
  			<tr>
				<td width="200" valign="top" background="images/menu_bg.jpg"><br>
					<p class="menu" dir="<%=dir%>" align="<%=align%>">
						<%=category.getName()%>:
						<br>
						<% for (MenuSubCategory menuSubCategory: upperSideMenuSubCategories){%>
							<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>">&nbsp;&nbsp;<%= menuSubCategory.getName() %>
							</a>
							<br/>
						<%}%>
					</p>
					<p align="center">
    					<IMG src="images/seperator3.gif" width="185" height="5">
    					<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "heb" : "eng"%>">
    						<%= isHebrew ? "דף הבית" :"Home" %>
    					</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "eng" : "heb"%>">
    						<%= isHebrew ? "English" : "Hebrew"%>
    					</a>
    					<IMG src="images/seperator3.gif" width="185" height="5">
    				</p>
					<p class="menu" dir="<%=dir%>"" align="<%=align%>">
 						<%= isHebrew ? "קישורים שימושיים" : "Useful Links" %>:
 						<br>
						<% for (MenuSubCategory menuSubCategory: lowerSideMenuSubCategories){%>
							<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>">&nbsp;&nbsp;<%= menuSubCategory.getName() %>
							</a>
							<br>
						<%}%>
						<% language.falsifyLanguageChanged();%>
						<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
					</p>
				</td>
  			<td valign="top">
    			<table border="0" cellpadding="30" width="100%">
        			<tbody>
						<tr>
          					<td>
            					<table class="funding" cellpadding="10">
              						<tbody>
										<tr>
    										<th class="<%=sideMenu.getCategory(pubPageViewer.getCategory(),false).getStyleClass()%>" style="vertical-align: top; padding: 20;" dir="<%= pubPageViewer.getPubPage().isHebrew() ? "rtl" : "ltr" %>" align="<%=pubPageViewer.getPubPage().isHebrew()  ? "right" : "left"%>">
												<table class="title" border="0" width="100%" dir="<%= pubPageViewer.getPubPage().isHebrew() ? "rtl" : "ltr" %>">
        											<tbody>
														<tr>
		      												<td class="title">
			      												<h1 ><%= pubPageViewer.getPubPage().getTitleHighlighted()%></h1>
                      										</td>
                      										<td width="150" align="<%=pubPageViewer.getPubPage().isHebrew() ? "left" : "right"%>">
		      													<p class="blacksmall"><%=pubPageViewer.getPubPage().isHebrew() ? "תאריך פרסום:" : "Publication Date:"%> <%=pubPageViewer.getPubPage().getFormatedPubDate()%><br>
                          											<%= pubPageViewer.getPubPage().isHebrew() ? "עדכון אחרון:" : "Last update:" %> <%=pubPageViewer.getFormatedPubPageLastUpdateDate()%>
																</p>
                      										</td>
                    									</tr>
                  									</tbody>
												</table>
												<p>
													<% if ( pubPageViewer.getPubPage().isFileRepresentation()){%>
														<%= pubPageViewer.getPubPage().isHebrew() ? "הקובץ שביקשת נפתח בחלון חדש" : "The File You Requested Was Opened in a New Window/Tab" %>
														<script language="javascript" type="text/javascript">
															<% if (pubPageViewer.getPubPage().isHebrew()){%>
																document.write('<p>מיד תחל ההורדה של הקובץ שבקשת, אם ההורדה לא החלה  <a href="<%=pubPageViewer.getPubPage().getLink()%>"">לחץ כאן.</a></p>');
															<%}else{%>
																document.write('<p>Your download should begin shortly. If it does not, <a href="<%=pubPageViewer.getPubPage().getLink()%>"">click here</a>"</p>');
															<%}%>
															window.setTimeout('document.location="<%=pubPageViewer.getPubPage().getLink()%>"', 2500);
														</script>
													<%}else{%>
														<%=pubPageViewer.getFormatedText(pubPageViewer.getPubPage().getHtml())%>
													<%}%>
												</p>
											</th>
										</tr>
									</tbody>
	      						</table>
								<jsp:include page="include/allRightsReserved.jsp"/>
							</td>
      					</tr>
      				</tbody>
   				</table>
			</td>
		</tr>
	</tbody>
</table>
<jsp:include page="include/googleAnalytics.jsp"/>
</body>
</html>