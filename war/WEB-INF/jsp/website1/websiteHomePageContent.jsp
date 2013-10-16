<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="rotator">
				<div class="bx-wrapper pictures_slider_wrapper">
					<div class="bx-viewport">
						  <div class="default">
  								<img width="960" height="340" src="/iws/image/website1/default_home_page_image.jpg"/>
								<div class="bx-caption">
									<h3><fmt:message key="${lang.localeId}.website.pictureSliderTitle"/></h3>
									<span> הרשות למחקר ופיתוח</span>										
								</div>
						 </div>
						  <div class="pictureslider bxslider" style="display: none;">
						  		
  								
  								<c:forEach items="${images}" var="image" varStatus="imgIndex">
  								<div>
  								<c:choose>
						  			<c:when test="${imgIndex.index == fn:length(images)-1}">
						  				<img width="960" height="340" src="imageViewer?imageId=${image.id}&attachType=bodyImage" />			
						  			</c:when>
						  			<c:otherwise>
						  				<img class="lazy" width="960" height="340" src="/iws/image/website1/default_home_page_image.jpg" data-src="imageViewer?imageId=${image.id}&attachType=bodyImage" />
						  			</c:otherwise>
						  		</c:choose>
  								
  									
									<div class="bx-caption">
										<h3><fmt:message key="${lang.localeId}.website.pictureSliderTitle"/></h3>
										<span><c:choose><c:when test="${lang.hebrew}">${image.captionHebrew}</c:when><c:otherwise>${image.captionEnglish}</c:otherwise></c:choose></span>										
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
							<a href="#"><fmt:message key="${lang.localeId}.website.readMore"/></a>
						</div>
						<div class="mainbox_text">
							<p></p>
						</div>
					</div>
					<div class="mainbox">
						<div class="title">
							<h2><fmt:message key="${lang.localeId}.website.latestUpdates"/></h2>
							<a href="messages.html?t=1"><fmt:message key="${lang.localeId}.website.allMessages"/></a>
						</div>
						<div class="mainbox_news_wrapper">
							<div class="mainbox_news" style="direction:ltr">
								<div class="bx-wrapper messages_slider_wrapper">
									<div class="bx-viewport"  >
										<div class="messageslider" style="display:none;">
	  										<c:forEach items="${textualPages}" var="textualPage" varStatus="textualIndex">
	  											<c:set var="direction" value="rtl"/>
	  											<c:if test="${textualPage.localeId=='en_US'}">
	  												<c:set var="direction" value="ltr"/>
	  											</c:if>
	  											
	  											<div  style="padding: 0px 5px; height:80px; width:380px; direction:${direction};overflow:hidden;">
	  												<!--${textualPage.creationTimeString}<br>-->
	  												${textualPage.title}<br>
	  												${textualPage.html}
	  												<!--<dfn class="messagePage" id="${textualPage.id}">להודעה המלאה</dfn>  -->
												</div>
	 										</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="side">
					<h3 class="board_title"><fmt:message key="${lang.localeId}.website.callForProposalCalendar"/></h3>
					<div class="board_calendar">
						<div class="date"></div>
						<div class="callForProposalsPerDay" style="display:none;">
							<div class="clearfix"></div>
							<div class="triangle"></div>
						</div>
						<a href="#" class="last_calls"><fmt:message key="${lang.localeId}.website.recentCallForProposals"/></a>
						<div class="board_search">
							<h3><fmt:message key="${lang.localeId}.website.search"/></h3>
							<a href="searchCallForProposals.html?t=1" class="board_search_advanced"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
							<form action="searchCallForProposals.html?t=1" method="post" class="board_form">
								<input type="text" name="searchWords" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" value="<fmt:message key="${lang.localeId}.website.typeSubject"/>" class="input_text" />
								<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="input_submit" />
							</form>
						</div>
					</div>
				</div>
			</div>
