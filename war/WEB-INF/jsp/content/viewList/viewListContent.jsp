<%@ page  pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/jsp/content/viewList/viewListScripts.jsp" %>

<c:choose>
	<c:when test="${aCompoundView}">
   	        <c:set var="hasPreface" value="${list.hasPreface}"/>
   	        <c:set var="preface" value="${list.preface}"/>
   	        <c:set var="hasFooter" value="${list.hasFooter}"/>
   	        <c:set var="footer" value="${list.footer}"/>
   </c:when>
   <c:otherwise>
        	<c:set var="hasPreface" value="${listBean.hasPreface}"/>
   	        <c:set var="preface" value="${listBean.preface}"/>
   	        <c:set var="hasFooter" value="${listBean.hasFooter}"/>
   	        <c:set var="footer" value="${listBean.footer}"/>
   </c:otherwise>
</c:choose>

<c:if test="${!iframeView && !print}">
	<tr>
		<td align="right" bgcolor="#787669">
   			<c:set var="applicationName" value="מערכת רשימות"/>
   	        <c:set var="pageName" value="צפייה ברשימה"/>
   	        <c:set var="enablePrint" value="1"/>
   	        <c:set var="urlAdd" value=""/>
        <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
        	<c:set var="urlAdd" value="Admin"/>
        </authz:authorize>
        <c:choose>
   	        <c:when test="${aCompoundView}">
       	        <c:set var="self" value="viewList${urlAdd}.html?id=${list.id}"/>
   	        </c:when>
   	        <c:otherwise>
   	        	<c:set var="self" value="viewList${urlAdd}.html?id=${listBean.id}"/>
   	        </c:otherwise>
        </c:choose>
 	   	    <c:set var="editMode" value="${editMode}"/>
		    <c:set var="editModeAuthorized" value="${editModeAuthorized}"/>
          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
      </td>
   </tr>
   </table>
 </td>
</tr>

</c:if>

<tr>
 <td>
 	<c:choose>
    	<c:when test="${print}">
      		<c:set var="totalWidth" value="600"/>
      	</c:when>
      	<c:otherwise>
      		<c:set var="totalWidth" value="1200"/>
      	</c:otherwise>
     </c:choose>


		<form:form id="form" name="form" method="POST" action="viewListAdmin.html" commandName="command">
			<form:hidden path="id"/>

      	<table width="${totalWidth}" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
      		<tr>
         		  <td valign="top" align="center">
		<c:choose>
	        <c:when test="${!aCompoundView}">
		              <table border="0" width="100%" align="center" cellpadding="0" dir="rtl">
		            		<%@ include file="/WEB-INF/jsp/content/viewList/viewListPreface.jsp" %>
			              	<tr>
				               	<td>
                					<%@ include file="/WEB-INF/jsp/content/viewList/viewSingleListContent.jsp" %>
                				</td>
                			</tr>
                			<%@ include file="/WEB-INF/jsp/content/viewList/viewListFooter.jsp" %>
                      </table>
           </c:when>
           <c:otherwise>
		              <table width="100%" align="center" cellpadding="0" dir="rtl">
			              	<%@ include file="/WEB-INF/jsp/content/viewList/viewListPreface.jsp" %>
			              	<tr>
				               	<td>
               				 		<%@ include file="/WEB-INF/jsp/content/viewList/viewMultipleListsContent.jsp" %>
                				</td>
                			</tr>
                			<%@ include file="/WEB-INF/jsp/content/viewList/viewListFooter.jsp" %>
	                	</table>
	           </c:otherwise>
         </c:choose>
				</td>
			</tr>
			<c:if test="${iframeView}">
			<tr>
				<td>
					&nbsp;
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
		    	</td>
		    </tr>
		   </c:if>

	<c:if test="${!iframeView && !print && (listBean.sendMailToListEnabled || list.sendMailToListEnabled)}">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>

		<c:choose>
		<c:when test="${aCompoundView}">
		<tr>
			<td colspan="10" align="right">
				<a href="sendMessage.html?listId=${list.id}&cp=viewList.html&cpoi=${list.id}"><fmt:message key="${lang.localeId}.eqfSystem.viewList.sendMailSystem"/></a>
				&nbsp;
				<a href="mailto:${list.emails}"><fmt:message key="${lang.localeId}.eqfSystem.viewList.sendMailClient"/></a>
			</td>
		</tr>
		</c:when>
		<c:otherwise>
		<tr>
			<td colspan="10" align="right">
				<a href="sendMessage.html?listId=${listBean.id}&cp=viewList.html&cpoi=${listBean.id}"><fmt:message key="${lang.localeId}.eqfSystem.viewList.sendMailSystem"/></a>
				&nbsp;
				<a href="mailto:${listBean.emails}"><fmt:message key="${lang.localeId}.eqfSystem.viewList.sendMailClient"/></a>
			</td>
		</tr>
		</c:otherwise>
		</c:choose>
	</c:if>
		 </table>
		 </form:form>
      </td>
 </tr>
</table>
</body>
</html>
