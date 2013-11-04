<%@ page  pageEncoding="UTF-8" %>

          <td align="right" bgcolor="#787669" height="20">
				<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="מחיקת הגדרות רשימה"/>
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
            <form:form method="POST" action="deleteListInstruction.html" commandName="command">
            	<form:hidden path="id"/>

              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>מחיקת  הגדרה של רשימה</h1>
                  </td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
		<c:choose>
		<c:when test="${!command.master}">

		<tr>
			<th>
				 האם את\ה בטוח\ה שברצונך למחוק את הגדרה של רשימת <c:out value="${command.list.name}"/> המשתמשת בטבלת <c:out value="${command.selectsFrom}"/> ?
			</th>
		</tr>



	    <tr>
		<td>

			<button class="grey" onclick="submit()" />מחק</button>
			<a style="text-decoration: none" href="/list.html?id=<c:out value="${command.listId}"/>"><button class="grey"/>בטל</button></a>
		</td>
		</tr>

		</c:when>
		<c:otherwise>
			<tr>
			<th align="right">
				  הגדרה זו של רשימת <c:out value="${command.list.name}"/> המשתמשת בטבלת <c:out value="${command.selectsFrom}"/> היא הגדרה ראשית של הרשימה ואין אפשרות למחקה
				  <br>
				  אפשר למחוק את הרשימה כולה, או לערוך הגדרה זו
			</th>
		</tr>




	    <tr>
		<td>


			<a style="text-decoration: none" href="/list.html?id=<c:out value="${command.listId}"/>"><button class="grey"/>חזור</button></a>
		</td>
		</tr>

		</c:otherwise>
		</c:choose>





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
