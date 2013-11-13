<jsp:useBean id="authorizer" scope="session" class="huard3.actions.Authorizer"/>
<jsp:setProperty name="authorizer" property="*"/>

<html>
<head>


<% if (authorizer.isAuthorityIp(request.getRemoteAddr())){ %>
 <meta http-equiv="refresh" content="0;
 url=http://ard.huji.ac.il/huard1/main.jsp" />

<%}else{%>
<meta http-equiv="refresh" content="0;
 url=http://ard.huji.ac.il/huard/menu1.jsp?eng=false" />

<%}%>
</head>
<body>