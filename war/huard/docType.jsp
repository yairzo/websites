<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="docTypeProfile" scope="page" class="huard.website.viewer.profile.DocTypeProfile" />
<jsp:setProperty name="docTypeProfile" property="*"/>

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

<html>
<head>

<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script src="js/cookiesFuncs.js"/>
<script>
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 boolean isHebrew = language.isHebrewVer();
	 docTypeProfile.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(docTypeProfile.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(docTypeProfile.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(docTypeProfile.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %> <%=docTypeProfile.getTitle(isHebrew)%></title>

<script language="javascript">
var titles = new Array(2);
var contents = new Array(2);


titles[0]   = new Array (
	<% for (int i=0; i<docTypeProfile.getInfoPagesByDocType().length; i++){%>
		"<td width=\"750\" style=\"text-align: <%=align%>;\"><table><tr><td><span dir=\"<%=docTypeProfile.getInfoPagesByDocType()[i].isHebrew() ? "rtl" : "ltr"%>\"><a style=\"text-decoration: none;\" href=\"infoPageViewer.jsp?ardNum=<%=docTypeProfile.getInfoPagesByDocType()[i].getArdNum()%>\"><%= docTypeProfile.getInfoPagesByDocType()[i].getTitle()%></a></span></td><td class=\"funding\">&nbsp;&nbsp;&nbsp;<%= docTypeProfile.getInfoPagesByDocType()[i].getSubmissionExpression(isHebrew)%>&nbsp;</td></tr></table></td>"
		<%= i<docTypeProfile.getInfoPagesByDocType().length-1 ? "," : "" %>
	<%}%>
);



contents[0] = new Array (
	<% for (int i=0; i<docTypeProfile.getInfoPagesByDocType().length; i++){%>
		"<%= docTypeProfile.getInfoPagesByDocType()[i].getFormatedDescription()%>"
		<%= i<docTypeProfile.getInfoPagesByDocType().length-1 ? "," : "" %>

	<%}%>

);



function CreateInfoPagesTable (){

	document.write(
	'<table class="funding" border="0" cellspacing="2" cellpadding="0" align="center" dir="<%=dir%>"><tr bgcolor="#f6f2e5">')
	<% for (int i=0; i< docTypeProfile.getInfoPagesByDocType().length; i++) {%>
		id1 = 'MyContentLine'+0+<%=i%>;
		id2 = 'MyPlusMinusImg'+0+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="funding" align="<%= isHebrew ? "right" : "left" %>">'
		+'<table class="profiles" boredr="0" width="750" height="100%"><tr>'
		+ titles[0][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<td width="25px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">'
		+'<img id="' + id2 +'"src="images/plus.gif"  alt="Opens a short description" title="Opens a short description"></td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td dir="<%=docTypeProfile.getInfoPagesByDocType()[i].isHebrew() ? "rtl" : "ltr" %>" class="fundingopen" style="text-align: <%=docTypeProfile.getInfoPagesByDocType()[i].isHebrew() ? "right" : "left" %>;" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">' + contents[0][<%=i%>] + '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
	<%}%>
	// end of table
	document.write(
	'</tr></table>'
	)
}






function toggleLine(id) {
	body=document.getElementById(id);
	if (body) {
		if (body.style.display == 'none') {
			// To make tr tags disappear we set display to none, as usual
        	// to make them appear again we set style to block for IE
        	// but for firefox we use table-row
        	try {
           			body.style.display='table-row';
            	} catch(e) {
              		body.style.display = 'block';
            	}
				return 'minus';
          	}
         else {
      		body.style.display = 'none';
			return 'plus';
       	}
   	}
}


function toggleLineImg(id1,id2) {
	flag = toggleLine(id1)
	theImg = document.getElementById(id2);
	if (flag=='plus') {
		theImg.setAttribute("src",'images/plus.gif');
		theImg.setAttribute("alt",'Opens a short description');
		theImg.setAttribute("title",'Opens a short description');
	} else {
		theImg.setAttribute("src",'images/minus.gif');
		theImg.setAttribute("alt",'Closes the short description');
		theImg.setAttribute("title",'Closes the short description');
	}
}


</script>
<script type="text/javascript">document.write(unescape("%3Cscript src='" + (("https:" == document.location.protocol) ? "https" : "http") + "://cdn.mouseflow.com/projects/c7bfcd1a-0fba-4cbc-9dac-af443d1c2be8.js' type='text/javascript'%3E%3C/script%3E"));</script>

<jsp:include page="include/menuHead.jsp" />
</head>
<body leftMargin=0 topmargin="0" marginheight="0" >

<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />

<table border="0" cellpadding="0" cellspacing="0" width="100%" dir="<%=dir%>">
 	<tbody>
  		<tr>
			<td height="15" background="images/menu_bg.jpg">
			</td>
			<td class="<%=category.getClassColor(isHebrew)%>">
				<p class="small" dir="<%=dir%> %>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=docTypeProfile.getTitle(isHebrew)%>
			</td>
		</tr>
  		<tr>
			<td width="200" valign="top" background="images/menu_bg.jpg">
				<br>
				<p class="menu" dir="<%=dir%>" align="<%=align%>">
					<%=category.getName()%>:
 					<br>
					<% for (MenuSubCategory menuSubCategory: upperSideMenuSubCategories){%>
						&nbsp;&nbsp;
						<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>"><%= menuSubCategory.getName() %>
						</a>
						<br>
					<%}%>
				</p>
				<p align="center">
    				<IMG src="images/seperator3.gif" width="185" height="5">
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "heb" : "eng"%>">
    				<%= isHebrew ? "דף הבית" :"Home" %></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "eng" : "heb"%>"><%= isHebrew ? "English" : "Hebrew"%>
    				</a>
    				<IMG src="images/seperator3.gif" width="185" height="5">
    			</p>
				<p class="menu" dir="<%=dir%>" align="<%=align%>">
 					<%= isHebrew ? "קישורים שימושיים" : "Useful Links" %>:
 					<br>
					<% for (MenuSubCategory menuSubCategory: lowerSideMenuSubCategories){%>
						<a class="rightmenu" href="<%=menuSubCategory.getLink() %>">&nbsp;&nbsp;<%= menuSubCategory.getName() %>
						</a>
						<br>
					<%}%>
					<% language.falsifyLanguageChanged();%>
				</p>
			</td>
			<td valign="top">
    			<table border="0" cellpadding="30" width="100%">
      				<tbody>
      					<tr>
							<td>
								<table width="100%" border="0" cellpadding="15">
									<tr>
										<td>
											<table border="0" width="100%" dir="<%=dir%>">
												<tbody>
        											<tr>
        												<td>
        													<h1>
        														<%=docTypeProfile.getTitle(isHebrew)%>
        													</h1>
        												</td>
														<td  width="150" align="<%= isHebrew ? "left" : "right"%>">
															<p class="small">
																<%= isHebrew ? "עדכון אחרון:" : "Last update:" %> <%=docTypeProfile.getLastUpdate()%>
															</p>
														</td>
													</tr>
												</tbody>
    										</table>
											<h2 dir="<%=dir%>" align="<%=align%>">
											<%if ( ! docTypeProfile.getInfoPagesByDocType()[0].getTitle().equals("No Results")){%>
												<script language="javascript1.2">
			 										CreateInfoPagesTable()
		 										</script>
											<%}else{%>
												<%= isHebrew ? " לא נמצאו" : "No " %>
												<%=docTypeProfile.getTitle(isHebrew)%></h2>
												<%= isHebrew ? "" : " Were Found" %>
											<%}%>
											<% if (isHebrew){%>
												<p>
													<a href="docType.jsp?docType=<%=docTypeProfile.getDocType()%>&fullList=<%= ! docTypeProfile.isFullList()%>"> <img border=0 src="images/<%= docTypeProfile.isFullList() ? "minus.gif" : "plus.gif" %>"></a>&nbsp;<%= docTypeProfile.isFullList() ? "לרשימה המצומצמת - קולות קוראים פתוחים להגשה" : "לרשימה המלאה - קולות קוראים פתוחים להגשה, וכאלו הנוטים לחזור בכל שנה" %>
												</p>
											<%}else{%>
												<p>
													<a href="docType.jsp?docType=<%=docTypeProfile.getDocType()%>&fullList=<%= ! docTypeProfile.isFullList()%>"> <img border=0 src="images/<%= docTypeProfile.isFullList() ? "minus.gif" : "plus.gif" %>"></a>&nbsp;<%= docTypeProfile.isFullList() ? "Reduced List - Calls for Proposals Open for Submission" : "Full List - Calls for Proposals Open for Submission, and Calls of Proposals that tends to repeat every year" %>
												</p>
											<%}%>
											<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
										</td>
      								</tr>
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
</body>
</html>