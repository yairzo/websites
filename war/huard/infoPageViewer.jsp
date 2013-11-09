<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  import="huard.website.model.Worker" %>
<%@ page pageEncoding="UTF-8" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="infoPageViewer" scope="request" class="huard.website.viewer.page.InfoPageViewer" />
<jsp:setProperty name="infoPageViewer" property="*"/>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="authorizer" scope="session" class="huard.website.session.Authorizer" />
<jsp:setProperty name="authorizer" property="*"/>

<jsp:useBean id="subscriber" scope="page" class="huard.website.session.Subscriber" />
<jsp:setProperty name="subscriber" property="*"/>

<jsp:useBean id="topMenu" scope="page" class="huard.website.menu.TopMenu" />
<jsp:setProperty name="topMenu" property="*"/>

<jsp:useBean id="sideMenu" scope="session" class="huard.website.menu.SideMenu" />
<jsp:setProperty name="sideMenu" property="*"/>

<% System.out.println("Viewing infoPageViewer.jsp"); %>

<html>
<head>

<script language="JavaScript1.2" src="js/cookiesFuncs.js"/>
<script language="JavaScript1.2">
<%= language.getLang()==null ? "manageLanguage();" : "" %>
<%	 if (language.getLang()==null)
		language.setLang("eng");
	 boolean isHebrew = language.isHebrewVer(); 
	 infoPageViewer.logAccessToPage(isHebrew, request.getRemoteAddr());
%>
	checkCookie('<%=language.getLang()%>');
</script>
<% 
   MenuCategory category =  sideMenu.getCategory(infoPageViewer.getCategory(isHebrew),isHebrew);
   String dir = isHebrew ? "rtl" : "ltr";
   String align = isHebrew ? "right" : "left";
   MenuSubCategory [] upperSideMenuSubCategories = sideMenu.getUpperSideMenuSubCategories(infoPageViewer.getCategory(isHebrew),isHebrew);
   MenuSubCategory [] lowerSideMenuSubCategories = sideMenu.getLowerSideMenuSubCategories(infoPageViewer.getCategory(isHebrew),isHebrew, language.isLanguageChanged());
%>

<title><%=infoPageViewer.getTabledInfoPage().getTitle()%></title>

<jsp:include page="include/menuHead.jsp" />

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-8-i">
<meta http-equiv="imagetoolbar" content="no">
<link href="style/style_ard.css" rel="stylesheet" type="text/css">
<script type="text/javascript">document.write(unescape("%3Cscript src='" + (("https:" == document.location.protocol) ? "https" : "http") + "://cdn.mouseflow.com/projects/c7bfcd1a-0fba-4cbc-9dac-af443d1c2be8.js' type='text/javascript'%3E%3C/script%3E"));</script>
</head>


<body leftMargin=0 topmargin="0" marginheight="0" >


<jsp:include page="include/menuBody.jsp" />

<jsp:include page="include/inner_header.jsp" />




<!-- main body table -->

<table border="0" cellpadding="0" cellspacing="1" height="100%" width="100%" dir="<%=dir%>">
	<tbody>
<!-- page location row -->
		<tr>
  			<td height="15" background="images/menu_bg.jpg">
  			</td>
  			<td class="<%= category.getClassColor(isHebrew)%>">
				<p class="small" dir="<%=dir%>" align="<%=align%>">
    				<img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">
    				&nbsp;
    				<%=category.getName()%>
    				<img src="images/nav_arrow_<%=category.getClassColor(isHebrew)%>.png">
    				<%= isHebrew ? "פרטי קול קורא" : "Call of Proposal's Details" %>
    			</p>
  			</td>
  		</tr>
<!-- End of page location row -->
<!-- side menu + content row -->
		<tr>
<!-- side menu -->
			<td width="200" valign="top" background="images/menu_bg.jpg"><br>
      			<p class="menu" dir="<%=isHebrew ? "rtl" : "ltr"%>" align="<%=isHebrew ? "right" : "left"%>">
      				<%=category.getName()%>:
        			<br>
      				<% for (MenuSubCategory menuSubCategory: upperSideMenuSubCategories){%>
						&nbsp;
						<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>"><%= menuSubCategory.getName() %>
						</a>
						<br>
					<%}%>
				</p>
      			<p align="center">
					<IMG src="images/seperator3.gif" width="185" height="5">
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "heb" : "eng"%>">
    				<%= isHebrew ? "דף הבית" :"Home" %>
    				</a>
    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<a class="rightmenu" href="main.jsp?lang=<%= isHebrew ? "eng" : "heb"%>">
    					<%= isHebrew ? "English" : "Hebrew"%>
    				</a>
    				<IMG src="images/seperator3.gif" width="185" height="5">
    			</p>
				<p class="menu" dir="<%=dir%>" align="<%=align%>">
    				<%= isHebrew ? "קישורים שימושיים" : "Useful Links" %>:
					<br>
      				<% for (MenuSubCategory menuSubCategory: lowerSideMenuSubCategories){%>
						&nbsp;
						<a class="rightmenu"  href="<%=menuSubCategory.getLink() %>"><%= menuSubCategory.getName() %>
						</a>
						<br>
					<%}%>
					<% language.falsifyLanguageChanged();%>
					<br>
				</p>
			</td>
<!-- End of side menu -->
			<td valign="top">
				<table border="0" cellpadding="30" width="100%" >
        			<tbody>
        				<tr>
          					<td>
								<table class="<%=infoPageViewer.getTabledInfoPage().isHasExpired() ? "fundingexpired" : "fundingopen"%>" cellpadding="10">
                  					<tbody>
                    					<tr>
                      						<td style="vertical-align: top; padding: 20;" dir="<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "rtl" : "ltr" %>" align="<%=infoPageViewer.getTabledInfoPage().isHebrew() ? "right" : "left"%>">
 <!-- Info page title and dates table -->
		  										<table border="0" width="100%" dir="<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "rtl" : "ltr" %>">
                    								<tbody>
 														<% if (! infoPageViewer.getTabledInfoPage().isDescriptionOnly() &&  authorizer.isAuthorized(request.getRemoteAddr(), infoPageViewer.getMd5())){ %>
                   										<tr>
                     										<td colspan="2" align="center">
		      													<% if (infoPageViewer.getTabledInfoPage().isHebrew()){%>
		      	 													<h1><font  color="purple">
		      	 													חדש!!! אישור הרמו"פ להגשת הצעות מחקר מתבצעת מעתה באמצעות <br>
		      	 													<a href='pubPageViewer.jsp?ardNum=451'><font color="purple">מערכת המחקרים האלקטרונית של הרשות למו"פ</font></a>
			 														</font></h1>
		      													<%}%>
		      													<% if (!infoPageViewer.getTabledInfoPage().isHebrew()){%>
		      	 													<h1><font  color="purple">
		      	 													New!!! From now onwards, research proposals will be approved by ARD, through the <br>
		      	 													<a href='pubPageViewer.jsp?ardNum=451'><font color="purple">ARD Electronic Research System </font></a>
			 														</font></h1>
		      													<%}%>
		      												</td>
		      											</tr>
 														<%}%>
                    									<tr>
                     										<td colspan="2">
		      													<% if (infoPageViewer.getTabledInfoPage().isHasExpired()){%>
		      	 													<p class="big"><%=infoPageViewer.getTabledInfoPage().isHebrew() ? "פג תוקף" : "EXPIRED"%>
			 														</p>
		      													<%}%>
		      												</td>
		      											</tr>
		      											<tr>
		      												<td>
		      													<h1 class="<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "heb" : "eng" %>"><%= infoPageViewer.getTabledInfoPage().getTitleHighlighted()%></h1>
                      										</td>
                      										<td width="150" align="<%=infoPageViewer.getTabledInfoPage().isHebrew() ? "right" : "left"%>"><p class="small" dir="<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "rtl" : "ltr" %>"><%=infoPageViewer.getTabledInfoPage().isHebrew() ? "תאריך פרסום:" : "Publication Date:"%> <%=infoPageViewer.getTabledInfoPage().getFormatedPubDate()%><br>
                          										<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "עדכון אחרון:" : "Last update:" %> <%=infoPageViewer.getInfoPageLastUpdateDate()%> </p>
                      										</td>
                    									</tr>
                  									</tbody>
		  										</table>
<!-- end of info page title and dates table -->
												<% if (! infoPageViewer.getTabledInfoPage().isDescriptionOnly() && authorizer.isAuthorized(request.getRemoteAddr(), infoPageViewer.getMd5())){ %>
<!-- General Infomation -->
													<h3><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "מידע כללי" : "General Information"%></h3>
                  									<p><!--<strong>-->
		  												<% if (infoPageViewer.getTabledInfoPage().getSubSite().equals("Fund")){ %>
       														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תאריך הגשה:" : "Submission Date:" %> <%=infoPageViewer.getTabledInfoPage().getFormatedSubDate()%>
      														<br>
      														<% if (! infoPageViewer.getTabledInfoPage().isHasExpired()){%>
																<% if (infoPageViewer.getTabledInfoPage().isHasSubDateDetails()) { %>
																	<%= infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getSubDateDetails())%>
																	<br>
																<%}%>
															<%}else{%>
																<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "פרטים הנוגעים לתאריך ההגשה:" : "Details About Submission Date:"%> <font color=red><b> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "נא לשים לב ! תאריך ההגשה הנוכחי חלף" : "Notice ! The current submission date is over" %>
																<% if (infoPageViewer.getTabledInfoPage().isHasAdditionalSubDates()){%> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "ראה/י תאריכי הגשה נוספים בהמשך" : ", check below for additional submission dates" %>
																<%}else if (infoPageViewer.getTabledInfoPage().isRepetitive()){%>
																	<%= infoPageViewer.getTabledInfoPage().isHebrew() ? ", לקול קורא זה סיכויים טובים לחזור בשנה הבאה" : ". This call has good chances to repeat next year" %>
																<%}%>
																</b>
																</font>
																<br>
															<%}%>
														<%}%>
    													<% if (infoPageViewer.getTabledInfoPage().getSubSite().equals("ARD")){ %>
      														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תאריך הגשה ברשות למו\"פ:" : "Submission Date at the Authority for Research & Development:"%> <%=infoPageViewer.getTabledInfoPage().getFormatedSubDate()%><br>
															<% if ( infoPageViewer.getTabledInfoPage().isHasSubDateFund()) { %>
     															<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תאריך הגשה בקרן:" : "Submission Date at Funding Agency:"%> <%=infoPageViewer.getTabledInfoPage().getSubDateFund()%> <br>
     														<%}%>
															<% if (! infoPageViewer.getTabledInfoPage().isHasExpired()){%>
     															<% if ( infoPageViewer.getTabledInfoPage().isHasSubDateDetails() ) { %>
					 												<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "פרטים הנוגעים לתאריך ההגשה:" : "Details About Submission Date:"%>	<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getSubDateDetails()) %>
																	<br>
																<%}%>
															<%}else{%>
    															<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "פרטים הנוגעים לתאריך ההגשה:" : "Details About Submission Date:"%> <font color=red><b> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "נא לשים לב ! תאריך ההגשה הנוכחי חלף" : "Notice ! The current submission date is over"%>
																<% if (infoPageViewer.getTabledInfoPage().isHasAdditionalSubDates()){%> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "ראה/י תאריכי הגשה נוספים בהמשך" : ", check below for additional submission dates" %>
																<%}else if (infoPageViewer.getTabledInfoPage().isRepetitive()){%><%= infoPageViewer.getTabledInfoPage().isHebrew() ? ", לקול קורא זה סיכויים טובים לחזור בשנה הבאה" : ". This call has good chances to repeat next year" %>
																<%}%>
																</b></font>
																<br>
															<%}%>
														<%}%>
														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "מקום ההגשה:" : "Submission Site:"%>
       													<%if (infoPageViewer.getTabledInfoPage().getSubSite().equals("ARD")){%>
															<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "ברשות למו\"פ" : "The Authority for Research & Development" %>
              											<% } else { %>
	      													<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "בקרן" : "Funding Agency" %>
	      												<%}%>
	      												<br>
	      												<% if (infoPageViewer.getTabledInfoPage().isHasAdditionalSubDates()){%>
															<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תאריכי הגשה נוספים:" : "Additional Submission Dates:"%>
		 													<% for (int i=0; i<infoPageViewer.getFormatedAdditionalSubDates().length; i++){%>
																<%=infoPageViewer.getFormatedAdditionalSubDates()[i]%>&nbsp;
	         												<%}%>
		 													<br>
	     												<%}%>
    	     											<% if (infoPageViewer.getTabledInfoPage().getNumOfCopies()>0){%>
             												<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "מספר עותקים:" : "Number Of Copies:"%>
      	     												<%=infoPageViewer.getTabledInfoPage().getNumOfCopies()%>
             												<%if (! infoPageViewer.getTabledInfoPage().isSendDigitalCopy()){%>
	             												<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "+ עותק נוסף לרשות למו\"פ" : "+ Additional Copy to the Authority for R&D"%>
    														<%}%>
    														<br>
    	     											<%}if (infoPageViewer.getTabledInfoPage().isSendDigitalCopy()){
    	     												String sendDigitalCopyText =
    	     												(infoPageViewer.getTabledInfoPage().isHebrew() ? "עותק מההצעה יש להעביר " : "Send one copy by ") + "<a href=\"mailto:"; 
     	     												for (Worker worker : infoPageViewer.getWorkersByDesk()) {
		 														if (worker.getEnglishTitle().contains("Assistant")){
		 															sendDigitalCopyText += worker.getMailAddress();
		 															break;
		 														}
    	     											    }
    	     												sendDigitalCopyText+= "\">" + (infoPageViewer.getTabledInfoPage().isHebrew() ? "בדואר אלקטרוני לרשות למו\"פ" : "Email to the Authority for R&D") + "</a>";
		 													%>
    	     												<%= sendDigitalCopyText %>
    	     												<br>
    	     											<%}%>
														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "קרן המימון:" : "Funding Agency:"%>
 														<span dir="ltr">
 														<% if (infoPageViewer.getFundByFundNum().getWebAddress().length()>0){%>
      														<a href="http://<%=infoPageViewer.getFundByFundNum().getWebAddress()%>" target="view_window"><%=infoPageViewer.getFundByFundNum().getFullName()%></a>
          												<%} else{%>
              												<%=infoPageViewer.getFundByFundNum().getFullName()%>
          												<%} %>
          												</span>
        												<br>
													</p>
<!-- End of General Inforamtion -->
												<%}%>
<!-- Description -->
												<h3 class="<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "heb" : "eng" %>">
           											<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תקציר" : "Description" %>
        										</h3>
												<p> <%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getDescription())%>
												</p>
<!-- End of Description -->
												<% if (! infoPageViewer.getTabledInfoPage().isDescriptionOnly() && authorizer.isAuthorized(request.getRemoteAddr(), infoPageViewer.getMd5())){ %>
<!-- Contacts in the Authority -->
													<h3><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "אנשי קשר ברשות למו\"פ" : "Contacts at the Authority for Research & Development" %></h3>
													<% if (infoPageViewer.getTabledInfoPage().isHebrew()){%>
														<p><!--<strong>-->
															<% for (Worker worker : infoPageViewer.getWorkersByDesk()) {
		 														boolean isFundsBudgetOfficer = infoPageViewer.getFundsBudgetOfficerEnglishName().equals(worker.getEnglishNameWithoutHtmlTags());
		 														if ((worker.getEnglishTitle().contains("Budget") || worker.getEnglishTitle().contains("Financial"))  && ! isFundsBudgetOfficer)
																	continue;
																worker.markText(infoPageViewer.getFoundBySearchWords());
																if (! worker.getEnglishName().equals("")) { %>
																	<a href="mailto:<%=worker.getMailAddress()%>"><%=worker.getHebrewPre() %> <%=worker.getHebrewName() %></a>
																	<img src="images/bullet_orange.gif" width="12" height="8">
																	<%=worker.getHebrewTitle() %>
																	<img src="images/bullet_orange.gif" width="12" height="8">
																	<%=worker.getPhone() %>
																	<br/>
																<%}
															} %>
															<br/>
													<%}else{%>
														<p>
															<% for (Worker worker : infoPageViewer.getWorkersByDesk()) {
																boolean isFundsBudgetOfficer = infoPageViewer.getFundsBudgetOfficerEnglishName().equals(worker.getEnglishNameWithoutHtmlTags());
																if ((worker.getEnglishTitle().contains("Budget") || worker.getEnglishTitle().contains("Financial"))  && ! isFundsBudgetOfficer)
																	continue;
																worker.markText(infoPageViewer.getFoundBySearchWords());
																if (! worker.getEnglishName().equals("")) { %>
																	<a href="mailto:<%=worker.getMailAddress()%>"><%=worker.getEnglishPre() %> <%=worker.getEnglishName() %></a>
																	<img src="images/bullet_orange.gif" width="12" height="8">
																	<%=worker.getEnglishTitle() %>
																	<img src="images/bullet_orange.gif" width="12" height="8">
																	<%=worker.getPhone() %>
																	<br>
																<%}
															}%>
															<br/>
													<%}%>
													<% if ( infoPageViewer.getTabledInfoPage().isHasDeskAndContact() ) { %>
		 												<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getDeskAndContact())%>
													<%}%>
<!-- End of Contacts in the Authority -->
<!-- Forms field -->
													<a name="forms"></a>
													<h3> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "טפסים" : "Forms" %> </h3>
													<p>
														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "טפסים להגשה לקרן:" : "Application Forms:" %>
													</p>
													<% if ( infoPageViewer.getTabledInfoPage().isHasForms () ) { %>
			 											<%=infoPageViewer.getFormatedText( infoPageViewer.getTabledInfoPage().getForms() )%>
													<%} else { %>
														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "לא פורסמו טפסים" : "No Forms Were Published" %>
													<%}%>
													<br>
													<% if (infoPageViewer.getTabledInfoPage().getDocType().equals("Research Grant")){%>
														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "טפסים להגשה ברשות:" : "General Forms:" %>
														<% if (infoPageViewer.getTabledInfoPage().isHebrew()){%>
														<p style="color:purple;font-size:16;font-weigth:bold"> לתשומת ליבכם, טופס מרובע, טופס ניגוד עניינים ונספח בטיחות, מוגשים מעתה באמצעות <a href='pubPageViewer.jsp?ardNum=451'><font color="purple">מערכת המחקרים האלקטרונית של הרשות למו"פ </font></a> ואין צורך להגישם בעותק קשיח בנפרד.</p>
														<%}%>
														<% if (!infoPageViewer.getTabledInfoPage().isHebrew()){%>
														<p style="color:purple;font-size:16;font-weigth:bold"> Please note, from now on the Approval Request, Declaration Regarding Conduct in Research and Security Appendix will be submitted through the <a href='pubPageViewer.jsp?ardNum=451'><font color="purple">ARD Electronic Research System. </font></a> There is no need to submit them seperately in hard copies.</p>
														<%}%>
														<% if (infoPageViewer.getTabledInfoPage().getDeskId().equals("EU")){%>
																<a href="ftp://ard.huji.ac.il/pub/EU/VAT_form.doc"> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "טופס החזר מע\"מ" : "VAT_form" %></a><br>
																<a href="ftp://ard.huji.ac.il/pub/EU/Yissum_form.doc"><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "טופס הסכמה של יישום" : "Yissum Consent Form" %></a>
																<p class="blacksmall"><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "יש להגיש את הטפסים עם חתימה מקורית" : "Forms should be submitted with original signature" %><br>
			 													<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "רשימה של " : "A list of" %> <a href="http://ard.huji.ac.il/huard/pubPageViewer.jsp?ardNum=116" target="_new"><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "בעלי תפקידים" : "functionaries" %></a> <%= infoPageViewer.getTabledInfoPage().isHebrew() ? "להחתמה על הטפסים" : "to sign the forms" %>
			 													</p>
														<%}%>
													<%}%>
<!-- End of Forms Field -->
<!--  Funding Details Part -->
													<h3><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תנאי מימון" : "Funding Details" %></h3>
													<p>
														<!--<strong>-->
														<ul>
															<% if ( infoPageViewer.getTabledInfoPage().isHasMaxFundingPeriod() ) { %>
    															<li>
																	<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "תקופה מירבית למימון:" : "Maximum Funding Period:" %>
      																<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getMaxFundingPeriod())%>
																</li>
															<%}%>
    														<% if ( infoPageViewer.getTabledInfoPage().isHasAmountOfGrant() ) { %>
      															<li>
																	<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "גובה מימון מירבי:" : "Amount of Grant:" %>
      																<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getAmountOfGrant())%>
																</li>
  															<%}%>
    														<% if ( infoPageViewer.getTabledInfoPage().isHasEligibilityRequirements() ) { %>
    		       												<li>
		       														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "זכאות למימון:" : "Eligibility Requirements:" %> <%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getEligibilityRequirements())%>
		       													</li>
    														<%}%>
    														<% if ( infoPageViewer.getTabledInfoPage().isHasActivityLocation() ) { %>
																<li>
																	<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "מיקום פעילות:" : "Activity Location:" %>
      																<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getActivityLocation())%>
																</li>
    														<%}%>
															<% if ( infoPageViewer.getTabledInfoPage().isHasPossibleCollaboration() ) { %>
    		        											<li>
																	<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "אפשרות שיתוף פעולה:" : "Possible Collaboration:" %>
      																<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getPossibleCollaboration())%>
																</li>
															<%}%>
															<li>
																<% if ( infoPageViewer.getTabledInfoPage().isHasLocalWebPage() ) {%>
																	<a href="http://ard.huji.ac.il/huard/pageExecuterFullText.jsp?ardNum=<%=infoPageViewer.getTabledInfoPage().getArdNum()%>" target="_blank">
																<%}else {%>
																	<a href="http://<%= infoPageViewer.getTabledInfoPage().getPageWebAddress() %>" target="view_window">
																<%}%>
																<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "לקול קורא המקורי" : "Original Call for Proposal" %>
																	</a>
															</li>
														</ul>
<!-- End of Funding Details -->
<!-- Budget Instructions Part -->
													<p>
														<!--<strong>-->
														<% if ( infoPageViewer.getTabledInfoPage().isHasBudgetDetails() || (infoPageViewer.getTabledInfoPage().getDocType().equals("Research Grant") && infoPageViewer.isHasBudgetOfficerWorker()) && infoPageViewer.getTabledInfoPage().isAppendBudgetOfficeLine()) { %>

																<h3><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "הנחיות תקצוב" : "Budget Details" %></h3>

														<%}%>
														<% if ( infoPageViewer.getTabledInfoPage().isHasBudgetDetails() ) { %>
      														<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getBudgetDetails())%>
															<br>
      													<%}%>
      													<% if ( infoPageViewer.getTabledInfoPage().getDocType().equals("Research Grant") && infoPageViewer.isHasBudgetOfficerWorker() && infoPageViewer.getTabledInfoPage().isAppendBudgetOfficeLine()){ %>
      														<%= infoPageViewer.getTabledInfoPage().isHebrew() ? "הצעת התקציב חייבת אישור התקציבאי/ת" : "The budget proposal needs the approval of the budget officer" %>
      														<a href="mailto:<%=infoPageViewer.getBudgetOfficerWorker().getEmail() %>"><%= infoPageViewer.getTabledInfoPage().isHebrew() ? (infoPageViewer.getBudgetOfficerWorker().getHebrewPre()+" "+infoPageViewer.getBudgetOfficerWorker().getHebrewName()) : (infoPageViewer.getBudgetOfficerWorker().getEnglishPre()+" "+infoPageViewer.getBudgetOfficerWorker().getEnglishName()) %></a>
      														<img src="images/bullet_orange.gif" width="12" height="8"><%=infoPageViewer.getBudgetOfficerWorker().getPhone()%>
      													<%}%>
<!-- End of Budget Details Part -->
<!-- Additional Information -->
														<% if ( infoPageViewer.getTabledInfoPage().isHasAdditionalInformation() ) { %>
															<h3><%= infoPageViewer.getTabledInfoPage().isHebrew() ? "מידע נוסף" : "Additional Information" %></h3>
															<p>
																<!--<strong>-->
																	<%=infoPageViewer.getFormatedText(infoPageViewer.getTabledInfoPage().getAdditionalInformation())%>
														<%}%>
<!-- End of Additional Information -->
												<%}%>
												<br><br>
												<% if (! infoPageViewer.getTabledInfoPage().isDescriptionOnly() && ! authorizer.isAuthorized(request.getRemoteAddr(), infoPageViewer.getMd5())){ %>
													<jsp:include page="include/signIn.jsp" />
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
			</td>
		</tr>
	</tbody>
</table>
<jsp:include page="include/googleAnalytics.jsp"/>
</body>
</html>