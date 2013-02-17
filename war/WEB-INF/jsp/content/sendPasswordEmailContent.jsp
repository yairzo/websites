<%@ page  pageEncoding="UTF-8" %>
<script language="Javascript">

 $(document).ready(function() {
 
 });

</script>
          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת מופ"/>
          	        <c:set var="pageName" value="אישור שליחת דואר"/>
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
                  <td colspan="2" align="center"><h1>אישור שליחת דואר לאיפוס סיסמה</h1>
                  </td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
				<tr>
					<th>
					<c:if test="${fn:length(message)>0}">
					${message}
					</c:if>
					</th>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
                <tr>
					<th colspan="2" align="right">
					<a style="text-decoration: underline;" href="mailto:mop@ard.huji.ac.il">יצירת קשר</a> לפתרון בעיות רישום או כניסה למערכת
					</th>
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
