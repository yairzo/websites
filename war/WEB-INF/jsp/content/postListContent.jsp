<%@ page  pageEncoding="UTF-8" %>

<script language="Javascript">



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

 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>


 $("#searchPhrase").focus();

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

              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת הודעות</h1>
                  </td>
                </tr>
                <tr>
                  <td width="201" align="center" valign="center"><form:input id="searchPhrase" cssClass="green" path="searchCreteria.searchPhrase"/>
                  </td>

                  <td width="173" align="right">

                     <button id="buttonSearch" class="grey">חפש</button>


                  </td>
                </tr>

                <tr>
                  <td colspan="2" align="center">חפש לפי:&nbsp;&nbsp;
                  <form:radiobutton id="searchField" path="searchCreteria.searchField" value="messageSubject"  /> כותרת ההודעה
					<form:radiobutton id="searchField" path="searchCreteria.searchField" value="sendTime" /> זמן השליחה
                  </td>
                </tr>
                <tr>
                  <td colspan="2"><img src="image/hr.gif" width="380" height="10"></td>
                </tr>
              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">




              <tr>

                <th align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="posts.html?orderBy=isVerified,messageSubject&page=<c:out value="${listView.page}"/>&searchField=<c:out value="${command.searchCreteria.searchField}"/>&searchPhrase=<c:out value="${command.searchCreteria.searchPhrase}"/>"><img src="image/downArrow.gif" border="0"></a>&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="posts.html?orderBy=isVerified,sendTime&page=<c:out value="${listView.page}"/>&searchField=<c:out value="${command.searchCreteria.searchField}"/>&searchPhrase=<c:out value="${command.searchCreteria.searchPhrase}"/>"><img src="image/downArrow.gif" border="0"></a></th>

              </tr>


			<c:forEach items="${posts}" var="post" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
  				<td align="right">
				  	<c:choose>
					  <c:when test="${false}">
						  &nbsp;
				  	  </c:when>
				  	  <c:otherwise>
			  			<form:radiobutton path="postId" value="${post.id}"/>
  					  </c:otherwise>
				  	</c:choose>

  				<c:out value="${post.messageSubject}"/>

  				<c:out value="${post.sendTime}"/>
  			</td>
  	  	</tr>
  	  	</tbody>
	   </c:forEach>

	    <tr>
		<td>
			<button class="grey" onclick="window.location='post.html'; return false;">הוסף</button>

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
