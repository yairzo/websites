<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>


<%= language.getLang()==null ? "<script language=\"JavaScript1.2\">manageLanguage()</script>" : "" %>

<% if (language.getLang()==null) language.setLang("eng");%>

<html>
<head>
<title><%= language.isHebrewVer() ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%= language.isHebrewVer() ? "הודעת שגיאה" : "Error Message"%></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-8-i">
<meta http-equiv="imagetoolbar" content="no">
<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script language="JavaScript1.2" src="js/cookiesFuncs.js">
</script>

<jsp:include page="include/menuHead.jsp" />

</head>

<body leftMargin=0 topmargin="0" marginheight="0" >

<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />

<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>">

  <tbody>
  <tr>

    <td height="15" bgcolor="<%= sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).getBgcolor()%>"></td>
    <td bgcolor="<%= sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).getBgcolor()%>"><p class="small" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left"%>">&nbsp;<%= sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).getName()%>   <%= language.isHebrewVer() ? "הודעת שגיאה" : "Error Message" %> </p> </td>


  </tr>
  <tr>

    <td bgcolor="<%= sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).getBgcolor()%>" valign="top" width="200"><br>


    <p class="menu" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left"%>">

	<!--<font size=+1>&nbsp;<%//= sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).getName()%>:</font><br>-->

	<% for (int i=0; i<sideMenu.getUpperSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer()).length;i++){%>
		<a class="rightmenu"  href="<%=sideMenu.getUpperSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer())[i].getLink() %>">&nbsp;&nbsp;<%= sideMenu.getUpperSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer())[i].getName() %>
		</a>
		<br>
	<%}%>



    </p>
    <hr color="#ffffff" noshade="noshade" size="1">
    <p align="center">
    <a class="rightmenu" href="main.jsp?lang=<%= language.isHebrewVer() ? "heb" : "eng"%>"><%= language.isHebrewVer() ? "דף הבית" :"Home" %></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="rightmenu" href="main.jsp?lang=<%= language.isHebrewVer() ? "eng" : "heb"%>"><%= language.isHebrewVer() ? "English" : "Hebrew"%></a>
    </p>
    <hr color="#ffffff" noshade="noshade" size="1">


<br>



 <p class="menu" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left"%>">
 <!--<font size=+1>&nbsp;<%//= language.isHebrewVer() ? "קישורים שימושיים" : "Useful Links" %>:</font><br>-->

<% for (int i=0; i<sideMenu.getLowerSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer(), language.isLanguageChanged()).length;i++){%>
		<a class="rightmenu"  href="<%=sideMenu.getLowerSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer(), false)[i].getLink() %>">&nbsp;&nbsp;<%= sideMenu.getLowerSideMenuSubCategories(language.isHebrewVer() ? "הרשות למופ" : "The Authority",language.isHebrewVer(), false)[i].getName() %>
		</a>
		<br>
	<%}%>

	<% language.falsifyLanguageChanged();%>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</p>
  </td>
  <td valign="top">
    <table border="0" cellpadding="30" width="100%">
        <tbody>
	<tr>
          <td>
            <table class="funding" cellpadding="10">
              <tbody>
	      <tr class="">
    			<th class="<%=sideMenu.getCategory(language.isHebrewVer() ? "הרשות למופ" : "The Authority",false).getStyleClass()%>" style="vertical-align: top; padding: 20;" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%=language.isHebrewVer()  ? "right" : "left"%>">

				<table border="0" width="100%" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>">
        				<tbody>
					<tr>
		      				<td>
			      				<h1 ><%= language.isHebrewVer() ? "שגיאה 500 - השרת לא יכול למלא את הבקשה" : "Html Error 500 - The Server Could Not Fulfill Your Request" %></h1>
                      				</td>

                    			</tr>
                  			</tbody>
				</table>
				<p>


				</p>

		</tr>
		</tbody>
	      </table>
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
