<%@ page  pageEncoding="UTF-8" %>

						<tr>
        		        	<td colspan="2"/>
        		        		&nbsp;
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<th align="right" width="200" valign="top">
	        		        	קבצי הצעת המחקר:
	        		        </th>
	        		        <th align="right">
        		        		<c:choose>
									<c:when test="${fn:length(command.proposalBean.researchAttachments)==0 }">
										טרם צורפו קבצי הצעת המחקר
									</c:when>
									<c:otherwise>

										<c:forEach items="${command.proposalBean.researchAttachments}" var="researchAttachment">

												<table>
													<tr>
														<th width="200" align="right">
															<a href="/fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=proposal&attachmentId=${researchAttachment.id}&contentType=<c:out value="${researchAttachment.contentType}"/>"/><c:out value="${researchAttachment.title}"/></a>
														</th>
														<td>
															<c:if test="${command.proposalBean.stateId == DRAFT && !viewDetails}">
																<button class="grey moveUpAttach" id="${researchAttachment.id}" >העלה</button>&nbsp;
																<button class="grey moveDownAttach" id="${researchAttachment.id}" >הורד</button>&nbsp;
																<button class="grey deleteAttach" id="${researchAttachment.id}" >מחק</button>
				 											</c:if>
				 										</td>
				 									</tr>
				 								</table>
				 								<br/>

										</c:forEach>

									</c:otherwise>
								</c:choose>
        		        	</th>
        		        </tr>
						<c:if test="${command.proposalBean.stateId == DRAFT && !viewDetails}">

						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>

        		        <tr class="proposalAttach">
        		        	<th align="right">
								צרוף/החלפת קובץ הצעת המחקר:
        		        	</td>
        		        	<td>
								<input class="green" type="file" name="proposalBean.addedResearchAttachment.file"  />
							</td>
        		        </tr>
        		        <tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
        		        <tr class="proposalAttach">
        		        	<th align="right">
								שם הקובץ:
        		        	</td>
        		        	<td>
								<input class="green" type="text" name="proposalBean.addedResearchAttachment.title"  />
								&nbsp;
								<button class="save grey">הוסף קובץ</button>
        		        	</td>
        		        </tr>

        		        </c:if>

        		        <tr>
        		        	<td colspan="2">
        		        		<span class="formError"><form:errors path="proposalBean.researchAttachments" /></span>
        		        	</td>
        		        </tr>


        		        <tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>

						<tr>
							<th align="right">
								טופס הצהרה על התנהגות ראויה במחקר:
							</th>
							<th align="right">
								<c:choose>
									<c:when test="${command.proposalBean.ethicsAttach == null || fn:length(command.proposalBean.ethicsAttach)==0 }">
										טרם צורף קובץ התנהגות ראויה במחקר
									</c:when>
									<c:otherwise>
										<a href="/fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=ethics&contentType=<c:out value="${command.proposalBean.ethicsAttachContentType}"/>"/>לצפייה בהתחייבות החתומה </a>
									</c:otherwise>
								</c:choose>
        		        	</th>
        		        </tr>

        		        <c:if test="${command.proposalBean.stateId == DRAFT && !viewDetails}">

						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>

        		        <tr id="ethicsAttach">
        		        	<th align="right">
								צרוף/החלפת קובץ התנהגות ראויה במחקר:
        		        	</th>
        		        	<td>
								<input class="green" type="file" name="proposalBean.ethicsAttach"  />
								&nbsp;
								<button class="save grey">הוסף קובץ</button>
							</td>
        		        </tr>
        		        </c:if>
        		        <tr>
        		        	<td colspan="2">
        		        		<span class="formError"><form:errors path="proposalBean.ethicsAttach" /></span>
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<td colspan="2">
        		        		&nbsp;
        		        	</td>
        		        </tr>
        		        <c:if test="${fn:length(command.proposalBean.researchAttachments)>0 && fn:length(command.proposalBean.ethicsAttach)>0}">
        		        	<%@ include file="/WEB-INF/jsp/content/editProposal/tabsMoveOnButton.jsp" %>
        		        </c:if>
