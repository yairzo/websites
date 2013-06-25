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
 			
 			<div id="genericDialog" style="display:none" dir="rtl"><p></p></div>
 
             <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
							<div class="bxslider">
  								<c:forEach items="${textualPages}" var="textualPage">
  								<c:set var="direction" value="rtl"/>
  								<c:if test="${textualPage.localeId=='en_US'}">
  									<c:set var="direction" value="ltr"/>
  								</c:if>
  								<div style="direction:${direction};">${textualPage.title}</div>
								</c:forEach>
							</div>
       						</td>
       					</tr>
       					</table>
       				</td>
               </tr>
	          </table>
              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords"/> 
							</td>
							<td align="left">
 							<button class="grey search">חפש</button>
       						</td>
       					</tr>
       					</table>
       				</td>
               </tr>
	          </table>
              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<span class="date"></span>
							</td>
							<td>
       						<span class="callForProposalsPerDay"></span>
       						</td>
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
