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
						<td>
							<fmt:message key="${lang.localeId}.post.subject"/>
						</td>
						<td>
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
						<td>
							<fmt:message key="${lang.localeId}.post.creator"/>
						</td>
						<td>
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


						<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.additionalAddresses"/>
						</td>
						<td>
							<c:out value="${command.additionalAddresses}"/>
						</td>
						</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="${lang.localeId}.post.sendImmediately"/>
							<input type="checkbox" value="" <c:if test="${command.sendImmediately}"> checked </c:if> />
						</td>
					</tr>


					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>


					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.timeSent"/>
						</td>
						<td>
							<c:out value="${sendDateTime}"/>
						</td>
						</tr>


					<tr>
						<td colspan="2">
							&nbsp;
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