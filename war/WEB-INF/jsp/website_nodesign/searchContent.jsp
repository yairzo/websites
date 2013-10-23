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
            <form:form id="form" name="form" method="POST" commandName="command" action="search.html">
 			
 			<div id="genericDialog" title="קול קורא" style="display:none" dir="rtl"><p></p></div>
            <input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            <input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container">
    					<table style="width: 100%;">
                		<tr class="form">
							<td colspan="2">
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords" value="${searchWords }"/> 
							<button class="grey search">חפש</button>							
							</td>
						</tr>
                	 	<tr>
                 		 <td colspan="2" align="center"><h1> דפי קולות קוראים</h1>	</td>
                		</tr>
     					<c:choose>
    					<c:when test="${fn:length(callForProposals) > 0}">
						<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
             			<tbody>
   							<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
							<td align="right">
							<a href="" class="viewProposal" id="${callForProposal.id}"><c:out value="${callForProposal.id}"></c:out></a>
  							</td>
  							<td align="right">
							<a href="" class="viewProposal" id="${callForProposal.id}"><c:out value="${callForProposal.title}"></c:out></a>
							</td>
   	  						</tr>
  	  					</tbody>
	   					</c:forEach>
 	  					</c:when>
  	  					<c:otherwise>
  	  			  			<tr class="darker" style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								לא נמצאו קולות קוראים 
   							</td>
  							</tr>
  						</c:otherwise>
	  					</c:choose> 
	               	 	<tr>
                 		 <td colspan="2" align="center"><h1> דפי טקסט</h1>	</td>
                		</tr>
    					<c:choose>
    					<c:when test="${fn:length(textualPages) > 0}">
						<c:forEach items="${textualPages}" var="textualPage" varStatus="varStatus">
             			<tbody>
   							<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
							<td align="right">
							<a href="textualPage.html?id=${textualPage.id}"><c:out value="${textualPage.id}"></c:out></a>
  							</td>
  							<td align="right">
							<a href="textualPage.html?id=${textualPage.id}">${textualPage.title}</a>
							</td>
   	  						</tr>
  	  					</tbody>
	   					</c:forEach>
 	  					</c:when>
  	  					<c:otherwise>
  	  			  			<tr class="darker" style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  							לא נמצאו דפי טקסט 
   							</td>
  						</tr>
  						</c:otherwise>
  						</c:choose> 
                   		</table>
                	</td>
              </tr>
	          </table>
			  </form:form>
              </td>
              </tr>
     </tbody>
</table>
