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
    			<td class="container" style="width: 35%;">
    				<span style="text-align: center;"><h2> סינון הבקשות </h2></span>
    				<br/>
    				<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
               		     לפי חוקר:
               		 <br/>    
               		     <form:input cssStyle="width: 400px;" cssClass="green" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                    </authz:authorize>
                    <br/>
                    <br/>
  				    <authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
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
				   <br/>
				   <br/>
				   <authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
 				   		לפי סטאטוס: 				   
 				   <br/>
 				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				  
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="0" <c:if test="${searchBySubmitted==0}">checked="checked"</c:if>/> הצעות מוגשות
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="1" <c:if test="${searchBySubmitted==1}">checked="checked"</c:if>/> טיוטות
  				   <br/>  				   
      			   <br/>
 				   		 לפי ועדה:  				   
				  	<br/>
 				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				  
  				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="0" <c:if test="${searchByDeadline==0}">checked="checked"</c:if>/> כינוס הועדה הקרוב
  				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="1" <c:if test="${searchByDeadline==1}">checked="checked"</c:if>/> כל ההגשות
				  	<br/>
				   </authz:authorize>
				</td>
    			<td class="container" style="width: 65%; vertical-align: top;">
    				<span style="text-align: center;"><h2> רשימת הבקשות </h2></span>
    				<table style="width: 100%;">
    				<c:forEach items="${conferenceProposals}" var="conferenceProposal" varStatus="varStatus">
             			<c:choose><c:when test="${varStatus.index%2==0}"><c:set var="cssClass" value="darker"/></c:when><c:otherwise><c:set var="cssClass" value="brighter"/></c:otherwise></c:choose>
             			<tr class="${cssClass}" style="height: 30px;">
  							<td align="right">
				  				<table style="width: 100%">
  									<tr>
				  						<td width="30%">
  											<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
  											<a href="person.html?id=${conferenceProposal.researcher.id}">
  												<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  											</a>
  											</authz:authorize>
  											<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN">
  												<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  											</authz:authorize>  											
  										</td>
										<td width="40%">
  											<a href="editConferenceProposal.html?id=${conferenceProposal.id}"><c:out value="${conferenceProposal.subject}"/></a>
  										</td>
  										<td width="30%">
 											<c:if test="${conferenceProposal.approverId!=0}">
  												<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
  													<a href="person.html?id=${conferenceProposal.approver.id}">
  														<c:out value="${conferenceProposal.approver.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.approver.lastNameHebrew}"/>
  													</a>
  											</authz:authorize>
  											<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN">
  												<c:out value="${conferenceProposal.approver.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.approver.lastNameHebrew}"/>
  											</authz:authorize>
  											</c:if>
  										</td>
  									</tr>
  								</table>
  							</td>
  	  					</tr>
  	  			  </c:forEach>
  	  			  
  	  			  </table>
  	  			  	 		  
    		</td>
    		</tr>    	
    	</table>
    	<table align="center" style="width: 80%; direction: rtl;">
  	  		<tr>
  	  			<td style="width: 35%;">
  	  				&nbsp;
  	  			</td>
                <td style="width: 65%;" align="center">
					<%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %>
                </td>
            </tr>
  	  	</table>
  	  	<br/>
  	  	<br/>
    	
    	<table align="center" class="container" style="width: 80%; direction: rtl;" cellspacing="10">
    		<tr>
    			<td>
    				<span style="text-align: center;"><h2>ניהול הדירוג</h2></span>
    			</td>
    		</tr>
    		<tr>
				<td>
  					<select name="approver" class="green">
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
		  					<td class="${rowBgBrightness}">${conferenceProposalGrading.approver.degreeFullNameHebrew}</td>
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
      
      	</form:form>
    </td>
  </tr>
</table>
<div id="dialog-confirm" title="" style="display: none;">
	<p><span class="dialogText">את/ה עומד/ת לשלוח בקשה לדירוג במייל לדיקן. האם את/ה מאשר/ת ?</span></p>
</div>
</body>
</html>
