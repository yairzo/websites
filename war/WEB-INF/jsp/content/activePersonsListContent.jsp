<%@ page  pageEncoding="UTF-8" %>
<script>

$(document).ready(function() {
	$("button.deleteLock").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"deleteLock\"/>");
		var lockId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"lockId\" value=\""+lockId +"\"/>");
   		$("#form").submit();
    	return true;
    });
});
</script>


          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת משולבת"/>
          	        <c:set var="pageName" value="רשימת משתמשים פעילים"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>

          </td>

        </tr>

      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="active.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת משתמשים פעילים</h1>
                  </td>
                </tr>
              </table>

				<table width="800" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
     
   			<c:choose>
    		<c:when test="${fn:length(activePersons) > 0}">
			<c:forEach items="${activePersons}" var="person" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right">
				<c:out value="${person.fullName}"></c:out>
  				</td>
  				<td align="right">
				<c:out value="${person.formattedLastAction}"></c:out>
				</td>
				<td align="right">
				<table>
				<c:forEach items="${person.lockedPages}" var="lockedPage" varStatus="varStatus">
				<tr>
				<td>${lockedPage.id}</td>
				<td>${lockedPage.arguments}</td>
				<td>נעול עד: ${lockedPage.expiryTimeFormatted}</td>
				<td><button class="deleteLock" id="${lockedPage.id}~${lockedPage.subject}~${lockedPage.arguments}">שחרר נעילה</button></td>
				</tr>
				</c:forEach>
				</table>
				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין משתמשים פעילים כרגע 
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose>  	  		



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
