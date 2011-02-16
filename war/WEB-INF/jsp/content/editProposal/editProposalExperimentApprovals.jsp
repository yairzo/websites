<%@ page  pageEncoding="UTF-8" %>
<%-- begin of safety, human, animals approvals --%>

				<c:if test="${command.proposalBean.stateId >= FUND_APPROVED}">
					<tr>
			          <td colspan="2" >
			          	&nbsp;
			          </td>
        			</tr>


				<c:choose>
				<c:when test="${command.proposalBean.stateId == FUND_APPROVED && !viewDetails}">

				<authz:authorize ifAnyGranted="ROLE_EQF_RESEARCHER">
					<tr>
						<td colspan="2">
							<table cellpadding="3" cellspacing="3">
        		        	<tr>
        		        	<td class="medium300 safetySelect">
        		        		<table>
        		        			<tr>
        		        				<th align="right">
        		        					בטיחות:
        		        				</th>
        		        			</tr>
        		        			<tr>
        		        				<td>
        		        					<form:radiobutton  id="experimental" path="proposalBean.experimental" value="false"/>המחקר אינו ניסויי
        		        					<br>
        		        					<form:radiobutton  id="experimental" path="proposalBean.experimental" value="true"/>המחקר ניסויי, מצורף נספח
 																								הבטיחות  חתום על ידי
																								מחלקה  לבטיחות ולגיהות
																								.טופס מס 025
										</td>
									</tr>
								</table>
							</td>
							<td class="medium300 safetyAttach">
								<table>
									<tr>
										<th align="right">
									<c:choose>
											<c:when test="${command.proposalBean.safetyAttach == null || fn:length(command.proposalBean.safetyAttach)==0 }">
													טרם צורף קובץ אישור בטיחות
											</c:when>
											<c:otherwise>
												<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=safety&contentType=<c:out value="${command.proposalBean.safetyAttachContentType}"/>"/>צפייה בנספח הבטיחות החתום</a>
											</c:otherwise>
										</c:choose>

										</th>
									</tr>
									<tr>
										<td>
												צרוף/החלפת אישור בטיחות:
        			        					<br>
												<input type="file" size="5" name="proposalBean.safetyAttach" value="עיון..."  />
												&nbsp;
												<button class="save grey">עדכן</button>
										</td>
									</tr>
								</table>
							</td>
        		        </tr>
        		        <tr>
        		        	<td colspan="3">
        		        		<span class="formError"><form:errors path="proposalBean.safetyAttach" /></span>
        		        	</td>
        		        </tr>

        		         <tr id="humanExperiment">
        		        	<td class="medium300 humanSelect">
        		        		<table>
        		        			<tr>
        		        				<th align="right">
        		        					ניסויים בבני אדם:
        		        				</th>
        		        			</tr>
        		        			<tr>
        		        				<td>
        		        					<form:radiobutton  id="humanExperiment" path="proposalBean.humanExperiment" value="false"/>המחקר אינו כרוך בניסויים באדם
        		        					<br>
        		        					<form:radiobutton  id="humanExperiment" path="proposalBean.humanExperiment" value="true"/>המחקר   כרוך   בניסויים   באדם,
      										מצורף  אישור  ועדת הלסינקי  או
									       הוועדה לאתיקה של ניסויים,  לא
      									 רפואיים, באדם.
      									 .טופס מס 025
      									</td>
      								</tr>
      							</table>
      						</td>
							<td align="right" class="medium300 humanAttach">
								<table>
									<tr>
										<th align="right">
											<c:choose>
												<c:when test="${command.proposalBean.humanAttach == null || fn:length(command.proposalBean.humanAttach)==0 }">
													טרם צורף קובץ אישור ועדת הלסינקי
												</c:when>
												<c:otherwise>
													<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=human&contentType=<c:out value="${command.proposalBean.humanAttachContentType}"/>"/>צפייה בהצעת המחקר המצורפת</a>
												</c:otherwise>
											</c:choose>
										</th>
									</tr>
									<tr>
										<td>
											צרוף/החלפת אישור ועדת הלסינקי:
        		        					<br>
											<input type="file" size="5" name="proposalBean.humanAttach" value="עיון..."   />
											&nbsp;
											<button class="save grey">עדכן</button>
										</td>
									</tr>
								</table>
							</td>
        		        </tr>
						<tr>
        		        	<td colspan="3">
        		        		<span class="formError"><form:errors path="proposalBean.humanAttach" /></span>
        		        	</td>
        		        </tr>
    			        	<tr id="animalsExperiment">
        		        	<td class="medium300 animalsSelect">
        		        		<table>
        		        			<tr>
        		        				<th align="right">
        		        					ניסויים בחיות:
        		        				</th>
        		        			</tr>
        		        			<tr>
        		        				<td>
        		        					<form:radiobutton  id="animalsExperiment" path="proposalBean.animalsExperiment" value="false"/>המחקר אינו כרוך בניסויים בחיות
        		        					<br>
        		        					<form:radiobutton  id="animalsExperiment" path="proposalBean.animalsExperiment" value="true"/>המחקר   כרוך   בניסויים   בחיות,
    									  מצורף אישור  הוועדה לאתיקה
      									 של טיפול וניסויים בבע"ח
      									</td>
      								</tr>
      							</table>
      						</td>
							<td class="medium300 animalsAttach">
								<table>
									<tr>
										<th align="right">
											<c:choose>
												<c:when test="${command.proposalBean.animalsAttach == null || fn:length(command.proposalBean.animalsAttach)==0 }">
														טרם צורף קובץ אישור הוועדה לאתיקה של טיפול וניסויים בבע"ח
												</c:when>
												<c:otherwise>
													<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=animals&contentType=<c:out value="${command.proposalBean.animalsAttachContentType}"/>"/>צפייה בהצעת המחקר המצורפת</a>
												</c:otherwise>
											</c:choose>
										</th>
									</tr>
									<tr>
										<td>
											צרוף/החלפת אישור הוועדה לאתיקה של טיפול וניסויים בבע"ח:
    	    		        				<br>
											<input type="file" size="5" name="proposalBean.animalsAttach" value="עיון..." />
											&nbsp;
											<button class="save grey">עדכן</button>
										</td>
									</tr>
								</table>
							</td>
        		        </tr>
        		        <tr>
        		        	<td colspan="3">
        		        		<form:errors cssClass="formError" path="proposalBean.animalsAttach" />
        		        	</td>
        		        </tr>

        		        <tr id="experimentApproved">
        		        	<th align="right" colspan="3">
        		        		<button class="experimentApproval grey">מאשר כי צורפו כל האישורים הניסויים הדרושים</button>
        		        	</th>
        		        </tr>
        		        </table>
        		       </td>
        		      </tr>


						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_EQF_RESEARCHER">
							<tr>
								<th align="right" colspan="2">
									טרם סומן אם המחקר ניסויי
								</th>
							</tr>
						</authz:authorize>
        		        </c:when>



        		        <c:when test="${command.proposalBean.stateId >= EXPERIMENT_APPROVED }">

        		        	<c:if test="${!viewDetails}">
        		        		<form:hidden path="proposalBean.experimental"/>
        		        		<form:hidden path="proposalBean.humanExperiment"/>
        		        		<form:hidden path="proposalBean.animalsExperiment"/>
        		        	</c:if>

        					<tr>
        						<td>
        		        	<c:choose>
        		        		<c:when test="${command.proposalBean.experimental}">
        		        			<tr>
        		        				<th align="right" class="medium300">
        		        					המחקר ניסויי
        		        					<br/>
        		        					<span style="font-weight: normal;">
        		        					מצורף נספח
 													הבטיחות  חתום על ידי
												מחלקה  לבטיחות ולגיהות
															.טופס מס 025
											</span>
										</th>
										<th class="medium300">
											<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=safety&contentType=<c:out value="${command.proposalBean.safetyAttachContentType}"/>"/>צפייה בניספח הבטיחות החתום</a>
										</th>
									</tr>
									<tr>
										<td colsapn="2">
											&nbsp;
										</td>
									</tr>
										<c:choose>
										<c:when test="${command.proposalBean.humanExperiment}">
											<tr>
        		        				<th align="right" class="medium300">
        		        					המחקר כרוך בניסויים באדם
        		        					<br/>
        		        					<span style="font-weight: normal;">
        		        					מצורף  אישור  ועדת הלסינקי  או
      										 הוועדה לאתיקה של ניסויים,  לא
    										   רפואיים, באדם.
    										  </span>
										</th>
										<th class="medium300">
											<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=human&contentType=<c:out value="${command.proposalBean.humanAttachContentType}"/>"/>צפייה בהצעת המחקר המצורפת</a>
										</th>
									</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th align="right" colspan="2">
										המחקר אינו כרוך בניסויים בבני אדם
											</th>
										</tr>

									</c:otherwise>
								</c:choose>
								<tr>
										<td colsapn="2">
											&nbsp;
										</td>
									</tr>

								<c:choose>
									<c:when test="${command.proposalBean.animalsExperiment}">
											<tr>
        		        				<th align="left" class="medium300">
        		        					המחקר  כרוך  בניסויים בחיות
        		        					<br/>
									       <span style="font--weight: normal;">
									       מצורף אישור  הוועדה לאתיקה
									       של טיפול וניסויים בבע"ח.
									       </span>
										</th>
										<th>
											<a href="fileViewer?proposalId=<c:out value="${command.proposalBean.id}"/>&attachType=animals&contentType=<c:out value="${command.proposalBean.animalsAttachContentType}"/>"/>צפייה בהצעת המחקר המצורפת ( <c:out value="${fn:length(command.proposalBean.animalsAttach)}"/> בייטים, מסוג:  <c:out value="${command.proposalBean.safetyAttachContentType}"/>)</a>
										</th>
									</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th align="right" colspan="2">
										המחקר אינו כרוך בניסויים בחיות
											</th>
										</tr>
									</c:otherwise>
									</c:choose>
								</c:when>
						<c:otherwise>
							<tr>
								<th align="right" colspan="2">
									המחקר אינו ניסויי
								</th>
							</tr>
						</c:otherwise>
					</c:choose>
			</c:when>
			<c:otherwise>
        	   	<tr>
					<th align="right" colspan="2">
							טרם סומן אם המחקר ניסויי
					</th>
				</tr>
			</c:otherwise>
		</c:choose>
	</c:if>



			<%-- end of safety, human, animals approvals --%>