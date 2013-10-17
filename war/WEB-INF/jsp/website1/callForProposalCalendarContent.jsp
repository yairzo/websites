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
								<a title="Next" onclick="window.location='callForProposalCalendar.html?action=nextMonth&t=1';return false;" class="ui-datepicker-next ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
								<a title="Prev" onClick="window.location='callForProposalCalendar.html?action=prevMonth&t=1';return false;" class="ui-datepicker-prev ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a>
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
        					<c:forEach items="${calendarList}" var="calendarDay" varStatus="varStatus">
        						<c:if test="${varStatus.index%7==0}">
        							</tr><tr style="vertical-align: top">
        						</c:if>
							 	<c:if test="${month!=calendarDay.monthOnly}">
							 		<td class="ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">
							 		<span>${calendarDay.dayOnly}</span>
							 		</td>
							 	</c:if>
								<c:if test="${month==calendarDay.monthOnly}">
							   	<td>
									<a href="#" class="ui-state-default viewAll <c:if test="${fn:length(calendarDay.fundsInDay)>0}">dayWithFund</c:if>">${calendarDay.dayOnly}
									<dfn class="day_details" style="direction:ltr;text-align:left">
									<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
         								<c:if test="${varStatusFund.index<3}">
            								${fundInDay.fundShortName}<br>
         								</c:if>
         								<c:if test="${varStatusFund.index==3}">
         									...
         								</c:if>
        							</c:forEach>
        							</dfn>
        							</a>
        							<div class="callForProposalsPerDay" style="display:none">
										<div class="clearfix">
										<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay">
											<h4>${fundInDay.fundShortName}</h4>
  											<c:forEach items="${fundInDay.callForProposals}" var="callForProposal">
        										<dfn class="viewProposal" id="${callForProposal.id}"><c:out escapeXml="false" value="${callForProposal.title}"/></dfn><br>
        									</c:forEach>
        									<br>
        								</c:forEach>
									</div>
									<div class="triangle"></div>
									</div>
 								</td>
 								</c:if>
        					</c:forEach>
        					</tr>
        					</tbody>
        					</table>
 						</div>
						<div class="callForProposalsPerDay"  style="display:none;">
							<div class="clearfix"></div>
							<div class="triangle"></div>
						</div>
						<!---->
					</div>
					<div class="clearfix mar_30">
						<form action="searchCallForProposals.html" method="post" class="calendar_form">
							<input type="text" name="searchWords" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" value="<fmt:message key="${lang.localeId}.website.typeSubject"/>" class="calendar_text" />
							<input type="submit" value="חיפוש" class="calendar_submit" />
						</form>
						<a href="searchCallForProposals.html?t=1" class="calendar_advanced"><fmt:message key="${lang.localeId}.website.advancedSearch"/></a>
					</div>
				</div>
			</div>
 