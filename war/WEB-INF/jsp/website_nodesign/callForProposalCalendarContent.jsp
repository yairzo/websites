<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<table border="0" width="80%" dir="${lang.dir}">
		<tbody>
			<tr>
      			<td>
	      			<h1>${pageTitle}</h1>
    			</td>
             </tr>
			<tr>
		    <td colspan="2">
            <form:form id="form" name="form" method="POST" commandName="command" action="callForProposalCalendar.html">
 			
			<div id="genericDialog" title="קול קורא" style="display:none" dir="rtl"><p></p></div>
               <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%">
                		<tr>
                			<td colspan="3" align="center">
							<button class="grey" title="שנה הבאה" onclick="window.location='callForProposalCalendar.html?action=nextYear';return false;"><font size="+1">&nbsp;&#x2192;&nbsp;</font></button>
							${year}
							<button class="grey" title="שנה קודמת" onclick="window.location='callForProposalCalendar.html?action=prevYear';return false;"><font size="+1">&nbsp;&#x2190;&nbsp;</font></button>&nbsp;&nbsp;
                 			</td>
                			<td colspan="4" align="center">
							<button class="grey" title="חודש הבא" onclick="window.location='callForProposalCalendar.html?action=nextMonth';return false;"><font size="+1">&nbsp;&#x2192;&nbsp;</font></button>
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
							<button class="grey" title="חודש קודם" onclick="window.location='callForProposalCalendar.html?action=prevMonth';return false;"><font size="+1">&nbsp;&#x2190;&nbsp;</font></button>&nbsp;&nbsp;
                 			</td>
                 		</tr>
                 		<tr style="vertical-align: top">
        					<c:forEach items="${calendarList}" var="calendarDay" varStatus="varStatus">
        						<c:if test="${varStatus.index%7==0}"></tr><tr style="vertical-align: top"></c:if>
								<td style="text-align:left;direction:ltr;border:thin black dotted;width:120px">
									<b><c:out escapeXml="false" value="${calendarDay.day}"/></b>
									<c:forEach items="${calendarDay.fundsInDay}" var="fundInDay" varStatus="varStatusFund">
        								<br>
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
 								</td>
        					</c:forEach>
 						</tr>
        				</table>
       				</td>
               </tr>
	          </table>
			  </form:form>
              </td>
              </tr>
     </tbody>
</table>
