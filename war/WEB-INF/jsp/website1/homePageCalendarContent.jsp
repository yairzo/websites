<%@ page  pageEncoding="UTF-8" %>

						<div class="ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" style="display: block;">
							<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all">
								<a title="Next" class="ui-datepicker-next ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
								<a title="Prev" class="ui-datepicker-prev ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a>
								<div class="ui-datepicker-title">
									<span class="ui-datepicker-month">
									<fmt:message key="${lang.localeId}.general.month.${month}"/>
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
							 		<span> </span>
							 		</td>
							 	</c:if>
								<c:if test="${month==calendarDay.monthOnly}">
							   	<td class="<c:if test="${fn:length(calendarDay.fundsInDay)>0}">dayWithFund</c:if>  <c:if test="${calendarDay.dayOnly==today}">ui-datepicker-today</c:if>">
							   		<a href="#" class="ui-state-default viewAll" >${calendarDay.dayOnly}
		   							</a>
        							<c:set var="atTop" value="bottom"/>
        							<c:set var="atEdge" value="middle"/>
        							
        							<c:if test="${varStatus.index < 7}">
        								<c:set var="atTop" value="top"/>
        							</c:if>
        							<c:if test="${(varStatus.index+1)%7==6 || (varStatus.index+1)%7==0}">
        								<c:set var="atEdge" value="edge"/>
        							</c:if>
        							
        							<div class="callForProposalsPerDay callForProposalsPerDayHomePage ${atTop} ${atEdge}" style="display:none">
	        							<c:if test="${varStatus.index<7}">
	        								<div class="triangle"></div>
	        							</c:if>		
										
										<div class="clearfix">

										<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
											<h4>${fundInDay.fundShortName}</h4>
  											<c:forEach items="${fundInDay.callForProposals}" var="callForProposal">
        										<dfn class="viewProposal" id="${callForProposal.id}"><c:out escapeXml="false" value="${callForProposal.title}"/></dfn><br>
        									</c:forEach>
        									<br>
        								</c:forEach>
										</div>
										
										<c:if test="${varStatus.index>7}">
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

