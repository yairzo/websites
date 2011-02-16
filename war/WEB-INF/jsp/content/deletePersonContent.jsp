<%@ page  pageEncoding="UTF-8" %>

         <tr>
          <td align="right" bgcolor="#787669" height="20">
				<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
      	        <c:set var="pageName" value="הסרת אישיות מהמאגר"/>
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
            <form:form name="form" action="deletePerson.html" method="POST" commandName="command">
				<form:hidden path="id"/>

              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

                <tr>
                  <td colspan="2" align="center"><h1>הסרת איש מהמאגר</h1>
                  </td>
                </tr>
                <tr>
                  <th colspan="2" align="center"> האם את\ה בטוח\ה שברצונך להסיר את <c:out value="${command.firstNameHebrew}"/> <c:out value="${command.lastNameHebrew}"/> מהמאגר?<br>
                  	הסרה מהמאגר פירושה הסרה מכל הרשימות ללא אפשרות שחזור !
                  </th>
                </tr>
        		 <td colspan="2" align="center">
                   	<table border="0" cellpadding="0" cellspacing="0">
		                <tr>
           			        <td ><button class="grey" onclick="submit();">הסר מהמאגר</button></td>
		                	<td><a style="text-decoration: none" href="persons.html"><button class="grey">ביטול</button></a>
		                </tr>
		            </table>
           		</td>
                </tr>
          </td>
        </tr>
      </table>

      </form:form>
    </td>
  </tr>

</table>


</body>
</html>