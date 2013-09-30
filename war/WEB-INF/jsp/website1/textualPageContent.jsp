<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

 			<div class="container clearfix">
				<div class="breadcrumbs clearfix" dir="${lang.dir}" align="${lang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" style="align:${lang.align}">
					<h1 class="maintitle" text-align="${lang.align}">${command.title}</h1>
					<div class="clearfix mar_20">
       						<c:choose>
    							<c:when test="${command.showFile}">
									<fmt:message key="${lang.localeId}.website.fileNotOpen"/><a href="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1"><fmt:message key="${lang.localeId}.website.fileOpen"/></a>				
									<br>
									<c:choose>
									<c:when test="${isImage}">
										<img src="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1"/>
									</c:when>
									<c:otherwise>
										<script language="javascript" type="text/javascript">
										document.location="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1";
										</script>
									</c:otherwise>
									</c:choose>
								</c:when>
    							<c:when test="${command.wrapExternalPage}">
									<!--<jsp:include page="/viewList.html?id=${command.externalPageUrl}&iv=1&p=1&a=1" />-->
									<c:if test="${aCompoundView}">
									<c:forEach items="${list.sublistsBeans}" var="listBean" varStatus="varStatusLists">
										<div class="kol open">
										<div class="clearfix">
											<h3 class="kol_title"><c:out escapeXml="false" value="${listBean.displayName}"/></h3>
										</div>
										<table class="table_kol">
										<tr>
										<th class="table_one">שם</th>
										<th class="table_two">תפקיד</th>
										<th class="table_email">דוא”ל</th>
										<th class="table_three">טלפון</th>
										</tr>
										
										<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
										<tr>
											<c:forEach items="${viewableBean.fields}" var="field">
											<td align="${field.align}">
											<c:out escapeXml="false" value="${field.prefix}"/><c:out escapeXml="false" value="${field.text}"/><c:out escapeXml="false" value="${field.suffix}"/>
											</td>
											</c:forEach>
										</tr>
										</c:forEach>
										</table>
										</div>
									</c:forEach>
									</c:if>
									<c:if test="${!aCompoundView}">
										<div class="kol open">
										<div class="clearfix">
											<h3 class="kol_title"><c:out escapeXml="false" value="${listBean.displayName}"/></h3>
										</div>
										<table class="table_kol">
										<tr>
										<th class="table_one">שם</th>
										<th class="table_two">תפקיד</th>
										<th class="table_email">דוא”ל</th>
										<th class="table_three">טלפון</th>
										</tr>
									
										<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
										<tr>
											<c:forEach items="${viewableBean.fields}" var="field">
											<td align="${field.align}">
											<c:out escapeXml="false" value="${field.prefix}"/><c:out escapeXml="false" value="${field.text}"/><c:out escapeXml="false" value="${field.suffix}"/>
											</td>
											</c:forEach>
										</tr>
										</c:forEach>
										</table>
										</div>
									</c:if>
									
									
    							</c:when>
    							<c:otherwise>
    								${command.html}
    							</c:otherwise>
    						</c:choose>
 					</div>
				</div>
			</div>
