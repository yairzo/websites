<%@ page language="java" contentType="text/html;charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8"%>
<jsp:useBean id="infoPageView" scope="page" class="huard3.actions.InfoPageViewer"/>
<jsp:setProperty name="infoPageView" property="*"/>


<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>




<%if (identify.isAuthorized()){ %>

<html>

<head>
  <title>InfoPage Details</title>
</head>

<body>


  <% infoPageView.getInfoPage(); %>

  <center><font size=+2>InfoPage Details View </font>
  <br><br>
   <form action="infoPagesList.jsp" method="post">
    <table border=1 width=990 height=220>
     <tr>
       <td>ARD Number: <%=infoPageView.getInfoPage().getArdNum()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

       <td>Title: <%=infoPageView.getInfoPage().getTitle()%></td>

       <td>Publication<br> Date: <%=infoPageView.getFormatedPubDate()%>

       <td>
       Submission<br> Date: <%=infoPageView.getFormatedSubDate()%>
       </td>
     </tr>  

     <tr>


       <td>Fund:<br> <%=infoPageView.getInfoPage().getFundShortName()%>


       <td>Document Type:<br> &nbsp;&nbsp;&nbsp;&nbsp;<%=infoPageView.getInfoPage().getDocType()%>



       <td>Desk Id:<br> <%=infoPageView.getInfoPage().getDeskId()%>

       <td>Document Owner:<br> <%=infoPageView.getInfoPage().getDocOwner()%>
    </tr>

    <tr>
       <td>ResearchFields: <br> <% for (int i=0;i<=infoPageView.getResearchFieldsIntArray().length-1;i++){%>
                                      <input type="checkbox"
                                         <% if(infoPageView.getResearchFieldsIntArray()[i]==1){%> checked="checked"<%}%>
                                         name="<%=infoPageView.getAllResearchFieldsOrderdByNum()[i].getResearchFieldShort()%>" value="<%=infoPageView.getAllResearchFieldsOrderdByNum()[i].getResearchFieldShort()%>"/>&nbsp;<%=infoPageView.getAllResearchFieldsOrderdByNum()[i].getResearchFieldName()%><br>


                                 <%}%>
       </td>




       <td>Additional Parameters:<br>
                      <input type="checkbox" <% if(infoPageView.getInfoPage().isHasTabledVersion()) { %> checked="checked" <%}%>
                            name="hasTabledVersion" value="hasTabledVersion"/>&nbsp; Has Tabled Vesion <br>

                      <input type="checkbox" <% if(infoPageView.getInfoPage().isRepetitive()){%> checked="checked" <%}%>
                            name="repetitive" value="repetitive"/>&nbsp; The Document is Repetative<br>

		      <input type="checkbox" <% if(infoPageView.getInfoPage().isHasLocalWebPage()){%> checked="checked" <%}%>
                            name="hasLocalWebPage" value="hasLocalWebPage"/>&nbsp; The Recored has a Local Web Page<br>	
			    
		       <%=infoPageView.getInfoPage().getPageWebAddress()%>
		       <br>
		            
		      <input type="checkbox" <% if(infoPageView.getInfoPage().isRestricted()){%> checked="checked" <%}%>
                            name="restricted" value="restricted"/>&nbsp; The Document is Restricted to HUJI Members Only

     </td>
      <td>Additional Submission Dates:<br>

            &nbsp;&nbsp;<%=infoPageView.getFormatedAdditionalSubDates()[0]%><br>
            &nbsp;&nbsp;<%=infoPageView.getFormatedAdditionalSubDates()[1]%><br>
            &nbsp;&nbsp;<%=infoPageView.getFormatedAdditionalSubDates()[2]%><br>
      </td>


      </tr>
    </table>
    <table border=1 width=990 height=80>
      <tr>
          <td>Keywords: <%=infoPageView.getInfoPage().getKeywords()%>
      </tr>
    </table>
    <table border=1 width=990 height=200>
    <tr>
    <td width=755>
    <iframe src="pageExecuter.jsp?ardNum=<%=infoPageView.getArdNum()%>" width=755 height=200 align=center>
    </iframe>
	</td>
    
       
         <td> <center>

       <input OnClick="window.history.back()" type="button" value="Back"></center>
       </td>
       </tr>
     </table>




    </form>


</body>

</html>


<%}

else {%>

<font size=+2>Sorry, you are not authorized using this tool.</font>

<%}%>
