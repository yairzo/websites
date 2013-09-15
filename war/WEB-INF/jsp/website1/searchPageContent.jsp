<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
 			<div class="container clearfix">
				<div class="breadcrumbs clearfix" dir="${lang.dir}" align="${lang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">${pageTitle}</h1>
					
					<div class="clearfix mar_20">
						<div class="advanced">
							<form action="search.html?t=1" method="post">
								<div class="clearfix">
									<div class="advanced_subject">
										<label for="advanced_subject"><fmt:message key="${lang.localeId}.callForProposal.searchWords"/></label>
										<input type="text" name="searchWords" value="${searchWords}" id="advanced_subject" />
									</div>
								</div>
								<div class="clearfix mar_15">
									<input type="submit" value="חיפוש" class="advanced_submit" />
									<a href="#" onClick="javascript:${'.advanced_subject'}.html('');" class="advanced_clear">נקה חיפוש</a>
								</div>
							</form>
							<div class="clearfix">
								<a href="searchCallForProposals.html?t=1" class="advanced_close">חיפוש מתקדם של קולות קוראים</a>
							</div>
						</div>
						
						<div class="clearfix mar_20">
							<div class="kol search_result">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/search_megaphone.png" alt="" /> &nbsp; קולות קוראים</h3>
								</div>
    							<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
								<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
								<a href="#" class="search_content viewProposal" id="${callForProposal.id}">
									<span class="clearfix <c:if test="${callForProposal.localeId=='en_US'}">search_eng</c:if>">${callForProposal.title}</span>
									<span class="clearfix search_icons">
										<span class="search_financing">מממן <strong>${callForProposal.fund.name}</strong></span>
										<span class="search_date">תאריך הגשה <strong><c:choose><c:when test="${callForProposal.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/></c:when><c:otherwise>${callForProposal.finalSubmissionTimeString}</c:otherwise></c:choose></strong></span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>
  								לא נמצאו קולות קוראים 
  								</c:otherwise>
	  							</c:choose> 								
								
							</div>
							
							<div class="kol search_result open">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/search_balloon.png" alt="" /> &nbsp; הודעות</h3>
								</div>
								<c:choose>
    							<c:when test="${fn:length(textualMessages) > 0}">
								<c:forEach items="${textualMessages}" var="textualMessage" varStatus="varStatus">
								<a href="textualPage.html?id=${textualMessage.id}&t=1" class="search_content">
									<span class="clearfix">${textualMessage.title}</span>
									<span class="clearfix search_icons">
										<span class="search_from">מאת <strong>${textualMessage.creator.degreeFullNameHebrew }</strong></span>
										<span class="search_date">תאריך ההודעה <strong>${textualMessage.creationTimeString }</strong></span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>לא נמצאו הודעות 
  								</c:otherwise>
  								</c:choose> 
							</div>
							
							<div class="kol search_result open">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/search_text.png" alt="" /> &nbsp; עמודי תוכן</h3>
								</div>
								<c:choose>
    							<c:when test="${fn:length(textualPages) > 0}">
								<c:forEach items="${textualPages}" var="textualPage" varStatus="varStatus">
								<a href="textualPage.html?id=${textualPage.id}&t=1" class="search_content">
									<span class="clearfix">${textualPage.title}</span>
									<span class="clearfix search_icons">
										<span class="search_info">מידע כללי</span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>לא נמצאו עמודי תוכן 
  								</c:otherwise>
  								</c:choose> 
							</div>
							
						</div>
					</div>
				</div>
			</div>
						<div class="popup_placeholder" style="display:none"></div>
			