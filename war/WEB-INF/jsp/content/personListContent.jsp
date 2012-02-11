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
    
    
    
    
    
    

});






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
      <table width="70%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="persons.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
            	<input type="hidden" name="searchCreteria.roleFilter" value="${command.searchCreteria.roleFilter}"/>

              <table style="width: 100%;" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת אנשים</h1>
                  </td>
                </tr>
                <tr>
                  <td align="center" valign="center">
                  חיפוש: <form:input cssClass="green" size="60" id="searchPhrase" path="searchCreteria.searchPhrase"/>
                  </td>                  
                </tr>

                <tr>
                  <td colspan="2" style="text-align: center;"><img src="image/hrWide.gif" width="600" height="10"></td>
                </tr>
              </table>

				<table width="70%" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">

              <c:forEach items="${persons}" var="person" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
  				<td align="right">
				  	<table>
  						<tr>				  			
  						
  						<td style="direction: rtl;">
  							<a href="person.html?id=${person.id}">  							
  							
  							<c:out value="${person.firstNameHebrew}"/> &nbsp;
  						
  							<c:out value="${person.lastNameHebrew}"/> &nbsp;
  						
  							<c:out value="${person.email}"/>
  							
  							</a>
  						
  						</tr>
  				</table>
  			</td>
  	  	</tr>
  	  	</tbody>
	   </c:forEach>

	    <tr>
		<td>
			<button class="grey" onclick="window.location='person.html'; return false;">הוסף</button>

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
