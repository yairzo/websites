<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="mainPageViewer" scope="page" class="huard.website.viewer.page.MainPageViewer" />
<jsp:setProperty name="mainPageViewer" property="*"/>

<jsp:useBean id="newsProfile" scope="page" class="huard.website.viewer.profile.NewsProfile" />
<jsp:setProperty name="newsProfile" property="*"/>

<jsp:useBean id="messagesProfile" scope="page" class="huard.website.viewer.profile.MessagesProfile" />
<jsp:setProperty name="messagesProfile" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="topMenu" scope="application" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<jsp:useBean id="randomPictures" scope="session" class="huard.website.viewer.page.MainPageViewer" />
<jsp:setProperty name="randomPictures" property="*"/>

<jsp:useBean id="authorizer" scope="session" class="huard.website.session.Authorizer" />
<jsp:setProperty name="authorizer" property="*"/>

<%
response.setStatus(301);
response.setHeader( "Location", "https://ard.huji.ac.il" );
response.setHeader( "Connection", "close" );
%>

<% System.out.println("Viewing main.jsp"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv=imagetoolbar content=no>
		<%@ include file="include/menuHead.jsp" %>
		<script language="JavaScript1.2" src="js/cookiesFuncs.js">
		</script>
		<script language="JavaScript1.2">
			<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 mainPageViewer.logAccessToPage(language.isHebrewVer(), request.getRemoteAddr());
%>
			checkCookie('<%=language.getLang()%>');
		</script>
		<title><%= language.isHebrewVer() ? "הרשות למחקר ופתוח - האוניברסיטה העברית" : "The Authority for Research and Development - The Hebrew University"%></title>
		<link href="style/style_ard.css" type=text/css rel=stylesheet>
		<style>
			#marqueecontainer{
				position: relative;
				width:  95%; /*marquee width */
				height: 220px; /*marquee height */
				vertical-align: top;
				overflow: hidden;
				border: 0px solid orange;
				padding-left: 8px;
				padding-left: 8px;
				position: relative;
			    <%= language.isHebrewVer() ? "" : "right: 10px;"%>
				text-align: <%= language.isHebrewVer() ? "" : "right" %>

			}
		</style>

		<script language="javascript1.2">
			var ImageUrls = new Array(
<% 			for(int i=0; i<randomPictures.getIWSImageIdsArray().length; i++){
					if (i > 0)
%>					,
				"https://ard.huji.ac.il/imageViewer?imageId=<%=randomPictures.getIWSImageIdsArray()[i]%>&attachType=bodyImage"
<%				}
%>
			 )

			var Text = new Array(
<% 			for(int i=0; i<randomPictures.getIWSImageHebNamesArray().length; i++){
					if (i > 0)
%>					,
					"<%= language.isHebrewVer() ? randomPictures.getIWSImageHebNamesArray()[i] : randomPictures.getIWSImageEngNamesArray()[i] %>"
<%
				}
%>
			)

			var RandNum = Math.floor((Math.random()*ImageUrls.length))
			var CurrentImg = -1; // not used yet
			function toggleImgText(DivID,ImgID) {
				if (CurrentImg == -1) {
					CurrentImg = RandNum;
				}
				if (CurrentImg == ImageUrls.length) {
					CurrentImg = 0;
				}
				theText = document.getElementById(DivID)
				theText.innerHTML = '<p class="menu" dir="rtl">' + Text[CurrentImg];
				theImg = document.getElementById(ImgID);
				theImg.setAttribute("src",ImageUrls[CurrentImg]);
				CurrentImg++;
			}

			var delayb4scroll=1000 //Specify initial delay before marquee starts to scroll on page (2000=2 seconds)
			var marqueespeed=1 //Specify marquee scroll speed (larger is faster 1-10)
			var pauseit=1 //Pause marquee onMousever (0=no. 1=yes)?

			var copyspeed=marqueespeed
			var pausespeed=(pauseit==0)? copyspeed: 0
			var actualheight=''

			function scrollmarquee(){
				if (parseInt(cross_marquee.style.top)>(actualheight*(-1)+8))
					cross_marquee.style.top=parseInt(cross_marquee.style.top)-copyspeed+"px"
				else
					cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
			}

			function initializemarquee(){
				cross_marquee=document.getElementById("vmarquee")
				cross_marquee.style.top=0
				marqueeheight=document.getElementById("marqueecontainer").offsetHeight
				actualheight=cross_marquee.offsetHeight
				if (window.opera || navigator.userAgent.indexOf("Netscape/7")!=-1){ //if Opera or Netscape 7x, add scrollbars to scroll and exit
					cross_marquee.style.height=marqueeheight+"px"
					cross_marquee.style.overflow="scroll"
					return
				}
				setTimeout('lefttime=setInterval("scrollmarquee()",30)', delayb4scroll)
			}

			if (window.addEventListener)
				window.addEventListener("load", initializemarquee, false)
			else if (window.attachEvent)
				window.attachEvent("onload", initializemarquee)
			else if (document.getElementById)
				window.onload=initializemarquee

		</script>
		<script type="text/javascript">document.write(unescape("%3Cscript src='" + (("https:" == document.location.protocol) ? "https" : "http") + "://cdn.mouseflow.com/projects/c7bfcd1a-0fba-4cbc-9dac-af443d1c2be8.js' type='text/javascript'%3E%3C/script%3E"));</script>
</head>
<body leftMargin=0 topmargin="0" marginheight="0">

<jsp:include page="include/menuBodyMain.jsp" />
<table width="100%" border="0" background="images/front_bg.jpg">
	<tr>
    	<td background="images/front_bg_menu.jpg">
	    	<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
    	    	<tr>
        	  		<td valign="top" colspan="<%=topMenu.getCategoriesArray(language.getLang()).length%>" align="center"><font color="#996633"><img src="images/header_front.jpg" width="910" height="106" border="0" usemap="#Map"></font></td>
       			</tr>
       		 	<tr>
					<% for (MenuCategory topMenuCategory: topMenu.getCategoriesArray(language.getLang())){ %>
						<td align="center" bgcolor="#787669" height="27" >
          	  				<font color="#996633">	<a href="<%=topMenuCategory.getLink()%>" class="menu"><%=topMenuCategory.getName()%></a></font>
						</td>
					<%}%>
           	   		<td width="5" height="27" align="center" bgcolor="#787669"></td>
            	</tr>
        		<tr>
					<% for (MenuCategory topMenuCategory: topMenu.getCategoriesArray(language.getLang())){%>
						<td background="images/<%=topMenuCategory.getBackgroundImage()%>">&nbsp;</td>
					<%}%>
		          	<td background="images/colors_bg_edge.gif"></td>
        		</tr>
         	</table>
       	</td>
	</tr>
</table>
<table width="100%" border=0 bgcolor="#efe5dc">
	<tr>
		<td>
        	<table width="910" border=0 align="center" cellPadding=0 cellSpacing=3 dir="<%= language.isHebrewVer() ? "ltr" : "rtl" %>">
      			<tr>
          			<td width="420" align="center" valign="top" ><font color="#996633">
                    	</font>
						<table width="100%" cellpadding="0" cellspacing="0" style="border:#a1995b thin solid">
							<tr>
								<td style="background-color: #787669;">
									<% String imgWidth = request.getHeader("User-Agent").indexOf("MSIE") != -1 ? "428" : "100%";%>
		            				<img onClick="toggleImgText('TheText','TheImage')" src="" alt="לחצו לתמונה חדשה" name="TheImage"  height=240 border="0" align="left" id='TheImage' width="<%=imgWidth %>">
        						</td>
							</tr>
							<tr>
                   				<td id='TheText' style="height: 30px; background-color: #787669; width: 100%; border-top: #a1995b 1px solid; border-bottom: #44433C 1px solid;" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left" %>">
                      				<p class="menu" dir="rtl"><font color="#996633"> שדרות מגנס</font></p>
                     			</td>
	            		 	</tr>
	            		 	<%//style="height: 285px;""%>
	            		 	<tr>
	            		 		<td dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left" %>">
          		 					<font color="#996633">
           								<script language="javascript1.2">
											toggleImgText('TheText','TheImage')
										</script>
          							</font>
 				          			<img width="100%" src="images/<%= language.isHebrewVer() ? "" : "e_" %>awards_title.jpg">
									<% if (language.isHebrewVer()){%>
				          				<jsp:include page="pubPageViewerBasic.jsp?ardNum=380" />
				          			<%}else{%>
				          				<jsp:include page="pubPageViewerBasic.jsp?ardNum=381" />
				          			<%} %>
				   				</td>
				   			</tr>
				   		</table>
				   	</td>
          			<td style="text-align: left; vertical-align: top; width: 280; border: thin solid rgb(161, 153, 91);">
          				<img style="width: 100%; height: 34;" src="<%= language.isHebrewVer() ? "images/message_title.jpg" : "images/e_message_title.jpg" %>">
						<br/>
						<br/>
						<% if (! messagesProfile.getTopPriorityMessage()[0].getTitle().equals("No Results")){%>
							<div style="<%= language.isHebrewVer() ? "direction: rtl;" : "direction: ltr;"%> position: relative; <%= language.isHebrewVer() ? "" : "right: 10px;" %> width: 100%; border-bottom: #a1995b thin solid; text-align: <%=(messagesProfile.isHebrew(messagesProfile.getTopPriorityMessage()[0].getTitle())) ? "right" : "left" %>">
	  							<a class="rollingMessages" href="pubPageViewer.jsp?ardNum=<%= messagesProfile.getTopPriorityMessage()[0].getArdNum()%>&category=<%=(messagesProfile.isHebrew(messagesProfile.getTopPriorityMessage()[0].getTitle())) ? "הרשות למופ" : "The Authority" %>">
								<p class="menu">
									<%=messagesProfile.getFormatedDate(messagesProfile.getTopPriorityMessage()[0].getPubDate())%><br><%= mainPageViewer.markApostrofWithBackSlash(messagesProfile.getTopPriorityMessage()[0].getTitle())%>
								</p>
								</a>
								<br/>
								<br/>
							</div>
						<%}%>
						<br/>
						<br/>
						<jsp:include page="js/rollingMessages.jsp" />
          			</td>
		          	<td width="200" align="center" background="images/menu_bg.jpg" valign="top" style="border:#a1995b thin solid">
						<br><br>
		            	<p class=menu dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left"%>">
						<%= language.isHebrewVer() ? "אודות הרשות למו\"פ:" : "The R&D Authority" %>
		            	<br/>
						<% for (int i=0; i<sideMenu.getUpperSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer()).length;i++){%>						<!-- <img src="images/bullet2.gif"> -->
							&nbsp;&nbsp; <a class="rightmenu"  href="<%=sideMenu.getUpperSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer())[i].getLink() %>">
							<%= sideMenu.getUpperSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer())[i].getName() %>
							</a>
							<br/>
						<%}%>
      			        <br/>
						</p>
             			<p align="center">
                        <img src="images/seperator3.gif" width="185" height="5">
              			<a class="rightmenu" href="main.jsp?lang=heb">דף הבית</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="rightmenu" href="main.jsp?lang=eng">English</a> <IMG src="images/seperator3.gif" width="185" height="5">
                        </p>

             		 	<p class="menu" dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left"%>">
                            <%= language.isHebrewVer() ? "פעולות שימושיות:" : "Useful Links" %>
			            	<br/>
							<% for (int i=0; i<sideMenu.getLowerSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer(), language.isLanguageChanged()).length;i++){%>
								&nbsp;&nbsp;
								<a class="rightmenu"  href="<%=sideMenu.getLowerSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer(), false)[i].getLink() %>">
									<%= sideMenu.getLowerSideMenuSubCategories(mainPageViewer.getCategory(language.isHebrewVer()),language.isHebrewVer(), false)[i].getName() %>
								</a>
								<br/>
							<%}%>
							<% if (authorizer.isMopIp(request.getRemoteAddr())){%>
								&nbsp;&nbsp;
								<a class="rightmenu"  href="http://ard.huji.ac.il/welcome.html"/>
									<%= language.isHebrewVer() ? "מערכת רשימות" : "Lists System" %>
								</a>
							<%}%>
          				</p>
          			</td>
        		</tr>
      		</table>
    	</td>
  	</tr>
	<%language.falsifyLanguageChanged();%>
	<tr>
    	<td>
    		<jsp:include page="include/allRightsReservedMainPage.jsp" />
    	</td>
  	</tr>
</table>
<font color="#996633">
<map name="Map">
	<area shape="rect" coords="3,63,305,102" href="/huard/main.jsp?lang=eng" alt="The Authority for Research and Development">
  	<area shape="rect" coords="611,80,902,102" href="/huard/main.jsp?lang=heb" alt="הרשות למחקר ולפיתוח">
  	<area shape="rect" coords="649,30,900,77" href="http://www.huji.ac.il" alt="www.huji.ac.il">
</map>
</font>

<jsp:include page="include/googleAnalytics.jsp"/>
</body>
</html>