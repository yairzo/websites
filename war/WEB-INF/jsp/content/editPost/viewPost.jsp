<tr>
	<td>
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="${lang.dir}">

		<tr>
			<td valign="top" align="center"><br>

				<table width="600" border="0" align="center" cellpadding="2"
					cellspacing="0">

				<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR,ROLE_POST_READER">
					<tr>
						<td colspan="2">
							<fmt:message key="${lang.localeId}.post.subject"/>
								${command.messageSubject}
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
				</authz:authorize>

					<tr>
						<td colspan="2">
							<%@ include file="/WEB-INF/jsp/content/editPost/singlePost.jsp" %>
						</td>
					</tr>

				<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR">

					<tr>
						<td>
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<fmt:message key="${lang.localeId}.post.creator"/>
							<c:out value="${command.creator.degreeFullName}"/>
						</td>
						</tr>


					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<%@ include file="/WEB-INF/jsp/content/editPost/plainSubjects.jsp" %>
		              </td>
	                </tr>
             		<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

						<c:if test="${fn:length(command.additionalAddresses)>0}">
							<tr>
								<td colspan="2">
									<fmt:message key="${lang.localeId}.post.additionalAddresses"/>
									<c:out value="${command.additionalAddresses}"/>
								</td>
							</tr>
						 </c:if>

						<c:if test="${command.sendImmediately}">
					 		<tr>
							<td colspan="2">
							&nbsp;
							</td>
							</tr>
							<tr>
							<td colspan="2">
							<fmt:message key="${lang.localeId}.post.sendImmediately"/>
							</td>
							</tr> 
						 </c:if>


					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>


					<tr>
						<td colspan="2">
							<fmt:message key="${lang.localeId}.post.timeSent"/>
							<c:out value="${sendDateTime}"/>
						</td>
						</tr>


					<tr>
						<td colspan="2">
							&nbsp;
						
						<form:form id="form" name="form" method="POST" action="post.html" commandName="command" >
						<form:hidden path="id" />
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN">
							<button class="cancelVerified grey"><fmt:message key="${lang.localeId}.post.cancelVerified"/></button>
						</authz:authorize>
						</form:form>
							
						</td>
					</tr>

					</authz:authorize>

				</table>

				<br>
			</td>
		</tr>
	</table>

	</td>
</tr>