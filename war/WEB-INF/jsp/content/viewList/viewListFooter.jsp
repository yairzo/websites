<%@ page  pageEncoding="UTF-8" %>
<c:choose>
    		          	<c:when test="${!editMode && hasFooter}">
							<tr>
                				<td>
                					${footer}
                				</td>
                			</tr>
               			 </c:when>
               			 <c:when test="${editMode}">
                			<tr>
                				<td>
                					<c:if test="${!hasFooter}">
                						<c:set var="footer" value="הוסף טקסט"/>
                					</c:if>
                				<span class="footerText">${footer}</span>
                				<span class="footerEdit">
	                				<textarea name="footer" class="tinymce" style="width: 100%; height: 100px;">${footer}</textarea>
	                			</span>
                		   	  </td>
                		  </tr>
                		  <tr>
                			<td>
                				<span class="footerEdit">
                				<a href="" class="updateFooter">עדכן</a>
                				<span class="footerEdit">
                			</td>
                		  </tr>
               			</c:when>
                    </c:choose>