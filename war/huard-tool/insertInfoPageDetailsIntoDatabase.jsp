
<%@ page language="java" contentType="text/html; charset=UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="infoPageValidator" scope="page" class="huard3.actions.InfoPageValidator"/>
<jsp:setProperty name="infoPageValidator" infoPageerty="*"/>



<html>

<Head>
  <title> Updating Database </title>
</Head>

<body>

<%=infoPageValidator.getTitle()%>
<%=infoPageValidator.getFundNum()%>
<%=infoPageValidator.getDocType()%>
<%=infoPageValidator.getKeywords()%>


<%=infoPageValidator.updateInfoPageDetailsInDatabase("username")%>


        <center><font size=+3>Details Were Inserted to Database Successfully</font><br><br>
        <form action="main.jsp" method="post">
        <input type="submit" value="O.K">
        </form>
        </center>

     <center><font size=+3>Problems in Inserting Details to Database </font><br><br>
     <form action="main.jsp" method="post">
     <input type="submit" value="O.K">
     </form>
     </center>






</body>

</html>
