<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="newsProfile" scope="page" class="huard.website.viewer.profile.NewsProfile" />
<jsp:setProperty name="newsProfile" property="*"/>

<jsp:useBean id="messagesProfile" scope="page" class="huard.website.viewer.profile.MessagesProfile" />
<jsp:setProperty name="messagesProfile" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<% System.out.println("Viewing news.jsp"); %>

<html>
<head>

<script language="JavaScript1.2" src="js/cookiesFuncs.js"/>
<script language="JavaScript1.2">
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 boolean isHebrew = language.isHebrewVer(); 
	 newsProfile.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(newsProfile.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(newsProfile.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(newsProfile.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%=newsProfile.getTitle(isHebrew)%> </title>

<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script>

var titles = new Array(2);
var contents = new Array(2);


titles[0]   = new Array (
	<% for (int i=0; i<newsProfile.getNewInfoPages().length; i++){%>
		"<td width=\"750\" style=\"text-align: <%=align%>;\"><table><tr><td><span dir=\"<%=newsProfile.getNewInfoPages()[i].isHebrew() ? "rtl" : "ltr"%>\"><a style=\"text-decoration: none;\" href=\"infoPageViewer.jsp?ardNum=<%=newsProfile.getNewInfoPages()[i].getArdNum()%>\"><%= newsProfile.getNewInfoPages()[i].getTitle()%></a></span></td><td class=\"funding\">&nbsp;&nbsp;&nbsp;<%= newsProfile.getNewInfoPages()[i].getSubmissionExpression(isHebrew)%>&nbsp;</td></tr></table></td>"
		<%= i<newsProfile.getNewInfoPages().length-1 ? "," : "" %>
	<%}%>
);



contents[0] = new Array (
	<% for (int i=0; i<newsProfile.getNewInfoPages().length; i++){%>
		"<%= newsProfile.getNewInfoPages()[i].getFormatedDescription()%>"
		<%= i<newsProfile.getNewInfoPages().length-1 ? "," : "" %>

	<%}%>

);

titles[1]   = new Array (
	<% for (int i=0; i<messagesProfile.getNewMessages().length; i++){%>


"<td nowrap><a href=\"pubPageViewer.jsp?ardNum=<%=messagesProfile.getNewMessages()[i].getArdNum()%>\"><%= messagesProfile.getNewMessages()[i].getTitle()%></a></td><td width=\"6\" class=\"funding\"><a href=\"\"><%=isHebrew ? "" : "" %></a><td>"
		<%= i<messagesProfile.getNewMessages().length-1 ? "," : "" %>


	<%}%>

);



contents[1] = new Array (
	<% for (int i=0; i<messagesProfile.getNewMessages().length; i++){%>
		"<span dir=\"<%=messagesProfile.getNewMessages()[i].isHebrew() ? "rtl" : "ltr"%>\" align=\"<%=messagesProfile.getNewMessages()[i].isHebrew() ? "right" : "left"%>\"><%= messagesProfile.getNewMessages()[i].getShortHtmlNo_13_10()%></span>"
		<%= i<messagesProfile.getNewMessages().length-1 ? "," : "" %>

	<%}%>

);





function CreateInfoPagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="funding">')
	<% for (int i=0; i< newsProfile.getNewInfoPages().length; i++) {%>
		id1 = 'MyContentLine'+0+<%=i%>;
		id2 = 'MyPlusMinusImg'+0+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="funding">'
		+'<table class="profiles" boredr="0" width="750" height="100%"><tr>'
		+ titles[0][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<td width="30px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">'
		+'<img id="' + id2 +'"src="images/plus.gif" alt="Opens a short description" title="Opens a short description"></td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td dir="<%=newsProfile.getNewInfoPages()[i].isHebrew() ? "rtl" : "ltr" %>" class="fundingopen" style="text-align: <%=newsProfile.getNewInfoPages()[i].isHebrew() ? "right" : "left" %>;" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">' + contents[0][<%=i%>] + '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
	<%}%>
	// end of table
	document.write(
	'</tbody></table>'
	)
}

function CreateMessagesTable (){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody class="funding">')
	<% for (int i=0; i< messagesProfile.getNewMessages().length; i++) {%>
		id1 = 'MyContentLine'+1+<%=i%>;
		id2 = 'MyPlusMinusImg'+1+<%=i%>;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="funding" align="<%= isHebrew ? "right" : "left" %>">'
		+'<table class="profiles" boredr="0" width="100%" height="100%"><tr>'
		+ titles[1][<%=i%>]
		+'</tr></table>'
		+'</th>'
		+'<td width="30px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">'
		+'<img id="' + id2 +'"src="images/plus.gif" alt="Opens a short description" title="Opens a short description"></td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="' + id1 + '" class="">'
		+'<td dir="<%=messagesProfile.getNewMessages()[i].isHebrew() ? "rtl" : "ltr" %>" class="fundingopen" style="text-align: <%=messagesProfile.getNewMessages()[i].isHebrew() ? "right" : "left" %>;" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">' + contents[1][<%=i%>] + '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027)">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
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
<body <%= language.getLang()==null ? "onLoad=\"manageLanguage()\"" : "" %> leftMargin=0 topmargin="0" marginheight="0" >

<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />

<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%=dir%>">
	<tbody>
  		<tr>
    			<td height="15" background="images/menu_bg.jpg">
    			</td>
    			<td class="<%=category.getClassColor(isHebrew)%>">
    				<p class="small" dir="<%=dir%> %>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=newsProfile.getTitle(isHebrew)%>
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
						<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
					</p>
				</td>
			<td valign="top">
    			<table border="0" cellpadding="10" width="100%">
      				<tbody>
      					<tr>
							<td>
								<table border="0" width="100%" dir="<%=dir%>">
									<tr>
										<td width="250" valign="top">
											<h1 dir="<%=dir%>" align="<%=align%>"><%=newsProfile.getTitle(isHebrew)%></h1>
										</td>
										<td  valign="bottom" align="<%=align%>">
											<%if ( ! messagesProfile.getNewMessages()[0].getTitle().equals("No Results")){%><font size=-1><a href="#messages"><%= isHebrew ? "מעבר להודעות" : "Go to Messages" %></a></font><%}else{ %>&nbsp;<%} %>
										</td>
										<td valign="top" width="150">
											<p class="blacksmall"><%= isHebrew ? "עדכון אחרון:" : "Last update:" %> <%=newsProfile.getLastUpdate()%></p>
										</td>
									</tr>
								</table>
								<h2 dir="<%=dir%>" align="<%=align%>">
								<%if ( ! newsProfile.getNewInfoPages()[0].getTitle().equals("No Results")){%>
									<%= isHebrew ? "קולות קוראים" : "Calls of Proposals" %></h2>
									<script language="javascript1.2">
			 							CreateInfoPagesTable()
		 							</script>
								<%}else{%>
									<%= isHebrew ? "לא נמצאו קולות קוראים מתאימים" : "No Matching Calls of Proposals Were Found" %>
								<%}%>
								<br>
								<a name="messages">
								<h2 dir="<%=dir%>" align="<%=align%>">
								<%if ( ! messagesProfile.getNewMessages()[0].getTitle().equals("No Results")){%>
									<%= isHebrew ? "הודעות" : "Messages" %>
									<script language="javascript1.2">
		 								CreateMessagesTable()
		  							</script>
								<%}else{%>
									<%= isHebrew ? "לא נמצאו הודעות חדשות" : "No Matching Messages Were Found" %></h2>
								<%}%>
							</td>
						</tr>
					</tbody>
				</table>
				<jsp:include page="include/allRightsReserved.jsp" />
			</td>
		</tr>
	</tbody>
</table>
<jsp:include page="include/googleAnalytics.jsp"/>
</body></html>