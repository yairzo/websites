<%@ page  pageEncoding="UTF-8" %>


<%--  begin of location line  + end of header --%>



        <tr>
          <td align="right" bgcolor="#787669" height="20">
          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          </td>


        </tr>
	</table>
    </td>
  </tr>

<%--  end of location line + end of header --%>

<%-- begin of main form --%>

  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>

            	<form:hidden id="proposalId" path="proposalBean.id"/>
            	<form:hidden path="proposalBean.stateId"/>




      <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

     <tr>
     	<td colspan="2">
     		<table>
     			<authz:authorize ifAnyGranted="ROLE_EQF_MOP">
					<c:if test="${command.proposalBean.hasYearId}">
						<tr>
							<th align="right">
							   	מספור ברשות:
							 </th>
							 <td>
							 	${command.proposalBean.formatedYearId}
							 </td>
							 <td>
							 	&nbsp;
							 </td>
						 </tr>
	    	 		</c:if>
     			</authz:authorize>
     			<tr>
     				<th class="medium">
						סטאטוס ההצעה:
					</th>
					<td>
						<c:out value="${proposalStates[command.proposalBean.stateId]}"/>
					</td>
					<td style="width: 100px; text-align: left; vertical-align: top;">
						<a href="proposalStateHistory.htm?proposalId=${command.proposalBean.id}"/>היסטוריה</a>
					</td>
				</tr>
				<tr>
					<th class="medium" valign="top" >
			<%-- begin of personProposal requiredActions line --%>
						פעולות :
					</th>
					<td>

						<c:forEach items="${command.proposalBean.personProposalBean.requiredActionsIds}" var="requiredActionId">
							<fmt:message key="iw_IL.eqfSystem.editProposal.personProposalRequiredAction.${requiredActionId}"/><br>
						</c:forEach>
			<%-- end of personProposal requiredActions line --%>
					</td>
				</tr>
	<%--  begin of message to user --%>

        	<c:if test="${userMessage!=null}">
        		<tr>
        			<th class="medium">
        				הודעה:
        			</th>
        			<td colspan="2">
        				<c:out escapeXml="false" value="${userMessage}"/>
        			</td>
        		</tr>
        	</c:if>
        	<c:if test="${validationErrors}">
        		<tr>
        			<th class="medium">
        				הודעה:
        			</th>
        			<td colspan="2">
        				<span class="formError">
        					<fmt:message key="iw_IL.eqfSystem.editProposal.validationErrors"/>
        				</span>
        			</td>
        		</tr>
        	</c:if>
        	</table>
        	</td>
        </tr>
	<%--  end of message to user --%>
	<tr>
		<td colspan="2" >&nbsp;<br><img src="image/hr.gif" width="580" height="10"><br>&nbsp;</td>
    </tr>

	<%--  begin of title --%>

                <tr>
                	<th colspan="2" >
                		<h1>בקשה לאישור הצעת מחקר (או להפעלת תקציב) והתחייבות
  "טופס מרובע"</h1>
  					</th>
  					</tr>
  					<tr>
  						<td colspan="2">
  							הוראת הנהלה 21-005
מחקרים שבמימון גורמים חיצוניים
						</td>
					</tr>

	<%--  end of title --%>