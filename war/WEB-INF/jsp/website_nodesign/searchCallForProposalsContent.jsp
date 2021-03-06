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
            <input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            <input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords" id="searchWords" value="${searchWords }"/> 
							<hidden id="fundId" value="${fundId}"/>
							</td>
						</tr>
 						<tr class="form">
						<td>
   			   			תאריך הגשה בין - <input type="text" name="submissionDateFrom" id="submissionDateFrom" class="green date cancelExpiredCheckbox medium100" value="${submissionDateFrom}"/>
  			   			ל - <input type="text" name="submissionDateTo" id="submissionDateTo" class="green date cancelExpiredCheckbox medium100" value="${submissionDateTo}"/> 
       					</td>
       					</tr>
                  		<tr class="form">
						<td align="right">
  			   				<input type="checkbox" name="searchByAllYear" id="searchByAllYear" class="green" <c:if test="${searchByAllYear}">checked="checked"</c:if>/>   להגשה כל השנה
 			   				<input type="checkbox" name="searchOpen" id="searchOpen" class="green" <c:if test="${searchOpen}">checked="checked"</c:if>/>  פתוחים להגשה
  			   				<input type="checkbox" name="searchExpired" id="searchExpired" class="green" <c:if test="${searchExpired}">checked="checked"</c:if>/> פגי תוקף</span>
       					</td>
       					</tr>
						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.desk"/>
         				<select name="deskId" id="deskId" class="green" >
      						<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<option htmlEscape="true" value="${mopDesk.id}" <c:if test="${mopDesk.id==deskId}">selected</c:if> >
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
         				<select name="typeId" id="typeId" class="green" >
      						<option value="0" <c:if test="${typeId==0}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
      						<option value="1" <c:if test="${typeId==1}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></option>
      						<option value="2" <c:if test="${typeId==2}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></option>
      						<option value="3" <c:if test="${typeId==3}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.conference"/></option>
      						<option value="4" <c:if test="${typeId==4}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></option>
      						<option value="5" <c:if test="${typeId==5}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.prizes"/></option>
        		        </select>
						</td>
						</tr>
   						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.targetAudience"/>
         				<select name="targetAudience" id="targetAudience" class="green" >
      						<option value="0" <c:if test="${targetAudience==0}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.all"/></option>
      						<option value="1" <c:if test="${targetAudience==1}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.researcher"/></option>
      						<option value="2" <c:if test="${targetAudience==2}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.doctoral"/></option>
      						<option value="3" <c:if test="${targetAudience==3}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.postdoctoral"/></option>
        		        </select>
						</td>
						</tr>
                  		<!-- <tr>
						<td align="right">
  			   			<input type="checkbox" name="searchByTemporary" id="searchByTemporary" class="green" <c:if test="${temporaryFund}">checked="checked"</c:if>/> קולות קוראים עם מממן זמני
       					</td>
       					</tr> -->
						<tr class="form subjectsRow" style="display:none">
						<td align="center">
						<c:set var="callForProposalSearch" value="true"/>
						<%@ include file="/WEB-INF/jsp/website/subjects.jsp" %>					
						</td>
						</tr>
                    	<tr>
						<td align="right">
  			   				<input type="checkbox" name="searchByAllSubjects" id="searchByAllSubjects" class="green" <c:if test="${searchByAllSubjects}">checked="checked"</c:if>/>  הצג רק קולות קולאים עם כל הנושאים
        				</td>
       					</tr>
                   		<tr>
						<td align="left">
 							<button class="grey search">חפש</button>&nbsp;
 							<button  class="grey cleanSearch" title="נקה חיפוש" >נקה חיפוש</button>
       					</td>
       					</tr>
       					</table>
       				</td>
   					<td class="container" style="width: 68%; vertical-align: top;text-align: center;">
     					<table style="width: 100%;">
               	 		<tr>
                 		 <td colspan="2" align="center"><h1>רשימת דפי קולות קוראים
                 		 ${linkToPersonPost }
                 		 </h1>	</td>
                		</tr>
     					<c:choose>
    					<c:when test="${fn:length(callForProposals) > 0}">
						<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
             			<tbody>
   							<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
							<td align="right">
							<a href="" class="viewProposal" id="${callForProposal.id}" <c:if test="${callForProposal.expired}">style="color:red"</c:if>><c:out value="${callForProposal.id}"></c:out></a>
 							</td>
  							<td align="right">
							<a href="" class="viewProposal" id="${callForProposal.id}" <c:if test="${callForProposal.expired}">style="color:red"</c:if>><c:out value="${callForProposal.title}"></c:out></a>
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
