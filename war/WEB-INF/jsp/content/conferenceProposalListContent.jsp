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
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
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

    $.get('selectBoxFiller',{type:'person'},function(data){
 		var persons = data.split(",,");
 		resetAutocomplete(persons);
 		$("#searchPhrase").focus();
 	});

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

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">

              <c:forEach items="${conferenceProposals}" var="conferenceProposal">
             <tbody>
  				<tr>
  				<td align="right">
				  	<table>
  						<tr>
				  			<td>
				  				<c:choose>
					  				<c:when test="${conferenceProposal.busyRecord}">
						  		&nbsp;
				  	  				</c:when>
				  	  				<c:otherwise>
			  					<form:radiobutton path="personId" value="${conferenceProposal.id}"/>
  					  				</c:otherwise>
				  				</c:choose>
				  		</td>
  						<td>
  							<c:out value="${conferenceProposal.firstNameHebrew}"/>
  						</td>
  						<td>
  							<c:out value="${conferenceProposal.lastNameHebrew}"/>
  						</td>
 						<td>
  							<c:out value="${conferenceProposal.subject}"/>
  						</td>
 						<td>
  							<c:out value="${conferenceProposal.location}"/>
  						</td>
  					</tr>
  				</table>
  			</td>
  	  	</tr>
  	  	</tbody>
	   </c:forEach>

	    <tr>
		<td>
			<button class="grey" onclick="window.location='conferenceProposal.html?action=new'; return false;">הוסף</button>

			<button id="buttonEdit" class="grey" />ערוך</button>
			<button id="buttonDelete" class="grey" />מחק</button>
		</td>
		</tr>
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
