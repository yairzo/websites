<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
 			<div class="container clearfix">
				<div class="breadcrumbs clearfix">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">הודעות</h1>
					<div class="clearfix mar_20">
						<c:choose>
    					<c:when test="${fn:length(textualMessages) > 0}">
						<c:forEach items="${textualMessages}" var="textualMessage" varStatus="varStatus">
						<div class="message">
							<div class="message_line clearfix">
								<div class="message_arrow"></div>
								<div class="message_text">
									<h3>${textualMessage.title}</h3>
									<span class="message_date">${textualMessage.creationTimeString }</span>
								</div>
							</div>
							<div class="message_content clearfix">
								${textualMessage.html}
							</div>
						</div>
	   					</c:forEach>
 	  					</c:when>
  	  					<c:otherwise>לא נמצאו הודעות 
  						</c:otherwise>
  						</c:choose>
					
					</div>
				</div>
			</div>
			