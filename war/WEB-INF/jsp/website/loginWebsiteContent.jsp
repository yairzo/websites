<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
 			<div class="container clearfix">
				<div class="breadcrumbs clearfix" style="direction: ${lang.dir}; text-align: ${lang.align}">
					&nbsp;
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" style="text-align: ${lang.align}; direction: ${lang.dir};">
					<h1 class="maintitle" style="text-align: ${lang.align}">${title}</h1>
					<div class="maintext_${lang.dir} clearfix mar_20">
       						<form method="post" action="j_acegi_security_check">
       							<input type="hidden" name="ilr" value="${ilr}"/>
								<input type="hidden" name="mts" value="post"/>
									<div class="login_${lang.dir}">
									${generalLoginInstructions}
									</div>
									<div>
										&nbsp;
									</div>
									<c:if test="${loginFailure}">
									<div>
										<fmt:message key="${lang.localeId}.general.login.loginFailure.1"/>
									</div>
									<div>
										&nbsp;
									</div>
									</c:if>
									<div class="clearfix" style="width: 350px; margin-${lang.align}: 70px;border: 2px solid #ececec; padding: 12px">
										<div class="login_box_col">
											<label class="login_label"><fmt:message key="${lang.localeId}.general.login.username"/></label>
											<input type="text" htmlEscape="true" placeholder="<fmt:message key="${lang.localeId}.website.loginPlaceholder"/>" id="j_username" name="j_username" class="login_input">
										</div>
										<div class="login_box_col pull-${lang.alignOpp}">
											<label for="password" class="login_label"><fmt:message key="${lang.localeId}.general.login.password"/></label>
											<input type="password" name="j_password" class="login_input">
										</div>
										
										<div class="login_box_col mar_15">
											<input type="submit" value="<fmt:message key="${lang.localeId}.general.login.login"/>" class="login_submit">
										</div>
										
									</div>
									<div>
										&nbsp;
									</div>
									<div>
										<fmt:message key="${lang.localeId}.general.login.loginProblemsInstructions.1"/>
									</div>
     						</form>       						
 					</div>
				</div>
			</div>
