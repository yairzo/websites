<%@ page  pageEncoding="UTF-8" %>
<script>

$(document).ready(function() {
	$("button.save").click(function(){
		if($("#galleryName").val()=='' || $("#galleryUrl").val()==''){
			alert("יש להזין שם וכותרת לקישור");
			return false;
		}
		else{
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"save\"/>");
   			$("#form").submit();
    		return true;
		}
    });
	$("button.cancelSave").click(function(){
		$("#addGallery").hide();
		$("#addButton").show();
		
    	return false;
    });
	
	$("button.update").click(function(e){
		e.preventDefault();
		var id=$(this).attr("id");
		if($("#name"+id).val()=='' || $("#url"+id).val()==''){
			alert("יש להזין שם וכותרת לקישור");
			return false;
		}
		else{
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"update\"/>");
			$("#form").append("<input type=\"hidden\" name=\"category\" value=\""+id+"\"/>");
			$("#form").submit();
		}    
	});

});


function deleteGallery(category){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
	$("#form").append("<input type=\"hidden\" name=\"category\" value=\""+category+"\"/>");
	$("#form").submit();
	
}

</script>

          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="ניהול אתר האוניברסיטה"/>
          	        <c:set var="pageName" value="רשימת דפי טקסט"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="galleryList.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

 
			<table width="650" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
            <tr>
                  <td colspan="2" align="center"><h1>רשימת גלריות</h1>
                  </td>
             </tr>				
            <tr>
                  <td>&nbsp;</td><td>&nbsp;</td>
            </tr>				
    		<c:choose>
    		<c:when test="${fn:length(galleries) > 0}">
			<c:forEach items="${galleries}" var="gallery" varStatus="varStatus">
             <tbody>
   				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td width="150" align="right">
				<a href="/gallery/${gallery.url}"><c:out value="${gallery.text}"></c:out></a>
 				</td>
 				<td align="right">
 				<span id="actions${gallery.id}">
 				<a onclick="document.getElementById('changeGallery${gallery.id}').style.display='block';document.getElementById('actions${gallery.id}').style.display='none';return false;">שנה שם</a>
				&nbsp;
 				<a onclick="deleteGallery(${gallery.id});">מחק</a>
 				</span>
  				<span id="changeGallery${gallery.id}" style="display:none;">
	    				שם:<input type="text" name="name${gallery.id}" id="name${gallery.id}" value="${gallery.text}"/>
	    				כותרת לקישור:<input type="text" name="url${gallery.id}" id="url${gallery.id}" value="${gallery.url}"/>
	    			<button class="grey update" id="${gallery.id}">שמור</button>
	    			<button class="grey" onclick="document.getElementById('changeGallery${gallery.id}').style.display='none';document.getElementById('actions${gallery.id}').style.display='block';return false;">בטל</button>
				</span>
  				
  				</td>
    	  		</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין גלריות  
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose> 

      </table>
      <table width="650" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
      		<tbody>
      		<tr>
	    	<td style="text-align:right;">
			<button id="addButton" class="grey" onclick="document.getElementById('addGallery').style.display='block';document.getElementById('addButton').style.display='none';return false;">הוסף</button>
	    	<span id="addGallery" style="display:none;">
	    		שם הגלריה:<input type="text" name="galleryName" id="galleryName"/>
	    		סוג הגלריה:
	    		<select name="galleryType" class="green">
 				   	<option value="0">תמונות</option>
 				   	<option value="1">דפי טקסט</option>
 				</select>			  
	    		שם לקישור:<input type="text" name="galleryUrl" id="galleryUrl"/>
	    		<button class="grey save">שמור</button>
	    		<button class="grey cancelSave">בטל</button>
			</span>
			</td>

			</tr>
			</tbody>
      </table>
      </form:form>
    </td>
  </tr>
</table>
</body>
</html>
