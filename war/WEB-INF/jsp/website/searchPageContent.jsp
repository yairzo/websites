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
            <form:form id="form" name="form" method="POST" commandName="command" action="searchPage.html">
            <input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            <input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords"/> 
							</td>
						</tr>
 						<tr class="form">
						<td>
   			   			תאריך הגשה בין - <input type="text" name="submissionDateFrom" class="green date medium100"/>
  			   			ל - <input type="text" name="submissionDateTo" class="green date medium100"/> 
       					</td>
       					</tr>
						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.desk"/>
         				<select name="deskId"  class="green" >
      						<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<option htmlEscape="true" value="${mopDesk.id}">
	        					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></c:if>
	        					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${mopDesk.englishName}"/></c:if>
	        					</option>
       						</c:forEach>
        		        </select>
						</td>
						</tr>
 						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.type"/>
         				<select name="typeId"  class="green" >
      						<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
      						<option value="1"><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></option>
      						<option value="2"><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></option>
      						<option value="3"><fmt:message key="${lang.localeId}.callForProposal.conference"/></option>
      						<option value="4"><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></option>
        		        </select>
						</td>
						</tr>
                 		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.fund"/>
							<input type="text" class="green" style="width:400px" id="fundName" value="${selectedFund}"/> 
							<hidden id="fundId"/>
							</td>
						</tr>
                 		<tr>
						<td align="right">
  			   			<input type="checkbox" name="searchByTemporary" id="searchByTemporary" class="green" <c:if test="${temporaryFund}">checked="checked"</c:if>/> קולות קוראים עם מממן זמני
       					</td>
       					</tr>
						<tr class="form">
						<td align="center">
						<c:set var="callOfProposalSearch" value="true"/>
						<%@ include file="/WEB-INF/jsp/content/editPost/subjects.jsp" %>					
						</td>
						</tr>
                  		<tr>
						<td align="left">
 						<button class="grey search">חפש</button>
       					</td>
       					</tr>
       					</table>
       				</td>
   					<td class="container" style="width: 68%; vertical-align: top;text-align: center;">
     					<table style="width: 100%;">
               	 		<tr>
                 		 <td colspan="2" align="center"><h1>רשימת דפי קולות קוראים</h1>	</td>
                		</tr>
     					<c:choose>
    					<c:when test="${fn:length(callOfProposals) > 0}">
						<c:forEach items="${callOfProposals}" var="callOfProposal" varStatus="varStatus">
             			<tbody>
   							<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
							<td align="right">
							<a href="callForProposal.html?id=${callOfProposal.id}"><c:out value="${callOfProposal.id}"></c:out></a>
  							</td>
  							<td align="right">
							<a href="callForProposal.html?id=${callOfProposal.id}"><c:out value="${callOfProposal.title}"></c:out></a>
							</td>
   	  						</tr>
  	  					</tbody>
	   					</c:forEach>
 	  					</c:when>
  	  					<c:otherwise>
  	  			  			<tr class="darker" style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין דפים 
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
