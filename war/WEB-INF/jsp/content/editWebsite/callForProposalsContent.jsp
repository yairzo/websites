<%@ page  pageEncoding="UTF-8" %>

	
          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="ניהול אתר האוניברסיטה"/>
          	        <c:set var="pageName" value="רשימת קולות קוראים"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>

          </td>

        </tr>

      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="1120" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" >
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="callForProposals.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="1120" border="0" align="center" cellpadding="3" dir="${lang.dir}">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%;">
                	 	<tr>
                 		 <td align="center"><h1><fmt:message key="${lang.localeId}.callForProposal.searchCallForProposals"/></h1>	</td>
                		</tr>
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" id="searchWords" name="searchWords" value="${searchWords}"/> 
							<hidden id="fundId" value="${fundId}"/>
							</td>
						</tr>
 						<tr class="form">
						<td>
   			   			<fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> <fmt:message key="${lang.localeId}.callForProposal.submissionTimeFrom"/>  <input type="text" name="submissionDateFrom" id="submissionDateFrom" class="green date cancelExpiredCheckbox medium80" value="${submissionDateFrom}"/>
  			   			<fmt:message key="${lang.localeId}.callForProposal.submissionTimeTo"/> <input type="text" name="submissionDateTo" id="submissionDateTo" class="green date cancelExpiredCheckbox medium80" value="${submissionDateTo}"/> 
       					</td>
       					</tr>
                 		<tr class="form">
						<td align="${lang.align}">
  			   				<input type="checkbox" name="searchByAllYear" id="searchByAllYear" class="green" <c:if test="${searchByAllYear}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/> 
 			   				<input type="checkbox" name="searchOpen" id="searchOpen" class="green" <c:if test="${searchOpen}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.open"/> 
  			   				<input type="checkbox" name="searchExpired" id="searchExpired" class="green" <c:if test="${searchExpired}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.expired"/>
       					</td>
       					</tr>
                   		<tr class="form">
						<td align="${lang.align}">
  			   				<input type="checkbox" name="searchDeleted" id="searchDeleted" class="green" <c:if test="${searchDeleted}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.deleted"/> 
       					</td>
       					</tr>
						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.desk"/>
         				<select name="deskId" id="deskId" class="green" >
      						<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
 	        					<option htmlEscape="true" value="${mopDesk.id}" <c:if test="${mopDesk.id==deskId}">selected</c:if> >
	        					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></c:if>
	        					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${mopDesk.englishName}"/></c:if>
      						</c:forEach>
        		        </select>
						</td>
						</tr>
 						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.type"/>
         				<select name="typeId" id="typeId" class="green" >
      						<option value="0" <c:if test="${typeId==0}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
      						<option value="1" <c:if test="${typeId==1}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></option>
      						<option value="2" <c:if test="${typeId==2}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></option>
      						<option value="3" <c:if test="${typeId==3}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.conference"/></option>
      						<option value="4" <c:if test="${typeId==4}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></option>
      						<option value="5" <c:if test="${typeId==5}">selected</c:if> ><fmt:message key="${lang.localeId}.callForProposal.prizes"/></option>
        		        </select>
						</td>
						</tr>
  						<tr class="form">
						<td>
						<fmt:message key="${lang.localeId}.callForProposal.targetAudience"/>
         				<select name="targetAudience" id="targetAudience" class="green" >
      						<option value="0" <c:if test="${targetAudience==0}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.all"/></option>
      						<option value="1" <c:if test="${targetAudience==1}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.researcher"/></option>
      						<option value="2" <c:if test="${targetAudience==2}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.doctoral"/></option>
      						<option value="3" <c:if test="${targetAudience==3}">selected</c:if>><fmt:message key="${lang.localeId}.callForProposal.targetAudience.postdoctoral"/></option>
        		        </select>
						</td>
						</tr>
                  		<tr>
						<td align="${lang.align}">
  			   			<input type="checkbox" name="searchByTemporary" id="searchByTemporary" class="green" <c:if test="${temporaryFund}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.temporaryFund"/>
       					</td>
       					</tr>
						<tr class="form subjectsRow" style="display:none">
						<td align="center">
						<c:set var="callForProposalSearch" value="true"/>
						<%@ include file="/WEB-INF/jsp/content/editPost/subjects.jsp" %>					
						</td>
						</tr>
                   		<tr>
						<td align="${lang.align}">
  			   				<input type="checkbox" name="searchByAllSubjects" id="searchByAllSubjects" class="green" <c:if test="${searchByAllSubjects}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.allSubjects"/>
        				</td>
       					</tr>
       					<tr>
       					<td>
       					<fmt:message key="${lang.localeId}.callForProposal.selectCountry"/>
						 <input type="text" class="green" style="width:130" id="selectCountry"/> 
						 <hidden id="countryId" name="countryId" />
						 <button class="grey addCountry"><fmt:message key="${lang.localeId}.callForProposal.addCountry"/></button>
						<div id="countryDiv">
							<c:forEach items="${countries}" var="country" varStatus="varStatus">
							<p><input type="checkbox" class="countryCheck" id="${country.id}">
								<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${country.nameHebrew}"/></c:if>
	        					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${country.name}"/></c:if>
							</p>
							</c:forEach> 
						</div>
						<div id="deleteCountry" style="display:none">
							<a href="" class="deleteCountry"><fmt:message key="${lang.localeId}.callForProposal.deleteCountry"/></a>
						</div>
       					</td>
       					</tr>
                  		<!-- <tr>
						<td align="${lang.align}">
  			   				<input type="checkbox" name="searchByAllCountries" id="searchByAllCountries" class="green" <c:if test="${searchByAllCountries}">checked="checked"</c:if>/> <fmt:message key="${lang.localeId}.callForProposal.allSelectedCountries"/>
        				</td>
       					</tr> -->
                  		<tr>
						<td align="left">
 							<button class="grey search"><fmt:message key="${lang.localeId}.general.search"/></button>&nbsp;
 							<button  class="grey cleanSearch"><fmt:message key="${lang.localeId}.general.cleanSearch"/></button>		
       					</td>
       					</tr>
      					</table>
       				</td>
   					<td class="container" style="width: 68%; vertical-align: top;text-align: center;">
     					<table style="width: 100%;">
               	 		<tr>
                 		 <td colspan="2" align="center"><h1><fmt:message key="${lang.localeId}.callForProposal.callForProposalsList"/></h1>	</td>
                		</tr>
     					<c:choose>
    					<c:when test="${fn:length(callForProposals) > 0}">
						<c:forEach items="${callForProposals}" var="callForProposal" varStatus="varStatus">
             			<tbody>
   							<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
							<td align="${lang.align}">
							<a href="editCallForProposal.html?id=${callForProposal.id}"><c:out value="${callForProposal.id}"></c:out></a>
  							</td>
  							<td align="${lang.align}">
							<a href="editCallForProposal.html?id=${callForProposal.id}"><c:out value="${callForProposal.title}"></c:out></a>
							</td>
   	  						</tr>
  	  					</tbody>
	   					</c:forEach>
 	  					</c:when>
  	  					<c:otherwise>
  	  			  			<tr class="darker" style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין דפים 
   							</td>
  							</tr>
  						</c:otherwise>
  						</c:choose> 
               	 		<tr>
                 		 <td colspan="2"><button class="grey" onclick="window.location='editCallForProposal.html?action=new';return false;"><fmt:message key="${lang.localeId}.callForProposal.createNew"/></button></td>
                		</tr>
						<tr>
                		<td colspan="2" align="center"><br>
							<%@ include file="/WEB-INF/jsp/include/searchPagination.jsp" %>
               			 </td>
                		</tr>
                   		</table>
                	</td>
              </tr>
	          </table>
      </form:form>

            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
