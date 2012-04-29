<%@ page  pageEncoding="UTF-8" %>

<script language="Javascript">

$(document).ready(function() {


	$('.pageStatus').click(function(){
 		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
 	});

});

</script>



          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת קישורים"/>
          	        <c:set var="pageName" value="רשימת קישורים"/>
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
                  <td colspan="2" align="center"><h1>רשימת קישורים</h1>
                  </td>
                </tr>
               <!-- <tr>
                  <td colspan="2">
                   כותרת:<input type="text" id="searchPhrase" class="green medium220" name="searchPhrase"/>
                   </td>
                </tr>-->
               <tr>
                  <td colspan="2">
  				   <input class="pageStatus" type="radio" name="pageStatus" class="green" value="0" <c:if test="${pageStatus==0}">checked="checked"</c:if>/>  קישורים מתים
  				   <input class="pageStatus" type="radio" name="pageStatus" class="green" value="1" <c:if test="${pageStatus==1}">checked="checked"</c:if>/>  קישורים מתים וחשודים
   				   <input class="pageStatus" type="radio" name="pageStatus" class="green" value="2" <c:if test="${pageStatus==2}">checked="checked"</c:if>/>  כל הקישורים
                 </td>
                </tr>
 
               </table>

				<table width="800" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
               <tr>
                  <td colspan="4"><img src="image/hr.gif" width="800" height="10"></td>
                </tr>

    		<tr>
    			<td colspan="4">
    				<span style="text-align: center;"><h2>דפי קולות קוראים</h2></span>
    			</td>
    		</tr>

   			<c:choose>
    		<c:when test="${fn:length(infoPagesURLs) > 0}">
				<!-- <tr>
  				<th>Ard</th>
  				<th>כותרת</th>
  				<th>קישור</th>
  				<th>סטטוס</th>
  	  			</tr> -->
			<c:forEach items="${infoPagesURLs}" var="infoPagesURL" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right" <c:choose><c:when test="${infoPagesURL.isDead}">style="color:red"</c:when></c:choose>>
				<c:out value="${infoPagesURL.ardNum}"></c:out>
  				</td>
  				<td align="right" <c:choose><c:when test="${infoPagesURL.isDead}">style="color:red"</c:when></c:choose>>
				<c:choose><c:when test="${fn:length(infoPagesURL.urlText)>0}"><c:out value="${infoPagesURL.urlText}"></c:out></c:when><c:otherwise>ללא כותרת</c:otherwise></c:choose>
				</td>
 				<td align="right">
				<a href="${infoPagesURL.url}" class="<c:choose><c:when test="${infoPagesURL.isDead}">red</c:when></c:choose>"><c:out value="${infoPagesURL.url}"></c:out></a>
  				</td>
				<td align="right" <c:choose><c:when test="${infoPagesURL.isDead}">style="color:red"</c:when></c:choose>>
				<c:out value="${infoPagesURL.status}"></c:out>
  				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין קישורים 
   						</td>
  					</tr>
  			</c:otherwise>
  			</c:choose>  	  		


    		<tr>
    			<td colspan="4">
    				<span style="text-align: center;"><h2>דפים ציבוריים</h2></span>
    			</td>
    		</tr>
   			<c:choose>
    		<c:when test="${fn:length(pubPagesURLs) > 0}">
				<!-- <tr>
  				<th>Ard</th>
  				<th>כותרת</th>
  				<th>קישור</th>
  				<th>סטטוס</th>
  	  			</tr> -->
			<c:forEach items="${pubPagesURLs}" var="pubPagesURL" varStatus="varStatus">
             <tbody>
  				<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
				<td align="right" <c:choose><c:when test="${pubPagesURL.isDead}">style="color:red"</c:when></c:choose> >
				<c:out value="${pubPagesURL.ardNum}"></c:out>
  				</td>
  				<td align="right" <c:choose><c:when test="${pubPagesURL.isDead}">style="color:red"</c:when></c:choose>>
				<c:choose><c:when test="${fn:length(pubPagesURL.urlText)>0}"><c:out value="${pubPagesURL.urlText}"></c:out></c:when><c:otherwise>ללא כותרת</c:otherwise></c:choose>
				</td>
 				<td align="right">
				<a href="${pubPagesURL.url}" class="<c:choose><c:when test="${pubPagesURL.isDead}">red</c:when></c:choose>"><c:out value="${pubPagesURL.url}"></c:out></a>
  				</td>
				<td align="right" <c:choose><c:when test="${pubPagesURL.isDead}">style="color:red"</c:when></c:choose>>
				<c:out value="${pubPagesURL.status}"></c:out>
  				</td>
   	  			</tr>
  	  		</tbody>
	   		</c:forEach>
 	  		</c:when>
  	  		<c:otherwise>
  	  			  	<tr class="darker" style="height: 30px;">
  						<td colspan="4" align="center" style="padding: 0px 20px;">
  							אין קישורים 
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
