<%@ page  pageEncoding="UTF-8" %>

<script language="Javascript">

	function resetAutocomplete(persons){
		$("#searchPhrase").unautocomplete();
		$("#searchPhrase").autocomplete(persons, {align: 'right', dir: 'rtl', scroll: 'true', scrollHeight: 90});
	}


$(document).ready(function() {


	$("#buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    	$("#form").submit();
    	return true;
    });

    $("#buttonDelete").click(function(){
    	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.deleteProposal.confirm"/>', "מחיקת הצעה",
        function(confirm){
         	 	if (confirm==1){
         	      	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
             	   	$("#form").submit();
             	   	return true;
         	 	}
         	 	else{
     				return false;
         	 	}
        });
    	return false;
    });

    $("#buttonStartGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"startGrading\"/>");
    	$("#form").submit();
    	return true;
    });
    $("#buttonSearch").click(function(){
    	$("input#listViewPage").remove();
		$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });

    $("#buttonCleanSearch").click(function(){
    	$("input#searchPhrase").val('');
       	$("#searchByApprover").val('0');
       	$("#searchBySubmitted").val('0');
       	$("#searchByDeadline").val('0');
   		$("input#listViewPage").remove();
		$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });

 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>



});



</script>



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
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="conferenceProposals.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
            	<input type="hidden" name="searchCreteria.roleFilter" value="${command.searchCreteria.roleFilter}"/>

              <table width="700" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת ההצעות</h1>
                  </td>
                </tr>
                <tr>
 				  <authz:authorize ifNotGranted="ROLE_EQF_RESEARCHER">
                  <td width="300" align="center" valign="center">
                  חוקר:<form:input cssClass="green" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                  </td>
  				  </authz:authorize>
  				  <authz:authorize ifNotGranted="ROLE_CONFERENCE_APPROVER">
				  <td width="100" >
  					<select name="searchByApprover" id="searchByApprover" class="green"> 
      				<option value="0">חפש/י מאשר</option>
       				<c:forEach items="${deans}" var="deanPerson">
       				<c:if test="${deanPerson.id==searchByApprover}">
	        			<option htmlEscape="true" value="${deanPerson.id}" selected><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></option>
	        		</c:if>
	        		<c:if test="${deanPerson.id!=searchByApprover}">
	        			<option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></option>
	        		</c:if>
       				</c:forEach>
       				</select>
				  </td>
  				  </authz:authorize>
 				  <td width="100" >
  					<select name="searchBySubmitted" id="searchBySubmitted" class="green"> 
      				<option value="0" <c:if test="${searchBySubmitted==0}"> selected</c:if>>הוגש</option>
      				<option value="1" <c:if test="${searchBySubmitted==1}"> selected</c:if>>לא הוגש</option>
      				<option value="2" <c:if test="${searchBySubmitted==2}"> selected</c:if>>הוגש/לא הוגש</option>
       				</select>
				  </td>
				  <td width="100" >
  					<select name="searchByDeadline" id="searchByDeadline" class="green"> 
      				<option value="0">הצעות לועדה הנוכחית</option>
      				<option value="1">כולל הצעות ישנות</option>
       				</select>
				  </td>
   				  <td width="200" align="right">
                    <button style="width:100" id="buttonSearch" class="grey" onclick="">חפש</button>
					<button style="width:100" id="buttonCleanSearch" class="grey" onclick="">נקה חיפוש</button>
                  </td>
                </tr>

                <tr>
                  <td colspan="2"><img src="image/hr.gif" width="380" height="10"></td>
                </tr>
              </table>

			<table width="900" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
              <thead>
  				<tr>
  				<td align="right">
				  	<table>
 						<tr>
 						<td width="20"></td>
				  		<td width="100">שם החוקר/ת</td>
				  		<td width="300">נושא הכנס</td>
				  		<td width="100">האם הוגש</td>
				  		<td width="100">דיקן</td>
				  		<td></td>
				  		</tr>
  					</table>
  				</td>
  	  			</tr>
  	  		</thead>
              <c:forEach items="${conferenceProposals}" var="conferenceProposal">
             <tbody>
  				<tr>
  				<td align="right">
				  	<table>
  						<tr>
				  		<td width="20">
			  					<form:radiobutton path="conferenceProposalId" value="${conferenceProposal.id}"/>
 				  		</td>
 						<td width="100">
  							<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  						</td>
						<td width="300">
  							<c:out value="${conferenceProposal.subject}"/>
  						</td>
  						<td width="100">
  						<c:if test="${conferenceProposal.submitted}">הוגש</c:if>
  						<c:if test="${!conferenceProposal.submitted}">לא הוגש</c:if>
  						</td>
 						<td width="100">
 						<c:if test="${conferenceProposal.approverId!=0}">
  							<c:out value="${conferenceProposal.approver.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.approver.lastNameHebrew}"/>
  						</c:if>
  						</td>
  						</tr>
  				</table>
  			</td>
  	  	</tr>
  	  	</tbody>
	   </c:forEach>

	    <tr>
		<td>
			<button class="grey" onclick="window.location='editConferenceProposal.html?action=new'; return false;">הוסף</button>

			<button id="buttonEdit" class="grey" />ערוך</button>
			<button id="buttonDelete" class="grey" />מחק</button>
		</td>
		</tr>
		
		<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
	    <tr>
		<td>
  			<select name="approver" class="green">
      			<option value="0">בחר/י גורם מאשר</option>
       			<c:forEach items="${deans}" var="deanPerson">
	        		<option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></option>
       			</c:forEach>
       		</select>
       		
			<button id="buttonStartGrading" class="grey" />שליחה לדירוג</button>
		</td>
		</tr>
		</authz:authorize>
		
		<tr>
                <td align="center"><br>
					<%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %>
                </td>
                </tr>

                  </table>
                </td>
              </tr>
			  <tbody>


            </table>

            <br>
          </td>
        </tr>
      </table>
      </form:form>
    </td>
  </tr>
</table>
</body>
</html>
