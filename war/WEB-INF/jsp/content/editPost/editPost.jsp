<tr>
	<td>
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="${lang.dir}">

		<tr>
			<td valign="top" align="center"><br>
			<form:form id="form" name="form" method="POST" action="post.html"
				commandName="command" enctype="multipart/form-data">


				<form:hidden path="id" />
				<form:hidden path="creatorId"/>

				<table width="600" border="0" align="center" cellpadding="2"
					cellspacing="0">

				<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.language"/>
						</td>
						<td>
							<form:select cssClass ="green langSelect" path="localeId">
								<c:forEach items="${langs}" var="lang">
									<form:option value="${lang.localeId}">${lang.name}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.type"/>
						</td>
						<td>
						<form:select path="typeId" cssClass="green postType">
							<c:forEach items="${postTypes}" var="postType" >
								<form:option value="${postType.id}"><fmt:message key="${lang.localeId}.post.type.${postType.id}"/></form:option>
							</c:forEach>
						</form:select>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr class="selectCallOfProposal">
						<td>
							<fmt:message key="${lang.localeId}.post.callOfProposal"/>
						</td>
						<td style="text-align: ${lang.align}; direction: ${lang.dir}">
						<input type="text" class="green callOfProposal">&nbsp;
						<a href="#" class="importCallOfProposal"><fmt:message key="${lang.localeId}.post.importCallOfProposal"/></a>&nbsp;
						<a href="#" class="reloadCallOfProposalsList"><fmt:message key="${lang.localeId}.post.reloadCallOfProposalsList"/></a>
						</td>
					</tr>
					<tr class="selectCallOfProposal">
						<td>
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.subject"/>
						</td>
						<td>
							<form:input size="40" path="messageSubject" cssClass="green messageSubject"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>


					<tr>
						<td valign="top" colspan="2">
							<fmt:message key="${lang.localeId}.post.content"/>
						</td>
					</tr>
					<tr>
						<td width="90%">
							<input type="radio"  name="radios" class="rdoTypeTinymce" value="rdoTypeTinymce" checked="checked" >
           					  <fmt:message key="${lang.localeId}.general.oldEditor"/>
            				&nbsp;
            				<input type="radio" name="radios" class="rdoTypeCKEDITOR" value="rdoTypeCKEDITOR">
          					  <fmt:message key="${lang.localeId}.general.newEditor"/>
          				</td>
          			</tr>
					<tr>
						<td colspan="2">

								<div class="bodyTA">
									<form:textarea id="body" path="message" cssClass="tinymce"/>
								</div>
						</td>
					</tr>

					<tr>
						<td>
							&nbsp;
							<div style="visibility: hidden" class="callOfProposalImportBox"/>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.files"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
							<c:forEach items="${command.attachments}" var="attachment"
								varStatus="i">
								<tr>
									<td style="width: 30px;"><input type="checkbox"
										name="filech${i.index}" value="true" checked="checked"></td>
									<td class="attach"><a
										href="http://${server}/iws/fileViewer?postId=${command.id}&attachmentId=${attachment.id}&contentType=${attachment.contentType}&md5=${command.md5}"
										target="_blank">${attachment.title}</a></td>
									<td>
										<a href="#" class="addAttachEditor"><fmt:message key="${lang.localeId}.post.addAttachEditor"/></a>
								</tr>
							</c:forEach>
						</table>
						  </td>
					</tr>
					<tr>
						<td colspan="2" class="addFile">
							<fmt:message key="${lang.localeId}.post.addAttach"/>
							<input class="green" type="file" name="file1"  /> &nbsp; <button class="save grey" onclick=""><fmt:message key="${lang.localeId}.post.addFile"/></button>
						</td>
					</tr>


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
						<td>
							<fmt:message key="${lang.localeId}.post.sender"/>
						</td>
						<td>
							<form:select cssClass ="green" path="senderId">
								<c:forEach items="${senders}" var="sender">
									<form:option value="${sender.id}">${sender.degreeFullName}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<%@ include file="/WEB-INF/jsp/content/editPost/subjects.jsp" %>
		              </td>
	                </tr>
             		<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.additionalAddressesInsert"/>
						</td>
						<td>
							<form:input size="40" id="additionalAddresses" path="additionalAddresses" cssClass="green"/>
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
							<form:checkbox path="sendImmediately"/>
						</td>
					</tr>


					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>


					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.timeSend"/>
						</td>
						<td>
							<c:out value="${sendDateTime}"/>
						</td>
					</tr>


					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.pickTimeSend"/>
						</td>
						<td>
							<form:input id="date" path="date" cssClass="green"/>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="${lang.localeId}.post.pickHourSend"/>
						</td>
						<td>
							<form:input id="hour" path="hour" cssClass="green"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;<br>
						<img src="image/hr.gif" width="580" height="10"><br>
						&nbsp;</td>
					</tr>

					<tr>
						<td colspan="2">

						<button class="sendme grey action" onclick="return false;"><fmt:message key="${lang.localeId}.post.sendMe"/></button>
						&nbsp;&nbsp;


						<button class="save grey action" onclick=""><fmt:message key="${lang.localeId}.post.save"/></button>

						&nbsp;&nbsp;

						<button class="cancel grey action"><fmt:message key="${lang.localeId}.post.done"/></button>
						</td>
					</tr>




				</table>

				<br></td>
		</tr>
	</table>

	</form:form></td>
</tr>
