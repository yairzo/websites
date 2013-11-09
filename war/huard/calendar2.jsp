<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="calendarProfile" scope="page" class="huard.website.viewer.profile.CalendarProfile" />
<jsp:setProperty name="calendarProfile" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="session" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<% System.out.println("Viewing calendar.jsp"); %>

<html>
<head>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

<link href="style/style_ard.css" rel="stylesheet" type="text/css">

<script src="js/cookiesFuncs.js"/>
<script>
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	boolean isHebrew = language.isHebrewVer(); 
	calendarProfile.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(calendarProfile.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(calendarProfile.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(calendarProfile.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%= isHebrew ? "הרשות למחקר ופתוח של האוניברסיטה העברית - " : "The Authority for Research & Development of the Hebrew University-" %><%=calendarProfile.getTitle(isHebrew)%> </title>

<script>

var titles = new Array(13);



function CreateInfoPagesTable (month){

	document.write(
	'<table class="funding" align="center" cellspacing="5" dir="<%=dir%>"><tbody>')
	for (i=0; i<titles[month+1].length; i++) {
		id1 = 'MyContentLine'+month+i;
		id2 = 'MyPlusMinusImg'+month+i;
		// writing the title line    the \u0027 is for ' (the APOSTROPHE neede in the function call arounf the id)
		s1 =
		'<tr class="" style="cursor: hand;">'
		+'<th class="funding" align="<%= isHebrew ? "right" : "left" %>">'
		+'<table class="profiles" width="750" height="100%"><tr>'
		+ titles[month+1][i]
		+'</tr></table>'
		+'</th>'
		+'<td width="30px" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027,'+ month + ',' + i+')">'
		+'<img id="' + id2 +'"src="images/plus.gif"  alt="Opens a short description" title="Opens a short description"></td>'
		+'</tr>'
		document.write(s1)

		// writing the content line
		s2 =
		'<tr style="display:none;" id="tr' + id1 + '" class="">'
		+'<td id=\"' + id1 + '\" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027,'+month + ',' + i+')"  class=\"fundingopen\" >'
		+ '</td>'
		+'<td class="" onClick="toggleLineImg(\u0027' + id1 + '\u0027,\u0027' + id2 + '\u0027,'+ month + ',' + i+')">&nbsp;</td>'
		+'</tr>'

		//alert(s2)
		document.write(s2)
	}
	// end of table
	document.write(
	'</tbody></table>'
	)
}

function toggleLine(id,month,infoPage){
	var fieldn = '#' + id;
	var trid='tr' + id;
	body=document.getElementById(trid);
	if (body) {
		if (body.style.display == 'none') {
			$.get("infoPageDescription.jsp?type=month&param="+month+"&infoPage="+infoPage, function(data) { 
		   		$(fieldn).html(data);
		    });
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


function toggleLineImg(id1,id2,month,infoPage) {
	flag = toggleLine(id1,month,infoPage)
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
    				<p class="small" dir="<%=dir%>" align="<%=align%>"><img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">&nbsp;<%=category.getName()%> <img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png"> <%=calendarProfile.getTitle(isHebrew)%>
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
													<h1><%=calendarProfile.getTitle(isHebrew)%></h1>
	   											</td>
	   											<td width="150" align="<%=align%>">
													<p class="small"><%= isHebrew ? "עדכון אחרון:" : "Last update:" %> <%=calendarProfile.getLastUpdate()%>
													</p>
	   											</td>
	  										</tr>
	 									</tbody>
									</table>
									<p align="center" >
										<% for (int j=0; j<12; j++){%>
           									<a class="content" href="#<%=calendarProfile.getMonthFullNameByMonthNum(j,false)%>"><%=calendarProfile.getMonthFullNameByMonthNum(j,isHebrew)%></a>
           									<%= j!=11 ? "|":"" %>
      									<%}%>
									</p>
									<% for (int j=-1; j<12; j++){%>
										<h4 align="<%=align%>" dir="<%=dir%>"> <%= calendarProfile.getMonthFullNameByMonthNum(j,isHebrew)%>
            								<a name="<%=calendarProfile.getMonthFullNameByMonthNum(j,false)%>">
            								</a>
            							</h4>
										<% if (calendarProfile.getInfoPagesByMonth(j).length>0){%>
											<script language="javascript1.2">
												titles[<%=j%>+1]   = new Array (
													<% for (int i=0; i<calendarProfile.getInfoPagesByMonth(j).length; i++){%>
														"<td width=\"750\" style=\"text-align: <%=isHebrew ? "right" : "left" %>;\"><table><tr><td><span dir=\"<%=calendarProfile.getInfoPagesByMonth(j)[i].isHebrew() ? "rtl" : "ltr"%>\"><a style=\"text-decoration: none;\" href=\"infoPageViewer.jsp?ardNum=<%=calendarProfile.getInfoPagesByMonth(j)[i].getArdNum()%>\"><%= calendarProfile.getInfoPagesByMonth(j)[i].getTitle()%></a></span></td><td class=\"funding\">&nbsp;&nbsp;&nbsp;<%= calendarProfile.getInfoPagesByMonth(j)[i].getSubmissionExpression(isHebrew)%>&nbsp;</td><td><%if (calendarProfile.getInfoPagesByMonth(j)[i].isHasAdditionalSubDates()){%><%=isHebrew ? "קיימים תאריכי הגשה נוספים" : "Additional submission dates do exist" %><%}%></td></tr></table></td>"
														<%= i<calendarProfile.getInfoPagesByMonth(j).length-1 ? "," : "" %>
													<%}%>
												);
												<%calendarProfile.nullifyInfoPages();%>
												CreateInfoPagesTable(<%=j%>)
		 									</script>
										<%}else{%>
											<table class="funding" align="center" cellspacing="5" dir="<%=dir%>">
												<tbody class="funding">
													<tr class="" style="cursor: hand;">
														<th class="funding">
															<table class="" boredr="0" width="100%" height="100%">
																<tr>
																	<th class="funding" style="text-align: <%=isHebrew ? "right" : "left" %>;">
																		<% if (! calendarProfile.isFullList()){%>
				  															<br><%=isHebrew ? "אין קולות קוראים פתוחים להגשה שהגשתם בחודש זה" : "There are no Open Calls of Proposal with Submission in this Month" %><br>&nbsp;
				   														<%}else{%>
				   															<br><%=isHebrew ? "אין קולות קוראים שהגשתם בחודש זה" : "There are no Calls of Proposal with Submission in this Month" %><br>&nbsp;
				   														<%}%>
			 														</th>
																</tr>
															</table>
														</th>
													</tr>
												</tbody>
											</table>
										<%}%>
										<% calendarProfile.nullifyInfoPages();%>
										<% if (j>-1){%>
											<table class="funding" align="center" cellspacing="5" dir="<%=dir%>">
												<tbody class="funding">
													<tr>
														<td class="blank" align="<%=align%>">
															<% if (isHebrew){%>
																<p><a href="calendar.jsp?fullList=<%= ! calendarProfile.isFullList()%>#<%=calendarProfile.getMonthFullNameByMonthNum(j,false)%>"> <img border=0 src="images/<%= calendarProfile.isFullList() ? "minus.gif" : "plus.gif" %>"></a>&nbsp;<%= calendarProfile.isFullList() ? "לרשימה המצומצמת - קולות קוראים פתוחים להגשה" : "לרשימה המלאה - קולות קוראים פתוחים להגשה, וכאלו הנוטים לחזור בכל שנה" %></p>
															<%}else{%>
																<p><a href="calendar.jsp?fullList=<%= ! calendarProfile.isFullList()%>#<%=calendarProfile.getMonthFullNameByMonthNum(j,false)%>"> <img border=0 src="images/<%= calendarProfile.isFullList() ? "minus.gif" : "plus.gif" %>"></a>&nbsp;<%= calendarProfile.isFullList() ? "Reduced List - Calls for Proposals Open for Submission" : "Full List - Calls for Proposals Open for Submission, and Calls of Proposals that tends to repeat every year" %></p>
															<%}%>
														</td>
													</tr>
												</tbody>
											</table>
										<%}%>
										<br>
									<%}%>
								</td>
							</tr>
						</tbody>
					</table>
					<jsp:include page="include/allRightsReserved.jsp"/>
				</td>
			</tr>
		</tbody>
	</table>
	<jsp:include page="include/googleAnalytics.jsp"/>
</body>
</html>