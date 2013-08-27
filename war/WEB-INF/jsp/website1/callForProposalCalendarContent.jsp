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
								<a title="Prev" onClick="window.location='callForProposalCalendar.html?action=prevMonth&t=1';return false;" class="ui-datepicker-prev ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a>
								<a title="Next" onclick="window.location='callForProposalCalendar.html?action=nextMonth&t=1';return false;" class="ui-datepicker-next ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
								<h3 class="transition">מעבר בין חודשים</h3>
								<div class="ui-datepicker-title">
									<h1>לוח קולות קוראים </h1>
									<span class="ui-datepicker-month">
									<c:if test="${month==1 }">ינואר</c:if>
									<c:if test="${month==2 }">פברואר</c:if>
									<c:if test="${month==3 }">מרץ</c:if>
									<c:if test="${month==4 }">אפריל</c:if>
									<c:if test="${month==5 }">מאי</c:if>
									<c:if test="${month==6 }">יוני</c:if>
									<c:if test="${month==7 }">יולי</c:if>
									<c:if test="${month==8 }">אוגוסט</c:if>
									<c:if test="${month==9 }">ספטמבר</c:if>
									<c:if test="${month==10 }">אוקטובר</c:if>
									<c:if test="${month==11 }">נובמבר</c:if>
									<c:if test="${month==12 }">דצמבר</c:if>
									</span> / <span class="ui-datepicker-year">${year }</span>
								</div>
							</div>
							<table class="ui-datepicker-calendar"><thead><tr><th class="ui-datepicker-week-end"><span title="Sunday">א</span></th><th><span title="Monday">ב</span></th><th><span title="Tuesday">ג</span></th><th><span title="Wednesday">ד</span></th><th><span title="Thursday">ה</span></th><th><span title="Friday">ו</span></th><th class="ui-datepicker-week-end"><span title="Saturday">שבת</span></th></tr></thead>
							<tbody>
                			<tr style="vertical-align: top">
        					<c:forEach items="${calendarList}" var="calendarDay" varStatus="varStatus">
        						<c:if test="${varStatus.index%7==0}"></tr><tr style="vertical-align: top"></c:if>
								<td>
								<span>
									<c:out escapeXml="false" value="${calendarDay.day}"/>
									<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
         								<c:if test="${varStatusFund.index<3}">
          								<c:if test="${fn:length(fundInDay.callForProposals)==1}">
       										<a href="" class="viewProposal" id="${fundInDay.callForProposals[0].id}"><c:out escapeXml="false" value="${fundInDay.fundShortName}"/></a>
         								</c:if>
        								<c:if test="${fn:length(fundInDay.callForProposals)>1}">
      										<a href="" class="viewAll"><c:out escapeXml="false" value="${fundInDay.fundShortName}"/>&nbsp;<c:out escapeXml="false" value="(${fn:length(fundInDay.callForProposals)})"/></a>
         								</c:if>
         								</c:if>
         								<c:if test="${varStatusFund.index==3}"><a href="" class="viewAll">...</a></c:if>
        							</c:forEach>
        							<div class="moreCalls" style="padding:10px;display:none;position:absolute;text-align:right;background-color:white;border: 1px solid black;">
									<u> קולות קוראים להגשה עד ${calendarDay.day}</u></br>	
									<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay">
  										<c:forEach items="${fundInDay.callForProposals}" var="callForProposal">
        									<a href="" class="viewProposal" id="${callForProposal.id}"><c:out escapeXml="false" value="${callForProposal.title}"/></a>
  											<br>
        								</c:forEach>
        							</c:forEach>
         							</div>
         							</span>
 								</td>
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
						<form action="#" method="post" class="calendar_form">
							<input type="text" name="" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" value="הקלידו נושא לחיפוש ..." class="calendar_text" />
							<input type="submit" value="חיפוש" class="calendar_submit" />
						</form>
						<a href="#" class="calendar_advanced">הצג חיפוש מתקדם</a>
					</div>
				</div>
			</div>
 