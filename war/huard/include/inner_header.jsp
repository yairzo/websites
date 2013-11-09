<%@page import="huard.website.menu.MenuCategory"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />


<table width="100%" border="0" dir="ltr">
  <tr>
    <td background="images/inner_bg.jpg">
	<table width="700" <%= request.getHeader("User-Agent").indexOf("MSIE") != -1 ? "" : "style=\"position: relative; left: 12px;\"" %> border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top" colspan="<%=topMenu.getCategoriesArray(language.getLang()).length%>" align="center"><img src="images/header_inner.jpg" width="700" height="96" border="0" usemap="#Map"></td>

		</tr>
		<tr>
			<td colspan="<%=topMenu.getCategoriesArray(language.getLang()).length+1%>" align="center" bgcolor="#787669" height="26">
		</tr>
		<tr>
			
          				<% 
          					int i=0;
          					for (MenuCategory menuCategory: topMenu.getCategoriesArray(language.getLang())){%>

					<td valign="top"><img src="images/<%=menuCategory.getBackgroundImage()%>" height="20" width="<%=menuCategory.getWidth()%>" ></td>

					<%
						i++;
          					}%>
					<td height="2px" background="images/colors_bg_edge.gif">&nbsp;</td>
        			</tr>
      	</table>
  </td>
  </tr>
</table>

<map name="Map">
  <area shape="rect" coords="4,63,298,102" href="main.jsp?lang=eng" alt="The Authority for Research and Development">
  <area shape="rect" coords="406,73,697,101" href="main.jsp?lang=heb" alt="הרשות למחקר ולפיתוח">
  <area shape="rect" coords="448,30,699,72" href="http://www.huji.ac.il" alt="אתר האוניברסיטה העברית">
</map>
