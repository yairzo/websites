<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function() {
	var dlg =$("#clashDialog").dialog({
	    autoOpen: false,
	    show: 'fade',
	    hide: 'fade',
	    open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
	});
	dlg.parent().appendTo($("#form"));
	$("#textualPage").change(function(){
		var fieldName="textualPageCategory" + $("#textualPage").val();
		var topCategory = "${topCategory}";
		var textualPageCategory = $("#"+fieldName).val();
		if(topCategory!=textualPageCategory && textualPageCategory>0){
			var text='<fmt:message key="iw_IL.website.categoryclash"/>';
			text += "<br> <a href=\"editTextualPage.html?id=" +$("#textualPage").val()+"\" target=\"_blank\">לצפייה ו/או עריכת העמוד</a>";
			dlg.dialog('option', 'buttons', {
	            "סגירה" : function() {
	            	$(this).dialog("close");
	            	return false;
	             }
	    	});			
			//dlg.dialog({ modal: true });
			dlg.dialog({ height: 200 });
			dlg.dialog({ width: 700 });
			dlg.html(text).dialog("open");
			//alert('<fmt:message key="${lang.localeId}.website.categoryclash"/>');
		}
	});
});
</script>

	
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" dir="rtl">
   <tr>
     <td valign="top" align="center"><br>
     <form:form id="form2" name="form2" method="POST" action="category.html" commandName="command" >
 			<div id="clashDialog" title="קטגוריות" style="display:none" dir="rtl"><p>text put here</p></div>
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
         				<select name="textualPage" id="textualPage" cssClass="green" >
      						<option value="">בחר/י</option>
       						<c:forEach items="${textualPages}" var="textualPage">
	        					<option htmlEscape="true" value="${textualPage.id}"><c:out escapeXml="false" value="${textualPage.title}"/></option>
        					</c:forEach>
        		        </select>
       					<c:forEach items="${textualPages}" var="textualPage">
      						<input type="hidden" name="textualPageCategory${textualPage.id}" id="textualPageCategory${textualPage.id}" value="${textualPage.categoryId}"/>
        				</c:forEach>
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

	
 		
    </table>
	</form:form>
    </td>
  </tr>

</table>
