<%@ page  pageEncoding="UTF-8" %>

          <td align="right" bgcolor="#787669" height="20"><p class="white">איפוס סיסמה</p>
          </td>
        </tr>

      </table>
    </td>
  </tr>

<script language="Javascript">

 $(document).ready(function() {
 	
	if("${isRegistered}"){
	 $("#divPasword").hide();
	 $("#divMessage").show();
	 $("#divMessage").val("<tr><th colspan='2' align='right'>אינך רשומ/ה במערכת</th></tr>");
 	} 
 	else{
	 $("#divPasword").show();
	 $("#divMessage").hide();
 	}

	$("#password").blur(function(){
		var passwordRegex = /^[a-zA-Z0-9]{4,9}$/;
		if(!passwordRegex.test($(this).val())){
			alert("יש להזין סיסמה בעלת 4-9 ספרות או אותיות");
			$(this).val("");
		}
	});
	
	$("#passwordConfirm").blur(function(){
		if($("#passwordConfirm").val()!=$("#password").val()){
			alert("אימות הסיסמה לא זהה לסיסמה");
			$(this).val("");
		}
	});
	
	$("#submit").click(function(e){
		e.preventDefault();
		if($("#passwordConfirm").val()=="" || $("#password").val()=="")
			alert("יש למלא את השדות סיסמה ואימות סיסמה");
		else{
			$("#form").append("<input type=\"hidden\" name=\"subscriptionMd5\" id=\"subscriptionMd5\" value=\"${subscriptionMd5}\"/>");
			$('#form').ajaxSubmit();
		}
	});	 
 });

</script>

  <tr>
    <td>
 <form:form id="form" name="form" method="POST" commandName="command" action="resetPassword.html">      
 
 <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
			  <table width="500" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>הרשות למחקר ופיתוח - איפוס סיסמה</h1>
                  </td>
                </tr>
                <div id="passwordDiv">
                <tr>
					<th align="right">
						נא להקליד סיסמה חדשה:
					</th>
					<th align="right">
					 	<input cssClass="green medium200" type="password" id="password" name="password">
					</th>
				</tr>
				<tr>
					<th align="right">
						 (יש להקליד שוב את הסיסמה)אימות סיסמה:
					</th>
					<th align="right">
						<input cssClass="green medium200" type="password" id="passwordConfirm" name="passwordConfirm">
					</th>
				</tr>
                <tr>
					<th colspan="2">
						<button class="grey" id="submit" >עדכן סיסמה</button>
					</th>
				</tr>
				</div>
                <div id="messageDiv">
                 <tr>
					<th colspan="2" align="right">
						
					</th>
				</tr>
 				</div>
                <tr>
					<th colspan="2" align="right">
					<a style="text-decoration: underline;" href="mailto:mop@ard.huji.ac.il">יצירת קשר</a> לפתרון בעיות רישום או כניסה למערכת
					</th>
				</tr>
			</table>
         </td>
      </tr>
	</table>
</form:form>  
</td>
 </tr>
</table>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>

</body>
</html>








