<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
 			<div class="container">
				<div class="breadcrumbs clearfix">
					<jsp:include page="location.jsp"/>
				</div>
				<h1 class="title">${command.title}</h1>
				<div class="content-box clearfix">
				<br>
 				<c:choose>
				<c:when test="${command.showFile}">
				    <div style="text-align: ${lang.align}; direction: ${lang.dir};"><a href="/page/${command.attachment.filename}"><fmt:message key="${lang.localeId}.textualPage.downloadFile"/></a></div>
					<iframe src="https://docs.google.com/gview?url=https%3A%2F%2F${server}%2Fpage%2F${command.attachment.filename}&amp;embedded=true" style="width:686px; height:700px;" frameborder="0"></iframe>
				</c:when>
				<c:when test="${command.wrapExternalPage}">
				   <c:if test="${listBean.sortEnabled}">
					<div class="filter clearfix">
					<h3 class="filter-title pull-left">Filter list</h3>
					<form action="#" method="post">
						<input type="hidden" name="urlTitle" value="${command.urlTitle }">
						<label class="pull-left">Name </label>
						<div class="select select-long pull-left">
 							<input type="text" class="fancyTextAutocomplete" id="searchPhrase" name="filterOrganizationUnit">

                           <!-- <div class="fancyText">Type or select</div>
							<select required="" class=" fancySelect" name="filterOrganizationUnit">
								<option value=" ">Type or select</option>
								<c:forEach items="${completeListForFilter.viewableBeans}" var="viewableBean" varStatus="varStatus">					
									<option value="${viewableBean.fields[0].text}">${viewableBean.fields[0].text}</option>
								</c:forEach>
							</select> -->
						</div>
						<button type="submit" class="filter-submit pull-right">Filter &nbsp;<img src="/image/nano/arrow-submit.png" alt="" /></button>
					</form>
					</div>
				   </c:if>									
					<!--<c:if test="${aCompoundView}">
						<div>
						${list.preface}
						</div><br>
						<c:forEach items="${list.sublistsBeans}" var="listBean" varStatus="varStatusLists">
							<div>
							<h3><c:out escapeXml="false" value="${listBean.displayName}"/></h3>
							</div>
					<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
					<div class="box">
						<div class="clearfix">
							<div class="box-image pull-left">
							<img src="/imageViewer?urlTitle=${viewableBean.fields[2].text}&attachType=bodyImage" height="100px" width="100px"/>
							</div>
							<div class="box-text pull-left">
							<h4 class="box-text-title"><c:out escapeXml="false" value="${viewableBean.fields[0].text}"/> <span>${viewableBean.fields[0].text}</span></h4>
							<nav>
								<p>${viewableBean.fields[0].text}</p>
							</nav>
							</div>
						</div>
						<div id="collapse${varStatus.index}" class="box-full">
							<p>${viewableBean.fields[0].text}</p>
						</div>
						<div class="box-links">
							<a href="mailto:${viewableBean.fields[1].text}" class="box-contact"><img src="/image/nano/i-contact.png" alt="" /> contact</a>
							<a href="#collapse${varStatus.index}" class="box-more">read more <img src="/image/nano/arrow-submit.png" alt="" /></a>
						</div>
					</div>
					</c:forEach>
					</c:forEach>
					</c:if>-->
					<c:if test="${!aCompoundView}">
					<div>
						${listBean.preface}
					</div>
					<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
					<div class="box">
						<div class="clearfix">
							<div class="box-image pull-left">
							<img src="/imageViewer?urlTitle=${viewableBean.fields[0].text}&attachType=bodyImage" height="100px" width="100px"/>
							</div>
							<div class="box-text pull-left">
							<h4 class="box-text-title"><c:out escapeXml="false" value="${viewableBean.fields[1].text}"/> <span>${viewableBean.fields[2].text}</span></h4>
							${viewableBean.fields[3].text}
							</div>
						</div>
						<div id="collapse${varStatus.index}" class="box-full">
							<p>${viewableBean.fields[5].text}</p>
						</div>
						<div class="box-links">
							<a href="mailto:${viewableBean.fields[4].text}" class="box-contact"><img src="/image/nano/i-contact.png" alt="" /> contact</a>
							<c:if test="${listBean.viewableColumnsCount>5}">
							<a href="#collapse${varStatus.index}" class="box-more">read more <img src="/image/nano/arrow-submit.png" alt="" /></a>
							</c:if>
						</div>
					</div>
					</c:forEach>
					<div>
						${listBean.footer}
					</div>
					</c:if>
   			</c:when>
    		<c:otherwise>
    				${command.html}
    		</c:otherwise>
    		</c:choose>
			</div>
		</div>
