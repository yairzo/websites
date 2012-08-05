<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>

          <td align="right" bgcolor="#787669" height="20"><p class="white">זיהוי משתמש</p>
          </td>
        </tr>

      </table>
    </td>
  </tr>

<script language="Javascript">

 $(document).ready(function() {

  $('input').keypress(function(e){
      if(e.which == 13){
       $('form').submit();
       }
      });
 });

</script>

  <tr>
    <td>
       <form method="POST" action="j_acegi_security_check">
       	<input type="hidden" name="mts" value="${moduleToSubscribe}"/>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
			  <table width="500" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>${title}</h1>
                  </td>
                </tr>
                <tr>
					<th align="right">
						שם משתמש:
					</th>
					<th align="right">
					 	<input class="green medium200" type="text" name="j_username">
					</th>
				</tr>
				<tr>
                  <td colspan="2">
                      ${usernameInstructions}
                  </td>
                </tr>
				<tr>
					<th align="right">
						סיסמה:
					</th>
					<th align="right">
						<input class="green medium200" type="password" name="j_password">
					</th>
				</tr>
				<tr>
                  <td colspan="2">
                      ${passwordInstructions}
                  </td>
                </tr>
                <tr>
					<th colspan="2">
						<button class="grey" onclick="submit()" >כניסה</button>
					</th>
				</tr>
                <tr>
					<th colspan="2" align="right">
						${generalLoginInstructions}
					</th>
				</tr>
                <tr>
					<th colspan="2" align="right" style="color:red">
						${loginError}
					</th>
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
	</form>
  </td>
 </tr>
</table>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>

</body>
</html>








