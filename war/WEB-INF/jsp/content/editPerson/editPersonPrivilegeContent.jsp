<%@ page  pageEncoding="UTF-8" %>
<script>
 function addPriveleges(){
		$("#form").append("<input type=\"hidden\" name=\"vals\" value=\""+$('select#allPrivilegesSelect').val() +"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"insert\"/>");
			$("#form").submit();
		return true;
 }
 
	$(document).ready(function() {

		$("#genericDialog").dialog({
		     autoOpen: false,
		     show: 'fade',
		     hide: 'fade',
		     modal: true,
		     width: 400,
		     open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
		});
		 $(".ui-dialog-titlebar").hide();
		$('.select').click(function(){
				if($('#password').val()!='' && "${hasPassword}"){// might be changing previous password
			    	$("#genericDialog").dialog('option', 'buttons', {
			            "ביטול" : function() {
			                $(this).dialog("close");
			                return false;
			               },
			            "אישור" : function() {
			                $(this).dialog("close");
			                addPriveleges();
			               }
			            });
					$("#genericDialog").dialog({ modal: false });
		    	   	$("#genericDialog").dialog("option", "position", "center");
		    	    $("#genericDialog").text("יש לשים לב כי אישור פעולה זו תדרוס סיסמה קודמת שקימת במערכת").dialog("open");
					return false;
				}
				else if($('#password').val()=='' && !"${hasPassword}"){
					$("#genericDialog").dialog("option", "buttons", {"סגור" : function() {  $(this).dialog("close");} });
					$("#genericDialog").dialog({ modal: false });
		    	   	$("#genericDialog").dialog("option", "position", "center");
		    	    $("#genericDialog").text("יש להזין סיסמה").dialog("open");
					return false;
				}
				else
					addPriveleges();
  		});
		
		$('.unselect').click(function(){
			$("#form").append("<input type=\"hidden\" name=\"vals\" value=\""+$('select#personPrivilegesSelect').val() +"\"/>");
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
	   		$("#form").submit();
	    	return true;
    	});
		$('#save').click(function(){
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"save\"/>");
	   		$("#form").submit();
	    	return true;
		});
		
  });
	

</script>




<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת דיוור"/>
         <c:set var="pageName" value="בחירת הרשאות"/>
       	 <%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
	</td>
</tr>
</table>

</td>
</tr>
<tr>
	<td>
	<form:form id="form" name="form" method="POST" action="personPrivilege.html" commandName="command">
	<table width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468"  dir="rtl">
		<tr>
			<td valign="top" align="center"><br>

				<form:hidden path="id" />
				<table width="700" border="0" align="center" cellpadding="2">
					<tr>
						<td colspan="2" align="center"><h1> הרשאות משתמש - ${personName} </h1></td>
					</tr>
				</table>
				
				<table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
				
				<div id="genericDialog" title="" style="display:none" dir="rtl"><p>text put here</p></div>
			
				<tr class="form">
					<td align="right" >
						סיסמה:
						<input class="green" name="password" id="password" value="">
					</td>
					<td align="right" >
						פעיל:
						<select name="enabled" class="green" >
      						<option value="0" <c:if test="${enabled==0}" > selected </c:if>>לא</option>
      						<option value="1" <c:if test="${enabled==1}" > selected </c:if>>כן</option>
        		        	</select>
						
					</td>
					<td align="right" >
					<button class="grey" id="save">שמור </button>
					</td>
						</tr>				
				
				<tr>
						<td align="center">רשימת ההרשאות<br>
						<select id="allPrivilegesSelect" cssClass="green" MULTIPLE SIZE="5" STYLE="width:300;">
       					<c:forEach items="${allPrivileges}" var="privilege">
       					
	        				<option htmlEscape="true" value="${privilege.privilege}" title="${privilege.privilege}"><fmt:message key="iw_IL.eqfSystem.editPersonPrivilege.${privilege.privilege}"/></option>
       					</c:forEach>
       		        	</select>
     		            </td> 
     		            
						<td align="center">
						<button class="grey select" ><span class="ui-icon ui-icon-triangle-1-w"></span></button><br/><br/>
						<button class="grey unselect" > <span class="ui-icon ui-icon-triangle-1-e"></span> </button>
     		            </td>
     		            
						 <td align="center">הרשאות משתמש<br>
        				<select id="personPrivilegesSelect" cssClass="green" MULTIPLE SIZE="5" STYLE="width:300;" >
       					<c:forEach items="${personPrivileges}" var="personPrivilege">
	        				<option htmlEscape="true" value="${personPrivilege.id}" title="${personPrivilege.privilege}"><fmt:message key="iw_IL.eqfSystem.editPersonPrivilege.${personPrivilege.privilege}"/> </option>
       					</c:forEach>
       		        	</select>
      		            </td> 
   
           		</tr>
           		<tr><td>&nbsp;</td>
           		</tr>
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
