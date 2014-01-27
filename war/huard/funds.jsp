<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="fundsProfile" scope="page" class="huard.website.viewer.profile.FundsProfile" />
<jsp:setProperty name="fundsProfile" property="*"/>

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
<script src="js/cookiesFuncs.js"/>
<script>
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 boolean isHebrew = language.isHebrewVer(); 
	 fundsProfile.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(fundsProfile.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(fundsProfile.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(fundsProfile.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%=fundsProfile.getTitle(isHebrew)%> </title>

<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script language="javascript">
var titles = new Array(<%=fundsProfile.getFunds().length%>);
var contents = new Array(<%=fundsProfile.getFunds().length%>);

function CreateFundTable(fundNum){

	document.write(
	'<table border="0" class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="funding">')
	for (i=0; i< titles[fundNum].length; i++){
		id1 = 'MyContentLine'+fundNum+i;
		id2 = 'MyPlusMinusImg'+fundNum+i;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="funding" align="<%=align%>">'
		+'<table border="0" class="profiles" width="900" height="100%"><tr>'
		+ titles[fundNum][i]
		+'</tr></table>'
		+'</th>'
		+'<td width="30px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">'
		+'<img id="' + id2 +'"src="images/plus.gif"></td>'
		+'</tr>'
		document.write(s1)

		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)" '
		+ contents[fundNum][i]
		+ '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
	}

	// end of table
	document.write(
	'</tbody></table>'
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
	} else {
		theImg.setAttribute("src",'images/minus.gif');
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
    			<p class="small" dir="<%=dir%>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=fundsProfile.getTitle(isHebrew)%></td>
		</tr>
  		<tr>
			<td width="200" valign="top" background="images/menu_bg.jpg">
				<br>
				<p class="menu" dir="<%=dir%>" align="<%=align%>">
					<%=category.getName()%>:
        			<br>
					<% for (MenuSubCategory menuSubCategory: upperSideMenuSubCategories){%>
						<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>">&nbsp;&nbsp;<%= menuSubCategory.getName() %>
						</a>
						<br>
					<%}%>
				</p>
				<p align="center">
    				<IMG src="images/seperator3.gif" width="185" height="5">
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "heb" : "eng"%>">
    				<%= isHebrew ? "דף הבית" :"Home" %>
    				</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "eng" : "heb"%>">
    				<%= isHebrew ? "English" : "Hebrew"%></a>
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
					<br>
				</p>
			</td>
			<td valign="top">
    			<table border="0" cellpadding="30" width="100%">
      				<tbody>
      					<tr>
							<td>
								<table border="0" width="100%" dir="<%=dir%>">
        							<tbody>
										<tr>
											<td>
												<h1 dir="<%=dir%>" align="<%=align%>"><%=fundsProfile.getTitle(isHebrew)%></h1>
											</td>
											<td align="<%=align%>" width="150">
												<p class="small"><%= isHebrew ? "עדכון אחרון:" : "Last update:" %> <%=fundsProfile.getLastUpdate()%>
												</p>
											</td>
										</tr>
        							</tbody>
								</table>
								<p align="center">
									<% for (int i=1; i<27; i++){%>
                   						<a href="#<%=(char)(64+i)%>"><%=(char)(64+i)%></a>
                   						<%= i!=26 ? "|" : "" %>
      								<%}%>
								</p>
								<% for (int i=0; i<fundsProfile.getFunds().length; i++){%>
	   								<% if (fundsProfile.isFundFirstLetterChanged(i)){%>
              							<h4 align="<%=align%>" dir="<%=dir%>"> <%= fundsProfile.getFunds()[i].getFullName().substring(0,1).toUpperCase()%>
              								<a name="<%=fundsProfile.getFunds()[i].getFullName().substring(0,1).toUpperCase()%>">
              								</a>
              							</h4>
           							<%}%>
									<% if (fundsProfile.getFunds()[i].getInfoPages().length>0){%>
										<font style="color:#44433c;background:#f3edd3;" dir="<%= isHebrew ? "ltr" : "ltr" %>" align="<%=align%>">
										<% if (fundsProfile.getFunds()[i].getWebAddress().length()>0){%>
              							<a href="http://<%=fundsProfile.getFunds()[i].getWebAddress()%>" target="_blank"><%= fundsProfile.getFunds()[i].getFullName()%></a>
          								<%} else{%>
              							<%= fundsProfile.getFunds()[i].getFullName()%>
          								<%} %>
          								<% if(fundsProfile.getFunds()[i].getShortName().length()>0){%>
          								 (<%= fundsProfile.getFunds()[i].getShortName()%>)
         								<%} %>
          								</font>
										<script language="javascript1.2">
											titles[<%=i%>]   = new Array (
												<% for (int j=0; j<fundsProfile.getFunds()[i].getInfoPages().length; j++){%>
													"<td class=\"\" width=\"750\" style=\"text-align: <%=isHebrew ? "right" : "left" %>;\"><table><tr><td><span dir=\"<%=fundsProfile.getFunds()[i].getInfoPages()[j].isHebrew() ? "rtl" : "ltr"%>\"><a style=\"text-decoration: none;\" href=\"infoPageViewer.jsp?ardNum=<%=fundsProfile.getFunds()[i].getInfoPages()[j].getArdNum()%>\"><%= fundsProfile.getFunds()[i].getInfoPages()[j].getTitle()%></a></span></td><td class=\"funding\">&nbsp;&nbsp;&nbsp;<%= fundsProfile.getFunds()[i].getInfoPages()[j].getSubmissionExpression(isHebrew)%></td><td>&nbsp;&nbsp;&nbsp;<span dir=\"<%=fundsProfile.getFunds()[i].getInfoPages()[j].isHebrew() ? "rtl" : "ltr"%>\"><a style=\"text-decoration: none;\" href=\"infoPageViewer.jsp?ardNum=<%=fundsProfile.getFunds()[i].getInfoPages()[j].getArdNum()%>#forms\">(<%=isHebrew ? "טפסים" : "forms"%>)</td></tr></table></td>"
													<%= j<fundsProfile.getFunds()[i].getInfoPages().length-1 ? "," : "" %>
												<%}%>
											);
											contents[<%=i%>] = new Array (
												<% for (int j=0; j<fundsProfile.getFunds()[i].getInfoPages().length; j++){%>
													"dir=\"<%=fundsProfile.getFunds()[i].getInfoPages()[j].isHebrew() ? "rtl" : "ltr" %>\" class=\"fundingopen\" style=\"text-align: <%=fundsProfile.getFunds()[i].getInfoPages()[j].isHebrew() ? "right" : "left" %>;\"><%=fundsProfile.getFunds()[i].getInfoPages()[j].getFormatedDescription()%>"
													<%= j<fundsProfile.getFunds()[i].getInfoPages().length-1 ? "," : "" %>
												<%}%>
											);
											CreateFundTable(<%=i%>)
		 								</script>
										<br>
									<%}%>
								<%}%>
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