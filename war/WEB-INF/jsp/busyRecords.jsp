<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>



          <td align="right" bgcolor="#787669" height="20"><p class="white">מנהל הרשימות > מחיקת הגדרה של טור</p>
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
                  <td colspan="2" align="center"><h1>מערכת רשימות</h1>
                  </td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
					<c:forEach items="${busyRecords}" var="record">
						<tr>
							<td>
								<c:out value="${record}"/>
							</td>
						</tr>
					</c:forEach>






                  </table>
                </td>
              </tr>
			  <tbody>


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