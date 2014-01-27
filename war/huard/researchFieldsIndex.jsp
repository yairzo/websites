<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="researchFieldsIndex" scope="page" class="huard.website.viewer.profile.ResearchFieldsIndex" />
<jsp:setProperty name="researchFieldsIndex" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<%
response.setStatus(301);
response.setHeader( "Location", "https://ard.huji.ac.il/search_funding" );
response.setHeader( "Connection", "close" );
%>

<% System.out.println("Viewing researchFieldIndex.jsp"); %>

<html>
<head>

<script src="js/cookiesFuncs.js"/>
<script>
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 boolean isHebrew = language.isHebrewVer();
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(researchFieldsIndex.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(researchFieldsIndex.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(researchFieldsIndex.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>


<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%= researchFieldsIndex.getTitle(isHebrew)%></title>



<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-8-i">
<meta http-equiv="imagetoolbar" content="no">
<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script type="text/javascript">document.write(unescape("%3Cscript src='" + (("https:" == document.location.protocol) ? "https" : "http") + "://cdn.mouseflow.com/projects/c7bfcd1a-0fba-4cbc-9dac-af443d1c2be8.js' type='text/javascript'%3E%3C/script%3E"));</script>

<jsp:include page="include/menuHead.jsp" />

</head>

<body <%= language.getLang()==null ? "onLoad=\"manageLanguage()\"" : "" %> leftMargin=0 topmargin="0" marginheight="0" >

<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />

<table border="0" cellpadding="0" cellspacing="1" height="80%" width="100%" dir="<%=dir%>">
	<tbody>
  		<tr>
			<td height="15" background="images/menu_bg.jpg"></td>
			<td class="<%=category.getClassColor(isHebrew)%>">
    			<p class="small" dir="<%=dir%> %>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=researchFieldsIndex.getTitle(isHebrew)%>
    		</td>
		</tr>
  		<tr>
				<td width="200" valign="top" background="images/menu_bg.jpg"><br>
					<p class="menu" dir="<%=dir%>"" align="<%=align%>">
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
						<br>
					</p>
				</td>
			<td valign="top">
    			<table border="0" cellpadding="10" width="700">
      				<tbody>
      					<tr>
							<td dir="<%=dir%>" align="<%=align%>">
								<table border="0" width="100%" dir="<%=dir%>">
        							<tbody>
										<tr>
		      								<td>
			      								<h1 ><%= researchFieldsIndex.getTitle(isHebrew)%></h1>
                      						</td>
										</tr>
                					</tbody>
								</table>
								<ul>
									<% for (int i=0; i<researchFieldsIndex.getResearchFields().length; i++){%>
										<li>
										<a href="researchField.jsp?researchFieldNum=<%=(i+1)%>"><%= isHebrew ? researchFieldsIndex.getResearchFields()[i].getHebrewName() : researchFieldsIndex.getResearchFields()[i].getEnglishName() %></a>
										</li>
									<%}%>
								</ul>
								<jsp:include page="include/allRightsReserved.jsp" />
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</tbody>
</table>
<jsp:include page="include/googleAnalytics.jsp"/>
</body></html>