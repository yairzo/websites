<%@ page  pageEncoding="UTF-8" %>

         <tr>
          <td align="right" bgcolor="#787669" height="20">
          	<c:set var="applicationName" value="מערכת רשימות"/>
          	<c:set var="pageName" value="סידור רשימה"/>
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
          <td valign="top" align="center"><br>
            <form:form method="POST" action="listOrder.html" commandName="command">
				<form:hidden path="id"/>


              <table width="250" border="0" align="center" cellpadding="2" cellspacing="0">
              <c:choose>
              <c:when test="${fn:length(attribBeans)>0}">
              <tr>
                  <td colspan="2" align="center"><h1>צפייה וסידור רשימת <c:out value="${command.list.name}"/></h1>
                  </td>
                 </tr>
                 <tr>
                 <th>
                 	שם
                 </th>
                 <th>
                   מיקום ברשימה
                  </th>
                 </tr>

                <c:forEach items="${attribBeans}" var="attrib" varStatus="varStatus">
					<form:hidden path="attribs[${varStatus.index}].id"/>

					<tr>
						<td width="150"><a style="text-decoration: none" href="/person.html?id=<c:out value="${attrib.person.id}"/>"><c:out value="${attrib.person.degreeHebrew}"/>&nbsp;<c:out value="${attrib.person.firstNameHebrew}"/>&nbsp;<c:out value="${attrib.person.lastNameHebrew}"/></a></td>
				    	<td width="100" dir="ltr" align="center"><form:input cssClass="green" cssStyle="width: 40px" path="attribs[${varStatus.index}].placeInList"/></td>
					</tr>
				</c:forEach>

	              <tr>
	              	<td colspan="2">
	              		<button class="grey" onclick="submit()">שמור</button>
						<button class="grey" onclick="document.location='list.html?id=${command.id}';return false;">סיים</button>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<tr>
					<th colspan="2">
						הרשימה ריקה
					</th>
					</tr>

					<tr>
	              	<td colspan="2">
						<a style="text-decoration: none" href="/list.html?id=<c:out value="${command.id}"/>"><button class="grey" onclick="submit()">חזור</button></a>
					</td>
				</tr>
				</c:otherwise>
				</c:choose>
	              </table>


      </form:form>
    </td>
  </tr>

</table>


</body>
</html>









