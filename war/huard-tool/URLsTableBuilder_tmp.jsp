<%@ page language="java" contentType="text/html;charset=windows-1255" info="HUARD test page" import="huard3.actions.*" %>
<jsp:useBean id="URLsHandler" scope="page" class="huard3.utils.huard.urlsCheck.UrlsChecker" />
<jsp:setProperty name="URLsHandler" property="*"/>

<html>
	<body>
		<% URLsHandler.buildInfoPagesURLsTable(); %>
		<% //URLsHandler.buildTabledInfoPagesMailURLsTable(); %>
		<% //URLsHandler.updateURLsStatusAndSizeInURLsTable(); %>
		<% //URLsHandler.notifyPeopleWhoseMailAddressIsOnTheSite(); %>
		<% //URLsHandler.updateMailURLsStatusInMailURLsTable(); %>
	</body>
</html>
