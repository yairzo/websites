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

<script>

function resetAutocomplete(persons){
	$("#searchPhrase").autocomplete( 
			{source: persons,
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

    $.get('selectBoxFiller',{type:'person'},function(data){
 		var persons = data.split(",,");
 		resetAutocomplete(persons)
 		$("#searchPhrase").focus();
 	});  
    
    $('input').keypress(function(e){
        if(e.which == 13){
        	console.log("entered")
	return;
         }
        });
    
});

function checkThis(checkbox)
{
	jQuery.ajax({url : "personAction.html?actionCommand=changeCollectPublications&personId="+checkbox.name});
}

</script>

          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="רשימת האנשים"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="persons.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
            	<input type="hidden" name="searchCreteria.roleFilter" value="${command.searchCreteria.roleFilter}"/>

			<p><h1><b>רשימת אנשים</b></h1></p>
			<div class="search"><p>
				חיפוש: <form:input cssClass="green" size="60" id="searchPhrase" path="searchCreteria.searchPhrase"/>
				<input type="button"  onclick="window.location='person.html'; return false;" value="הוסף">
			</p></div>
			<div>
			<%-- people table content --%>
			<table width="70%" border="1" align="center" cellpadding="1" cellspacing="0" style="direction:rtl">
				<%-- Title Row --%>
				<tr  class="darker">
					<td><b>#</b></td>
					<td><b>שם פרטי</b></td>
					<td><b>דוא"ל</b></td>
					<td><b>אסוף פרסומים?</b></td>
				</tr>
				<c:forEach items="${persons}" var="person" varStatus="line">
					<tr class=<c:choose><c:when test="${(line.index % 2) == 0 }">"darker"</c:when><c:otherwise>"brighter"</c:otherwise></c:choose>>
						<td>${line.index}</td>
						<td>
							<%-- First Name column --%>
							<a href="/person.html?id=${person.id}">
								${person.firstNameHebrew} ${person.lastNameHebrew}
							</a>
						</td>
						<td>
							<%-- E-mail column --%>
							<a href="mailto:${person.email}">
								${person.email}
							</a>
						</td>
						<td>
							<%-- Collect Publications column --%>
							<input type="checkbox" name="${person.id}" onclick="checkThis(this);" <c:if test="${person.collectPublications == true}">checked</c:if>/>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			</div>
			<div style="direction: rtl"><%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %></div>
			

			
		<%-- Closing tags --%>	
                </td>
              </tr>
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
