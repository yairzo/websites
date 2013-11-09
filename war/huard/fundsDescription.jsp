<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="fundsProfile" scope="page" class="huard.website.viewer.profile.FundsProfile" />
<jsp:setProperty name="fundsProfile" property="*"/>


<span dir="<%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("fundNum"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "rtl" : "ltr" %>" style="text-align: <%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("fundNum"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].isHebrew() ? "right" : "left" %>;">
<%=fundsProfile.getFunds()[Integer.parseInt(request.getParameter("fundNum"))].getInfoPages()[Integer.parseInt(request.getParameter("infoPage"))].getFormatedDescription()%>
</span>
