<%@ page  pageEncoding="UTF-8" %>

          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="רשימת ההצעות לכנסים"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>

          </td>

        </tr>

      </table>
    </td>
  </tr>
  <tr>
    <td>
    	<form:form id="form" name="form" method="POST" commandName="command" action="conferenceProposals.html">
        <input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
        <input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
        <input type="hidden" name="searchCreteria.roleFilter" value="${command.searchCreteria.roleFilter}"/>
    	<table border="0" align="center" style="width: 80%; direction: rtl;" cellspacing="10">
    		<tr>
    			<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
    			<td class="container" style="width: 35%;">
    				<span style="text-align: center;"><h2> סינון הבקשות </h2></span>
    				<br/>
    				    לפי חוקר:
               		 <br/>    
               		     <form:input cssStyle="width: 400px;" cssClass="green" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                    
  				    <authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				  	<br/>
				  	<br/>
				  	<select style="width: 400px;" name="searchByApprover" id="searchByApprover" class="green"> 
      					<option value="0"/>בחר דיקן</option>
      				<c:forEach items="${deans}" var="deanPerson">
       					<c:set var="selected" value="false"/>
       					<c:if test="${deanPerson.id==searchByApprover}">
	        				<c:set var="selected" value="true"/>
	        			</c:if>
	        			<option value="${deanPerson.id}" 
	        				<c:if test="${selected}">selected="selected"</c:if>
	        			>
	        				<c:out escapeXml="false" value="${deanPerson.id} - ${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/>
	        			</option>
	        		</c:forEach>
       				</select>
				    </authz:authorize>
				    
				   <authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				   <br/>
				   <br/>	
 				   		לפי סטאטוס: 				   
 				   <br/>
 				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				  
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="1" <c:if test="${searchBySubmitted==1}">checked="checked"</c:if>/> הצעות מוגשות
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="0" <c:if test="${searchBySubmitted==0}">checked="checked"</c:if>/> טיוטות
  				   </authz:authorize>
  				   <authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
  				   	 <input type="hidden" name="searchBySubmitted" value="1"/>
  				   </authz:authorize>
  				   <authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
  				   <br/>  				   
      			   <br/>
 				   		 לפי ועדה:  				   
				  	<br/>
 				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				  
  				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="1" <c:if test="${searchByDeadline==1}">checked="checked"</c:if>/> כינוס הועדה הקרוב
  				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="0" <c:if test="${searchByDeadline==0}">checked="checked"</c:if>/> כל ההגשות
				  	<br/>
				   </authz:authorize>
				</td>
				</authz:authorize>
    			<td class="container" style="width: 65%; vertical-align: top;">
    				<span style="text-align: center;"><h2> רשימת הבקשות </h2></span>
    				<table style="width: 100%;">
    				<c:choose>
    				<c:when test="${fn:length(conferenceProposals) > 0}">
    				<c:forEach items="${conferenceProposals}" var="conferenceProposal" varStatus="varStatus">
             			<c:choose><c:when test="${varStatus.index%2==0}"><c:set var="cssClass" value="darker"/></c:when><c:otherwise><c:set var="cssClass" value="brighter"/></c:otherwise></c:choose>
             			<tr class="${cssClass}" style="height: 30px;">
  							<td align="right" style="padding: 0px 20px;">
				  				<table style="width: 100%">
  									<tr>
				  						<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
				  						<td width="25%">
  											<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
  											<a href="person.html?id=${conferenceProposal.researcher.id}">
  												<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  											</a>
  											</authz:authorize>
  											<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN">
  												<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  											</authz:authorize>  											
  										</td>
  										</authz:authorize>
										<td width="35%">
  											<a href="editConferenceProposal.html?id=${conferenceProposal.id}"><c:choose><c:when test="${fn:length(conferenceProposal.subject)>0}"><c:out value="${conferenceProposal.subject}"></c:out></c:when><c:otherwise>ללא נושא</c:otherwise></c:choose></a>
  										</td>
  										<td width="25%">
 											<c:choose>
 											<c:when test="${conferenceProposal.approverId > 0}">
  											<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
  													<a href="person.html?id=${conferenceProposal.approver.id}">
  														<c:out value="${conferenceProposal.approver.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.approver.lastNameHebrew}"/>
  													</a>
  											</authz:authorize>
  											<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN">
  												<c:out value="${conferenceProposal.approver.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.approver.lastNameHebrew}"/>
  											</authz:authorize>
  											</c:when>
  											<c:otherwise>
  												טרם נבחר דיקן
  											</c:otherwise>
  											</c:choose>
  										</td>
  										<td>
  											<c:choose>
  											<c:when test="${conferenceProposal.submitted}">
  												הוגשה
  											</c:when>
  											<c:otherwise>
  												טיוטה
  											</c:otherwise>
  											</c:choose>
  									</tr>
  								</table>
  							</td>
  	  					</tr>
  	  			  </c:forEach>
  	  			  </c:when>
  	  			  <c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td align="right" style="padding: 0px 20px;">
  							אין בקשות לצפייה
  						</td>
  					</tr>
  				</c:otherwise>
  				</c:choose>  	  			  
  	  			  </table>
  	  			  	 		  
    		</td>
    		</tr>    	
    	</table>
    	<table align="center" style="width: 80%; direction: rtl;">
  	  		<tr>
  	  			<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
  	  			<td style="width: 35%;">
  	  				&nbsp;
  	  			</td>
  	  			</authz:authorize>
                <td style="width: 65%;" align="center">
					<%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %>
                </td>
            </tr>
  	  	</table>
  	  	<br/>
  	  	<br/>
  	  	
  	  	<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
    	
    	<table align="center" class="container" style="width: 80%; direction: rtl;" cellspacing="10">
    		<tr>
    			<td>
    				<span style="text-align: center;"><h2>ניהול הדירוג</h2></span>
    			</td>
    		</tr>
    		<tr>
				<td>
  					<select name="approver" id="approver" class="green">
      					<option value="0">בחר דיקן לשליחת בקשה לדירוג</option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></option>
       					</c:forEach>
       				</select>
					&nbsp;&nbsp;
					<button id="buttonStartGrading" class="grey" />שלח לדירוג</button>
			    </td>
		  </tr>
		  <tr>
		  	<td>
		  		&nbsp;&nbsp;&nbsp;
		  	</td>
		  </tr>
		  <tr>
		  	<td>
    				<c:choose>
    				<c:when test="${fn:length(conferenceProposalGradings)>0}">
    				<table style="width: 70%;" align="center">
             			<thead>
  						<tr>
		  					<th style="width: 30%;">נשלח לדיקן</th>
			  				<th style="width: 30%;">נשלח בתאריך</th>
			  				<th style="width: 30%;">הדיקן סיים לדרג</th>
  	  					</tr>
  	  					</thead>
             			<c:forEach items="${conferenceProposalGradings}" var="conferenceProposalGrading" varStatus="varStatus">
							<c:choose>
								<c:when test="${varStatus.index % 2 == 1}">
									<c:set var="rowBgBrightness" value="brighter"/>
								</c:when>
								<c:otherwise>
									<c:set var="rowBgBrightness" value="darker"/>
								</c:otherwise>
							</c:choose>
             			<tbody>
  						<tr>
		  					<td class="${rowBgBrightness}"><a href="conferenceProposalsGrade.html?approverId=${conferenceProposalGrading.approver.id}">${conferenceProposalGrading.approver.degreeFullNameHebrew}</a></td>
			  				<td class="${rowBgBrightness}">${conferenceProposalGrading.formattedSentForGradingDate}</td>
			  				<td class="${rowBgBrightness}">${conferenceProposalGrading.formattedFinishedGradingDate}</td>
  	  					</tr>
  	  		 			</tbody>
  	  		 		   </c:forEach>
					</table>
					</c:when>
					<c:otherwise>
						טרם נשלחו בקשות לדירוג
					</c:otherwise>
					</c:choose>
    			</td>
    		</tr>
    	</table>
    	</authz:authorize>
      
      	</form:form>
    </td>
  </tr>
</table>
<div id="dialog-confirm" title="" style="display: none;">
	<p><span class="dialogText">את/ה עומד/ת לשלוח בקשה לדירוג במייל לדיקן. האם את/ה מאשר/ת ?</span></p>
</div>
</body>
</html>
