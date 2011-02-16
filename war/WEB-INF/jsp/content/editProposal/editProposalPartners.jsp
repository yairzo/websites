<%@ page  pageEncoding="UTF-8" %>

						<tr>
        		        	<th colspan="2">
        		        		&nbsp;
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<th width="200" align="right" valign="top">
        		        		חוקרים שותפים:
        		        	</td>

        		        <c:choose>
        		        	<c:when test="${command.proposalBean.fundId > 0}">


        		        	<th dir="ltr" align="left" colspan="2">
        		        		<c:choose>
        		        		<c:when test="${fn:length(command.proposalBean.partnerProposalBeans)>0}">
        		        		<ul>
        		        		<c:forEach items="${command.proposalBean.partnerProposalBeans}" var="partnerProposal">
        		        			<li class="bold">	<c:out value="${partnerProposal.partnerBean.fullDegreeName}"/>,
        		        			<c:out value="${partnerProposal.partnerBean.instituteBean.name}"/>,
        		        			<c:out value="${partnerProposal.partnerBean.instituteBean.city}"/>,
        		        			<c:if test="${fn:length(partnerProposal.partnerBean.instituteBean.state) > 0}">
	        		        			<c:out value="${partnerProposal.partnerBean.instituteBean.state}"/>,
	        		        		</c:if>
        		        			<c:out value="${partnerProposal.partnerBean.instituteBean.country.name}"/>
        		        			</li>
        		        		</c:forEach>
        		        		</ul>
        		        		</c:when>
        		        		<c:otherwise>
        		        			<div align="right">
        		        				לא הוזנו חוקרים שותפים
        		        			</div>
        		        		</c:otherwise>
        		        		</c:choose>
        		        	</td>
        		        </th>

        		       <c:if test="${command.proposalBean.stateId==DRAFT && !viewDetails}">

        		        <c:choose>
        		        	<c:when test="${command.proposalBean.fund.deskId > 0}">

        		        <tr>
        		        	<td colspan="2">
        		        		&nbsp;
        		        	</td>
        		        </tr>
        		        <tr>
        		        	<th align="right">
	        					 הוספת חוקר שותף:
	        				</th>
	        			</tr>
	        			<tr>
        		        	<th colspan="2">
        		        	 &nbsp;
        		        	</th>
        		        </tr>
	        			<tr>
							<td>
								שם:
							</td>
							<td>
									<form:input 	id="partnerInput" htmlEscape="true" cssClass="green" path="partner.name" size="50"/>
							</td>
						</tr>
						<tr>
        		        	<td>&nbsp;</td>
        		        	<td colspan="2">
								<span class="formError"><form:errors path="partner.name" /></span>
							</td>
						</tr>

						<tr>
							<td>
								תואר:
							</td>
							<td>
									<form:select cssClass="green" path="partner.degree">
										<form:option value="">בחר תואר</form:option>
										<form:option value="Prof.">Prof.</form:option>
										<form:option value="Dr.">Dr.</form:option>
										<form:option value="Mrs.">Mrs.</form:option>
										<form:option value="Mr.">Mr.</form:option>
									</form:select>


							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td colspan="2">
								<span class="formError"><form:errors path="partner.degree" /></span>
							</td>
						</tr>
						<tr>
							<td>
								 יבשת/איזור:
							</td>
							<td>
        		        		<select id="continent" class="green" style="width: 230px">
        		        			<option>בחר/י</option>
        		        			<c:forEach items="${continents}" var="continent">
        		        				<option value="<c:out value="${continent.id}"/>"><c:out value="${continent.name}"/></option>
        		        			</c:forEach>
        		        		</select>
        		        	</td>
						</tr>
						<tr id="country">
							<td>
								 מדינה:
							</td>
							<td>
        		        		<select id="countrySelect" class="green" style="width: 230px">
        		        			<option>בחר/י</option>
        		        		</select>
        		        	</td>
						</tr>
						<tr id="institute">
							<td>
								הקלד/י ובחר/י שם מוסד:
							</td>
							<td>
        		        		<form:input id="instituteInput" path="institute.name" cssClass="green" size="50"/>
        		        	</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td colspan="2">
								<form:errors cssClass="formError" path="institute.name" />
							</td>
						</tr>
						<tr>
							<td>
								<button class="grey" id="partnerAddButton" >הוסף</button>
							</td>
						</tr>
						<c:if test="${fn:length(command.proposalBean.partnerProposalBeans)>0}">
						<tr>
        		        	<th colspan="2">
        		        	 &nbsp;
        		        	</th>
        		        </tr>
						<tr>
							<td>
								הסרת חוקר שותף:
							</th>
							<td>
									<form:select id="partnerRemoveSelect" cssClass="green" path="removedPartnerId">
        		        				<form:option value="0">בחר שותף להסרה</form:option>
        		        					<c:forEach items="${command.proposalBean.partnerProposalBeans}" var="partnerProposal">
        		        						<form:option value="${partnerProposal.partnerId}"> <c:out value="${partnerProposal.partnerBean.name}"/> </form:option>
        		        					</c:forEach>
        		        			</form:select>
        		        	</td>
        		        </tr>
        		        </c:if>
        		        </c:when>
        		        <c:otherwise>
							<tr>
								<td colspan="2">
			        		        	ניתן יהיה לטפל בחוקרים שותפים לאחר שהרשות למו"פ תטפל במממן החדש שהוסף.
			        		     </td>
			        		 </tr>
        		        </c:otherwise>
        		        </c:choose>
        		        </c:if>
        		      </c:when>
        		      <c:otherwise>
        		      		<tr>
        		      			<td colspan="2">
        		      				ניתן יהיה להוסיף שותפים לאחר שיבחר מממן
        		      			</td>
        		      		</tr>
        		      	</c:otherwise>
        		      </c:choose>

