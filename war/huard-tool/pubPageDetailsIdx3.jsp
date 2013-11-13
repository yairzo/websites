<%@ page language="java" contentType="text/html" info="HUARD test page" import="huard3.actions.*"%>
<jsp:useBean id="pubPageDetails" scope="page" class="huard3.actions.PubPageHandler"/>
<jsp:setProperty name="pubPageDetails" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />
<jsp:setProperty name="identify" property="*"/>

<html>
<head>
<title> Information Page Details Handling</title>

<script type="text/javascript">
var old = '';
function init()
{
	update();
}
function update()
{
	var textarea = window.frame3.document.form1.html;
	var d = frame2.document;
	if (old != textarea.value) {
		old = textarea.value;
    		d.open();
    		d.write(old);
    		d.close();
	}

	window.setTimeout(update, 150);
}
</script>

</head>


<frameset rows="50%,*" >


	<frame src="viewExternalDocumentForm.html" name="frame1">

<frameset cols="40%,*" onload="init();">

   	<frame scrolling="yes" name="frame2" src="javascript:'';">
	<frame src="pubPageDetails.jsp?ardNum=<%= pubPageDetails.getArdNum()%>&hebrew=<%= pubPageDetails.isHebrew()%>&message=<%= pubPageDetails.isMessage()%> " name="frame3" scrolling="yes" >


</frameset>

</html>
