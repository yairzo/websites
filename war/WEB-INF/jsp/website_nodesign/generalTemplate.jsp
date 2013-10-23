<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

		<title><fmt:message key="${lang.localeId}.website.title"/></title>  


		<jsp:include page="${templateScripts}" />
	</head>
	<body>
	
		<table width="100%" border="0">
		<tr>
    		<td>
			<%@ include file="/WEB-INF/jsp/website/topMenu.jsp" %>		
       		</td>
		</tr>
		</table>
		<table width="100%" border="0">
		<tr>
    		<td>
				<%@ include file="/WEB-INF/jsp/website/location.jsp" %>	
      		</td>
		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="10" height="100%" width="100%" dir="${lang.dir}">
			<tr>
				<td width="200" valign="top">
					<%@ include file="/WEB-INF/jsp/website/sideMenu.jsp" %>		
				</td>
				<td valign="top">
					<jsp:include page="${templateBody}" />
				</td>
			</tr>
			</tbody>
		</table>
		<%@ include file="/WEB-INF/jsp/website/allRightsReserved.jsp" %>	
		<%@ include file="/WEB-INF/jsp/website/googleAnalytics.jsp" %>	
	</body>
</html>