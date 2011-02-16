<%@ page  pageEncoding="UTF-8" %>


<script language="Javascript">



$(document).ready(function() {



var fundsShortNames = "<c:forEach items="${funds}" var="fund"><c:out value="${fund.shortName}"/>,</c:forEach>".split(",");


var v = $("input:radio[@id='searchFund']").fieldValue();
        if (v[0] == 'fundId') {
                $("#searchPhrase").unautocomplete();
                $("#searchPhrase").autocomplete(fundsShortNames, {align: 'left'});
        }

$("input:radio[@id='searchFund']").click(function() {
    $("#searchPhrase").val('');
    $("#searchPhrase").css("text-align", "left");
    $("#searchPhrase").unautocomplete();
    $("#searchPhrase").autocomplete(fundsShortNames, {align: 'left'});
    $("#searchPhrase").focus();
});


var proposalsTitles = "<c:forEach items="${viewableProposals}" var="proposal"><c:out value="${proposal.hebrewTitleHandled}"/>,</c:forEach>".split(",");


var v1 = $("input:radio[@id='searchTitle']").fieldValue();
if (v1[0] == 'hebrewTitle') {
	$("#searchPhrase").unautocomplete();
	$("#searchPhrase").autocomplete(proposalsTitles, {align: 'right'});
}

$("input:radio[@id='searchTitle']").click(function() {
	$("#searchPhrase").val('');
	$("#searchPhrase").css("text-align", "right");
	$("#searchPhrase").unautocomplete();
	$("#searchPhrase").autocomplete(proposalsTitles, {align: 'right'});
    $("#searchPhrase").focus();
});

var mainResearchers = "<c:forEach items="${mainResearchers}" var="mainResearcher"><c:out value="${mainResearcher.fullNameHebrew}"/>,</c:forEach>".split(",");


var v2 = $("input:radio[@id='searchResearcher']").fieldValue();
if (v2[0] == 'mainResearcherId') {
	$("#searchPhrase").unautocomplete();
	$("#searchPhrase").autocomplete(mainResearchers, {align: 'right'});
}

$("input:radio[@id='searchResearcher']").click(function() {
	$("#searchPhrase").val('');
	$("#searchPhrase").css("text-align", "right");
	$("#searchPhrase").unautocomplete();
	$("#searchPhrase").autocomplete(mainResearchers, {align: 'right'});
    $("#searchPhrase").focus();
});

$("#buttonAdd").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"open\"/>");
    $("#form").submit();
    return false;
    });


$("#buttonEdit").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    $("#form").submit();
    return false;
    });

$("#buttonCopy").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"opencopy\"/>");
    $("#form").submit();
    return false;
    });

$("#buttonDelete").click(function(){

	$.get('proposalCheck',{type: 'stateId', proposalId: $("form #proposalId").val()}, function(j){
		if (j==0){
			$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.deleteProposal.confirm"/>',
     	 		'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
     	 		function(confirm){
     	 			if (confirm==1){
     	 				$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
    					$("#form").submit();
    					return false;
     	 		}
     		 });
     	 }
     	 else{
     	 	$.alerts.alert('<fmt:message key="iw_IL.eqfSystem.editProposal.deleteProposal.reject"/>');
     	 }

    });
    return false;
});

$("#buttonSearch").click(function(){
	$("input#listViewPage").remove();
	$("input#orderBy").remove();
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
    $("#form").submit();
    return false;
    });

$("#buttonCancelSearch").click(function(){
	$("input#listViewPage").remove();
	$("input#orderBy").remove();
	$(".searchBy").attr("checked",false);
	$("#searchPhrase").attr("value","");
	$("#form").submit();
    return false;
    });

$('#searchPhrase').keyup(function(e) {
	if(e.keyCode == 13) {
		$("input#listViewPage").remove();
		$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
    	$("#form").submit();
    	return false;
	}
});

$("form#form").bind("keypress", function(e) {
     if (e.keyCode == 13) {
        return false;
     }
});



 $("input:radio[@id='searchYearId']").click(function() {
	$("#searchPhrase").val('');
	    $("#searchPhrase").css("text-align", "rtl");
	$("#searchPhrase").unautocomplete();
 });


 $("th.title").click(function(){
 	$("input#listViewPage").remove();
 	$("input#listViewOrderBy").remove();
 	$("form#form").append('<input type=\"hidden\" name=\"listView.orderBy\" value=\"hebrewTitle\"/>');
 	$("form#form").submit();
 });

 $("th.status").click(function(){
 	$("input#listViewPage").remove();
 	$("input#listViewOrderBy").remove();
 	$("form#form").append('<input type=\"hidden\" name=\"listView.orderBy\" value=\"stateId\"/>');
 	$("form#form").submit();
 });

 $("th.mainResearcher").click(function(){
 	$("input#listViewPage").remove();
 	$("input#listViewOrderBy").remove();
 	$("form#form").append('<input type=\"hidden\" name=\"listView.orderBy\" value=\"mainResearcherId\"/>');
 	$("form#form").submit();
 });

 $("th.fundId").click(function(){
 	$("input#listViewPage").remove();
 	$("input#listViewOrderBy").remove();
 	$("form#form").append('<input type=\"hidden\" name=\"listView.orderBy\" value=\"fundId\"/>');
 	$("form#form").submit();
 });


 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>

 $("input#searchPhrase").focus();

});


</script>



          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="טופס מרובע"/>
          	        <c:set var="pageName" value="רשימת הצעות"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>

          </td>

        </tr>

      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

            	<table width="90%" border=0  cellspacing=1 cellpadding=2 rules="groups" dir="rtl">
                <tr>
                  <th align="right" width="5%">&nbsp;</th>
                  <th class="title" align="right" width="20%">כותרת</th>
                  <th class="status" align="right" width="15%">סטטוס</th>
                  <th class="mainResearcher" align="right" width="10%">חוקר אחראי</th>
                  <th class="fundId" align="right" width="10%">גורם מממן</th>
                  <th align="right" width="10%">תשובת הקרן</th>
                  <th align="right" width="10%">עדכון אחרון</th>
                  <th align="right" width="10%">
                  <authz:authorize ifAnyGranted="ROLE_EQF_ADMIN,ROLE_EQF_MOP">
                  	מספר סידורי שנתי
                  </authz:authorize>
                  </th>

                </tr>


                <c:forEach items="${proposals}" var="proposal" varStatus="varStatus">


	                <c:choose>
	                	<c:when test="${varStatus.index%2==1}">
			                <c:set var="cssClass" value="brighter" />
			             </c:when>
			             <c:otherwise>
			             	<c:set var="cssClass" value="darker" />
			             </c:otherwise>
			         </c:choose>

	                <tr class="${cssClass}">
    	                <td align="right">
    	                	<c:choose>
					  			<c:when test="${proposal.busyRecord}">
						  			&nbsp;
				  	  			</c:when>
				  	  			<c:otherwise>
			  						<form:radiobutton id="proposalId" path="proposalId" value="${proposal.id}"/>
  					  			</c:otherwise>
				  			</c:choose>
						</td>

        	            <td align="right">${proposal.hebrewTitleHandled}</td>
            	        <td align="right">${proposalStates[ proposal.stateId]}</td>
                	    <td align="right"><c:out value="${proposal.mainResearcher.degreeFullNameHebrew}"/></td>
                    	<td align="right">${proposal.fund.shortName}</td>

                    	<c:choose>
 									<c:when test="${proposal.stateId <= APPROVED_BY_ALL}">
 										<c:set var="fundingAgencyApproval" value="טרם הוזנה תשובת הקרן"/>
 									</c:when>
 									<c:when test="${proposal.fundingAgencyApproved==1}">
 										<c:set var="fundingAgencyApproval" value="אושרה על-ידי הקרן"/>
 									</c:when>
 									<c:when test="${proposal.fundingAgencyApproved==2}">
 										<c:set var="fundingAgencyApproval" value="נדחתה בציון גבוה"/>
 									</c:when>
 									<c:otherwise>
 										<c:set var="fundingAgencyApproval" value="נדחתה על-ידי הקרן"/>
 									</c:otherwise>
 								</c:choose>


                    	<td align="right">${fundingAgencyApproval}</td>
                    	<td align="right">${proposal.proposalUpdateFormatted}</td>

                    	<td>
                    	<authz:authorize ifAnyGranted="ROLE_EQF_ADMIN,ROLE_EQF_MOP">

                    		<c:if test="${proposal.yearId>0}">
  								<c:out value="${proposal.formatedYearId}"/>
  								&nbsp;&nbsp;
  								</c:if>
  					   </authz:authorize>
  					   </td>

                  </tr>
                 </c:forEach>

                 <tr>
                  <td colspan="7" align="center"><br>
                    <table border="0" align="center" cellpadding="4" cellspacing="0">
                      <tr>

                        <td>
                        	<table border="0" align="center" cellpadding="4" cellspacing="0">
                            	<tr>
                              		<td align="center">
                              			<td>
											<button id="buttonAdd" class="grey" onclick="return false;">הוסף</button>
											<button id="buttonEdit" class="grey" onclick="return false;">ערוך</button>
											<button id="buttonCopy" class="grey" onclick="return false;">העתק</button>
											<button id="buttonDelete" class="grey" onclick="return false;">מחק</button>
										</td>
                            	</tr>
                           </table>
                        </td>

                        <td width="20">&nbsp;</td>
                        <td align="right" bgcolor="#f1ecd2"><table border="0" cellpadding="0" cellspacing="0">
                            <table>
                            <tr>
                              <td width="43" align="right">
                              	<button id="buttonSearch" class="grey">סנן</button>
                              </td>

                              <td width="10">&nbsp;</td>
                            </tr>
                          </table>
                         </td>
                        <td align="left" valign="center" bgcolor="#f1ecd2">
                        	<form:input id="searchPhrase" dir="rtl" cssClass="green" path="searchCreteria.searchPhrase"/>
                        </td>



                        <td align="center" valign="middle" bgcolor="#f1ecd2"> סנן לפי:&nbsp;&nbsp;
		                    <form:radiobutton id="searchTitle" cssClass="searchBy" path="searchCreteria.searchField" value="hebrewTitle"  /> כותרת
	                       	| <form:radiobutton id="searchFund" cssClass="searchBy" path="searchCreteria.searchField" value="fundId"  /> שם מממן
                            | <form:radiobutton id="searchResearcher" cssClass="searchBy" path="searchCreteria.searchField" value="mainResearcherId"  /> חוקר אחראי
                            <authz:authorize ifAnyGranted="ROLE_EQF_ADMIN,ROLE_EQF_MOP">
                            	| <form:radiobutton id="searchYearId" cssClass="searchBy" path="searchCreteria.searchField" value="yearId"  /> מספור שנתי
                        	</authz:authorize>
					   </td>

					   <td align="right" bgcolor="#f1ecd2"><table border="0" cellpadding="0" cellspacing="0">
                            <table>
                            <tr>
                              <td width="43" align="right">
                              	<button id="buttonCancelSearch" class="grey">הצג הכל</button>
                              </td>

                              <td width="10">&nbsp;</td>
                            </tr>
                          </table>
                         </td>

                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td colspan="7" align="center"><br>
                  </td>

                </tr>
                <tr>
                  <td colspan="7" align="center"><%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %></td>
                </tr>


             </table>




            </form:form>
            </td>
  </tr>
</table>
</body>
</html>
