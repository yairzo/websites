<%@ page  pageEncoding="UTF-8" %>
<%--  begin of proposers details --%>



	<tr>
    	<td colspan="2">
        	&nbsp;
        </td>
    </tr>
	<tr>
    	<td align="right"  colspan="2" >
			<table width="100%">
				<tr>
						<th>
							שם המגיש
						</th>
						<th>
							תפקיד בהגשת ההצעה
						</th>
						<th>
							דואל
						</th>
						<th>
							מצבו בהגשת ההצעה
						</th>
					</tr>
				<c:forEach items="${command.proposalBean.researchersPersonProposalBeans}" var="personProposal" varStatus="varStatus">
					<c:choose>
						<c:when test="${varStatus.index % 2 == 1}">
							<c:set var="rowBgBrightness" value="bright"/>
						</c:when>
						<c:otherwise>
							<c:set var="rowBgBrightness" value="dark"/>
						</c:otherwise>
					</c:choose>



					<tr class="viewList">
						<td class="viewList${rowBgBrightness}">
							<c:out value="${personProposal.personBean.degreeFullNameHebrew}"/>
						</td>

						<td class="viewList${rowBgBrightness}">
							<c:choose>
								<c:when test="${personProposal.typeId == MAIN_RESEARCHER}">
									חוקר אחראי
								</c:when>
								<c:otherwise>
									חוקר נוסף
								</c:otherwise>
							</c:choose>
						</td>

						<td class="viewList${rowBgBrightness}">
							<c:out value="${personProposal.personBean.email}"/>
						</td>
						<td class="viewList${rowBgBrightness}">

								<c:out value="${personProposalStates[ personProposal.stateId ] }"/>

						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>




        		        	<%-- The main researcher is viewing the proposal --%>

        		        	<c:if test="${command.proposalBean.personProposalBean.typeId == MAIN_RESEARCHER}">


        		        <tr>
        		        	<td>
        		        		&nbsp;
        		        	</td>
        		        </tr>


        		        <%-- Add or remove secondary researchers from researchers list --%>



        				<c:choose>
        		        <c:when test="${command.proposalBean.stateId==DRAFT && command.proposalBean.readyForAddResearchers && !viewDetails}">



        		        <c:if test="${fn:length( command.proposalBean.personProposalBeans ) > 1}">

        		        <tr>
        		        	<td width="300">
        		        		הסרת חוקר/ת מרשימת המגישים:
        		        	</td>
        		        	<td>

									<form:select id="researchersRemoveSelect" cssClass="green" path="removedResearcherId">
        		        				<form:option value="0">בחר חוקר/ת להסרה</form:option>
        		        					<c:forEach items="${command.proposalBean.personProposalBeans}" var="personProposal">
        		        						<c:if test="${ personProposal.typeId != MAIN_RESEARCHER}">
        		        							<form:option value="${personProposal.personId}"> <c:out value="${personProposal.personBean.fullNameHebrew}"/> </form:option>
        		        						</c:if>
        		        					</c:forEach>
        		        			</form:select>
        		        			</td>
						</tr>

						</c:if>

						<tr>
        		        	<td>
        		        		&nbsp;
        		        	</td>
        		        </tr>


        		        <tr>
        		        	<td width="300">
        		        		הוספת חוקר/ת לרשימת המגישים:
        		        	</td>
        		        	<td>

									<form:input id="researcherAdd" cssClass="green" path="addedResearcher" />
									&nbsp; &nbsp;
									<button id="researcherAddButton" class="grey" >הוסף </button>

									<br>
									<span class="formError"><form:errors path="addedResearcher" /></span>

        		        	</td>

						</tr>


						</c:when>
						<c:when test="${command.proposalBean.stateId==DRAFT && !viewDetails}">
							<tr>
								<td colspan="2">
									ניתן יהיה להוסיף חוקרים נוספים להצעה לאחר שתוזן כותרת/כותרת זמנית בעברית
								</td>
							</tr>
						</c:when>

						</c:choose>



						</c:if>



        		        	 <%@ include file="/WEB-INF/jsp/content/editProposal/tabsMoveOnButton.jsp" %>



	<%--  end of proposers details --%>