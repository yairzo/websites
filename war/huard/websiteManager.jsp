<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<jsp:useBean id="manager" scope="page" class="huard.website.util.WebsiteManager" />
<jsp:setProperty name="manager" property="*"/>

<html>
<head>
<meta http-equiv="Refresh" content="5"/>
</head>

<body>
Max Memory for JVM: <%= manager.getMaxMemory()%><br>
Total Memory in JVM: <%= manager.getTotalMemory()%><br>
Free Memory in JVM: <%= manager.getFreeMemory()%><br>
Total - Free: <%= manager.getUsedMemory()%>

<br>
<br>

<% Runtime.getRuntime().gc();%>

Max Memory for JVM: <%= manager.getMaxMemory()%><br>
Total Memory in JVM: <%= manager.getTotalMemory()%><br>
Free Memory in JVM: <%= manager.getFreeMemory()%><br>
Total - Free: <%= manager.getUsedMemory()%>

</body>
</html>