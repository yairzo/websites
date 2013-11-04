<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="popup_placeholder" style="display:none"></div>
			<div class="container clearfix">
				<div class="breadcrumbs clearfix" dir="${lang.dir}" align="${lang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">${pageTitle}</h1>
					
					<div class="clearfix mar_20">
						<div class="advanced">
							<form id="form" action="searchCallForProposals.html" method="post">
								<div class="clearfix">
									<div class="advanced_subject">
										<label for="advanced_subject"><fmt:message key="${lang.localeId}.callForProposal.searchWords"/></label>
										<input type="text" name="searchWords" value="${searchWords}" id="advanced_subject" placeholder="<fmt:message key="${lang.localeId}.website.placeholder.search_words"/>"/>
										
										<input type="hidden" id="fundId" value="${fundId}"/>
									</div>
									<div class="advanced_date">
										<label for="advanced_date_from"><fmt:message key="${lang.localeId}.website.submissionDatesRange"/></label>
										<div class="clearfix">
											<input type="text" name="submissionDateFrom" id="advanced_date_from" class="date" value="${submissionDateFrom}" placeholder="<fmt:message key="${lang.localeId}.website.placeholder.from_date"/>"/>
											<a href="#" class="advanced_cal" id="advanced_cal_from"></a>
											<span class="advanced_date_sep">-</span>
											<input type="text" name="submissionDateTo" id="advanced_date_to" class="date" value="${submissionDateTo}" placeholder="<fmt:message key="${lang.localeId}.website.placeholder.to_date"/>"/>
											<a href="#" class="advanced_cal" id="advanced_cal_to"></a>
										</div>
									</div>
								</div>
								<div class="clearfix checks mar_15">
									<div class="check">
										<div class="checkbox_box search_by_all_year" check-value="${searchByAllYear}">
											
										</div>
										<label><fmt:message key="${lang.localeId}.website.allYearSubmission"/></label>
									</div>
									<div class="check">
										<div class="checkbox_box search_open" check-value="${searchOpen}">
											
										</div>
										<label><fmt:message key="${lang.localeId}.website.openForSubmission"/></label>
									</div>
									<div class="check">
										<div class="checkbox_box search_expired" check-value="${searchExpired}">
											
										</div>
										<label><fmt:message key="${lang.localeId}.website.expired"/></label>
									</div>
								</div>
								<div class="clearfix mar_5">
									<div class="advanced_select">
										<label><fmt:message key="${lang.localeId}.callForProposal.desk"/></label>
										<div class="advanced_select_select">
											<select style="display: none" name="deskId" id="deskId" class="styled" >
      											<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
       											<c:forEach items="${mopDesks}" var="mopDesk">
	        										<option htmlEscape="true" value="${mopDesk.id}" <c:if test="${mopDesk.id==deskId}">selected</c:if> >
	        										<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></c:if>
	        										<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${mopDesk.englishName}"/></c:if>
	        										</option>
       											</c:forEach>
        		        					</select>
										</div>
										<a href="#" class="advanced_select_arrow"></a>
									</div>
									<div class="advanced_select">
										<label><fmt:message key="${lang.localeId}.callForProposal.type"/></label>
										<div class="advanced_select_select">
											<select style="display: none" name="typeId" id="typeId" class="styled" >
      											<option value="0" <c:if test="${typeId==0}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
      											<option value="1" <c:if test="${typeId==1}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></option>
      											<option value="2" <c:if test="${typeId==2}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></option>
      											<option value="3" <c:if test="${typeId==3}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.conference"/></option>
      											<option value="4" <c:if test="${typeId==4}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></option>
      											<option value="5" <c:if test="${typeId==5}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.prizes"/></option>
        		        					</select>
										</div>
										<a href="#" class="advanced_select_arrow"></a>
									</div>
									<div class="advanced_select nomar">
										<label><fmt:message key="${lang.localeId}.callForProposal.targetAudience"/></label>
										<div class="advanced_select_select">
        									<select style="display: none" name="targetAudience" id="targetAudience" class="styled" >
      											<option value="0" <c:if test="${targetAudience==0}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.all"/></option>
      											<option value="1" <c:if test="${targetAudience==1}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.researcher"/></option>
      											<option value="2" <c:if test="${targetAudience==2}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.doctoral"/></option>
      											<option value="3" <c:if test="${targetAudience==3}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.postdoctoral"/></option>
        		        					</select>
										</div>
										<a href="#" class="advanced_select_arrow"></a>
									</div>
								</div>
								<div class="clearfix checks mar_15">
									<h3 class="search_choose"><fmt:message key="${lang.localeId}.website.subjectSelection"/></h3>
									<div class="checks_left">
										<div class="check check_all">
											<div class="select_all checkbox_box" check-value="false">
												
											</div>
											<label><fmt:message key="${lang.localeId}.website.selectAll"/></label>
										</div>
										<div class="check check_only">
											<div class="only_all_subjects checkbox_box" check-value="${searchByAllSubjects}">
												
											</div>
											<label><fmt:message key="${lang.localeId}.website.showCallsWithAllSubjects"/></label>
										</div>
									</div>
								</div>
								<div class="clearfix scrollbox">
									<div class="scroll_content">
										<div class="clearfix mar_10">
										<c:forEach items="${rootSubject.subSubjectsBeans}" var="subject" varStatus="varStatus">		
											<c:if test="${varStatus.index%3==0}">	
												</div>
												<div class="clearfix mar_10">	
											</c:if>	
															
											<div class="check scroll_col <c:if test='${varStatus.index%3==0}'>scroll_col_last</c:if>">
												<div class="checkbox_box selectSubject"></div>
												<label>${subject.name}</label>
												<div class="checkbox_list" id="${subject.id}Sub">
                   									<div class="checkbox_list_top"></div>
													<div class="checkbox_list_bottom">
														<ul>
	                   									<c:forEach items="${subject.subSubjectsBeans}" var="subSubject">
	                   										<li>
																<div class="${subSubject.id} checkbox_box checkbox_box_sub checkbox_box_sub_${lang.dir} selectSubSubject" check-value="${subSubject.checked}"></div>
	                    										<label><c:out value="${subSubject.name}"/></label>
	                    									</li>
	                  									</c:forEach>
	                 									</ul>
                 									</div>
                 								</div>
											</div>
										</c:forEach>
										</div>
									</div>
								</div>
								<div class="clearfix mar_15">
									<input type="submit" value="<fmt:message key="${lang.localeId}.website.search"/>" class="advanced_submit" />
									<a href="" class="advanced_clear"><fmt:message key="${lang.localeId}.website.cleanSearch"/></a>
								</div>
							</form>
							<div class="clearfix">
								<a href="/search.html" class="advanced_close"><fmt:message key="${lang.localeId}.website.generalSearch"/></a>
							</div>
						</div>
						
						<div class="clearfix mar_20">
							<div class="kol search_result">
								<div class="clearfix">
									<h3 class="kol_title_${lang.dir}"><img src="/image/website/search_megaphone_${lang.dir}.png" alt="" /> &nbsp;<fmt:message key="${lang.localeId}.callForProposal.callForProposalsList"/>
									<c:if test="${isDefault}"><fmt:message key="${lang.localeId}.callForProposal.callForProposalsListNew"/></c:if></h3>
								</div>
   								<c:choose>
    							<c:when test="${fn:length(callForProposals) > 0}">
								<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
									<a href="" class="search_content viewProposal" id="${callForProposal.id}">
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
					</div>
				</div>
			</div>