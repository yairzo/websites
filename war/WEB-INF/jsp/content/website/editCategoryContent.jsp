<%@ page  pageEncoding="UTF-8" %>

	<!-- <script type="text/javascript">
	  $(document).ready(function() {
		$(".save").click(function() {
			$("#form2").ajaxSubmit();
			$('#categoryEditDiv').hide();
		});
		$(".close").click(function(e) {
			e.preventDefault();
			$('#categoryEditDiv').hide();
		});
	  });
	</script> -->
	
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" dir="rtl">
   <tr>
     <td valign="top" align="center"><br>
     <form:form id="form2" name="form2" method="POST" action="category.html" commandName="command" >
 			<form:hidden path="id"/>
 			<form:hidden path="parentId"/>
 			<form:hidden path="categoryOrder"/>
 			
             <table border="0" align="center" cellpadding="2" cellspacing="0">
 				
                <tr class="form">
                <td colspan="4">
                <table width="700" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
              	<tr class="form">
                	 <td style="border:1px #bca2a2 dotted" align="center"><h1>עריכת קטגוריה</h1></td>
                	</tr>
               	<tr>
                <tr class="form">
					<td style="border:1px #bca2a2 dotted">
						שם: <form:input htmlEscape="true" cssClass="green long500" path="name"/>
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
						בחר/י קישור מתוך רשימת דפי הטקסט שהוזנו במערכת:
         				<select name="textualPage" cssClass="green" >
      						<option value="">בחר/י</option>
       						<c:forEach items="${textualPages}" var="textualPage">
	        					<option htmlEscape="true" value="textualPage.html?id=${textualPage.id}"><c:out escapeXml="false" value="${textualPage.title}"/></option>
       						</c:forEach>
        		        </select>
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
						קישור: <form:input htmlEscape="true" cssClass="green long500" path="url"/>
   					</td>
				</tr>
				
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>

	
 		
		<!--<tr class="form">
			<td colspan="4" align="center">
				<button title="שמירה" class="grey save" > שמירה</button>
				<button title="סגירה" class="grey close" > סגירה</button>
			</td>
		</tr>-->
    </table>
	</form:form>
    </td>
  </tr>

</table>
