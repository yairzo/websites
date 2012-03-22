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
		width:200;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}


</style>
<script language="Javascript">

function resetAutocomplete(posts){
	$("#searchPhrase").autocomplete( 
			{source: posts,
			 minLength: 2,
			 highlight: true,
			 select: function(event, ui) {
				$("input#listViewPage").remove();
				$("input#orderBy").remove();
				$("#searchPhrase").val(ui.item.label);
				$("#form").append("<input type=\"hidden\" path=\"searchCreteria.searchField\" value=\"messageSubject\"/>");
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

$(function() {
    $.datepicker.regional['he'] = {
        closeText: 'סגור',
        prevText: '&#x3c;הקודם',
        nextText: 'הבא&#x3e;',
        currentText: 'היום',
        monthNames: ['ינואר','פברואר','מרץ','אפריל','מאי','יוני',
        'יולי','אוגוסט','ספטמבר','אוקטובר','נובמבר','דצמבר'],
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
        dayNames: ['ראשון','שני','שלישי','רביעי','חמישי','שישי','שבת'],
        dayNamesShort: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
        dayNamesMin: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
        weekHeader: 'Wk',
        dateFormat: 'dd/mm/yy',
        firstDay: 0,
        isRTL: true,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['he']);

});

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
    
    $("#buttonCopy").click(function(){
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

 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>
 	$('.searchVerified').click(function(){
 		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
 	});

 	$("#searchPhrase").click(function(){
 		cleanSearch()
 	});
 	
 	$.get('selectBoxFiller',{type:'post'},function(data){
		var posts = data.split(",,");
		resetAutocomplete(posts)
		$("#searchPhrase").focus();
	});

 	$("#postDate").datepicker({ dateFormat: 'yy-mm-dd', onSelect: function(){
 	   	$("#searchPhrase").empty();
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	}
	});
 	
    $("#buttonCleanSearch").click(function(){
    	$("input#listViewPage").remove();
    	$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });
});

</script>



          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת דיוור"/>
          	        <c:set var="pageName" value="רשימת הודעות"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="posts.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="600" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת הודעות</h1>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">
                  לפי נושא:<input type="text" id="searchPhrase" class="green medium220" name="searchPhrase"/>
					לפי תאריך:<input type="text" class="green medium100" name="postDate" id="postDate" readonly="readonly"/>

                   </td>
                </tr>
               <tr>
                  <td colspan="2">
  				   <input class="searchVerified" type="radio" name="searchVerified" class="green" value="1" <c:if test="${searchVerified==1}">checked="checked"</c:if>/>  כולל טיוטות
  				   <input class="searchVerified" type="radio" name="searchVerified" class="green" value="0" <c:if test="${searchVerified==0}">checked="checked"</c:if>/>  לא כולל טיוטות
                   &nbsp;<button id="buttonCleanSearch" class="grey">בטל חיפוש</button> 

                 </td>
                </tr>
 
               </table>

				<table width="600" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
               <tr>
                  <td colspan="2"><img src="image/hr.gif" width="600" height="10"></td>
                </tr>




 

   			<c:choose>
    		<c:when test="${fn:length(posts) > 0}">
             <tr>
               <th align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="posts.html?orderBy=messageSubject&page=<c:out value="${listView.page}"/>&searchPhrase=<c:out value="${command.searchCreteria.searchPhrase}"/>"><img src="image/downArrow.gif" border="0"></a></th>
                <th align="right"><a href="posts.html?orderBy=sendTime&page=<c:out value="${listView.page}"/>&searchPhrase=<c:out value="${command.searchCreteria.searchPhrase}"/>"><img src="image/downArrow.gif" border="0"></a></th>
              </tr>
			<c:forEach items="${posts}" var="post" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
  				<td width="300" align="right">
				  	<c:choose>
					  <c:when test="${false}">
						  &nbsp;
				  	  </c:when>
				  	  <c:otherwise>
			  			<form:radiobutton path="postId" value="${post.id}"/>
  					  </c:otherwise>
				  	</c:choose>
				<a href="post.html?id=${post.id}"><c:choose><c:when test="${fn:length(post.messageSubject)>0}"><c:out value="${post.messageSubject}"></c:out></c:when><c:otherwise>ללא כותרת</c:otherwise></c:choose></a>
				</td>
				<td align="right">
  				<c:out value="${post.formattedSendTime}"/>
  				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td align="right" style="padding: 0px 20px;">
  							אין הודעות 
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose>  	  		
	    <tr>
		<td>
			<button class="grey" onclick="window.location='post.html?action=new'; return false;">הוסף</button>

			<button id="buttonEdit" class="grey" />ערוך</button>
			<button id="buttonDelete" class="grey" />מחק</button>
			<button id="buttonCopy" class="grey"/>שכפל</button>
		</td>
		</tr>
		<tr>
                <td colspan="2" align="center"><br>
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
