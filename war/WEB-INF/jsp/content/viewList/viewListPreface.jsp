<%@ page  pageEncoding="UTF-8" %>
<c:choose>
        			   <c:when test="${!editMode && hasPreface}">
							<tr>
                				<td>
                					${preface}
                				</td>
                			</tr>
               			</c:when>
               			<c:when test="${editMode}">
               				<tr>
               					<td>
               						<c:if test="${!hasPreface}">
               							<c:set var="preface" value="הוסף טקסט"/>
               						</c:if>
		                			<span class="prefaceText">${preface}</span>
           			    			<span class="prefaceEdit">
                						<textarea name="preface" class="tinymce preface" style="width: 100%; height: 100px;">${preface}</textarea>
                					</span>
                				</td>
                			</tr>
                			<tr>
                				<td>
                					<span class="prefaceEdit">
                						<a href="#" class="updatePreface">עדכן</a>
                					</span>
                				</td>
                			</tr>
               			 </c:when>
               		</c:choose>