 <%@ page  pageEncoding="UTF-8" %>
 <%@ include file="/WEB-INF/jsp/include/include.jsp" %>
     <table width="1120" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" commandName="command" action="sitemap.html">
            	<input type="hidden" id="listViewPage" name="listView.page" value="${command.listView.page}"/>
            	<input type="hidden" id="listViewOrderBy" name="listView.orderBy" value="${command.listView.orderBy}"/>
	

              <table width="800" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
   					<td class="container" style="width: 68%; vertical-align: top;text-align: center;">
     					<table style="width: 100%;">
               	 		<tr>
                 		 <td colspan="2" align="center"><h1>קטגוריות</h1>	
                 		 </td>
                		</tr>
               	 		<tr>
               	 		<td>
   						<ul>
 						<li>
						<a href="${rootCategoryHeb.url}">${rootCategoryHeb.name}</a>
    					<c:choose>
    					<c:when test="${fn:length(hebCategories) > 0}">
    					<ul>
						<c:forEach items="${hebCategories}" var="hebCategory" varStatus="varStatus">
 							<li>
 							<a href="${hebCategory.url}">${hebCategory.name}</a>
      						<c:choose>
    						<c:when test="${fn:length(hebCategory.subCategories) > 0}">
							<ul>
							<c:forEach items="${hebCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
      							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul>
								<c:forEach items="${category.subCategories}" var="subCategory" varStatus="varStatus">
 									<li>
 									<a href="${subCategory.url}">${subCategory.name}</a>
 									</li>
	   							</c:forEach>
	   							</ul>
	  							</c:when>
  								</c:choose> 
	   							</li>
 	   						</c:forEach>
 	   						</ul>
 	  						</c:when>
  							</c:choose> 
	   					</c:forEach>
	   					</ul>
 	  					</c:when>
  	  					<c:otherwise>
   	  			  			<tr style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין קטגוריות 
   							</td>
  							</tr>
  						</c:otherwise>
  						</c:choose> 
  						</li>
  						</ul>
  						</td>
  						</tr>
              	 		<tr>
               	 		<td>
   						<ul>
 						<li>
						<a href="${rootCategoryEng.url}">${rootCategoryEng.name}</a>
    					<c:choose>
    					<c:when test="${fn:length(engCategories) > 0}">
    					<ul>
						<c:forEach items="${engCategories}" var="engCategory" varStatus="varStatus">
 							<li>
 							<a href="${engCategory.url}">${engCategory.name}</a>
       						<c:choose>
    						<c:when test="${fn:length(engCategory.subCategories) > 0}">
							<ul>
							<c:forEach items="${engCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
     							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul>
								<c:forEach items="${category.subCategories}" var="subCategory" varStatus="varStatus">
 									<li>
 									<a href="${subCategory.url}">${subCategory.name}</a> 
 									</li>
	   							</c:forEach>
	   							</ul>
	  							</c:when>
  								</c:choose> 
	   							</li>
 	   						</c:forEach>
 	   						</ul>
 	  						</c:when>
  							</c:choose> 
	   					</c:forEach>
	   					</ul>
 	  					</c:when>
  	  					<c:otherwise>
   	  			  			<tr style="height: 30px;">
  							<td colspan="4" align="center" style="padding: 0px 20px;">
  								אין קטגוריות 
   							</td>
  							</tr>
  						</c:otherwise>
  						</c:choose> 
  						</li>
  						</ul>
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
