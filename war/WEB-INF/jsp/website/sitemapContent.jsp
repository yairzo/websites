<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<div class="container clearfix">
				<div class="breadcrumbs clearfix">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<table>
					<tr>
					<td style="vertical-align:top">
				   		<ul class="list_content_${lang.dir}">
 						<li>
						<a href="${rootCategoryHeb.url}">${rootCategoryHeb.name}</a>
    					<c:choose>
    					<c:when test="${fn:length(hebCategories) > 0}">
    					<ul class="list_content_${lang.dir}">
						<c:forEach items="${hebCategories}" var="hebCategory" varStatus="varStatus">
 							<li>
 							<a href="${hebCategory.url}">${hebCategory.name}</a>
      						<c:choose>
    						<c:when test="${fn:length(hebCategory.subCategories) > 0}">
							<ul class="list_content_${lang.dir}">
							<c:forEach items="${hebCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
      							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul class="list_content_${lang.dir}">
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
  						</c:choose> 
  						</li>
  						</ul>
 					</td>
 					<td style="vertical-align:top">	
  				  		<ul class="list_content_${lang.dir}">
 						<li>
						<a href="${rootCategoryEng.url}">${rootCategoryEng.name}</a>
    					<c:choose>
    					<c:when test="${fn:length(engCategories) > 0}">
    					<ul  class="list_content_${lang.dir}">
						<c:forEach items="${engCategories}" var="engCategory" varStatus="varStatus">
 							<li>
 							<a href="${engCategory.url}">${engCategory.name}</a>
       						<c:choose>
    						<c:when test="${fn:length(engCategory.subCategories) > 0}">
							<ul  class="list_content_${lang.dir}">
							<c:forEach items="${engCategory.subCategories}" var="category" varStatus="varStatus">
     							<li>
 								<a href="${category.url}">${category.name}</a>
     							<c:choose>
    							<c:when test="${fn:length(category.subCategories) > 0}">
								<ul class="list_content_${lang.dir}">
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
  						</c:choose> 
  						</li>
  						</ul>
  					</td>
  					</tr>
  					</table>
  				</div>
			</div>