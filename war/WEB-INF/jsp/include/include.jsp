<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%@ taglib prefix="form" uri="/WEB-INF/tld/spring-form.tld"%>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>

<c:if test="${!ajaxView}">
	<link href="/iws/style/style.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery.alerts.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/iws/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="/iws/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="/iws/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.regexp.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.form.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.field.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.bgiframe.js"></script>
	<!--<script type="text/javascript" src="js/jquery.dimensions.js"></script>  -->
	<script type="text/javascript" src="/iws/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="/iws/js/date.js"></script>
</c:if>
