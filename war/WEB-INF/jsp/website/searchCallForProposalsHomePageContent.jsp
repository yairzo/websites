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
            <form:form id="form" name="form" method="POST" commandName="command" action="searchCallForProposals.html">
 			
			<div id="genericDialog" title="קול קורא" style="display:none" dir="rtl"><p></p></div>
              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords"/> 
							</td>
							<td align="left">
 							<button class="grey">חפש</button>
       						</td>
       					</tr>
       					</table>
       				</td>
               </tr>
	          </table>
              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%">
                		<tr>
                			<td colspan="3" align="center">
							<button class="grey" title="שנה הבאה" onclick="window.location='searchCallForProposalsHomePage.html?action=nextYear';return false;"><font size="+1">&nbsp;&#x2192;&nbsp;</font></button>
							${year}
							<button class="grey" title="שנה קודמת" onclick="window.location='searchCallForProposalsHomePage.html?action=prevYear';return false;"><font size="+1">&nbsp;&#x2190;&nbsp;</font></button>&nbsp;&nbsp;
                 			</td>
                			<td colspan="4" align="center">
							<button class="grey" title="חודש הבא" onclick="window.location='searchCallForProposalsHomePage.html?action=nextMonth';return false;"><font size="+1">&nbsp;&#x2192;&nbsp;</font></button>
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
							<button class="grey" title="חודש קודם" onclick="window.location='searchCallForProposalsHomePage.html?action=prevMonth';return false;"><font size="+1">&nbsp;&#x2190;&nbsp;</font></button>&nbsp;&nbsp;
                 			</td>
                 		</tr>
                 		<tr style="vertical-align: top">
        					<c:forEach items="${calendarList}" var="calendarDay" varStatus="varStatus">
        						<c:if test="${varStatus.index%7==0}"></tr><tr style="vertical-align: top"></c:if>
								<td style="border:thin black dotted;width:120px">
								<b><c:out escapeXml="false" value="${calendarDay.day}"/></b>
								<c:if test="${calendarDay.callForProposals[0].localeId=='en_US'}"><c:set var="dir" value="left"/></c:if>
								<c:if test="${calendarDay.callForProposals[0].localeId=='iw_IL'}"><c:set var="dir" value="right"/></c:if>
       							<div align="${dir}"><a href="" class="viewProposal" id="${calendarDay.callForProposals[0].id}"><c:out escapeXml="false" value="${calendarDay.callForProposals[0].title}"/></a></div>
       							<c:if test="${fn:length(calendarDay.callForProposals)>1}">עוד...</c:if>
       							<!--<div id="moreCalls">
       							<c:forEach items="${calendarDay.callForProposals}" var="callForProposal">
       							<a href="" class="viewProposal" id="${calendarDay.callForProposals[0].id}"><c:out escapeXml="false" value="${calendarDay.callForProposals[0].title}"/></a>
        						</c:forEach>
        						</div>-->
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
