<%@ page  pageEncoding="UTF-8" %>

	<script type="text/javascript">
	  $(document).ready(function() {
		  
		var dlg =$("#genericDialog").dialog({
		    autoOpen: false,
		    show: 'fade',
		    hide: 'fade',
		    open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
		});
		dlg.parent().appendTo($("#form"));
		  
		$(".edit").click(function() {
				var categoryId=$(this).attr("id");	   		
				dlg.dialog('option', 'buttons', {
		        	"ביטול" : function() {
		        		dlg.dialog("close");
		        	},
	            	"שמירה" : function() {
	    				$("#form2").submit();
	             	}
	    		});
				dlg.dialog({ height: 400 });
				dlg.dialog({ width: 900 });
				dlg.dialog("option", "position", "center");
	  			$.get('category.html?id='+categoryId, function(data) {
	  				dlg.html(data).dialog("open");
	  			});
		});
		
		$('.moveUp').click(function(){
			var categoryId = this.id;
			$('form').append('<input type="hidden" id="moveUp" name="moveUp" value="'+categoryId+'"/>');
			$('form').submit();
		});
		
		$('.moveDown').click(function(){
			var categoryId = this.id;
			$("#moveUp").remove();
			$('form').append('<input type="hidden" id="moveDown" name="moveDown" value="'+categoryId+'"/>');
			$('form').submit();
		});
		$('.add').click(function(){
			var categoryId = this.id;
			$('#form').append('<input type="hidden" id="add" name="add" value="'+categoryId+'"/>');
			$('#form').submit();
		});
		$('.delete').click(function(){
			var categoryId = this.id;
	   		$("#genericDialog").dialog('option', 'buttons', {
	            "ביטול" : function() {
	            	$(this).dialog("close");
	            	return false;
	             },
	            "המשך" : function() {
					$('form').append('<input type="hidden" id="delete" name="delete" value="'+categoryId+'"/>');
					$('form').submit();
	             }
	    		});
		   		$("#genericDialog").dialog({ height: 200 });
		   		$("#genericDialog").dialog({ width: 400 });
	  			var text ="הנך עומד/ת למחוק את הקטגוריה. האם להמשיך?";
	  			$('#genericDialog').dialog({position: { my: 'top', at: 'top', of: $('.delete')} });
	  		    $("#genericDialog").html(text).dialog("open");
		});
	 });
	</script>
          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="ניהול אתר האוניברסיטה"/>
          	        <c:set var="pageName" value="רשימת קטגוריות"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
				<div id="genericDialog" title="קטגוריות" style="display:none" dir="rtl"><p>text put here</p></div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="1120" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="categories.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
	

              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
   					<td class="container" style="width: 68%; vertical-align: top;text-align: center;">
     					<table style="width: 100%;">
               	 		<tr>
                 		 <td colspan="2" align="center"><h1>קטגוריות</h1>	
                 		 </td>
                		</tr>
               	 		<tr>
               	 		<td>
   						<ul>
 						<li>
						<a href="${rootCategoryHeb.url}">${rootCategoryHeb.name}</a>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
						&nbsp;<img id="${rootCategoryHeb.id}" class="add" src="/image/icon_plus.gif" alt="add"/>
						</authz:authorize>
     					<c:choose>
    					<c:when test="${fn:length(hebCategories) > 0}">
    					<ul>
						<c:forEach items="${hebCategories}" var="hebCategory" varStatus="varStatus">
 							<li>
 							<a href="${hebCategory.url}">${hebCategory.name}</a>
 							<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 								&nbsp;<img src="/image/icon_edit.gif" class="edit" id="${hebCategory.id}" border="0"/>&nbsp;<img id="${hebCategory.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${hebCategory.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${hebCategory.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${hebCategory.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
      						</authz:authorize>
      						<c:choose>
    						<c:when test="${fn:length(hebCategory.subCategories) > 0}">
							<ul>
							<c:forEach items="${hebCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
  								<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 									&nbsp;<img src="/image/icon_edit.gif" class="edit" id="${category.id}" border="0"/>&nbsp;<img id="${category.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${category.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${category.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${category.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
      							</authz:authorize>
     							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul>
								<c:forEach items="${category.subCategories}" var="subCategory" varStatus="varStatus">
 									<li>
 									<a href="${subCategory.url}">${subCategory.name}</a>
  									<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 									 &nbsp;<img src="/image/icon_edit.gif" class="edit" id="${subCategory.id}" border="0"/>&nbsp;<img id="${subCategory.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${subCategory.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${subCategory.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${subCategory.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
 									</authz:authorize>
 									</li>
	   							</c:forEach>
	   							</ul>
	  							</c:when>
  								</c:choose> 
	   							</li>
 	   						</c:forEach>
 	   						</ul>
 	  						</c:when>
  							</c:choose> 
	   					</c:forEach>
	   					</ul>
 	  					</c:when>
  	  					<c:otherwise>
   	  			  			<tr style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין קטגוריות 
   							</td>
  							</tr>
  						</c:otherwise>
  						</c:choose> 
  						</li>
  						</ul>
  						</td>
  						</tr>
              	 		<tr>
               	 		<td>
   						<ul>
 						<li>
						<a href="${rootCategoryEng.url}">${rootCategoryEng.name}</a>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
						&nbsp;<img id="${rootCategoryEng.id}" class="add" src="/image/icon_plus.gif" alt="add"/>
						</authz:authorize>
     					<c:choose>
    					<c:when test="${fn:length(engCategories) > 0}">
    					<ul>
						<c:forEach items="${engCategories}" var="engCategory" varStatus="varStatus">
 							<li>
 							<a href="${engCategory.url}">${engCategory.name}</a>
 							<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 							&nbsp;<img src="/image/icon_edit.gif" class="edit" id="${engCategory.id}" border="0"/>&nbsp;<img id="${engCategory.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${engCategory.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${engCategory.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${engCategory.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
      						</authz:authorize>
      						<c:choose>
    						<c:when test="${fn:length(engCategory.subCategories) > 0}">
							<ul>
							<c:forEach items="${engCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
 								<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 								&nbsp;<img src="/image/icon_edit.gif" class="edit" id="${category.id}" border="0"/>&nbsp;<img id="${category.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${category.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${category.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${category.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
     							</authz:authorize>
     							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul>
								<c:forEach items="${category.subCategories}" var="subCategory" varStatus="varStatus">
 									<li>
 									<a href="${subCategory.url}">${subCategory.name}</a> 
 									<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_EDIT">
 									&nbsp;<img src="/image/icon_edit.gif" class="edit" id="${subCategory.id}" border="0"/>&nbsp;<img id="${subCategory.id}" class="moveDown" src="/image/icon_down.gif" alt="move down"/>&nbsp;<img id="${subCategory.id}" class="moveUp" src="/image/icon_up.gif" alt="move up"/>&nbsp;<img id="${subCategory.id}" class="add" src="/image/icon_plus.gif" alt="add"/>&nbsp;<img id="${subCategory.id}" class="delete" src="/image/icon_delete.gif" alt="delete"/>
 									</authz:authorize>
 									</li>
	   							</c:forEach>
	   							</ul>
	  							</c:when>
  								</c:choose> 
	   							</li>
 	   						</c:forEach>
 	   						</ul>
 	  						</c:when>
  							</c:choose> 
	   					</c:forEach>
	   					</ul>
 	  					</c:when>
  	  					<c:otherwise>
   	  			  			<tr style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין קטגוריות 
   							</td>
  							</tr>
  						</c:otherwise>
  						</c:choose> 
  						</li>
  						</ul>
  						</td>
  						</tr>
                  		</table>
                	</td>
              </tr>
	          </table>
      </form:form>

            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
