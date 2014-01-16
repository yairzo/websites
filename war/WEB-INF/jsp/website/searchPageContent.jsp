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
						<c:if test="${!searchBoxBottom}">
						<div class="advanced">
							<form action="/search" method="post">
								<div class="clearfix">
									<div class="advanced_subject">
										<label for="advanced_subject"><fmt:message key="${lang.localeId}.callForProposal.searchWords"/></label>
										<input type="text" name="searchWords" value="${searchWords}" id="advanced_subject" />
									</div>
								</div>
								<div class="clearfix mar_15">
									<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="advanced_submit" />
									<a href="#" class="advanced_clear"><fmt:message key="${lang.localeId}.website.cleanSearch"/></a>
								</div>
							</form>
							<div class="clearfix">
								<a href="/search_funding" class="advanced_close"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
							</div>
						</div>
						</c:if>
						
						<div class="clearfix mar_20">
						
						<c:if test="${!isDefault || viewType=='new_cfps'}">
							<div class="kol_${lang.dir} search_result">
								<c:if test="${viewType!='new_cfps' }">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_megaphone.png" alt="" /> &nbsp;
									<fmt:message key="${lang.localeId}.callForProposal.callForProposalsList"/>
									</h3>
								</div>
								</c:if>
    							<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
								<div class="kol_remark"><fmt:message key="${lang.localeId}.callForProposal.callForProposalsHebrewRemark"/></div>
								<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
								<a href="" class="search_content viewProposal ${callForProposal.urlTitle}" id="${callForProposal.id}">
									<span class="clearfix <c:choose><c:when test="${callForProposal.localeId=='en_US'}">search_eng</c:when><c:otherwise>search_heb</c:otherwise></c:choose>">${callForProposal.title}</span>
									<span class="clearfix search_icons">
										<span class="search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/> <font dir="ltr"><strong>${callForProposal.fund.name}</strong></font></span>
										<span class="search_date"><fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> <strong><c:choose><c:when test="${callForProposal.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/></c:when><c:otherwise>${callForProposal.finalSubmissionTimeString}</c:otherwise></c:choose></strong></span>
										<c:if test="${callForProposal.expired}"><span class="search_expired"><img src="/image/website/i-careful-small.png" alt=""> <fmt:message key="${lang.localeId}.website.isExpired"/></span></c:if>
									</span>
								</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>
  								<fmt:message key="${lang.localeId}.website.noCallForProposals"/> 
  								</c:otherwise>
	  							</c:choose> 								
							</div>
						</c:if>
						<c:if test="${!isDefault && viewType!='new_cfps'}">
								<div class="kol_${lang.dir} search_result open">
									<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_balloon.png" alt="" /> &nbsp;<fmt:message key="${lang.localeId}.website.messages"/></h3>
									</div>
									<c:choose>
    								<c:when test="${fn:length(textualMessages) > 0}">
									<c:forEach items="${textualMessages}" var="textualMessage" varStatus="varStatus">
									<a href="/textualPage.html?id=${textualMessage.id}" class="search_content">
							 			<span class="clearfix">${textualMessage.title}</span>
										<span class="clearfix search_icons">
											<!-- <span class="search_from"><fmt:message key="${lang.localeId}.website.by"/> <strong>${textualMessage.creator.degreeFullNameHebrew }</strong></span> -->
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
										<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_text.png" alt="" /> &nbsp; <fmt:message key="${lang.localeId}.website.textualPagesList"/></h3>
									</div>
									<c:choose>
    								<c:when test="${fn:length(textualPages) > 0}">
									<c:forEach items="${textualPages}" var="textualPage" varStatus="varStatus">
									<a href="/textualPage.html?id=${textualPage.id}" class="search_content">
										<span class="clearfix">${textualPage.title}</span>
										<span class="clearfix search_icons">
											<span class="search_info">${textualPage.category.name}</span>
										</span>
									</a>
	   								</c:forEach>
 	  								</c:when>
  	  								<c:otherwise><fmt:message key="${lang.localeId}.website.noTextualPages"/> 
  									</c:otherwise>
  									</c:choose> 
								</div>
						</c:if>
						</div>

						<c:if test="${searchBoxBottom}">
						<div class="advanced">
							<form action="/search" method="post">
								<div class="clearfix">
									<div class="advanced_subject">
										<label for="advanced_subject"><fmt:message key="${lang.localeId}.callForProposal.searchWords"/></label>
										<input type="text" name="searchWords" value="${searchWords}" id="advanced_subject" />
									</div>
								</div>
								<div class="clearfix mar_15">
									<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="advanced_submit" />
									<a href="#" class="advanced_clear"><fmt:message key="${lang.localeId}.website.cleanSearch"/></a>
								</div>
							</form>
							<div class="clearfix">
								<a href="/search_funding" class="advanced_close"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>
						<div class="popup_placeholder" style="display:none"></div>
			