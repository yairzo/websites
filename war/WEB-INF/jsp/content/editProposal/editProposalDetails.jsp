<%@ page  pageEncoding="UTF-8" %>


	<%--  begin of proposal details --%>

        		        <tr>
        		        	<td>
        		        		&nbsp;
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<th align="right">
        		        		נושא המחקר בעיברית:
        		        	</th>
        		        	<td>
        		        		<c:choose>
        		        			<c:when test="${command.proposalBean.stateId==DRAFT && !viewDetails}">
			        		        	<form:textarea id="hebrewTitle" path="proposalBean.hebrewTitle" cssClass="green" dir ="rtl" cols="60" rows="3"/>
			        		        </c:when>
			        		        <c:otherwise>
			        		        	<c:choose>
			        		        		<c:when test="${command.proposalBean.hasHebrewTitle}">
					        		        	<c:out value="${command.proposalBean.hebrewTitle}"/>
					        		        	<c:if test="${!viewDetails}">
					        		        	<form:hidden path="proposalBean.hebrewTitle"/>
					        		        	</c:if>
					        		        </c:when>
					        		        <c:otherwise>
					        		        	טרם הוזן
					        		        </c:otherwise>
					        		       </c:choose>
			        		        </c:otherwise>
			        		    </c:choose>
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<td>&nbsp;</td>
        		        	<td>
        		        		<span class="formError"><form:errors path="proposalBean.hebrewTitle" /></span>
        		        	</td>
        		        </tr>

        		        <tr>
        		        	<th align="right">
        		        		 נושא המחקר באנגלית:
        		        	</th>
        		        	<td>
        		        		<c:choose>
        		        			<c:when test="${command.proposalBean.stateId==DRAFT && !viewDetails}">
			        		        	<form:textarea id="englishTitle" path="proposalBean.englishTitle" cssClass="green" dir ="rtl" cols="60" rows="3"/>
			        		        </c:when>
			        		        <c:otherwise>
			        		        	<c:choose>
			        		        		<c:when test="${command.proposalBean.hasEnglishTitle}">
					        		        	<c:out value="${command.proposalBean.englishTitle}"/>
					        		        	<c:if test="${!viewDetails}">
					        		        	<form:hidden path="proposalBean.englishTitle"/>
					        		        	</c:if>
					        		        </c:when>
					        		        <c:otherwise>
					        		        	טרם הוזן
					        		        </c:otherwise>
					        		       </c:choose>
			        		        </c:otherwise>
			        		    </c:choose>

        		        	</td>
        		        </tr>
        		        <tr>
        		        	<td>&nbsp;</td>
        		        	<td>
        		        		<span class="formError"><form:errors path="proposalBean.englishTitle" /></span>
        		        	</td>
        		        </tr>

        		        <tr>
        		        	<td>
        		        		&nbsp;
        		        	</td>
        		        </tr>



        		        <tr>
        		        	<th colspan="2" align="right">
        		        		הגורם המממן: &nbsp;&nbsp

								<span class="fundName">
								<c:choose>

									<c:when test="${command.proposalBean.fundId != 0}">
										<c:out value="${command.proposalBean.fund.name}"/>&nbsp;&nbsp; <c:if test="${command.proposalBean.fund.temporary}">(ממתין לטיפול הרשות למו"פ)</c:if>
        		        				<c:if test="${!viewDetails}">
        		        				<form:hidden path="proposalBean.fundId"/>
        		        				</c:if>
        		        			</c:when>
        		        			<c:otherwise>
        		        				טרם נבחר מממן
        		        			</c:otherwise>
        		        		</c:choose>
        		        		</span>
        		        	</th>
        		        </tr>
        		        <tr>
        		        	<td colspan="2">
								&nbsp;
        		        	</td>
        		        </tr>
        		        <c:if test="${command.proposalBean.stateId == DRAFT && !viewDetails}">
        		        <tr>
        		        	<th width="60" align="right">
        		        		 בחירת מממן:
        		        	</th>
        		        	<td>
        		        		<form:input htmlEscape="true" size="80" cssClass="green" id="fundingAgencyAdd" path="addedFund"/>
        		        			&nbsp;
        		        		<button class="addFund grey">עדכן</button>
        		        	</td>
        		        </tr>

        		        <tr>
        		        	<td>&nbsp;</td>
        		        	<td>
        		        		יש להקליד אותיות לטיניות ראשונות של שם הקרן.
        		        		אם שם הקרן אינו ברשימה יש להקליד אותו בשלמותו, והכנסתו למאגר תטופל על-ידי הרשות למו"פ.
        		        		<span class="formError"><form:errors path="proposalBean.fundId" /></span>
        		        	</td>
        		        </tr>

						</c:if>



						<%@ include file="/WEB-INF/jsp/content/editProposal/tabsMoveOnButton.jsp" %>


        	<%--  end of proposal details --%>