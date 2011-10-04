<%@ page  pageEncoding="UTF-8" %>


			<tr>
          	<td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת תמונות"/>
          	        <c:set var="pageName" value="הוספת תמונה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          	</td>
        	</tr>
		</table>
	</td>
</tr>
<tr>
    <td>
      <table width="800" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
		<form:form id="form" name="form" method="POST" action="uploadImage.html" commandName="command" enctype="multipart/form-data">

             <table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
				<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"/>
                <tr class="form">
					<td width="250">${compulsoryFieldSign}
						שם תמונה:
					</td>
					<td width="300">
					 	<input class="green" type="text" name="name">
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="name" /></font>
					</td>
				</tr>
               <tr class="form">
					<td width="250">${compulsoryFieldSign}
						 כותרת תמונה בעברית:
					</td>
					<td width="300">
					 	<input class="green" type="text" name="captionHebrew">
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="captionHebrew"/></font>
					</td>
				</tr>
               <tr class="form">
					<td width="250">${compulsoryFieldSign}
						 כותרת תמונה באנגלית:
					</td>
					<td width="300">
					 	<input class="green" type="text" name="captionEnglish">
					</td>
 				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="captionEnglish"/></font>
					</td>
				</tr>
				<tr>
					<td width="250">${compulsoryFieldSign}
						 צרף קובץ (רוחב 420 גובה 240 גודל עד 300K):
					</td>
					<td width="300">
						<input class="green" type="file" name="image" />
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
 					</td>
				</tr>
		<tr>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="2" style="border: black dotted 1px;">
			<table width="100%">
				<tr>
				<c:forEach items="${images}" var="image" varStatus="varStatus">
  						<td>
  							<span id="img${image.id}">
  								<img id="${image.id}" src="imageViewer?imageId=${image.id}&attachType=bodyImage" width="72" height="72">
  								<input type="checkbox" value="${image.id}"/>
	  							<br/>
  								${image.name}<br>
  								<c:if test="${image.approved==1}">
								מאושר
							   </c:if>
  								
  							</span>
  						</td>
 				</c:forEach>
			  	</tr>
			   	<tr>
			  		<td align="center" colspan="4">
				  		<a href="uploadImage.html?page=${previousPage} ">&lt;</a>
				  		&nbsp;
				  		&nbsp;
				  		<a href="uploadImage.html?page=${nextPage}">&gt;</a>
					</td>
			  	</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<a href="#" class="delete">מחק</a>
			&nbsp;
			&nbsp;
			<a href="#" class="approve">אשר</a>
			&nbsp;
			&nbsp;
			<a href="#" class="cancel">סיים</a>
		</td>
  	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>

	
    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>