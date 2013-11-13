<%@ page language="java" contentType="text/html;charSet=windows-1255 or charset=windows-1255" info="HUARD test page" import="huard3.utils.*"%>
<jsp:useBean id="busysViewer" scope="page" class="huard3.utils.BusysListViewer"/>
<jsp:setProperty name="busysViewer" property="*"/>

<html>
	<body>
		<table border=1>
			<tr>
				<td> Ard Number </td>
				<td> Catcher </td>
				<td> Catch Time </td>
			</tr>
			
				<% for (int i=0; i< busysViewer.getBusyInfoPagesArray().length; i++){%>
					<tr>
						<td><%= busysViewer.getBusyInfoPagesArray()[i].getNum()%>&nbsp;</td>
						<td><%= busysViewer.getBusyInfoPagesArray()[i].getUsername()%>&nbsp;</td>
						<td><%=busysViewer.getFormatTime(busysViewer.getBusyInfoPagesArray()[i].getCreationTime())%>&nbsp;</td>
					</tr>
				<%}%>
			
			
		</table>
	</body>
</html>


