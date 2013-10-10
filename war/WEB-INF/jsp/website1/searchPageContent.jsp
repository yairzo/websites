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
									<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="advanced_submit" />
									<a href="#" onClick="javascript:${'.advanced_subject'}.html('');" class="advanced_clear"><fmt:message key="${lang.localeId}.website.cleanSearch"/></a>
								</div>
							</form>
							<div class="clearfix">
								<a href="searchCallForProposals.html?t=1" class="advanced_close"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
							</div>
						</div>
						
						<div class="clearfix mar_20">
							<div class="kol_${lang.dir} search_result">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="image/website1/search_megaphone.png" alt="" /> &nbsp;<fmt:message key="${lang.localeId}.callForProposal.callForProposalsList"/></h3>
								</div>
    							<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
								<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
								<a href="#" class="search_content viewProposal" id="${callForProposal.id}">
									<span class="clearfix <c:if test="${callForProposal.localeId=='en_US'}">search_eng</c:if>">${callForProposal.title}</span>
									<span class="clearfix search_icons">
										<span class="search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/> <strong>${callForProposal.fund.name}</strong></span>
										<span class="search_date"><fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> <strong><c:choose><c:when test="${callForProposal.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/></c:when><c:otherwise>${callForProposal.finalSubmissionTimeString}</c:otherwise></c:choose></strong></span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>
  								<fmt:message key="${lang.localeId}.website.noCallForProposals"/> 
  								</c:otherwise>
	  							</c:choose> 								
								
							</div>
							
							<div class="kol_${lang.dir} search_result open">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="image/website1/search_balloon.png" alt="" /> &nbsp;<fmt:message key="${lang.localeId}.website.messages"/></h3>
								</div>
								<c:choose>
    							<c:when test="${fn:length(textualMessages) > 0}">
								<c:forEach items="${textualMessages}" var="textualMessage" varStatus="varStatus">
								<a href="textualPage.html?id=${textualMessage.id}&t=1" class="search_content">
									<span class="clearfix">${textualMessage.title}</span>
									<span class="clearfix search_icons">
										<span class="search_from"><fmt:message key="${lang.localeId}.website.by"/> <strong>${textualMessage.creator.degreeFullNameHebrew }</strong></span>
										<span class="search_date"><fmt:message key="${lang.localeId}.website.messageDate"/> <strong>${textualMessage.creationTimeString }</strong></span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise><fmt:message key="${lang.localeId}.website.noMessages"/> 
  								</c:otherwise>
  								</c:choose> 
							</div>
							
							<div class="kol_${lang.dir} search_result open">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="image/website1/search_text.png" alt="" /> &nbsp; <fmt:message key="${lang.localeId}.website.textualPagesList"/></h3>
								</div>
								<c:choose>
    							<c:when test="${fn:length(textualPages) > 0}">
								<c:forEach items="${textualPages}" var="textualPage" varStatus="varStatus">
								<a href="textualPage.html?id=${textualPage.id}&t=1" class="search_content">
									<span class="clearfix">${textualPage.title}</span>
									<span class="clearfix search_icons">
										<span class="search_info"><fmt:message key="${lang.localeId}.website.generalInfo"/></span>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise><fmt:message key="${lang.localeId}.website.noTextualPages"/> 
  								</c:otherwise>
  								</c:choose> 
							</div>
							
						</div>
					</div>
				</div>
			</div>
						<div class="popup_placeholder" style="display:none"></div>
			