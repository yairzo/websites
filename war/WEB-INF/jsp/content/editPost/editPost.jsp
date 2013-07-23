<tr>
	<td>
	<form:form id="form" name="form" method="POST" action="post.html"
				commandName="command" enctype="multipart/form-data">

				<div id="genericDialog" title="" style="display:none" dir="rtl"><p>text put here</p></div>

				<form:hidden path="id" />
				<form:hidden path="creatorId"/>
	
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="${lang.dir}">

		<tr>
			<td valign="top" align="center"><br>
			
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
				<tr class="selectCallForProposal">
						<td>
							<fmt:message key="${lang.localeId}.post.callForProposal"/>
						</td>
						<td style="text-align: ${lang.align}; direction: ${lang.dir}">
						<input type="text" class="green callForProposal">&nbsp;

						<button class="importCallForProposal grey"><fmt:message key="${lang.localeId}.post.importCallForProposal"/></button>&nbsp;
						<button class="reloadCallForProposalsList grey"><fmt:message key="${lang.localeId}.post.reloadCallForProposalsList"/></button>

						</td>
				</tr>
				<tr class="selectCallForProposal">
						<td>
							&nbsp;
						</td>
				</tr>

				<tr>
						<td>
							<font color="red">*</font><fmt:message key="${lang.localeId}.post.subject"/>
						</td>
						<td>
							<form:input htmlEscape="true" size="40" path="messageSubject" cssClass="green messageSubject"/>
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
					<td colspan="2">
						<div id="editable" contenteditable="true" style="border:black thin dotted;text-align:${lang.align}">
 							${command.message}<c:if test="${fn:length(command.message)<5}">&nbsp;&nbsp;</c:if>
						</div>
           				<textarea class="green" id="message" name="message" cols="100" rows="12" style="display:none">${command.message}</textarea>
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
									<button class="addAttachEditor grey" onclick=""><fmt:message key="${lang.localeId}.post.addAttachEditor"/></button>
								</tr>
							</c:forEach>
						</table>
						  </td>
				</tr>
				<tr>
						<td colspan="2" class="addFile">
							<!--<fmt:message key="${lang.localeId}.post.addAttach"/>
							 <input class="green" type="file" name="file1"  /> &nbsp; <button class="save grey" onclick=""><fmt:message key="${lang.localeId}.post.addFile"/></button> -->
						<span style="display: block; width: 80px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 79px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)"><fmt:message key="${lang.localeId}.post.addFile"/></a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="file1" id="file1"/>
						</span>
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
							<form:select cssClass ="green sender" path="senderId">
								<c:forEach items="${senders}" var="sender">
									<form:option value="${sender.id}">${sender.degreeFullName}</form:option>
								</c:forEach>
							</form:select>
 							<c:forEach items="${senders}" var="sender">
 								<input type="hidden" id="sender${sender.id}" value="${sender.email}"/>
							</c:forEach>
						</td>
				</tr>

				<tr>
						<td colspan="2">
							&nbsp;
						</td>
				</tr>
				<tr>
						<td colspan="2">
						<input type="checkbox" class="green viewSubjects"/>
						<fmt:message key="${lang.localeId}.callForProposal.selectSubjects"/>
						</td>
				</tr>
				<tr  id="subjectView">
						<td colspan="2" >
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

		<br>
		</td>
	 </tr>
  </table>

	</form:form>
  </td>
</tr>
