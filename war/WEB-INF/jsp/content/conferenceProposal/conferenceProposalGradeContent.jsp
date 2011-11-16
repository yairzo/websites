<%@ page  pageEncoding="UTF-8" %>

<script language="Javascript">

	function resetAutocomplete(persons){
		$("#searchPhrase").unautocomplete();
		$("#searchPhrase").autocomplete(persons, {align: 'right', dir: 'rtl', scroll: 'true', scrollHeight: 90});
	}


$(document).ready(function() {


	$("button.buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.buttonSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"save\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.buttonUp").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"moveup\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.buttonDown").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"movedown\"/>");
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
            <form:form id="form" name="form" method="POST" commandName="command" action="conferenceProposalsGrade.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
            	<input type="hidden" name="searchCreteria.roleFilter" value="${command.searchCreteria.roleFilter}"/>

              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת ההצעות</h1>
                  </td>
                </tr>
                <tr>
                  <td width="201" align="center" valign="center">
                  <form:input cssClass="green" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                  </td>

                  <td width="173" align="right">

                     <button id="buttonSearch" class="grey" onclick="">חפש</button>
					<button id="buttonCleanSearch" class="grey" onclick="">נקה חיפוש</button>

                  </td>
                </tr>

                <tr>
                  <td colspan="2"><img src="image/hr.gif" width="380" height="10"></td>
                </tr>
              </table>

				<table width="700" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
              <thead>
  				<tr>
  				<td align="right">
				  	<table>
 						<tr>
				  		<td  width="20"></td>
				  		<td width="60">שם החוקר/ת</td>
				  		<td width="60">נושא הכנס</td>
				  		<td width="20">דירוג</td>
				  		<td width="60">חוות דעת</td>
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
				  		<td  width="20">
			  				<form:radiobutton path="conferenceProposalId" value="${conferenceProposal.id}"/>
 				  		</td>
  						<td width="60">
  							<c:out value="${conferenceProposal.researcher.firstNameHebrew}"/>&nbsp;<c:out value="${conferenceProposal.researcher.lastNameHebrew}"/>
  							
  						</td>
 						<td width="60">
  							<c:out value="${conferenceProposal.subject}"/>
  						</td>
						<td width="20">
  							<c:out value="${conferenceProposal.grade}"/>
  						</td>
 						<td width="60">
  							<textarea class="green" name="approverEvaluation${conferenceProposal.id}" rows="2" cols="40">${conferenceProposal.approverEvaluation}</textarea>
  						</td>
				  		<td>
							<button class="grey buttonUp" />העלה</button>
							<button class="grey buttonDown" />הורד</button>
							<button class="grey buttonSave" />שמור</button>
							<button class="grey buttonEdit" />ערוך</button>
 				  		</td>
   					</tr>
  				</table>
  			</td>
  	  	</tr>
  	  	</tbody>
	   </c:forEach>
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
