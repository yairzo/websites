<%@ page  pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<title>הרשות למחקר ופיתוח - מערכת אינטרנט משולבת</title>
	
	<c:if test="${!ajaxView}">
	<link href="/style/style.css" rel="stylesheet" type="text/css">
	<link href="/style/jquery.alerts.css" rel="stylesheet" type="text/css">
	<link href="/style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
	<link href="/style/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css">
	<link href="/style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css">
	<!-- <script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>-->
	<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script> 
	<script type="text/javascript" src="/js/jquery-migrate-1.2.1.min.js"></script>
	<!-- <script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>-->
	<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script> 
	<script type="text/javascript" src="/js/jquery.regexp.js"></script>
	<script type="text/javascript" src="/js/jquery.form.js"></script>
	<!-- <script type="text/javascript" src="/js/jquery.field.js"></script> -->
	<!-- <script type="text/javascript" src="/js/jquery.bgiframe.js"></script> -->
	<!--<script type="text/javascript" src="/js/jquery.dimensions.js"></script>  -->
	<script type="text/javascript" src="/js/jquery.alerts.js"></script> 
	<script type="text/javascript" src="/js/date.js"></script>
	
	

	<script>

		$(document).ready(function() {
	    	$("#popupDialog").dialog({
	           autoOpen: false,
	           show: 'fade',
	           hide: 'fade',
	           open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
	    	 });
			 $(".ui-dialog-titlebar").hide();
			<c:if test="${popupMessage!=null && popupMessage!=''}">
				var userMessage = "${popupMessage}";
				$("#popupDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
				$("#popupDialog").dialog({ modal: false });
    			$("#popupDialog").dialog("option", "position", "center");
	    		$("#popupDialog").html(userMessage).dialog("open");
				</c:if>
		});

	</script>
	</c:if>


</head>


<body leftmargin="0" topmargin="0" marginheight="0"
	<c:choose>
		<c:when test="${iframeView && false}"> onload="resizeIframe()" </c:when>
		
	</c:choose>
	>


	<map name="Map" id="Map"><area shape="rect" coords="469,28,695,74" href="http://huji.ac.il" alt="huji.ac.il" />
			<area shape="rect" coords="4,63,277,94" href="http://ard.huji.ac.il/huard/main.jsp?lang=eng" alt="Authority for Research and Development" />
			<area shape="rect" coords="428,75,693,95" href="http://ard.huji.ac.il/huard/main.jsp?lang=heb" alt="Authority for Research and Development" />
		</map>
 	<c:choose>
    	<c:when test="${printcp}">
      		<c:set var="backgroundWidth" value="1000"/>
      	</c:when>
      	<c:otherwise>
      		<c:set var="backgroundWidth" value="100%"/>
      	</c:otherwise>
     </c:choose>

<table width="${backgroundWidth}" border="0" align="center">
<div id="popupDialog" title="עזרה" style="display:none" dir="rtl"><p></p></div>
   <tr>
    <td <c:if test="${!iframeView && !print}">background="/image/bg.jpg" </c:if>>
      <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" align="center"><c:if test="${!iframeView || !ajaxView}"><img src="/image/header.jpg" width="700" height="97" border="0" useMap="#Map"></c:if></td>
        </tr>
     <c:if test="${iframeView || print}">
     </table>
    </td>
  </tr>
	</c:if>