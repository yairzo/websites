<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<table border="0" width="80%" dir="${lang.dir}">
 				<tbody>
					<tr>
		      			<td>
			      			<h1>${command.title}</h1>
                      	</td>
                      	<td width="150" align="${lang.align}">
                         	<fmt:message key="${lang.localeId}.website.lastUpdate"/>
                      	</td>
                    </tr>
					<tr>
		      			<td colspan="2">
      						<c:choose>
    							<c:when test="${command.showFile}">
									<fmt:message key="${lang.localeId}.website.fileNotOpen"/><a href="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1"><fmt:message key="${lang.localeId}.website.fileOpen"/></a>				
									<script language="javascript" type="text/javascript">
										document.location="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1";
									</script>
								</c:when>
    							<c:when test="${command.wrapExternalPage}">
									<jsp:include page="/viewList.html?id=${command.externalPageUrl}&iv=1&p=1&a=1" />					
    							</c:when>
    							<c:otherwise>
    								${command.html}
    							</c:otherwise>
    						</c:choose>
                     	</td>
                    </tr>
                </tbody>
			</table>
