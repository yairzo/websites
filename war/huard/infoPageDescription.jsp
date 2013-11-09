<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="fundsProfile" scope="page" class="huard.website.viewer.profile.FundsProfile" />
<jsp:useBean id="calendarProfile" scope="page" class="huard.website.viewer.profile.CalendarProfile" />
<jsp:useBean id="researchFieldProfile" scope="page" class="huard.website.viewer.profile.ResearchFieldProfile" />
<jsp:setProperty name="researchFieldProfile" property="*"/>
<jsp:useBean id="docTypeProfile" scope="page" class="huard.website.viewer.profile.DocTypeProfile" />
<jsp:setProperty name="docTypeProfile" property="*"/>

<%if(request.getParameter("type").equals("month")){ %>
<table>
<tr>
<td dir="<%=calendarProfile.getInfoPagesByMonth(Integer.parseInt(request.getParameter("param")))[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "rtl" : "ltr" %>" style="text-align: <%=calendarProfile.getInfoPagesByMonth(Integer.parseInt(request.getParameter("param")))[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "right" : "left" %>;">
<%=calendarProfile.getInfoPagesByMonth(Integer.parseInt(request.getParameter("param")))[Integer.parseInt(request.getParameter("infoPage"))].getFormatedDescription()%>
</td>
</tr>
</table>
<%}else if(request.getParameter("type").equals("fund")){ %>
<table>
<tr>
<td dir="<%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("param"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "rtl" : "ltr" %>" style="text-align: <%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("param"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "right" : "left" %>;">
<%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("param"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].getFormatedDescription()%>
</td>
</tr>
</table>
<%}else if(request.getParameter("type").equals("research")){ %>
<table>
<tr>
<td dir="<%=researchFieldProfile.getInfoPagesByResearchField()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "rtl" : "ltr" %>" style="text-align: <%=researchFieldProfile.getInfoPagesByResearchField()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "right" : "left" %>;">
<%= researchFieldProfile.getInfoPagesByResearchField()[Integer.parseInt(request.getParameter("infoPage"))].getFormatedDescription()%>
</td>
</tr>
</table>
<%} else if(request.getParameter("type").equals("doc")){ %>
<table>
<tr>
<td dir="<%=docTypeProfile.getInfoPagesByDocType()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "rtl" : "ltr" %>" style="text-align: <%=docTypeProfile.getInfoPagesByDocType()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "right" : "left" %>;">
<%= docTypeProfile.getInfoPagesByDocType()[Integer.parseInt(request.getParameter("infoPage"))].getFormatedDescription()%>
</td>
</tr>
</table>
<%} %>