<%@ page  pageEncoding="UTF-8" %>

          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת דיוור"/>
          	        <c:set var="pageName" value="אישור שליחת הודעה"/>
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

              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>אישור שליחת הודעה</h1>
                  </td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
		<tr>
			<th>
				<c:choose>
				<c:when test="${acceptApproval}">
					ההודעה אושרה ותתחיל להשלח לנמענים על פי זמני הקבלה שהוגדרו
				</c:when>
				<c:otherwise>
					האישור לא התקבל - רק יוצר ההודעה רשאי לאשרה
				</c:otherwise>
				</c:choose>
			</th>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center">
				<input type="button" value="סגור חלון" onclick="window.close()">	
			</td>
		</tr>

                  </table>
                </td>
              </tr>



            </table>

            <br>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>
</body>
</html>
