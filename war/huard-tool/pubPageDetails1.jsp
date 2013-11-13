<%@ page language="java" contentType="text/html; UTF-8" info="HUARD test page" import="huard3.actions.*"%>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="pubPageDetails" scope="page" class="huard3.actions.PubPageHandler"/>
<jsp:setProperty name="pubPageDetails" property="*"/>

<jsp:useBean id="identify" scope="session" class="huard3.utils.Identity" />

<html>

<head>
		<link href="js/style3.css" type="text/css" rel="stylesheet">
     		<script src="js/coolmenus4.js" type="text/javascript"></script>
     		<title> Edit Publication Page Html </title>

<script type="text/javascript">

var editboxHTML = 
'<html>' +
'<head>' +
'<\/head>' +
'<body class="expand close" onload="document.f.ta.focus(); document.f.ta.select();">' +

'<%if (identify.isAuthorized()){%>'+
'<%if((!(pubPageDetails.isPubPageBusy()))||(pubPageDetails.isPubPageStillHoldedByMe(identify.getUsername()))){%>'+
'<% pubPageDetails.setPubPageAsBusy(identify.getUsername());%>'+
'<br>'+
'<%= pubPageDetails.getPubPageByArdNum().isOnSite() ? " <font color=red size=+2> This Page is on site, the right way to do major editing is to create a new page, copy the html from old page, change it, and then link the new page instead of this one</font> " : "" %>'+
'<form name="form1" action="insertPubPage.jsp" method="post">'+
'<input type="hidden" name="ardNum" value="<%= pubPageDetails.getPubPageByArdNum().getArdNum()%>">'+
'<br><br>'+
'<%= (pubPageDetails.isMessage() ? "Message Page Handeling" : "Information Page Handeling") %>'+
'<br><br>'+
'Ard Number: <%= pubPageDetails.getPubPageByArdNum().getArdNum() %>'+
'<br><br>'+
'Title: '+
'<% if (pubPageDetails.getPubPageByArdNum().isOnSite()){ %>'+
'<%= pubPageDetails.getPubPageByArdNum().getTitle() %>'+
'<input type="hidden" name="title" value="<%= pubPageDetails.getPubPageByArdNum().getTitle() %>"/>'+
'<%}else{%>'+
'<input name="title" value="<%= pubPageDetails.getPubPageByArdNum().getTitle() %>" size=80'+
'<%= pubPageDetails.isHebrew() ? "dir=\"rtl\"" : "\"\"" %> onClick="accessInputControl(this)">'+
'<%}%>'+
'<br><br>'+
'html:'+
'<br>'+
'<textarea name="html"  rows="<%= pubPageDetails.getPubPageByArdNum().getHtml().length()/30 +10 %>" cols="75" <%= pubPageDetails.isHebrew() && (pubPageDetails.getPubPageByArdNum().isHebrewEdit() || pubPageDetails.isNewPubPage()) ? "dir=\"rtl\"" : "\"\"" %> onClick="accessInputControl(this)" >'+
'<% if (pubPageDetails.isHebrew() && (pubPageDetails.getPubPageByArdNum().isHebrewEdit() || pubPageDetails.isNewPubPage())) { %>'+
''+
'<%}else{%>'+
''+
'<%}%>'+
'</textarea>'+
'<br><br>'+
'</form>'+
'<%}%>'+
'<%}%>'+

'<form class="expand close" name="f">' +
'<textarea class="expand close" style="background: #def;" name="ta" wrap="hard" spellcheck="false">' +
'<\/textarea>' +
'<\/form>' +
'<\/body>' +
'<\/html>';

var defaultStuff = '<h3>Welcome<\/h3>\n <p>HTML<\/p>';

var extraStuff = '';

var old = '';
         
function init()
{
  window.editbox.document.write(editboxHTML);
  window.editbox.document.close();
  window.editbox.document.f.ta.value = defaultStuff;
  update();
}

function update()
{
  var textarea = window.editbox.document.f.ta;
  var d = dynamicframe.document; 

  if (old != textarea.value) {
    old = textarea.value;
    d.open();
    d.write(old);
    if (old.replace(/[\r\n]/g,'') == defaultStuff.replace(/[\r\n]/g,''))
      d.write(extraStuff);
    d.close();
  }

  window.setTimeout(update, 150);
}

</script>
</head>

	

<frameset resizable="yes" cols="40%,*" onload="init();">
  <frame name="dynamicframe" >
  <frame name="editbox" >
</frameset>




</html>

