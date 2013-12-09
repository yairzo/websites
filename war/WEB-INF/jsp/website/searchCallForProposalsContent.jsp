<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="popup_placeholder" style="display:none"></div>
			<div class="container clearfix">
				<div class="breadcrumbs clearfix"></div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">${pageTitle}</h1>
					
					<div class="clearfix mar_20">
						<c:if test="${!searchBoxBottom}">
						<%@ include file="/WEB-INF/jsp/website/fundingSearchForm.jsp" %>
						</c:if>
						<div class="clearfix mar_20">
							<div class="kol search_result">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_megaphone_${lang.dir}.png" alt="" />
									<c:if test="${!isDefault}"> &nbsp;<fmt:message key="${lang.localeId}.callForProposal.callForProposalsListFound"/></c:if>
									<c:if test="${isDefault}"> &nbsp;<fmt:message key="${lang.localeId}.callForProposal.callForProposalsListNew"/></c:if>
									</h3>
								</div>
   								<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
									<c:if test="${!isDefault}"><div><fmt:message key="${lang.localeId}.callForProposal.callForProposalsHebrewRemark"/></div></c:if>
								<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
									<a href="" class="search_content viewProposal" id="${callForProposal.urlTitle}">
									<span class="clearfix <c:if test="${callForProposal.localeId=='en_US'}">search_eng</c:if>">${callForProposal.title}</span>
									<span class="clearfix search_icons">
										<span class="search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/> <strong>${callForProposal.fund.name}</strong></span>
										<span class="search_date"><fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> <strong><c:choose><c:when test="${callForProposal.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/></c:when><c:otherwise>${callForProposal.finalSubmissionTimeString}</c:otherwise></c:choose></strong></span>
										<c:if test="${callForProposal.expired}"><span class="search_expired"><img src="/image/website/i-careful-small.png" alt=""> <fmt:message key="${lang.localeId}.website.isExpired"/></span></c:if>
									</span>
									</a>
	   							</c:forEach>
 	  							</c:when>
  	  							<c:otherwise>
  									<span class="clearfix">
  										<fmt:message key="${lang.localeId}.website.noCallForProposals"/>
  									</span> 
  								</c:otherwise>
	  							</c:choose> 								

							</div>							
						</div>
						<c:if test="${searchBoxBottom}">
						<%@ include file="/WEB-INF/jsp/website/fundingSearchForm.jsp" %>
						</c:if>
					</div>
				</div>
			</div>