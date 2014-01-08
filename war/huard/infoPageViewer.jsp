<%
response.setStatus(301);
response.setHeader( "Location", "/call_for_proposal/redirect/"+request.getParameter("ardNum") );
response.setHeader( "Connection", "close" );
%>