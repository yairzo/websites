<%@ page  pageEncoding="UTF-8" %>

	<div id="genericDialog" title="" style="display:none" dir="rtl">
		<p>text put here</p>
	</div>

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
    	<table border="0" align="center" style="width: 95%; direction: rtl;" cellspacing="10">
    		<tr>
    			<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
    			<td class="container" style="width: 25%;">
    				<span style="text-align: center;"><h2> סינון הבקשות שתוצגנה ברשימה</h2></span>
    				<br/>
    				    לפי שם החוקר:
               		 <br/>    
               		     <form:input cssStyle="width: 280px;" cssClass="green" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                    
  				    <authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				  	<br/>
				  	<br/>
				  	<select style="width: 280px;" name="searchByApprover" id="searchByApprover" class="green"> 
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
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="1" <c:if test="${searchBySubmitted==1}">checked="checked"</c:if>/> מוגשות
  				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="0" <c:if test="${searchBySubmitted==0}">checked="checked"</c:if>/> טיוטות
  				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="3" <c:if test="${searchBySubmitted==3}">checked="checked"</c:if>/> מבוטלות
  				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				   <input class="searchBySubmitted" type="radio" name="searchBySubmitted" class="green" value="2" <c:if test="${searchBySubmitted==2}">checked="checked"</c:if>/> כל הבקשות
  				   </authz:authorize>
  				   <authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
  				   	 <input type="hidden" name="searchBySubmitted" value="1"/>
  				   </authz:authorize>
  				   <authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
  				   <span id="searchByDeadlineSpan">
  				   <br/>  				   
 				   		 לפי דיון:  				   
 				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="1" <c:if test="${searchByDeadline==1}">checked="checked"</c:if>/> מוגשות לדיון הקרוב
  				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="2" <c:if test="${searchByDeadline==2}">checked="checked"</c:if>/> מוגשות לדיון הבא  
 				   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				  
   				   <input class="searchByDeadline" type="radio" name="searchByDeadline" class="green" value="0" <c:if test="${searchByDeadline==0}">checked="checked"</c:if>/> כל הבקשות
				  	</span>
				   </authz:authorize>
				</td>
				</authz:authorize>
    			<td class="container" style="width: 75%; vertical-align: top;">
				  	<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
    					<span style="text-align: center;"><h2> רשימת הבקשות שלך</h2></span>
   					</authz:authorize>
				  	<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
    					<span style="text-align: center;"><h2> רשימת הבקשות שבהמלצת הדיקן - יחידה:<c:out escapeXml="false" value="${myFaculty}"></c:out></h2> </span>
   					</authz:authorize>
				  	<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_APPROVER">
    					<span style="text-align: center;"><h2> רשימת הבקשות </h2></span>
   					</authz:authorize>
    				<table style="width: 100%;">
    				<c:choose>
    				<c:when test="${fn:length(conferenceProposals) > 0}">
    				  	<tr>
 							<td align="right" style="padding: 0px 20px;">
				  				<table style="width: 100%">
  									<tr>
				  					<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
		  							<td style="font-weight: bold;width:20%;align:right;">החוקר המבקש</td>
  									</authz:authorize>
			  						<td style="font-weight: bold;width:25%;align:right;">שם הכנס</td>
			  						<td style="font-weight: bold;width:10%;align:right;">תאריך הכנס</td>
			  						<td style="font-weight: bold;width:15%;align:right;">הדיקן הממליץ</td>
 			  						<td style="font-weight: bold;width:10%;align:right;">סטטוס</td>
 			  						<td style="font-weight: bold;width:10%;align:right;">תאריך הסטטוס</td>
   									<c:if test="${conferenceProposal.submitted && admin}">
 			  						<td style="font-weight: bold;width:5%;align:right;">לדיון הקרוב</td>
 			  						</c:if>
 									</tr>
  								</table>
  							</td>
   	  					</tr>
    				
    				<c:forEach items="${conferenceProposals}" var="conferenceProposal" varStatus="varStatus">
             			<c:choose><c:when test="${varStatus.index%2==0}"><c:set var="cssClass" value="darker"/></c:when><c:otherwise><c:set var="cssClass" value="brighter"/></c:otherwise></c:choose>
             			<tr class="${cssClass}" style="height: 30px;">
  							<td align="right" style="padding: 0px 20px;">
				  				<table style="width: 100%">
  									<tr>
				  						<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
				  						<td width="20%">
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
 										<td width="25%">
  											<a href="editConferenceProposal.html?id=${conferenceProposal.id}"><c:choose><c:when test="${fn:length(conferenceProposal.subject)>0}"><c:out value="${conferenceProposal.subject}"></c:out></c:when><c:otherwise>ללא נושא</c:otherwise></c:choose></a>
  										</td>
 										<td width="10%">
   											<a href="editConferenceProposal.html?id=${conferenceProposal.id}">	<c:out value="${conferenceProposal.formattedFromDate}"/></a>
 										</td>
  										<td width="15%">
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
  										<td width="10%">
  											<a href="editConferenceProposal.html?id=${conferenceProposal.id}">
  											<c:choose>
   											<c:when test="${conferenceProposal.deleted}">
  												בוטלה
  											</c:when>
   											<c:otherwise>
 												<c:choose>
  												<c:when test="${conferenceProposal.submitted}">
  												הוגשה
  												</c:when>
  												<c:otherwise>
  												טיוטה
  												</c:otherwise>
  												</c:choose>
  											</c:otherwise>
  											</c:choose>
  											</a>
  										</td>
 										<td width="10%">
   											<a href="editConferenceProposal.html?id=${conferenceProposal.id}">	<c:out value="${conferenceProposal.statusDate}"/></a>
 										</td>
   										<c:if test="${conferenceProposal.submitted && admin}">
 										<td width="5%">
 										<input type="checkbox" class="saveCheckbox" name="insideDeadline${conferenceProposal.id}" id="${conferenceProposal.id}" <c:if test="${conferenceProposal.isInsideDeadline}" > checked </c:if> />
   										</td>
   										</c:if>
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
    			
    			<tr>
				<td align="right">
					&nbsp;
				</td>
				</tr> 
    			<tr>
				<td>
				<table width="100%">	
				<tr>
					<td  align="right">
					<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
					<button class="grey confirmLink">הגשת בקשה למימון כנס</button>
      		        <img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogNewConferenceProposal"/>
					</authz:authorize>
					</td>
					<td align="left">
					<button  class="grey" title="חזרה לתפריט הראשי"  onclick="window.location='welcome.html';return false;">חזרה לתפריט </button>		
					</td>
				</tr>
				</table>
				</td>
				</tr>
				 
  	  			</table>

  	  			  	 		  
    		</td>
    		</tr>   
    		
    		 	
    	</table>
    	<table align="center" style="width: 90%; direction: rtl;">
  	  		<tr>
  	  			<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
  	  			<td style="width: 25%;">
  	  				&nbsp;
  	  			</td>
  	  			</authz:authorize>
                <td style="width: 75%;" align="center">
					<%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %>
                </td>
            </tr>
 

  	  	</table>
  	  	<br/>
  	  	
  	  	<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
    	
    	<table align="center" style="width: 95%; direction: rtl;" cellspacing="10">
    	<tr>
    	<td>
    	<table align="center" class="container" style="width: 100%; direction: rtl;" >
    		<tr>
    			<td style="text-align: center;">
    				<span ><h2> ניהול תהליך התייחסות הדיקן</h2></span>
    			</td>
    		</tr>
    		<tr>
				<td>שיגור דרישות להתייחסות:<br />
  					<select name="approver" id="approver" class="green">
      					<option value="0">בחר דיקן </option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></option>
       					</c:forEach>
       				</select>
					&nbsp;הערה לדיקן:
					<textarea class="green" name="adminSendRemarks" id="adminSendRemarks" cols="60" rows="2"></textarea>
					&nbsp;<button id="buttonStartGrading" class="grey" />שלח לדיקן</button>
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
    				<table style="width: 100%;" align="center">
             			<thead>
  						<tr>
		  					<td style="font-weight: bold;width: 30%;">הדיקן הנמען</td>
			  				<td style="font-weight: bold;width: 15%;">נשלח בתאריך</td>
			  				<td style="font-weight: bold;width: 20%;">הערת הרכז/ת לדיקן</td>
			  				<td style="font-weight: bold;width: 15%;">הדיקן התייחס</td>
			  				<td style="font-weight: bold;width: 20%;">הערת הדיקן</td>
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
		  					<td class="${rowBgBrightness}"><a href="conferenceProposalsGrade.html?approverId=${conferenceProposalGrading.approver.id}">${conferenceProposalGrading.approver.degreeFullNameHebrew} - ${conferenceProposalGrading.approverFaculty}</a></td>
			  				<td class="${rowBgBrightness}">${conferenceProposalGrading.formattedSentForGradingDate}</td>
			  				<td class="${rowBgBrightness}"><font title="${conferenceProposalGrading.adminSendRemark}">${conferenceProposalGrading.adminSendRemark}</font></td>
			  				<td class="${rowBgBrightness}">${conferenceProposalGrading.formattedFinishedGradingDate}</td>
			  				<td class="${rowBgBrightness}"><font title="${conferenceProposalGrading.deadlineRemark}">${conferenceProposalGrading.deadlineRemark}</font></td>
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
