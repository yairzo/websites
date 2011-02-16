<%@ page  pageEncoding="UTF-8" %>


<%--  begin of location line  + end of header --%>



        <tr>
          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="טופס מרובע"/>
          	    <c:set var="pageName" value="עריכת הצעה"/>
          	    <%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
        </tr>
	</table>
    </td>
  </tr>

<%--  end of location line + end of header --%>

<%-- begin of main form --%>

  <tr>
    <td>
    	<table class="green" width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
    	<form:form id="form" name="form" method="POST" commandName="command" enctype="multipart/form-data" >
	    	<form:hidden id="proposalId" path="proposalBean.id"/>
    	   	<form:hidden path="proposalBean.stateId"/>
    	   	<form:hidden path="proposalBean.yearId"/>
        	<tr>
          		<td align="center" valign="top"><br>




            		<table width="95%" border=0  cellspacing=0 cellpadding=0 dir="rtl">
              <tr>
              		<c:set var="tabsCounter" value="1"/>

                <td class="proposalDetailsTabEdge tabEdge" width="7"><img src="image/leshonit_bright_side_right.gif" width="7" height="35"></td>
                <td class="proposalDetailsTab tab" align="right" valign="middle" background="image/leshonit_bright_left.gif">פרטי ההצעה</td>


				<c:set var="tabsCounter" value="${tabsCounter+1}"/>
				<c:set var="isBasicDetailsFilled" value="${fn:length(command.proposalBean.hebrewTitle)>0 && fn:length(command.proposalBean.englishTitle)>0 && command.proposalBean.fundId>0}"/>

				<c:choose>
				<c:when test="${isBasicDetailsFilled}">
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="proposersTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="proposersTab tab"/>
    	         	<c:set var="cssClassTabText" value=""/>
    	         	<c:set var="title" value="לחץ להוספת חוקרים מגישים נוספים"/>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="tabColor" value="grey"/>
    	         		<c:set var="cssClassEdge" value=""/>
    	         		<c:set var="cssClassTab" value=""/>
   	    	         	<c:set var="cssClassTabText" value="tabexpired"/>
	    	         	<c:set var="title" value="ניתן יהיה להוסיף חוקרים מגישים נוספים לאחר הזנת נושא המחקר והמממן"/>
    	          </c:otherwise>
    	         </c:choose>


				<td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	        <td class="${cssClassTab} ${cssClassTabText}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">הוספת מגישים</td>

                <c:choose>
				<c:when test="${fn:length(command.proposalBean.hebrewTitle)>0}">
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="proposalFilesTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="proposalFilesTab tab"/>
    	         	<c:set var="cssClassTabText" value=""/>
    	         	<c:set var="title" value="לחץ להוספת קובץ הצעת המחקר והתחייבות על התנהגות ראויה במחקר"/>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="tabColor" value="grey"/>
    	         		<c:set var="cssClassEdge" value=""/>
    	         		<c:set var="cssClassTab" value=""/>
   	    	         	<c:set var="cssClassTabText" value="tabexpired"/>
	    	         	<c:set var="title" value="ניתן יהיה להוסיף קבצים לאחר שמירת נושא המחקר בעברית ובאנגלית"/>
    	          </c:otherwise>
    	         </c:choose>

					<c:set var="tabsCounter" value="${tabsCounter+1}"/>
					<td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab} ${cssClassTabText}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">קבצים</td>



				<c:choose>
				<c:when test="${command.proposalBean.fundId > 0}">
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="partnersTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="partnersTab tab"/>
    	         	<c:set var="cssClassTabText" value=""/>
    	         	<c:set var="title" value="לחץ לטיפול בחוקרים שותפים"/>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="tabColor" value="grey"/>
    	         		<c:set var="cssClassEdge" value=""/>
    	         		<c:set var="cssClassTab" value=""/>
    	         		<c:set var="cssClassTabText" value="tabexpired"/>
	    	         	<c:set var="title" value="הטיפול בחוקרים שותפים יתאפשר לאחר בחירת מממן"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab} ${cssClassTabText}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">חוקרים שותפים</td>


			<c:choose>
				<c:when test="${command.proposalBean.stateId >= APPROVED_BY_ALL}">
					<c:set var="showTab" value="true"/>

					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="fundingAgencyResponseTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="fundingAgencyResponseTab tab"/>
    	         	<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId == MOP_DESK}">
	    	        	 	<c:set var="title" value="לחץ להזנה/צפייה בתשובת הקרן"/>
						</c:when>
						<c:otherwise>
    	         			<c:set var="title" value="לחץ לצפייה בתשובת הקרן"/>
    	         		</c:otherwise>
    	         	</c:choose>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="showTab" value="false"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:if test="${showTab}">

				<c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">תשובת הקרן</td>

    	           </c:if>

			<c:choose>
				<c:when test="${command.proposalBean.stateId >= FUND_APPROVED}">
					<c:set var="showTab" value="true"/>
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="experimentApprovalsTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="experimentApprovalsTab tab"/>
    	         	<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId <= RESEARCHER}">
	    	        	 	<c:set var="title" value="לחץ להזנה/צפייה באישורים לניסויים"/>
						</c:when>
						<c:otherwise>
    	         			<c:set var="title" value="לחץ לצפייה באישורים לניסויים"/>
    	         		</c:otherwise>
    	         	</c:choose>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="showTab" value="false"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:if test="${showTab}">

					<c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">אישורים לניסויים</td>

    	           </c:if>

				<c:choose>
				<c:when test="${command.proposalBean.stateId >= FUND_APPROVED}">
					<c:set var="showTab" value="true"/>
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="yissumTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="yissumTab tab"/>
    	         	<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId <= RESEARCHER}">
	    	        	 	<c:set var="title" value="לחץ להזנה/צפייה באישורים לניסויים"/>
						</c:when>
						<c:otherwise>
    	         			<c:set var="title" value="לחץ לצפייה באישורים לניסויים"/>
    	         		</c:otherwise>
    	         	</c:choose>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="showTab" value="false"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:if test="${showTab}">

				<c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">&nbsp;יישום</td>

    	           </c:if>

				<c:choose>
				<c:when test="${command.proposalBean.stateId >= EXPERIMENT_APPROVED}">
					<c:set var="showTab" value="true"/>
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="deanApprovalTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="deanApprovalTab tab"/>
    	         	<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId == DEAN}">
	    	        	 	<c:set var="title" value="לחץ להזנה / צפייה באישור הדיקן / מנהל הביהס"/>
						</c:when>
						<c:otherwise>
    	         			<c:set var="title" value="לחץ לצפייה באישור הדיקן / מנהל הביהס"/>
    	         		</c:otherwise>
    	         	</c:choose>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="showTab" value="false"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:if test="${showTab}">

				<c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">אישור הפקולטה</td>

    	           </c:if>

			<c:choose>
				<c:when test="${(command.proposalBean.stateId >= DEAN_APPROVED && command.proposalBean.personProposalBean.typeId != YISSUM)}">
					<c:set var="showTab" value="true"/>
					<c:set var="tabColor" value="bright"/>
    	         	<c:set var="cssClassEdge" value="mopTabEdge tabEdge"/>
    	         	<c:set var="cssClassTab" value="mopTab tab"/>
    	         	<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId == MOP}">
	    	        	 	<c:set var="title" value="לחץ להעברה לתקציבאי או צפייה בפרטי התקציב"/>
						</c:when>
						<c:otherwise>
    	         			<c:set var="title" value="לחץ לצפייה בפרטי התקציב"/>
    	         		</c:otherwise>
    	         	</c:choose>
    	         </c:when>
    	         <c:otherwise>
    	         		<c:set var="showTab" value="false"/>
    	          </c:otherwise>
    	         </c:choose>

    	         <c:if test="${showTab}">

				<c:set var="tabsCounter" value="${tabsCounter+1}"/>

    	         <td class="${cssClassEdge}" width="7" alt="${title}" title="${title}"><img src="image/leshonit_${tabColor}_side_right.gif" width="7" height="35"></td>
    	            <td class="${cssClassTab}" align="right" valign="middle" background="image/leshonit_${tabColor}_left.gif" alt="${title}" title="${title}"">תקציב</td>

    	           </c:if>


    	         <c:set var="minTabsNumber" value="8"/>
    	         <c:set var="emptyTabsToAdd" value="${minTabsNumber - tabsCounter}"/>

    	         <c:if test="${emptyTabsToAdd > 0}">
    	         	<c:forEach var="i" begin="1" end="${emptyTabsToAdd}">
    	         		<td width="7"><img src="image/leshonit_grey_side_right.gif" width="7" height="35"></td>
                		<td align="right" valign="middle" background="image/leshonit_grey_left.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                	</c:forEach>
                </c:if>






              </tr>

            </table>

            <table style="height: 435px; vertical-align: top;" class="tableborder" width="95%" cellspacing=0 cellpadding=10>
              <tr>
                <td align="center" valign="top">
                	<table id="proposalDetailsTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalDetails.jsp" %>
                	</table>
                	<table id="proposalFilesTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalFiles.jsp" %>
                	</table>
                	<table id="proposersTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalProposers.jsp" %>
                	</table>
                	<table id="partnersTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalPartners.jsp" %>
                	</table>
                	<table id="fundingAgencyResponseTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalFundingAgencyResponse.jsp" %>
                	</table>
                	<table id="experimentApprovalsTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalExperimentApprovals.jsp" %>
                	</table>
                	<table id="yissumTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalYissum.jsp" %>
                	</table>
                	<table id="deanApprovalTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalDeanApproval.jsp" %>
                	</table>
                	<table id="mopTable" class="editableTable" dir="rtl">
                	<%@ include file="/WEB-INF/jsp/content/editProposal/editProposalMop.jsp" %>
                	</table>


                </td>
              </tr>


            </table>
            <br>
          </td>
          <td valign="top" width="170">
          	<table width="95%" style="height: 490px"  border="0" align="center" cellpadding="5" cellspacing="2" dir="rtl">
  						<tr style="height: 15px">
  							<td>

  							</td>
  						</tr>
  						<tr>
						    <td width="25%" align="right" valign="top" bgcolor="#f1ecd2">
    							<h2 dir="rtl">מצב מלוי ההצעה:</h1>
    							<c:out value="${proposalStates[command.proposalBean.stateId]}"/>
    						</td>
    					</tr>
    					<tr>
 							<td width="25%" align="right" valign="top" bgcolor="#f1ecd2">
 								<h2 dir="rtl"> פעולות דרושות \ אפשריות:</h1>
 								<c:forEach items="${command.proposalBean.personProposalBean.requiredActionsIds}" var="requiredActionId">
									<fmt:message key="iw_IL.eqfSystem.editProposal.personProposalRequiredAction.${requiredActionId}"/><br>
								</c:forEach>
 							</td>
 						</tr>
 						<tr>
 							<td class="userMessage" width="25%" align="right" valign="top" bgcolor="#f1ecd2">
 								<h2 dir="rtl">הודעות:</h1>
 								<span class="userMessage"><c:out escapeXml="false" value="${userMessage}"/></span>
 							</td>
 						</tr>
 						<tr>
    						<td width="25%" valign="bottom" bgcolor="#f1ecd2" align="center">
    							<table border="0" cellspacing="0" cellpadding="4">
      								<c:if test="${command.proposalBean.readyForApproval && command.proposalBean.stateId < APPROVED_BY_ALL
      									&& !approvedByCurrentUser}">
      								<tr>
      									<td>
      										<button class="approveDetails grey">אשר סופית הפרטים</button>
      									</td>
      								</tr>
      								</c:if>

      								<tr>
        								<td>
        									<button class="cancel grey">הפסק מילוי הטופס</button>
        								</td>
      								</tr>
    							</table>
    						</td>
  						</tr>
					</table>



          </td>

        </tr>
      </table></td>
  </tr>
  </form:form>
</table>
</body>
</html>




