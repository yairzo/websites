<%@ page  pageEncoding="UTF-8" %>

<table width="100%" border="0" dir="rtl">
	<tr>
    	<td align="right">
			<p class="white">${applicationName} > ${pageName}</p>
		</td>
		<c:if test="${enablePrint ==1}">
			<td>
				<a style="text-decoration: none; color: white; font-size:9pt;" href="${self}&p=1">גרסת הדפסה</a>
			</td>
		</c:if>
		<c:if test="${editModeAuthorized}">
			<td>
				<c:choose>
				<c:when test="${editMode}">
					<a style="text-decoration: none; color: white; font-size:9pt;" href="${self}&em=0">הפסק עריכה</a>
				</c:when>
				<c:otherwise>
					<a style="text-decoration: none; color: white; font-size:9pt;" href="${self}&em=1">התחל עריכה</a>
				</c:otherwise>
				</c:choose>
			</td>
		</c:if>
		<c:if test="${aItemsList ==1}">
			<td>
				<a style="text-decoration: none; color: white; font-size:9pt;" href="${self}?iip=${iip + 5}">+</a>
				<span style="text-decoration: none; color: white; font-size:9pt;"/> / </span>
				<a style="text-decoration: none; color: white; font-size:9pt;" href="${self}?iip=${iip - 5}">-</a>
			</td>
		</c:if>
		<td>
			<c:if test="${userPersonBean!=null && fn:length(userPersonBean.firstNameHebrew) > 0}">
				<p class="white">משתמש: <c:out value="${userPersonBean.degreeFullNameHebrew}"/></p>
			</c:if>
		</td>
		<c:if test="${titleCode==2}">
		<td>
			<a href="mailto:conferences_committee@ard.huji.ac.il"><img src="image/mail_1.png" align="top" title="לחצ/י לקבלת עזרה" width="20" height="20" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
		</td>
		</c:if>
		<td>
			<a style="text-decoration: none; color: white; font-size:9pt;"  href="welcome.html">תפריט ראשי</a>
		</td>
		<td align="left">
			<a style="text-decoration: none; color: white; font-size:9pt;"  href="j_acegi_logout">צא</a>
		</td>
	</tr>
</table>