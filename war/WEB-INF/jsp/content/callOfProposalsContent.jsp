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
      <table width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="urls.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>רשימת דפי קולות קוראים</h1>
                  </td>
                </tr>
                </table>

				<table width="800" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
    		<c:choose>
    		<c:when test="${fn:length(callOfProposals) > 0}">
			<c:forEach items="${callOfProposals}" var="callOfProposal" varStatus="varStatus">
             <tbody>
   				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right">
				<a href="callForProposal.html?id=${callOfProposal.id}"><c:out value="${callOfProposal.id}"></c:out></a>
  				</td>
  				<td align="right">
				<a href="callForProposal.html?id=${callOfProposal.id}"><c:out value="${callOfProposal.title}"></c:out></a>
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


                  </table>
                </td>
              </tr>
			  <tbody>


            </table>

            <br>
          </td>
        </tr>
      </table>
      </form:form>
    </td>
  </tr>
</table>
</body>
</html>
