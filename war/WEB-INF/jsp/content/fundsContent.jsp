<%@ page  pageEncoding="UTF-8" %>


          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="ניהול אתר האוניברסיטה"/>
          	        <c:set var="pageName" value="רשימת דפי טקסט"/>
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
            <form:form id="form" name="form" method="POST" commandName="command" action="funds.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>

 
			<table width="800" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
            <tr>
                  <td colspan="2" align="center"><h1>רשימת קרנות מימון</h1>
                  </td>
             </tr>				
			<tr>
	    	<td>
			<button class="grey" onclick="window.location='fund.html?action=new';return false;">הוסף</button>
			</td>
			</tr>
            <tr>
                  <td>&nbsp;</td>
            </tr>				
    		<c:choose>
    		<c:when test="${fn:length(funds) > 0}">
			<c:forEach items="${funds}" var="fund" varStatus="varStatus">
             <tbody>
   				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right">
				<a href="/fund.html?id=${fund.financialId}"><c:out value="${fund.id}"></c:out></a>
  				</td>
  				<td align="right">
				<a href="/fund.html?id=${fund.financialId}"><c:out value="${fund.name}"></c:out></a>
				</td>
  				<td align="right">
				<a href="/fund.html?id=${fund.financialId}"><c:out value="${fund.shortName}"></c:out></a>
				</td>
  				<td align="right">
				<a href="/fund.html?id=${fund.financialId}"><c:out value="${fund.financialId}"></c:out></a>
				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין קרנות  
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose> 


            <tr>
                  <td colspan="2" align="center"><h1>רשימת קרנות מימון זמניות</h1>
                  </td>
             </tr>				
    		<c:choose>
    		<c:when test="${fn:length(temporaryFunds) > 0}">
			<c:forEach items="${temporaryFunds}" var="tempFund" varStatus="varStatus">
             <tbody>
   				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right">
				<a href="/fund.html?id=${tempFund.financialId}"><c:out value="${tempFund.id}"></c:out></a>
  				</td>
  				<td align="right">
				<a href="/fund.html?id=${tempFund.financialId}"><c:out value="${tempFund.name}"></c:out></a>
				</td>
  				<td align="right">
				<a href="/fund.html?id=${tempFund.financialId}"><c:out value="${tempFund.shortName}"></c:out></a>
				</td>
  				<td align="right">
				<a href="/fund.html?id=${tempFund.financialId}"><c:out value="${tempFund.financialId}"></c:out></a>
				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין קרנות זמניות  
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose> 
	    <tr>
	    	<td>
	    		&nbsp;
	    	</td>
	    </tr>
	    <tr>
	    <td>
			<button class="grey" onclick="window.location='fund.html?action=new';return false;">הוסף</button>
		</td>
		</tr>

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
