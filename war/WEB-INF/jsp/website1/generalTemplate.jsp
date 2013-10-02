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
        <c:choose>
        <c:when test="${lang.localeId=='en_US'}">
        	<link rel="stylesheet" href="style/style1English.css">
        </c:when>
        <c:otherwise>
        	<link rel="stylesheet" href="style/style1.css">
       	</c:otherwise>
       	</c:choose>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
        <header class="header">
			<div class="container clearfix">
				<nav class="nav">
					<ul class="menu">
						<%@ include file="/WEB-INF/jsp/website1/topMenu.jsp" %>
					</ul>
				</nav>
				<nav class="icons">
					<ul class="menu">
						<li><a href="#"><img src="image/website1/menu_icon_heb.png" alt="" /></a></li>
						<li><a href="#"><img src="image/website1/menu_icon_magnifying.png" alt="" /></a></li>
						<li><a href="#"><img src="image/website1/menu_icon_home.png" alt="" /></a></li>
						<li><a href="#"><img src="image/website1/menu_icon_envelope.png" alt="" /></a></li>
					</ul>
				</nav>
			</div>
        	<div class="container clearfix">
				<a href="#" class="logo_authority"><img src="image/website1/logo_authority.png" alt="הרשות למחקר ופיתוח, האוניברסיטה העברית בירושלים" /></a>
				<a href="#" class="logo"><img src="image/website1/logo.png" alt="האוניברסיטה העברית בירושלים" /></a>
			</div>
        </header>
        <section class="section">
        	<jsp:include page="${templateBody}" />
        </section>
        <footer class="footer">
			<div class="container">
				<div class="footer_bg clearfix">
					<div class="pull-right"><a href="sitemap.html">מפת אתר</a>&nbsp; /  &nbsp;<a href="#">צור קשר</a>&nbsp; /  &nbsp;<a href="#">הוסף למועדפים</a></div>
					<div class="pull-left">עודכן ופורסם לאחרונה ב-${updateTime}</div>
				</div>
				<div class="footer_bottom clearfix mar_10">
					<div class="pull-right">&copy; כל הזכויות שמורות לאוניברסיטה העברית בירושלים</div>
					<div class="pull-left">עיצוב: למון <a href="#">עיצוב אתרים</a></div>
				</div>
			</div>
        </footer>
        
		<jsp:include page="${templateScripts}" />

    </body>
</html>
