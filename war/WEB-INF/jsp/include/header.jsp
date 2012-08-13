<%@ page  pageEncoding="UTF-8" %>

<html>
<head>
	<title>הרשות למחקר ופיתוח - מערכת אינטרנט משולבת</title>
<c:if test="${!ajaxView}">
	<link href="/iws/style/style.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery.alerts.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css">
	<link href="/iws/style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/iws/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="/iws/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="/iws/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.regexp.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.form.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.field.js"></script>
	<script type="text/javascript" src="/iws/js/jquery.bgiframe.js"></script>
	<!--<script type="text/javascript" src="js/jquery.dimensions.js"></script>  -->
	<script type="text/javascript" src="/iws/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="/iws/js/date.js"></script>
</c:if>
	

	<script>

		function setCookie(c_name,value,expiredays)
		{
			var exdate=new Date();
			exdate.setDate(exdate.getDate()+expiredays);
			document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
		}

		function getCookie(c_name)
		{
			if (document.cookie.length>0)
  			{
  				c_start=document.cookie.indexOf(c_name + "=");
  				if (c_start!=-1)
   				 {
    				c_start=c_start + c_name.length+1;
    				c_end=document.cookie.indexOf(";",c_start);
    				if (c_end==-1) c_end=document.cookie.length;
    				return unescape(document.cookie.substring(c_start,c_end));
    			}
  			}
			return "";
		}

		var postion = [0,0];
 		function getScrollTop(){
			//alert(window.pageYOffset + " " + document.documentElement.scrollTop + " " +document.body.scrollTop);
   		 	if (typeof window.pageYOffset != 'undefined')
			{
				position = [
					window.pageXOffset,
					window.pageYOffset
				];
				//alert ("1 worked " + position[1]);
			}
			else if (typeof document.documentElement.scrollTop
				!= 'undefined' && document.documentElement.scrollTop > 0)
			{
				position = [
					document.documentElement.scrollLeft,
					document.documentElement.scrollTop
				];
		//	alert ("2 worked " + position[1]);
			}
			else if (typeof document.body.scrollTop != 'undefined')
			{
				position = [
					document.body.scrollLeft,
					document.body.scrollTop
				];
			//	alert ("3 worked " + position[1]);
			}
	     	//alert (position[1]);
	    	 setCookie("scrollTopPos",position[1],1);
		}

		function moveToScrollTop(){
			 var scrollTopPos = getCookie("scrollTopPos");
		//	alert(scrollTopPos);
			 document.body.scrollTop = scrollTopPos;
		}
	</script>



</head>

<body leftmargin="0" topmargin="0" marginheight="0"
	<c:choose>
		<c:when test="${iframeView && false}"> onload="resizeIframe()" </c:when>
		<c:otherwise> onload="moveToScrollTop()" </c:otherwise>
	</c:choose>
	onbeforeunload="getScrollTop()">

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
  <tr>
    <td <c:if test="${!iframeView && !print}">background="image/bg.jpg" </c:if>>
      <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" align="center"><c:if test="${!iframeView || !ajaxView}"><img src="image/header.jpg" width="700" height="97" border="0" useMap="#Map"></c:if></td>
        </tr>
     <c:if test="${iframeView || print}">
     </table>
    </td>
  </tr>
	</c:if>