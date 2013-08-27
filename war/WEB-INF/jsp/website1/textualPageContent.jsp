<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

 			<div class="container clearfix">
				<div class="breadcrumbs clearfix" dir="${lang.dir}" align="${lang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" align="${lang.align}">
					<h1 class="maintitle">${command.title}</h1>
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
									<jsp:include page="/viewList.html?id=${command.externalPageUrl}&iv=1&p=1&a=1" />					
    							</c:when>
    							<c:otherwise>
    								${command.html}
    							</c:otherwise>
    						</c:choose>
 					</div>
				</div>
			</div>
