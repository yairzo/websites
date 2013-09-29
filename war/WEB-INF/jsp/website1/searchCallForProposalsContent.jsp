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
							<form id="form" action="searchCallForProposals.html?t=1" method="post">
								<div class="clearfix">
									<div class="advanced_subject">
										<label for="advanced_subject"><fmt:message key="${lang.localeId}.callForProposal.searchWords"/></label>
										<input type="text" name="searchWords" value="${searchWords}" id="advanced_subject" />
										<input type="hidden" id="fundId" value="${fundId}"/>
									</div>
									<div class="advanced_date">
										<label for="advanced_date_from">טווח תאריכי הגשה</label>
										<div class="clearfix">
											<input type="text" name="submissionDateFrom" id="advanced_date_from" class="date" value="<c:choose><c:when test="${fn:length(submissionDateFrom)>0}">${submissionDateFrom}</c:when><c:otherwise>מתאריך</c:otherwise></c:choose>"/>
											<a href="#" class="advanced_cal" id="advanced_cal_from"></a>
											<span class="advanced_date_sep">-</span>
											<input type="text" name="submissionDateTo" id="advanced_date_to" class="date" value="<c:choose><c:when test="${fn:length(submissionDateTo)>0}">${submissionDateTo}</c:when><c:otherwise>עד תאריך</c:otherwise></c:choose>"/>
											<a href="#" class="advanced_cal" id="advanced_cal_to"></a>
										</div>
									</div>
								</div>
								<div class="clearfix checks mar_15">
									<div class="check">
										<div class="checkbox_box">
										<input type="checkbox" name="searchByAllYear" id="searchByAllYear" <c:choose><c:when test="${searchByAllYear}">checked="checked"</c:when><c:otherwise>checked="false"</c:otherwise></c:choose> class="styled" />
										</div>
										<label>להגשה כל השנה</label>
									</div>
									<div class="check">
										<div class="checkbox_box"><input type="checkbox" name="searchOpen" id="searchOpen" <c:choose><c:when test="${searchOpen}">checked="checked"</c:when><c:otherwise>checked="false"</c:otherwise></c:choose> class="styled" /></div>
										<label>פתוחים להגשה</label>
									</div>
									<div class="check">
										<div class="checkbox_box"><input type="checkbox" name="searchExpired" id="searchExpired"  <c:choose><c:when test="${searchExpired}">checked="checked"</c:when><c:otherwise>checked="false"</c:otherwise></c:choose> class="styled" /></div>
										<label>פגי תוקף</label>
									</div>
								</div>
								<div class="clearfix mar_5">
									<div class="advanced_select">
										<label><fmt:message key="${lang.localeId}.callForProposal.desk"/></label>
										<div class="advanced_select_select">
											<select name="deskId" id="deskId" class="styled" >
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
											<select name="typeId" id="typeId" class="styled" >
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
        									<select name="targetAudience" id="targetAudience" class="styled" >
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
									<h3 class="search_choose">בחירת נושאים</h3>
									<div class="checks_left">
										<div class="check check_all">
											<div class="checkbox_box"><input type="checkbox" id="selectAll" class="styled" <c:choose><c:when test="${selectAll}">checked="checked"</c:when><c:otherwise>checked="false"</c:otherwise></c:choose>/></div>
											<label>סמן הכל</label>
										</div>
										<div class="check check_only">
											<div class="checkbox_box "><input type="checkbox" name="searchByAllSubjects" id="searchByAllSubjects" <c:choose><c:when test="${searchByAllSubjects}">checked="checked"</c:when><c:otherwise>checked="false"</c:otherwise></c:choose> class="styled" /></div>
											<label>הצג רק קולות קוראים עם כל הנושאים</label>
										</div>
									</div>
								</div>
								<div class="clearfix scrollbox">
									<div class="scroll_bar">
										<div class="scroll_arrow"></div>
									</div>
									<div class="scroll_content">
										<div class="clearfix mar_10">
										<c:forEach items="${rootSubject.subSubjectsBeans}" var="subject" varStatus="varStatus">		
											<c:if test="${varStatus.index%3==0}">	
											</div><div class="clearfix mar_10">	
											</c:if>	
															
											<div class="check scroll_col <c:if test='${varStatus.index%3==0}'>scroll_col_last</c:if>">
												<div class="checkbox_box selectSubject">
													<input type="checkbox" id="${subject.id}" class="subject styled" checked="false"/>
												</div>
												<label class="openSubSubjects">${subject.name}</label>
												<div id="${subject.id}Sub" class="subSubjects" style="text-align:${lang.align};direction:${lang.dir}" > 
                   								<c:forEach items="${subject.subSubjectsBeans}" var="subSubject">
													<div class="checkbox_box selectSubSubject">
                     								<input type="checkbox" id="${subject.id}.${subSubject.id}" checked="false" class="subSubject styled" value="${subSubject.id}"/>
                     								</div>
                    								<label><c:out value="${subSubject.name}"/></label>
													<br>	
                 								</c:forEach>
                 								</div>
											</div>
										</c:forEach>
										</div>
									</div>
								</div>
								<div class="clearfix mar_15">
									<input type="submit" value="חיפוש" class="advanced_submit" />
									<a href="#" class="advanced_clear">נקה חיפוש</a>
								</div>
							</form>
							<div class="clearfix">
								<a href="search.html?t=1" class="advanced_close"> חיפוש כללי</a>
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
							
						</div>
					</div>
				</div>
			</div>