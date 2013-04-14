<%@ page  pageEncoding="UTF-8" %>
<tr>
		<td>
				<p dir="${lang.dir}" align="${lang.align}">שלום, 			
				<c:if test="${userPersonBean!=null && fn:length(userPersonBean.firstNameHebrew) > 0}">
					<c:out value="${userPersonBean.degreeFullNameHebrew}"/>
				</c:if>
				<c:if test="${userPersonBean==null || fn:length(userPersonBean.firstNameHebrew) == 0}">
					אורח
				</c:if>
				</p>
		</td>
</tr>
<tr>
		<td>
     		<p dir="${lang.dir}" align="${lang.align}">${category.name} >> ${pageTitle}
		</td>
</tr>
