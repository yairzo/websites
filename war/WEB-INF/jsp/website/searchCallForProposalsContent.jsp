<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="popup_placeholder" style="display:none"></div>
			<div class="container clearfix">
				<div class="breadcrumbs clearfix"></div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">${pageTitle}</h1>
					
					<div class="clearfix mar_20">
					
							<c:if test="${!authorized}">
							<div class="kol open kol_${lang.dir}">
								<div class="clearfix">
									<a class="button_${lang.dir}">
										<span id="login_open" class="button_inner_${lang.dir}"><img src="/image/website/i-user.png" alt=""><fmt:message key="${lang.localeId}.callForProposal.loginLink"/></span>
									</a>
									<p class="forbidden_${lang.dir}"><fmt:message key="${lang.localeId}.callForProposal.fullDetailsLogin"/></p>
								</div>
								<div class="login_box_cp" style="direction: ${lang.dir}; text-align: ${lang.align};">
									<div class="login_box_bottom_cp">
										<form action="/j_acegi_security_check" method="post">									
										<input type="hidden" name="ilr" value="${ilr}"/>
										<div class="clearfix">
										<div class="login_box_col">
											<label class="login_label"><fmt:message key="${lang.localeId}.general.login.username"/></label>
											<input type="text" placeholder="<fmt:message key="${lang.localeId}.website.loginPlaceholder"/>" id="j_username" name="j_username" class="login_input">
										</div>
										<div class="login_box_col pull-${lang.alignOpp}">
											<label for="password" class="login_label"><fmt:message key="${lang.localeId}.general.login.password"/></label>
											<input type="password" name="j_password" class="login_input">
										</div>
										<div class="login_box_col mar_15">
											<input type="submit" value="<fmt:message key="${lang.localeId}.general.login.login"/>" class="login_submit">
										</div>
										<div class="login_box_col mar_15 pull-left">
											<div class="clearfix">
												<a class="login_forgot"><fmt:message key="${lang.localeId}.general.login.loginForgot"/></a>
											</div>
										</div>
										</div>
										<div class="login_register mar_15 clearfix"><fmt:message key="${lang.localeId}.general.login.toSubscribe"/> <a href="#"><fmt:message key="${lang.localeId}.general.login.clickHere"/></a></div>
										</form>
									</div>
								</div>
							</div>
							</c:if>
						<c:if test="${!searchBoxBottom && authorized}">
						<%@ include file="/WEB-INF/jsp/website/fundingSearchForm.jsp" %>
						</c:if>
						<div class="clearfix mar_20">
							<c:if test="${!newSearch || searchedSingleDay || customView}">
							<div class="kol search_result">
								<c:if test="${!searchedSingleDay && !customView}">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_megaphone_${lang.dir}.png" alt="" />
									 &nbsp;<fmt:message key="${lang.localeId}.callForProposal.callForProposalsListFound"/>
									</h3>
								</div>
								</c:if>
   								<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
									<div class="kol_remark"><fmt:message key="${lang.localeId}.callForProposal.callForProposalsHebrewRemark"/></div>
									<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
									<a href="" class="search_content viewProposal" id="${callForProposal.urlTitle}">
									<span class="clearfix <c:if test="${callForProposal.localeId=='en_US'}">search_eng</c:if>">${callForProposal.title}</span>
									<span class="clearfix search_icons">
										<span class="search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/>&nbsp;<span dir="ltr"><strong>${callForProposal.fund.name}</strong></span></span>
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
							</c:if>						
						</div>
						<c:if test="${searchedSingleDay && authorized}">
							<div class="kol_remark_more" style="direction:${lang.dir}"><fmt:message key="${lang.localeId}.callForProposal.callForProposalsRemarkMore"/></div>
						</c:if>
						<c:if test="${customView && authorized}">
							<%@ include file="/WEB-INF/jsp/website/fundingSearchForm.jsp" %>
						</c:if>
					</div>
				</div>
			</div>