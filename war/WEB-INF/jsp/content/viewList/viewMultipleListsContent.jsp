<%@ page  pageEncoding="UTF-8" %>

<table align="center">
<tr>
       <td align="center">
       		<b><font size="+1"><c:out escapeXml="false" value="${list.displayName}"/></font><b>
       </td>
    </tr>
</table>



<c:forEach items="${list.sublistsBeans}" var="listBean" varStatus="varStatusLists">

	<table width="100%" align="center" cellpadding="0" dir="rtl">
    	<tr>
           	<td>
           		<%@ include file="/WEB-INF/jsp/content/viewList/viewSingleListContent.jsp" %>
           	</td>
        </tr>
    </table>

</c:forEach>
