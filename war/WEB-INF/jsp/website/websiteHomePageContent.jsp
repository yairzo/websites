<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<div class="rotator">
				<div class="bx-wrapper pictures_slider_wrapper">
					<div class="bx-viewport">
						  <div class="default">
  								<img width="960" height="340" src="/image/website/default_home_page_image.jpg"/>
								<div class="bx-caption">
									<h3><fmt:message key="${lang.localeId}.website.pictureSliderTitle"/></h3>
									<span><fmt:message key="${lang.localeId}.website.authority"/><br><br>
									<fmt:message key="${lang.localeId}.website.pictureSliderStopMessage"/></span>										
								</div>
						 </div>
						  <div class="pictureslider bxslider" style="display: none;">
						  		
  								
  								<c:forEach items="${images}" var="image" varStatus="imgIndex">
  								<div>
  								<c:choose>
						  			<c:when test="${(lang.rtl && imgIndex.index == fn:length(images)-1) || (lang.ltr && imgIndex.index == 0)}">
						  				<img width="960" height="340" src="/imageViewer?imageId=${image.id}&attachType=bodyImage" />			
						  			</c:when>
						  			<c:otherwise>
						  				<img class="lazy" width="960" height="340" src="/image/website/default_home_page_image.jpg" data-src="/imageViewer?imageId=${image.id}&attachType=bodyImage" />
						  			</c:otherwise>
						  		</c:choose>
  								
  									
									<div class="bx-caption">
										<h3><fmt:message key="${lang.localeId}.website.pictureSliderTitle"/></h3>
										<span>
										<c:choose><c:when test="${lang.hebrew}">${image.captionHebrew}</c:when><c:otherwise>${image.captionEnglish}</c:otherwise></c:choose>
										<br>
										<c:if test="${fn:length(image.url)>0}">
										<a href="${image.url}"><fmt:message key="${lang.localeId}.website.researcherWebsite"/></a>
										</c:if>
										</span>										
																				
									</div>
								</div>
								</c:forEach>							
						 </div>
 					</div>
 				</div>
			</div>
			
			<div class="container clearfix">
				<jsp:include page="sideLinks.jsp"/>
				<div class="main">
					<div class="mainbox">
						<div class="title">
							<h1><fmt:message key="${lang.localeId}.website.welcome"/></h1>
							<a href="/page/Mission_Statement_Hebrew"><fmt:message key="${lang.localeId}.website.readMore"/></a>
						</div>
						<div class="mainbox_text">
							<c:if test="${lang.localeId=='iw_IL'}">
							<p>באתר הרשות למחקר ופיתוח תמצאו מידע מפורט על אפשרויות למימון מחקר, מלגות לתארים מתקדמים ופרסומים על כנסים מדעיים, וכן הנחיות להגשת הצעות מחקר, כללים להפעלת מענקי מחקר ומידע כללי על הסביבה המדעית באוניברסיטה.
							<br />
							רישום למערכת הדיוור הישיר והתחברות לאתר מאפשרים צפייה בקולות קוראים רק בתחומי המחקר שנבחרו.</p>
							</c:if>
							<c:if test="${lang.localeId=='en_US'}">
							<p>The Authority for Research & Development’s website provides information on research funding opportunities, fellowships and conferences, and guidelines on how to apply for research grants and to manage research budgets, as well as general information on scientific activity at the Hebrew University.
							<br />
							Subscribing to the mailing list and login to the website will enable you to browse only the research areas you chose.							
							</p>
							</c:if>
						</div>
					</div>
					<div class="mainbox">
						<div class="title">
							<h2><fmt:message key="${lang.localeId}.website.latestUpdates"/></h2>
							<a href="/messages"><fmt:message key="${lang.localeId}.website.allMessages"/></a>
						</div>
						<div class="mainbox_news_wrapper">
							<div class="mainbox_news" style="direction:ltr">
										<div class="messageslider" style="display:none;">
										<!--[if lte IE 8]><c:forEach items="${textualPagesForIE}" var="textualPage" varStatus="textualIndex">										
	  											<c:set var="direction" value="rtl"/>
	  											<c:set var="left" value="10px"/>
	  											<c:if test="${textualPage.localeId=='en_US'}">
	  												<c:set var="direction" value="ltr"/>
	  												<c:set var="left" value="315px"/>
	  											</c:if>
	  											
	  											<div style="padding: 0px 5px; height:100px; width:380px; direction:${direction};overflow:hidden;">
	  												<div style="padding: 0px 5px; height:70px; width:380px; direction:${direction};overflow:hidden;">
	  													<div><strong>${textualPage.title}</strong></div>
	  													<div><br>${textualPage.html}></div>
	  												</div>
	  												<div class="messagePage" id="${textualPage.urlTitle}" style="position:absolute;top:80px;left:${left};text-decoration:underline;"><fmt:message key="${textualPage.localeId}.website.forFullMessage"/></div>
												</div>
	 										</c:forEach>
	 										<![endif]-->
										<!--[if gt IE 8]><!--><c:forEach items="${textualPages}" var="textualPage" varStatus="textualIndex">										
	  											<c:set var="direction" value="rtl"/>
	  											<c:set var="left" value="10px"/>
	  											<c:if test="${textualPage.localeId=='en_US'}">
	  												<c:set var="direction" value="ltr"/>
	  												<c:set var="left" value="315px"/>
	  											</c:if>
	  											
	  											<div style="padding: 0px 5px; height:100px; width:380px; direction:${direction};overflow:hidden;">
	  												<div style="padding: 0px 5px; height:70px; width:380px; direction:${direction};overflow:hidden;">
	  													<div><strong>${textualPage.title}</strong></div>
	  													<div><br>${textualPage.html}></div>
	  												</div>
	  												<div class="messagePage" id="${textualPage.urlTitle}" style="position:absolute;top:80px;left:${left};text-decoration:underline;"><fmt:message key="${textualPage.localeId}.website.forFullMessage"/></div>
												</div>
	 										</c:forEach>
	 										<!--<![endif]-->
										</div>
									</div>
						</div>
					</div>
				</div>
				<div class="side">
					<h3 class="board_title"><fmt:message key="${lang.localeId}.website.callForProposalCalendar"/></h3>
					<div class="board_calendar">
						<div class="homePageCalendar">
						<jsp:include page="/callForProposalCalendar.html?h=1"/>
						</div>
						
						<a href="/recent_announcements" class="last_calls"><fmt:message key="${lang.localeId}.website.recentCallForProposals"/></a>
						<div class="board_search">
							<h3><fmt:message key="${lang.localeId}.website.search"/></h3>
							<a href="/search_funding" class="board_search_advanced"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
							<form action="/search" method="post" class="board_form">
								<input type="text" name="searchWords" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" placeholder="<fmt:message key="${lang.localeId}.website.typeSubject"/>" class="input_text" />
								<input type="hidden" name="open" value="true"/>
								<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="input_submit" />
							</form>
						</div>
					</div>
				</div>
			</div>
