<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>



          <td align="right" bgcolor="#787669" height="20"><p class="white"> מערכת טופס מרובע > זיהוי משתמש</p>
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
                  <td colspan="2" align="center"><h1><fmt:message key="iw_IL.websiteInterface.websiteName"/> - אנא הזדהו לכניסה</h1>
                  </td>
                </tr>

              </table>

				<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
		<form method="POST" action="j_acegi_security_check">
		<tr>
			<th align="right">
				מספר זהות:
			</th>
			<th align="right">
				 	<input class="green" type="text" name="j_username">
			</th>

		</tr>
		<tr>
			<th align="right">
				קוד סודי:
			</th>
			<th align="right">
				<input class="green" type="password" name="j_password">
			</th>
		</tr>
		<tr>
			<th colspan="2">
					<button class="grey" onclick="submit()" >כניסה</button>
				</th>
		</tr>


		</form>





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








