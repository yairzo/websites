<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="infoPageValidator" scope="page" class="huard3.actions.InfoPageValidator"/>
<jsp:setProperty name="infoPageValidator" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>


<% infoPageValidator.insertBlackListWords(); %>

<%if (identify.isAuthorized()){
     if (infoPageValidator.isInfoPageStillHoldedByMe(identify.getUsername())){%>

<head>
  <title>InfoPage Details Validation</title>
</head>

<body>



  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=+2>Validating Your Enteries</font>
  <br>
  <br>
  You Have entered this Information:
  <br>
  <br>

  Ard Number: <%=infoPageValidator.getArdNum()%>
  <br>
  <br>
  Title: <%=infoPageValidator.getTitle()%>
  <br>
  <br>

  Publication Date: <%=infoPageValidator.getPubDate()%>
  <br>
  <br>
  Submission Date: <%=infoPageValidator.getSubDate()%>
  <br>
  <br>
  Second, third, fourth submission dates: <%=infoPageValidator.getAdditionalSubDatesString()%>
  <br>
  <br>
  Fund Full Name: <%=infoPageValidator.getFundFullName()%>
  <br>
  <br>
  Document Type: <%=infoPageValidator.getDocType()%>
  <br>
  <br>
  Desk Id: <%=infoPageValidator.getDeskId()%>
  <br>
  <br>
  Document Owner: <%=infoPageValidator.getDocOwner()%>
  <br>
  <br>
  ResearchFields: <%for (int i=0;i<=infoPageValidator.getAllResearchFieldsOrderdByNum().length-1;i++){
                          if(request.getParameter(infoPageValidator.getAllResearchFieldsOrderdByNum()[i].getResearchFieldShort())!=null) { infoPageValidator.setResearchFieldInResearchFieldsIntArray(i);%>
                          <%=infoPageValidator.getAllResearchFieldsOrderdByNum()[i].getResearchFieldName()%>,
                          <%}%>
                  <%}
                    infoPageValidator.setResearchFields();%>
  <br>
  <br>
  The InfoPage  <%=(infoPageValidator.isHasTabledVersion() ? "Is In Tabled Form" : "Is In Full Text Version")%>
  <br>
  <br>
  The InfoPage is <%=(infoPageValidator.isRepetitive() ? "" : "not")%> Repeatative
  <br>
  <br>
  <%=(infoPageValidator.isDescriptionOnly() ? "View only description <br><br>" : "")%>

  <% if (infoPageValidator.isHasLocalWebPage()) { %>
	InfoPage Has a Local Web Page
  <%} else {%>
  	InfoPage has a Foreign Web Page, it's Web Address is : <a href="http://<%= infoPageValidator.getPageWebAddress() %>" target="view_window" > <%= infoPageValidator.getPageWebAddress() %> </a>
  <%}%>
  <br>
  <br>
  	The InfoPage <%= infoPageValidator.isKeepInRollingMessages() ? "will" : "will not" %> roll in the rolling messages window
  <br>
  <br>
	It will stop roll on: <%= infoPageValidator.getStopRollingDate() %>
  <br>
  <br>


  Keywords: <%for(int i=0;i<=infoPageValidator.getKeywordsArray().length-1;i++){%>
                 <%=infoPageValidator.getKeywordsArray()[i]%>,
            <%}%>
  <br>
  <br>



  <%if (infoPageValidator.isDetailsAreAllright()){ %>

  <form action="updateInfoPageDetailsToDatabase.jsp" method="get">
    <input type="hidden" name="ardNum" value="<%=infoPageValidator.getArdNum()%>">
    <input type="hidden" name="title" value="<%=infoPageValidator.getTitle()%>">
    <input type="hidden" name="pubDate" value="<%=infoPageValidator.getPubDate()%>">
    <input type="hidden" name="subDate" value="<%=infoPageValidator.getSubDate()%>">
    <input type="hidden" name="secondSubDate" value="<%=infoPageValidator.getSecondSubDate()%>">
    <input type="hidden" name="thirdSubDate" value="<%=infoPageValidator.getThirdSubDate()%>">
    <input type="hidden" name="fourthSubDate" value="<%=infoPageValidator.getFourthSubDate()%>">

    <input type="hidden" name="fundNum" value="<%=infoPageValidator.getFundNum()%>">
    <input type="hidden" name="fundShortName" value="<%=infoPageValidator.getFundShortName()%>">
    <input type="hidden" name="docType" value="<%=infoPageValidator.getDocType()%>">
    <input type="hidden" name="deskId" value="<%=infoPageValidator.getDeskId()%>">
    <input type="hidden" name="researchFields" value="<%=infoPageValidator.getResearchFields()%>">
    <input type="hidden" name="docOwner" value="<%=infoPageValidator.getDocOwner()%>">
    <% if (infoPageValidator.isHasTabledVersion()) {%>
        <input type="hidden" name="hasTabledVersion" value="hasTabledVersionr">
    <%}%>

    <% if (infoPageValidator.isRepetitive()) {%>
    <input type="hidden" name="repetitive" value="repetitive">
    <%}%>

    <% if (infoPageValidator.isDescriptionOnly()) {%>
    <input type="hidden" name="descriptionOnly" value="descriptionOnly">
    <%}%>

    <% if (infoPageValidator.isRestricted()) {%>
    <input type="hidden" name="restricted" value="restricted">
    <%}%>

    <% if (infoPageValidator.isHasLocalWebPage() ) { %>
    	<input type="hidden" name="hasLocalWebPage" value="hasLocalWebPage">
    <%}
    	else { %>
    	<input type="hidden" name="pageWebAddress" value="<%=infoPageValidator.getPageWebAddress() %>"/>
    <%}%>

    <% if (infoPageValidator.isHasAdditionalSubDates()) {%>
    <input type="hidden" name="hasAdditionalSubDates" value="hasAdditionalSubDates">
    <%}%>

    <% if (infoPageValidator.isKeepInRollingMessages()) {%>
    <input type="hidden" name="keepInRollingMessages" value="keepInRollingMessages">
    <input type="hidden" name="stopRollingDate" value="<%=infoPageValidator.getStopRollingDate()%>"/>
    <%}%>


    <input type="hidden" name="keywords" value="<%=infoPageValidator.getKeywords()%>">
    <%if(infoPageValidator.isNewInfoPage()){%><input type="hidden" name="newInfoPage" value="true"><%}%>

    <input type="submit" value="Update Database">
    <input OnClick="window.history.back()" type="button" value="Back">
  </form>
  <%}
    else{%>
   <form>
    <input OnClick="window.history.back()" type="button" value="Go Fix the Details">
   </form>
   <%}%>




</body>


     <%}
     else{%><html>
               <body><font size=+2>Sorry, page has expired, details may have been change by<br>
                          another user, you will have to update the details again.</font>
               </body>
            </html>

   <%}
   }
 else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>


