<%@ page  pageEncoding="UTF-8" %>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <title><fmt:message key="${lang.localeId}.website.title"/></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="style/style_${lang.nameLowerCase}.css">
        
       	<jsp:include page="${templateCss}" />
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
        <header class="header">
			<div class="container clearfix">
				<nav class="nav">
					<ul class="menu">
						<%@ include file="/WEB-INF/jsp/website/topMenu.jsp" %>
					</ul>
				</nav>
				<nav class="icons">
					<ul class="menu">
						<li><a href="#" onclick="changeLanguage();"><img src="image/website/menu_icon_${lang.nameShort}.png" alt="Change language to ${lang.nameCapitalized}" /></a></li>
						<li><a href="search.html"><img src="image/website/menu_icon_magnifying.png" alt="Search this website" /></a></li>
						<li><a href="/iws/homePage.html"><img src="image/website/menu_icon_home.png" alt="Go to homepage" /></a></li>
						<li><a href="textualPage.html?id=${contactsPageId}"><img src="image/website/menu_icon_envelope.png" alt="" /></a></li>
					</ul>
				</nav>
			</div>
        	<div class="container clearfix">
				<a href="#" class="logo_authority">
				<c:choose>
				<c:when test="${lang.localeId=='en_US'}"><img src="image/website/logo_authorityEN.png" alt="הרשות למחקר ופיתוח, האוניברסיטה העברית בירושלים" style="width:80%;height:80%"/></c:when>
				<c:otherwise><img src="image/website/logo_authority.png" alt="הרשות למחקר ופיתוח, האוניברסיטה העברית בירושלים" /></c:otherwise>
				</c:choose>
				</a>
				<a href="#" class="logo">
				<c:choose>
				<c:when test="${lang.localeId=='en_US'}"><img src="image/website/logoEN.png" alt="האוניברסיטה העברית בירושלים" /></c:when>
				<c:otherwise><img src="image/website/logo.png" alt="האוניברסיטה העברית בירושלים" /></c:otherwise>
				</c:choose>
				</a>
			</div>
        </header>
        <section class="section">
        	<jsp:include page="${templateBody}" />
        </section>
        <footer class="footer">
			<div class="container">
				<div class="footer_bg clearfix">
					<div class="pull-right"><a href="sitemap.html"><fmt:message key="${lang.localeId}.general.siteMap"/></a>&nbsp; /  &nbsp;<a href="#"><fmt:message key="${lang.localeId}.general.contact"/></a>&nbsp; /  &nbsp;<a href="#"><fmt:message key="${lang.localeId}.general.addToFavorites"/></a></div>
					<div class="pull-left"><fmt:message key="${lang.localeId}.general.lastUpdate"/> - ${updateTime}</div>
				</div>
				<div class="footer_bottom clearfix mar_10">
					<div class="pull-right"><fmt:message key="${lang.localeId}.general.allRightsReserved"/></div>
					<div class="pull-left"><fmt:message key="${lang.localeId}.website.designCompany"/></div>
				</div>
			</div>
        </footer>
        
        <script>
        	function changeLanguage(){
        		var location = "" + window.location;
        		location = location.replace("#","");
        		location = location.replace("locale=${lang.localeId}","");
        		if (location.indexOf("?")!=-1){
        			location = location.replace("?&","?");
        			location = location.replace("&&","&");
        			
        			if (location.match("^.*?\\?$") || location.match("^.*?&$"))
        				location = location + "locale=";
        			else 
        				location = location + "&locale=";
        		}
        		else{
        			location = location + "?locale=";
        		}
    			console.log(location);
        		<c:choose>        		
        			<c:when test="${lang.rtl}">
        				window.location = location + "en_US";
        			</c:when>
        			<c:otherwise>
        				window.location = location + "iw_IL";
        			</c:otherwise>
        		</c:choose>       		
        	}
        
        </script>
		<jsp:include page="${templateScripts}" />

    </body>
</html>
