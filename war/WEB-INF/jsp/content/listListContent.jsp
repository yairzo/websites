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



var lists = '<c:forEach items="${allLists}" var="list"><c:out escapeXml="false" value="${list.name}"/>,</c:forEach>'.split(",");

function resetAutocomplete(lists){
	$("#searchPhrase").autocomplete( 
			{source: lists,
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

function cleanSearch(){
	$("input#searchPhrase").val('');
	$("input#listViewPage").remove();
	$("input#orderBy").remove();
}


$(document).ready(function() {

	$(".buttonAdd").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"add\"/>");
    $("#form").submit();
    return true;
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

    $(".buttonCopy").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"copy\"/>");
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
    	cleanSearch();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });
    
    $("#searchPhrase").click(function(){
    	cleanSearch()
    });

 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>



resetAutocomplete(lists);
$("#searchPhrase").focus();

});
</script>


          <td align="right" bgcolor="#787669" height="20">
				<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="רשימת הרשימות"/>
          	        <c:set var="aItemsList" value="1"/>
          	        <c:set var="self" value="lists.html"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="lists.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
                <form:hidden path="searchCreteria.searchField"/>

              <table width="500" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת רשימות</h1>
                  </td>
                </tr>
                <tr>
                  <td width="320" align="center" valign="center">
                  <form:input id="searchPhrase" htmlEscape="true" cssClass="green fillWidth" path="searchCreteria.searchPhrase"/>

                  </td>

                  <td width="180" align="right">

                     <button id="buttonSearch" class="grey">חפש</button>
                     <button id="buttonCleanSearch" class="grey">נקה חיפוש</button>


                  </td>
                </tr>


                <tr>
                  <td colspan="2"><img src="image/hr.gif" width="500" height="10"></td>
                </tr>

              </table>

				<table width="500" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">



			<tbody>

			<c:if test="${fn:length(lists)>10}">


			<tr>

		<td>
           <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
 			<button class="buttonAdd grey">הוסף</button>
			<button class="buttonEdit grey"/>ערוך</button>
			<button class="buttonDelete grey"/>מחק</button>
			<button class="buttonCopy grey"/>שכפל</button>
			</authz:authorize>
			<button class="buttonView grey" />צפייה ברשימה</button>
		</td>

		</tr>

		</c:if>


		<tr>
				<td>&nbsp;
				</td>
			</tr>
			</tbody>



			<c:forEach items="${lists}" var="list" varStatus="varStatus">
				<tbody>
				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				   	<td>
			    		<form:radiobutton path="id" value="${list.id}"/>
			    		<c:out value="${list.name}"></c:out>
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
			<button class="buttonAdd grey">הוסף</button>
			<button class="buttonEdit grey"/>ערוך</button>
			<button class="buttonDelete grey"/>מחק</button>
			<button class="buttonCopy grey"/>שכפל</button>
			</authz:authorize>
			<button class="buttonView grey" />צפייה ברשימה</button>
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