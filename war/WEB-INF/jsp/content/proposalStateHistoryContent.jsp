<%@ page  pageEncoding="UTF-8" %>
<tr>
          <td align="right" bgcolor="#787669" height="20">
          	<c:set var="applicationName" value="טופס מרובע"/>
          	        <c:set var="pageName" value="היסטוריה של הצעה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          </td>
        </tr>
	</table>

    </td>
  </tr>
  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">

<tr>
	<th>
		תאריך
	</th>
	<th>
		מבצע הפעולה
	</th>
	<th>
		פרטי הפעולה
	</th>
	<th>
		סטאטוס ההצעה
	</th>
</tr>

<c:forEach items="${proposalStates}" var="proposalState">




<tr>
	<td>
		<c:out value="${proposalState.formattedTimeLogged}"/>
	</td>
	<td>
		<c:out value="${proposalState.personBean.degreeFullNameHebrew}"/>
	</td>
	<td>
		<c:out value="${proposalState.actionDetails}"/>
	</td>
	<td>
		<c:out value="${proposalState.stateDetails}"/>
	</td>
</tr>

</c:forEach>



      </table>


    </td>
  </tr>

</table>


</body>
</html>