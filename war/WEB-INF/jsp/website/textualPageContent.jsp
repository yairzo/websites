<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
 			<div class="container clearfix">
				<div class="breadcrumbs clearfix" style="direction: ${pageLang.dir}; text-align: ${pageLang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" style="text-align: ${pageLang.align}; direction: ${pageLang.dir};">
					<h1 class="maintitle" style="text-align: ${pageLang.align}">${command.title}</h1>
					<div class="maintext_${pageLang.dir} clearfix mar_20">
       						<c:choose>
    							<c:when test="${command.showFile}">
    							    <div style="text-align: ${lang.align}; direction: ${lang.dir};"><a href="/page/${command.attachment.filename}"><fmt:message key="${lang.localeId}.textualPage.downloadFile"/></a></div>
									<iframe src="https://docs.google.com/gview?url=https%3A%2F%2F${server}%2Fpage%2F${command.attachment.filename}&amp;embedded=true" style="width:686px; height:700px;" frameborder="0"></iframe>
								</c:when>
    							<c:when test="${command.wrapExternalPage}">
									<!--<jsp:include page="/viewList.html?id=${command.externalPageUrl}&iv=1&p=1&a=1" />-->
									<c:if test="${fn:length(command.html)>0}">
										${command.html}
									</c:if>
									
									<c:if test="${aCompoundView}">
									<div class="kol open">
										${list.preface}
									</div>
									<br>
									<c:forEach items="${list.sublistsBeans}" var="listBean" varStatus="varStatusLists">
										<div class="kol open">
										<div class="clearfix">
											<h3 class="kol_title_${pageLang.dir}"><c:out escapeXml="false" value="${listBean.displayName}"/></h3>
										</div>
										<table class="table_kol table_kol_${pageLang.dir}">
										<tr>
											<c:forEach items="${listBean.columnBeans}" var="column" varStatus="varStatus">
											<c:if test="${!column.hidden}">
											<th style="width: ${column.width}%;">${column.columnDisplayName}</th>
											</c:if>
											</c:forEach>
										</tr>
										<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
										<tr>
											<c:forEach items="${viewableBean.fields}" var="field">
											<td <c:if test="${field.isEmailAddress}">class="table_email" </c:if> <c:if test="${field.isWebAddress}">class="table_web" </c:if> align="${field.align}">
											<c:out escapeXml="false" value="${field.prefix}"/><c:out escapeXml="false" value="${field.text}"/><c:out escapeXml="false" value="${field.suffix}"/>
											</td>
											</c:forEach>
										</tr>
										</c:forEach>
										</table>
										</div>
									</c:forEach>
									</c:if>
									<c:if test="${!aCompoundView}">
										<div class="kol open">
										<div class="clearfix">
											<h3 class="kol_title"><c:out escapeXml="false" value="${listBean.displayName}"/></h3>
										</div>
										<table class="table_kol table_kol_${pageLang.dir}">
										<tr>
											<c:forEach items="${listBean.columnBeans}" var="column" varStatus="varStatus">
											<c:if test="${!column.hidden}">
											<th>${column.columnDisplayName}</th>
											</c:if>
											</c:forEach>
										</tr>
									
										<c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
										<tr>
											<c:forEach items="${viewableBean.fields}" var="field">
											<td <c:if test="${field.isEmailAddress}">class="table_email"</c:if> <c:if test="${field.isWebAddress}">class="table_web"</c:if> align="${field.align}">
											<c:out escapeXml="false" value="${field.prefix}"/><c:out escapeXml="false" value="${field.text}"/><c:out escapeXml="false" value="${field.suffix}"/>
											</td>
											</c:forEach>
										</tr>
										</c:forEach>
										</table>
										</div>
									</c:if>
									
									
    							</c:when>
    							<c:otherwise>
    								${command.html}
    							</c:otherwise>
    						</c:choose>
 					</div>
				</div>
			</div>
