<%@ page  pageEncoding="UTF-8" %>


			<c:if test="${popup!=1}">
			<tr>
          	<td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת תמונות"/>
          	        <c:set var="pageName" value="הוספת תמונה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          	</td>
        	</tr>
        	</c:if>
		</table>
	</td>
</tr>
<tr>
    <td>
      <table width="800" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
          
        <c:if test="${command.id!=0}">
          
		<form:form id="form" name="form" method="POST" action="uploadImage.html" commandName="command" enctype="multipart/form-data">

   			<form:hidden path="id"/>
   			<input type="hidden" name="popup" value="${popup}"/>
   			<input type="hidden" name="pageId" value="${pageId}"/>
    			
             <table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
				<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"/>
                <tr class="form">
					<td width="250">${compulsoryFieldSign}
						שם תמונה:
					</td>
					<td width="300">
					 	<form:input cssClass="green" path="name" size="46"/>
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="name" /></font>
					</td>
				</tr>
                <tr class="form">
					<td width="250">${compulsoryFieldSign}
						כותרת לקישור:
					</td>
					<td width="300">
					 	<form:input cssClass="green title" path="title" size="46"/>
					</td>
 				</tr>
 				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="title" /></font>
					</td>
				</tr>
               <tr class="form">
					<td width="250">${compulsoryFieldSign}
						 תיאור בעברית:
					</td>
					<td width="300">
					 	<form:textarea cssClass="green" path="captionHebrew" cols="50" rows="2"/>
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="captionHebrew"/></font>
					</td>
				</tr>
               <tr class="form">
					<td width="250">${compulsoryFieldSign}
						 תיאור באנגלית:
					</td>
					<td width="300">
					 	<form:textarea cssClass="green" path="captionEnglish" cols="50" rows="2"/>
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="captionEnglish"/></font>
					</td>
				</tr>
                <tr class="form">
					<td width="250">
						 כתובת אתר המקושר לתמונה:
					</td>
					<td width="300">
					 	<form:input cssClass="green" path="url" size="46"/>
					</td>
 				</tr> 
				
				<tr>
					<td width="250">${compulsoryFieldSign}
						 צרף קובץ (גודל עד 600K):
					</td>
					<td width="300">
						<input class="green" type="file" name="image" size="30"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="image"/></font>
					</td>
				</tr>
				<tr>
					<td>
						<input class="green" type="submit" name="Upload" value="שמור"/>
						<c:if test="${popup==1}">
							<input type="button" class="green" value="ביטול" id="cancelUpload"/>
						</c:if>
 					</td>
 					<td>
						
 					</td>
				</tr>
		<tr>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>
	</table>
	</form:form>
	</c:if>
	<form:form id="editImage" name="editImage" method="POST" action="editImage.html" commandName="command" >

   	<form:hidden path="id"/>
   	<input type="hidden" name="popup" value="${popup}"/>
   	<input type="hidden" name="pageId" value="${pageId}"/>
 	<c:if test="${popup!=1}">
    	<table width="700" border="0" align="center" cellpadding="2" cellspacing="0">

		<tr>
			<td colspan="2" style="border: black dotted 1px;">
			<table width="100%">
				<tr>
				<c:forEach items="${images}" var="image" varStatus="varStatus">
  						<c:choose>
  						<c:when test="${image.approved==1}">
  							<c:set var="borderStyle" value="border: 2px solid green;"/>
  						</c:when>
  						<c:otherwise>
  							<c:set var="borderStyle" value="border: 2px solid grey;"/>
  						</c:otherwise>
  						</c:choose>
  						<td>
  							<span id="img${image.id}">
  								<a href="uploadImage.html?id=${image.id}"><img id="${image.id}" style="${borderStyle}" src="/imageViewer?imageId=${image.id}&attachType=bodyImage" width="72" height="72"></a>
  								<input name="chkboxName" id="chkboxName" type="checkbox" value="${image.id}"/>
	  							<br/>
  								${image.name}&nbsp;
  								<br/>
  							</span>
    						</td>
 				</c:forEach>
			  	</tr>
			   	<tr>
			  		<td align="center" colspan="4">
				  		<a href="/uploadImage.html?id=${command.id}&page=${previousPage} ">&lt;</a>
				  		&nbsp;
				  		&nbsp;
				  		<a href="/uploadImage.html?id=${command.id}&page=${nextPage}">&gt;</a>
					</td>
			  	</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<a href="" class="delete">מחק</a>
			&nbsp;
			&nbsp;
			<authz:authorize ifAnyGranted="ROLE_IMAGE_ADMIN">
			<a href="" class="approve">אשר</a>
			&nbsp;
			&nbsp;
			<a href="" class="disapprove">בטל אישור</a>
			&nbsp;
			&nbsp;
			</authz:authorize>
			<a href="" class="cancel">סיים</a>
		</td>
  	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>
	<tr>
	<td><input type="button" class="green" value="חדש" onclick="window.location='uploadImage.html?action=new';"/>
	</td>
	</tr>


    </table>

    </td>
  </tr>

	</table>
	</c:if>
	</form:form>

</body>
</html>