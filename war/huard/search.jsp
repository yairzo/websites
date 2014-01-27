<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="huard.website.model.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="searchProfile" scope="page" class="huard.website.viewer.profile.SearchProfile" />
<jsp:setProperty name="searchProfile" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<%
response.setStatus(301);
response.setHeader( "Location", "https://ard.huji.ac.il/search" );
response.setHeader( "Connection", "close" );
%>

<% System.out.println("Viewing search.jsp"); %>

<html>
<head>

<script language="JavaScript1.2" src="js/cookiesFuncs.js"/>
<script language="JavaScript1.2">
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
     boolean isHebrew = language.isHebrewVer();
	 searchProfile.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(searchProfile.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(searchProfile.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(searchProfile.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%=searchProfile.getTitle(isHebrew)%></title>

<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script language="javascript">

var titles = new Array(4);
var contents = new Array(4);


titles[0]   = new Array (
	<% int i=0;%>
	<% for (TabledInfoPage infoPage : searchProfile.getInfoPages()){%>
		<% infoPage.markWords(); %>

		"<td nowrap><a href=\"infoPageViewer.jsp?ardNum=<%=infoPage.getArdNum()%>&foundBySearchWords=<%=infoPage.getFoundBySearchWordsHandled()%>\"><%= infoPage.getTitleHighlightedQuotsBackslashed()%></a></span></td><!--<th width=\"36\" class=\"authority\"><img border=0 src=\"images/icon_submission_blue.gif\" alt=\"<%=isHebrew ? "תאריך הגשה" : "Submission Date" %>\" title=\"<%=isHebrew ? "תאריך הגשה" : "Submission Date"%>\"></th><th width=\"104\" class=\"authority\"> <%= infoPage.getSubmissionExpression(isHebrew)%>&nbsp;</th>--><% if (infoPage.isHasAdditionalSubDates()){%><!--<th width=\"36\" class=\"authority\"><img border=0 src=\"images/icon_submission_plus_blue.gif\" alt=\"<%=isHebrew ? "קיימים תאריכי הגשה נוספים" : "Additional Submission Dates" %>\" title=\"<%=isHebrew ? "קיימים תאריכי הגשה נוספים" : "Additional Submission Dates"%>\"></th>--><%}else{%><th width=\"36\" class=\"authority\">&nbsp;</th><%}%>"


		<%= i<searchProfile.getInfoPages().size()-1 ? "," : "" %>
		<%i++;%>

	<%}%>
);



contents[0] = new Array (
	<% i=0;%>
	<% for (TabledInfoPage infoPage: searchProfile.getInfoPages()){%>
		"<%= infoPage.getFormatedDescription()%>"
		<%= i<searchProfile.getInfoPages().size()-1 ? "," : "" %>
		<%i++;%>
	<%}%>

);

titles[1]   = new Array (
	<% i=0;%>
	<% for (MultipleOptionsPatternedPage mopPage :searchProfile.getMultipleOptionsPatternedPages()){%>
		"<td nowrap><a href=\"<%=mopPage.getFileName()%>?<%=mopPage.getContentOptionsName()%>=<%=mopPage.getEnglishContentOptionByRessemblanceToFoundBySearchWords(false)%>\"><% if (isHebrew){%> קולות קוראים ל<%=mopPage.getHebrewContentOptionByRessemblanceToFoundBySearchWords(true)%><%}else{%>Calls for&nbsp;<%=mopPage.getEnglishContentOptionByRessemblanceToFoundBySearchWords(true)%><%}%></a></td>"
		<%= i<searchProfile.getMultipleOptionsPatternedPages().size()-1 ? "," : "" %>
		<%i++;%>
	<%}%>
);

contents[1] = new Array ("");


titles[2] = new Array(
	<% i=0;%>
	<% for (ComposedPatternedPage cpPage: searchProfile.getComposedPatternedPages()){%>
		"<td nowrap><a href=\"<%=cpPage.getPureFileName()%>?foundBySearchWords=<%=cpPage.getFoundBySearchWords()%><%= cpPage.isFileNameHasParameters() ? "&"+cpPage.getFileNameParameters() : "" %><%=(cpPage.isFoundBySearchWordsHebrew() ? "&eng=false" : "")%>#go\"><%=(cpPage.isFoundBySearchWordsHebrew() ? cpPage.getHebrewTitle() : cpPage.getEnglishTitle())%></a></td>"
		<%= i<searchProfile.getComposedPatternedPages().size()-1 ? "," : "" %>
		<%i++;%>
	<%}%>
);

contents[2] = new Array("");


titles[3] = new Array(
	<% i=0;%>
	<%for (PubPage pubPage: searchProfile.getPubPages()){%>
	        <% pubPage.markWords(); %>
		"<td nowrap><a href=\"pubPage<% if (pubPage.isWraper()) {%>Wraper<%}else{%>Viewer<%}%>.jsp?ardNum=<%=pubPage.getArdNum()%>&foundBySearchWords=<%=pubPage.getFoundBySearchWordsHandled()%>\"><%=pubPage.getTitleHighlightedQuotsBackslashed()%><% if (pubPage.isFileRepresentation()){%> [<%=pubPage.getFileFormat()%>]<%}%></a></td>"
		<%= i< searchProfile.getPubPages().size()-1 ? "," : "" %>
		<% i=i+1;%>
	 <%}%>
);

contents[3] = new Array(
	<% i=0;%>
	<%for (PubPage pubPage: searchProfile.getPubPages()){%>
		"<span dir=\"<%=pubPage.isHebrew() ? "rtl" : "ltr"%>\" align=\"<%=pubPage.isHebrew() ? "right" : "left"%>\"><%= pubPage.getShortHtmlNo_13_10()%></span>"
		<%= i<searchProfile.getPubPages().size()-1 ? "," : "" %>
		<% i=i+1;%>
	<%}%>
);


function CreateInfoPagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="funding">')
	<% i=0; %>
	<% for (TabledInfoPage infoPage : searchProfile.getInfoPages()){%>
		id1 = 'MyContentLine'+0+<%=i%>;
		id2 = 'MyPlusMinusImg'+0+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="authority" align="<%= isHebrew ? "right" : "left" %>">'
		+'<table class="profiles" width="700" height="100%"><tr>'
		+ titles[0][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<td width="30px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">'
		+'<img id="' + id2 +'"src="images/b_plus.gif" alt="Open a Short Description" title="Open a Short Description"></td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td dir="<%=infoPage.isHebrew() ? "rtl" : "ltr" %>" class="authorityopen" style="text-align: <%=infoPage.isHebrew() ? "right" : "left" %>;" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">' + contents[0][<%=i%>] + '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
		<% i++;%>
	<%}%>
	// end of table
	document.write(
	'</tbody></table>'
	)
}



function CreateMultipleOptionsPatternedPagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="authority">')
	<% i=0;%>
	<% for (MultipleOptionsPatternedPage mopPage :searchProfile.getMultipleOptionsPatternedPages()){%>
		id1 = 'MyContentLine'+2+<%=i%>;
		id2 = 'MyPlusMinusImg'+2+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="authority" style="text-align: <%=isHebrew ? "right" : "left" %>;">'
		+'<table class="profiles" boredr="0" width="100%" height="100%"><tr>'
		+ titles[1][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<th width="30px">&nbsp;</th>'
		+'</tr>'
		document.write(s1)
		<% i++;%>
	<%}%>
	// end of table
	document.write(
	'</tbody></table>'
	)
}

function CreateComposedPatternedPagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="authority">')
	<% for (ComposedPatternedPage cpPage: searchProfile.getComposedPatternedPages()){%>
		id1 = 'MyContentLine'+3+<%=i%>;
		id2 = 'MyPlusMinusImg'+3+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="authority" style="text-align: <%=isHebrew ? "right" : "left" %>;">'
		+'<table class="profiles" boredr="0" width="100%" height="100%"><tr>'
		+ titles[2][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<th width="30px">&nbsp;</th>'
		+'</tr>'
		document.write(s1)
	<%}%>
	// end of table
	document.write(
	'</tbody></table>'
	)
}

function CreatePubPagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="authority">')
	<% i=0; %>
	<%for (PubPage pubPage: searchProfile.getPubPages()){%>
		id1 = 'MyContentLine'+4+<%=i%>;
		id2 = 'MyPlusMinusImg'+4+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="authority" align="<%= isHebrew ? "right" : "left" %>">'
		+'<table class="profiles" boredr="0" width="100%" height="100%"><tr>'
		+ titles[3][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<td>&nbsp;</td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td dir="<%=pubPage.isHebrew() ? "rtl" : "ltr" %>" class="authorityopen" style="text-align: <%=pubPage.isHebrew() ? "right" : "left" %>;" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">' + contents[3][<%=i%>] + '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
		<% i++; %>
	<%}%>
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
		theImg.setAttribute("src",'images/b_plus.gif');
		theImg.setAttribute("alt",'Opens a short description');
		theImg.setAttribute("title",'Opens a short description');
	} else {
		theImg.setAttribute("src",'images/b_minus.gif');
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

<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%=dir%>">
	<tbody>
  		<tr>
    			<td height="15" background="images/menu_bg.jpg">
    			</td>
    			<td class="<%=category.getClassColor(isHebrew)%>">
    				<p class="small" dir="<%=dir%> %>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=searchProfile.getTitle(isHebrew)%>
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
						<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
					</p>
				</td>
			<td valign="top">
    			<table border="0" cellpadding="10" width="100%">
      				<tbody>
      					<tr>
      						<td>
								<table border="0" width="100%" dir="<%=dir%>">
									<tr>
										<td width="150" valign="top">
											<h1 dir="<%=dir%>" align="<%=align%>"><%=searchProfile.getTitle(isHebrew)%></h1>
										</td>
										<% i = searchProfile.getMultipleOptionsPatternedPages().size() + searchProfile.getComposedPatternedPages().size() + searchProfile.getPubPages().size(); %>
										<td  valign="center" align="<%=align%>">
											<% if (i>0) {%>
												<font size=-1><a href="#messages"><%= isHebrew ? "(תוצאות נוספות)" : "(Additional Results)" %></a></font>
											<%}else{%>
												&nbsp;
											<%}%>
										</td>
										<td valign="top" width="150">
											<p class="blacksmall"><%= isHebrew ? "עדכון אחרון:" : "Last update:" %> <%=searchProfile.getLastUpdate()%></p>
										</td>
									</tr>
								</table>
								<h2 dir="<%=dir%>" align="<%=align%>">
								<%if (searchProfile.getInfoPages().size()>0){%>
									<%= isHebrew ? "קולות קוראים" : "Calls of Proposals" %>&nbsp;(<%=searchProfile.getInfoPages().size()%>)</h2>
										<script language="javascript1.2">
			 								CreateInfoPagesTable()
		 								</script>
								<%}else{%>
									<%= isHebrew ? "לא נמצאו קולות קוראים מתאימים" : "No Matching Calls of Proposals Were Found" %>
								<%}%>
								<table class="funding" align="center" cellspacing="5" dir="<%=dir%>">
									<tbody class="funding">
										<tr>
											<td class="blank" align="<%=align%>">
												<% if (! searchProfile.isFullList() || (searchProfile.isFullList() && searchProfile.getInfoPages().size()>0)) {%>
													<br>
														<% if (isHebrew){%>
															<p><a href="search.jsp?keywords=<%=searchProfile.getKeywordsQuotsHex()%>&fullList=<%= ! searchProfile.isFullList()%>"> <img border=0 src="images/<%= searchProfile.isFullList() ? "b_minus.gif" : "b_plus.gif" %>"></a>&nbsp;<%= searchProfile.isFullList() ? "לחיפוש מצומצם - קולות קוראים פתוחים להגשה בלבד" : "לחיפוש מורחב - קולות קוראים פתוחים להגשה, וכאלו הנוטים לחזור כל שנה" %></p>
														<%}else{%>
															<p><a href="search.jsp?keywords=<%=searchProfile.getKeywordsQuotsHex()%>&fullList=<%= ! searchProfile.isFullList()%>"> <img border=0 src="images/<%= searchProfile.isFullList() ? "b_minus.gif" : "b_plus.gif" %>"></a>&nbsp;<%= searchProfile.isFullList() ? "Reduced List - Calls for Proposals Open for Submission" : "Full List - Calls for Proposals Open for Submission, and Calls of Proposals that tends to repeat every year" %></p>
														<%}%>
												<%}%>
											</td>
										</tr>
									</tbody>
								</table>
								<br>
								<a name="messages">
								<h2 dir="<%=dir%>" align="<%=align%>">
								<%if ( i>0){%>
									<%= isHebrew ? "תוצאות נוספות" : "Additional Results" %>&nbsp;(<%=i%>)
										<script language="javascript1.2">
		 									<% if (searchProfile.getMultipleOptionsPatternedPages().size()>0){%>
												CreateMultipleOptionsPatternedPagesTable()
											<%}%>
											<% if (searchProfile.getComposedPatternedPages().size()>0){%>
												CreateComposedPatternedPagesTable()
											<%}%>
											<% if (searchProfile.getPubPages().size()>0){%>
												CreatePubPagesTable()
											<%}%>
		  								</script>
								<%}else{%>
									<%= isHebrew ? "לא נמצאו תוצאות נוספות" : "No Additional Results Were Found"  %></h2>
								<%}%>
							</td>
						</tr>
					</tbody>
				</table>
				<p>&nbsp;&nbsp;<a href="pubPageViewer.jsp?ardNum=<%= isHebrew ? "196" : "212"%>"><img src="<%= isHebrew ? "images/button_new_search.gif" : "images/e_button_new_search.gif" %>" border="0"></p>
				<jsp:include page="include/allRightsReserved.jsp" />
			</td>
		</tr>
	</tbody>
</table>
<jsp:include page="include/googleAnalytics.jsp"/>
</body></html>