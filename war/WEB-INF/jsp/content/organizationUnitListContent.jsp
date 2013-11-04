<%@ page  pageEncoding="UTF-8" %>

<style>
	.ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
		/* add padding to account for vertical scrollbar */
		padding-right: 20px;
		direction: rtl;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}


</style>

<script language="Javascript">

//var organizationUnits = "<c:forEach items="${allOrganizationUnits}" var="organizationUnit"><c:out escapeXml="false" value="${organizationUnit.nameHebrew}"/>;;</c:forEach>".split(";;");

function resetAutocomplete(organizationUnits){
	$("#searchPhrase").autocomplete( 
			{source: organizationUnits,
			 minLength: 2,
			 highlight: true,
			 select: function(event, ui) {
				$("input#listViewPage").remove();
				$("input#orderBy").remove();
				$("#searchPhrase").val(ui.item.label);
				$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
				$("#form").submit();				 
			 }
		    }
	);
}
$(document).ready(function() {
	

    $.get('selectBoxFiller',{type:'organizationUnit'},function(data){
		var organizationUnits = data.split(",,");
		resetAutocomplete(organizationUnits);
		$("#searchPhrase").focus();
 	});
    
    
	$(".buttonEdit").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    $("#form").submit();
    return true;
    });

    $(".buttonDelete").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
    $("#form").submit();
    return true;
    });

    $(".buttonView").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"view\"/>");
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
				<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="רשימת היחידות"/>
          	        <c:set var="aItemsList" value="1"/>
          	        <c:set var="self" value="organizationUnits.html"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="organizationUnits.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
                <form:hidden path="searchCreteria.searchField"/>

              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת היחידות</h1>
                  </td>
                </tr>
                <tr>
                  <td width="201" align="center" valign="center"><form:input id="searchPhrase" htmlEscape="true" cssClass="green" path="searchCreteria.searchPhrase"/>

                  </td>

                  <td width="173" align="right">

                     <button id="buttonSearch" class="grey">חפש</button>
                     <button id="buttonCleanSearch" class="grey">נקה חיפוש</button>


                  </td>
                </tr>


                <tr>
                  <td colspan="2"><img src="/image/hr.gif" width="380" height="10"></td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">



			<tbody>

			<c:if test="${fn:length(organizationUnits)>10}">


			<tr>

		<td>
           <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
            <button class="grey" onclick="window.location='organizationUnit.html?action=open';return false;">הוסף</button>
			<button class="buttonEdit grey"/>ערוך</button>
			<button class="buttonDelete grey"/>מחק</button>
			</authz:authorize>

		</td>

		</tr>

		</c:if>


		<tr>
				<td>&nbsp;
				</td>
			</tr>
			</tbody>



			<c:forEach items="${organizationUnits}" var="organizationUnit" varStatus="varStatus">
				<tbody>
				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				   	<td>
			    		<form:radiobutton path="id" value="${organizationUnit.id}"/>
			    		<c:out escapeXml="false" value="${organizationUnit.nameHebrew}"></c:out>
			    	</td>
				</tr>
			</tbody>
		   </c:forEach>

	    <tr>
	    	<td>
	    		&nbsp;
	    	</td>
	    </tr>
	    <tr>
	    <td>
           <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
             <button class="grey" onclick="window.location='organizationUnit.html?action=open';return false;">הוסף</button>
			<button class="buttonEdit grey"/>ערוך</button>
			<button class="buttonDelete grey"/>מחק</button>
			</authz:authorize>

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