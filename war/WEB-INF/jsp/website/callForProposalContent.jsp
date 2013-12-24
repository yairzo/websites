<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<div class="container clearfix">
				<div class="breadcrumbs clearfix"></div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" style="direction: ${copLang.dir};">
					<h1 class="maintitle">${command.title}</h1>
					<div class="clearfix mar_20">
						<div class="clearfix mar_20" style="direction: ${copLang.dir}; text-align: ${copLang.align};">
							<div class="kol open kol_${copLang.dir} general_info">
								<c:if test="${command.expired}">
								<div class="clearfix">
									<h3 class="careful_${copLang.dir}"><fmt:message key="${copLang.localeId}.website.isExpired"/></h3>
								</div>
								</c:if>
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_i.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.generalInfo"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
										<p>
										<strong><fmt:message key="${copLang.localeId}.callForProposal.finalSubmissionTime"/></strong>
										<c:if test="${command.allYearSubmission}">
											<fmt:message key="${copLang.localeId}.callForProposal.allYearSubmission"/>
										</c:if>
										<c:if test="${!command.allYearSubmission}">
											${finalSubmissionTime}<c:if test="${fn:length(command.finalSubmissionHour)>0}">,&nbsp <fmt:message key="${copLang.localeId}.callForProposal.atHour"/> ${command.finalSubmissionHour} (<fmt:message key="${copLang.localeId}.callForProposal.finalSubmissionHour.${command.hourType}"/>)</c:if>
										</c:if> 
										<br/>
										<c:if test="${fn:length(submissionDate1)>0}">
											<strong><fmt:message key="${copLang.localeId}.callForProposal.anotherSubmissionDate"/></strong>
											${submissionDate1}
											<c:if test="${fn:length(submissionDate2)>0}">,
											${submissionDate2}</c:if>
											<c:if test="${fn:length(submissionDate3)>0}">,
											${submissionDate3}</c:if>
											<br/>
										</c:if>
										<strong><fmt:message key="${copLang.localeId}.callForProposal.fund"/>:</strong>&nbsp;${command.fund.name}<br/>
     									<a href="#" onclick="window.open('${command.originalCallWebAddress}')"><img src="/image/website/original_call.png" height="13px" alt=""/></a>
     									<a href="#" onclick="window.open('${command.originalCallWebAddress}')"><fmt:message key="${copLang.localeId}.website.originalCallWebAddress"/></a> 
     									|
     									<a href="#" onclick="window.open('${command.fund.webAddress}')"><img src="/image/website/menu_icon_home.png" alt=""/></a>
     									<a href="#" onclick="window.open('${command.fund.webAddress}')"><fmt:message key="${copLang.localeId}.website.fundWebAddress"/></a>
     									<br>
										</p>
								</div>
							</div>
							
							<div class="kol open kol_${copLang.dir} description">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_details.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.description"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>${command.description}</p>
								</div>
							</div>
							
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
							
							<c:if test="${authorized && !command.showDescriptionOnly}">
							
							<c:if test="${!fundingDetailsIsEmpty}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_dolar.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.fundingDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>
										<c:if test="${fn:length(command.strippedFundingPeriod)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.fundingPeriod"/>:</strong>&nbsp;${command.strippedFundingPeriod}<br /></c:if>
										<c:if test="${fn:length(command.strippedAmountOfGrant)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.amountOfGrant"/>:</strong>&nbsp;${command.strippedAmountOfGrant}<br /></c:if>
										<c:if test="${fn:length(command.strippedEligibilityRequirements)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.eligibilityRequirements"/>:</strong>&nbsp;${command.strippedEligibilityRequirements}<br /></c:if>
										<c:if test="${fn:length(command.strippedActivityLocation)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.activityLocation"/>:</strong>&nbsp;${command.strippedActivityLocation}<br /></c:if>
										<c:if test="${fn:length(command.strippedPossibleCollaboration)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.possibleCollaboration"/>:</strong>&nbsp;${command.strippedPossibleCollaboration}</c:if>
									</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.formDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_v.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.forms"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>${command.formDetails}</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.submissionDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_envelope.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.submissionDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>${command.submissionDetails}</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.budgetDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_chart.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.budgetDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
								<p>${command.budgetDetails }</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(callForProposalContacts)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.contactPersons"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<c:if test="${fn:length(callForProposalContacts)>0}">
									<div class="contacts_desk">
									<c:if test="${copLang.name=='Hebrew'}"><c:out escapeXml="false" value="${command.desk.hebrewName}"/></c:if>
	        					    <c:if test="${copLang.name=='English'}"><c:out escapeXml="false" value="${command.desk.englishName}"/></c:if>
									</div>
									<table class="table_kol table_kol_${copLang.dir}">
										<tr>
											<th class="table_one"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonName"/></th>
											<th class="table_two"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonPosition"/></th>
											<th class="table_three"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonPhone"/></th>
											<th class="table_four"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonEmail"/></th>
										</tr>
								    	<c:forEach items="${callForProposalContacts}" var="callForProposalContact">
								    	<tr>
											<td class="table_one">${callForProposalContact.name}</td>
											<td class="table_two">${callForProposalContact.position}</td>
											<td class="table_three">${callForProposalContact.phone}</td>
											<c:if test="${fn:length(callForProposalContact.email)>0}">
												<td class="table_email" align="center"><a href="mailto:${callForProposalContact.email}">@</a></td>
											</c:if>
										</tr>
								    	</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.contactPersonDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.contactPersonDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
								<p>${command.contactPersonDetails }</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.fundContact)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_man.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.contactAtFund"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>${command.fundContact}</p>								
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.additionalInformation)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">									
									<h3 class="kol_title_${copLang.dir}"><img src="/image/website/kol_plus.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.additionalInformation"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>									
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>${command.additionalInformation }</p>
								</div>
							</div>
							</c:if>
							
							</c:if>
						</div>
					</div>
				</div>
			</div>
