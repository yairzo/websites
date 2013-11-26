<%@ page  pageEncoding="UTF-8" %>

						<div class="ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" style="display: block;">
							<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all">
								<a title="<c:choose><c:when test="${lang.rtl}">הבא</c:when><c:otherwise>Next month</c:otherwise></c:choose>" class="ui-datepicker-next ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
								<a title="<c:choose><c:when test="${lang.rtl}">הקודם</c:when><c:otherwise>Previous month</c:otherwise></c:choose>" class="ui-datepicker-prev ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a>
								<div class="ui-datepicker-title">
									<span class="ui-datepicker-month">
									<fmt:message key="${lang.localeId}.general.month.${month}"/>
									</span> / <span class="ui-datepicker-year">${year}</span>
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
							 	<c:if test="${month>calendarDay.monthOnly}">
							 	<td class="ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">
							 		
							 		</td>
							 	</c:if>
								<c:if test="${month==calendarDay.monthOnly}">
							   	<td class="<c:if test="${fn:length(calendarDay.fundsInDay)>0}">dayWithFund</c:if>  <c:if test="${calendarDay.dayOnly==today && calendarDay.isCurrentMonth && calendarDay.isCurrentYear}">ui-datepicker-today</c:if>">
							   		<a class="ui-state-default <c:if test="${fn:length(calendarDay.fundsInDay)>0}">viewAll</c:if>" >${calendarDay.dayOnly} </a>
        							
        							<div class="callForProposalsPerDay callForProposalsPerDayHomePage" style="display:none">
									
										<div class="clearfix">
										
										<div class="close_datepicker_dialog" onclick="$('.callForProposalsPerDay').hide();return false;">x</div>
										
										<c:set var="showedCounter" value="0"/> 
										<c:set var="totalCounter" value="0"/> 
										<c:if test="${fn:length(calendarDay.fundsInDay)==1}">
											<c:set var="fundMax" value="3"/> 
										</c:if>
										<c:if test="${fn:length(calendarDay.fundsInDay)==2}">
											<c:set var="fundMax" value="2"/> 
										</c:if>
										<c:if test="${fn:length(calendarDay.fundsInDay)>2}">
											<c:set var="fundMax" value="1"/> 
										</c:if>
										<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
         									<c:set var="totalCounter" value="${totalCounter+fn:length(fundInDay.callForProposals)}"/>
											<c:if test="${showedCounter<3}">
											<h4>${fundInDay.fundShortName}</h4>
  											<c:forEach items="${fundInDay.callForProposals}" var="callForProposal" varStatus="varStatusCP">
        										<c:if test="${varStatusCP.index<fundMax}">
        										<div class="viewProposal" id="${callForProposal.urlTitle}" style="text-align:${callForProposal.align};direction:${callForProposal.dir}"><c:out escapeXml="false" value="${callForProposal.trimmedTitle}"/><br><br></div>
          										<c:set var="showedCounter" value="${showedCounter+1}"/>
         										</c:if>
         									</c:forEach>
         									</c:if>
         								</c:forEach>
        								<c:if test="${totalCounter>3}"><div class="allCallForProposals" id="${calendarDay.day}" style="text-align:${lang.align};direction:${lang.dir}"><fmt:message key="${lang.localeId}.callForProposal.more"/></div></c:if>
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

