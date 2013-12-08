<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="container clearfix">
				<div class="breadcrumbs clearfix">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="calendar">
					<div class="board_calendar">
						<!---->
						<div class="ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" style="display: block;">
							<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all">
								<a title="<c:choose><c:when test="${lang.rtl}">החודש הבא</c:when><c:otherwise>Next month</c:otherwise></c:choose>" onclick="window.location='/calendar/next/';return false;" class="ui-datepicker-next ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
								<a title="<c:choose><c:when test="${lang.rtl}">החודש הקודם</c:when><c:otherwise>Previous month</c:otherwise></c:choose>" onClick="window.location='/calendar/prev/';return false;" class="ui-datepicker-prev ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a>
								<h3 class="transition"><fmt:message key="${lang.localeId}.website.changeMonth"/></h3>
								<div class="ui-datepicker-title">
									<h1><fmt:message key="${lang.localeId}.website.callForProposalCalendar"/> </h1>
									<span class="ui-datepicker-month">
									<fmt:message key="${lang.localeId}.general.month.${month }"/>
									</span> / <span class="ui-datepicker-year">${year }</span>
								</div>
							</div>
							<table class="ui-datepicker-calendar"><thead><tr><th class="ui-datepicker-week-end"><span title="Sunday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.1"/></span></th><th><span title="Monday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.2"/></span></th>
							<th><span title="Tuesday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.3"/></span></th><th><span title="Wednesday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.4"/></span></th><th><span title="Thursday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.5"/></span></th>
							<th><span title="Friday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.6"/></span></th><th class="ui-datepicker-week-end"><span title="Saturday"><fmt:message key="${lang.localeId}.general.dayOfWeekShort.7"/></span></th></tr></thead>
							<tbody>
                			<tr style="vertical-align: top">
        					<c:set var="stopCal" value="false"/>
        					
        					<c:forEach items="${calendarList}" var="calendarDay" varStatus="varStatus">
        						<c:if test="${varStatus.index%7==0}">
       								<c:if test="${(calendarDay.monthOnly>month && calendarDay.yearOnly==year) || calendarDay.yearOnly>year}">
        								<c:set var="stopCal" value="true"/>
        							</c:if>
        							<c:if test="${stopCal=='false'}">
        								</tr>
        								<tr style="vertical-align: top">
        							</c:if>
         						</c:if>
 							 	<c:if test="${month!=calendarDay.monthOnly && stopCal=='false'}">
							 		<td class="ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">
							 		<span>${calendarDay.dayOnly}</span>
							 		</td>
							 	</c:if>
							 	<c:if test="${month!=calendarDay.monthOnly && stopCal=='true'}">
							 		
							 	</c:if>
								<c:if test="${month==calendarDay.monthOnly}">
							   	<td>
							   		<a href="#" class="ui-state-default viewAll <c:if test="${fn:length(calendarDay.fundsInDay)>0}">dayWithFund</c:if>">${calendarDay.dayOnly}
									<dfn class="day_details" style="direction:ltr;text-align:left">
									<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
         								<c:if test="${varStatusFund.index<3}">
            								${fundInDay.trimmedName}<br>
         								</c:if>
         								<c:if test="${varStatusFund.index==3}">
         									<div style="text-align:${lang.align};direction:${lang.dir}"><fmt:message key="${lang.localeId}.callForProposal.more"/></div>
         								</c:if>
        							</c:forEach>
        							</dfn>
        							</a>
        							<c:set var="atTop" value="bottom"/>
        							<c:set var="atEdge" value="middle"/>
        							
        							<c:if test="${varStatus.index < 14}">
        								<c:set var="atTop" value="top"/>
        							</c:if>
        							<c:if test="${(varStatus.index+1)%7==6 || (varStatus.index+1)%7==0}">
        								<c:set var="atEdge" value="edge"/>
        							</c:if>
        							
        							<div class="callForProposalsPerDay ${atTop} ${atEdge}" style="display:none">
	        							<c:if test="${varStatus.index<14}">
	        								<div class="triangle"></div>
	        							</c:if>		
										
										<div class="clearfix">
										<div class="close_datepicker_dialog" onclick="$('.callForProposalsPerDay').hide();return false;">x</div>

										<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay">
											<h4>${fundInDay.fundShortName}</h4>
  											<c:forEach items="${fundInDay.callForProposals}" var="callForProposal">
        										<div class="viewProposal" id="${callForProposal.urlTitle}" style="text-align:${callForProposal.align}"><c:out escapeXml="false" value="${callForProposal.title}"/><br><br></div>
        									</c:forEach>
         								</c:forEach>
										</div>
	        							<c:if test="${varStatus.index>14}">
											<div class="triangle"></div>
										</c:if>
									</div>
 								</td>
 								</c:if>
        					</c:forEach>
        					</tr>
        					</tbody>
        					</table>
 						</div>
						<!---->
					</div>
					<div class="clearfix mar_30">
						<form action="/search/" method="post" class="calendar_form">
							<input type="text" name="searchWords" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" placeholder="<fmt:message key="${lang.localeId}.website.typeSubject"/>" class="calendar_text" />
							<input type="submit" value="חיפוש" class="calendar_submit" />
						</form>
						<a href="/searchCallForProposals.html" class="calendar_advanced"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
					</div>
				</div>
			</div>
 