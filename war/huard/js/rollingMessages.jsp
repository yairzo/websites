<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="messagesProfile" scope="page" class="huard.website.viewer.profile.MessagesProfile" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="mainPageViewer" scope="page" class="huard.website.viewer.page.MainPageViewer" />
<jsp:setProperty name="language" property="*"/>

<div id="marqueecontainer" onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed">
<div id="vmarquee" style="position: absolute; width: 95%;">
<!--//-->
<!--YOUR SCROLL CONTENT HERE-->

<% for(int j=0; j<messagesProfile.getToRollingMessages().length && messagesProfile.getToRollingMessages()[0].getArdNum()!=0; j++){%>
	<div align="<%=(messagesProfile.isHebrew(messagesProfile.getToRollingMessages()[j].getTitle())) ? "right" : "left" %>">
		<a class="rollingMessages" href="pubPageViewer.jsp?ardNum=<%= messagesProfile.getToRollingMessages()[j].getArdNum()%>&category=<%=(messagesProfile.isHebrew(messagesProfile.getToRollingMessages()[j].getTitle())) ? "הרשות למופ" : "The Authority" %>">
			<font style="font-color: black; font-weight: bold">
				<%=messagesProfile.getFormatedDate(messagesProfile.getToRollingMessages()[j].getPubDate())%><br><%= mainPageViewer.markApostrofWithBackSlash(messagesProfile.getToRollingMessages()[j].getTitle())%>
			</font>
		</a>
	</div>
	<br>
	<br>
<%}%>

<%if(mainPageViewer.getInfoPagesToRollingMessages().length>0){%>
	<%for(int i=0; i<mainPageViewer.getInfoPagesToRollingMessages().length; i++){%>
		<div align="<%= mainPageViewer.isHebrew(mainPageViewer.getInfoPagesToRollingMessages()[i].getTitle()) ? "right" :"left" %>">
		<a class="rollingMessages" href="infoPageViewer.jsp?ardNum=<%= mainPageViewer.getInfoPagesToRollingMessages()[i].getArdNum()%>">
			<font style="font-color: black; font-weight: bold">
				<%=mainPageViewer.getFormatedDate(mainPageViewer.getInfoPagesToRollingMessages()[i].getPubDate())%><br><%= mainPageViewer.markApostrofWithBackSlash(mainPageViewer.getInfoPagesToRollingMessages()[i].getTitle())%><br><%= mainPageViewer.isHebrew(mainPageViewer.getInfoPagesToRollingMessages()[i].getTitle()) ? "תאריך הגשה" : "Submission Date" %> - <%=mainPageViewer.getFormatedDate(mainPageViewer.getInfoPagesToRollingMessages()[i].getSubDate())%>
			</font>
		</a>
		</div>
		<br>
		<br>
	<%}%>
<%}%>


<%if(mainPageViewer.getInfoPagesWithCloseSubmission() != null){%>
	<br><br>
	<% for(int i=0; i<mainPageViewer.getInfoPagesWithCloseSubmission().length; i++){%>
		<div align="<%=mainPageViewer.isHebrew(mainPageViewer.getInfoPagesWithCloseSubmission()[i].getTitle()) ? "right" : "left" %>">
		<a class="rollingMessages" href="infoPageViewer.jsp?ardNum=<%= mainPageViewer.getInfoPagesWithCloseSubmission()[i].getArdNum()%>">
			<font style="font-color: black; font-weight: bold">
				<%=mainPageViewer.getFormatedDate(mainPageViewer.getInfoPagesWithCloseSubmission()[i].getPubDate())%>
				<br>
				<%= mainPageViewer.markApostrofWithBackSlash(mainPageViewer.getInfoPagesWithCloseSubmission()[i].getTitle())%><br><%=mainPageViewer.isHebrew(mainPageViewer.getInfoPagesWithCloseSubmission()[i].getTitle()) ?  "תאריך הגשה" : "Submission Date" %> - <font color="red"><%=mainPageViewer.getFormatedDate(mainPageViewer.getInfoPagesWithCloseSubmission()[i].getSubDate())%></font>
			</font>
		</a>
		</div>
		<%if(i != mainPageViewer.getInfoPagesWithCloseSubmission().length-1){%>
			<br><br>
		<%}%>
	<%}%>
<%}%>


</div>
</div>
