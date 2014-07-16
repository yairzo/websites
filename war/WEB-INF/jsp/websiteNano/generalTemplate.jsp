<%@ page  pageEncoding="UTF-8" %>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->

   <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>THE HARVEY M. KRUGER FAMILY CENTER FOR NANOSCIENCE AND NANOTECHNOLOGY</title>
        <meta name="description" content="">
        <link rel="stylesheet" href="/style/style_nano.css">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="/image/nano/apple-touch-icon-144-precomposed.png">
		<link rel="shortcut icon" href="/image/nano/favicon.ico">
        <script src="/js/modernizr-2.6.2.min.js"></script>
    </head>
    <body>

			<header class="header">
				<div class="black">
					<div class="container clearfix">
						<nav class="menu pull-left">
							<ul>
							<%@ include file="/WEB-INF/jsp/websiteNano/topMenu.jsp" %>
							</ul>
						</nav>
					</div>
				</div>
				<div class="container clearfix">
					<h2 class="title-web">THE HEBREW UNIVERSITY CENTER FOR NANOSCIENCE AND NANOTECHNOLOGY</h2>
					<a href="#" class="logo-nano pull-left"><img src="/image/nano/logo-nano.png" alt="" /></a>
					<a href="#" class="logo-university pull-right"><img src="/image/nano/logo-university.png" alt="" /></a>
				</div>
			</header>
		<section class="section">
		   	<jsp:include page="${templateBody}" />
        </section>
        
			<footer class="footer">
				<div class="container">
					<div class="footer-gray clearfix">
						<div class="pull-left"><a href="/sitemap"><fmt:message key="${lang.localeId}.general.siteMap"/></a>&nbsp; /  &nbsp;<a href="/page/${contactsPageUrlTitle}"><fmt:message key="${lang.localeId}.general.contact"/></a>&nbsp; /  &nbsp;<a href="javascript:bookmarksite();"><fmt:message key="${lang.localeId}.general.addToFavorites"/></a></div>
						<div class="pull-right"><fmt:message key="${lang.localeId}.general.lastUpdate"/> - ${updateTime}</div>
					</div>
					<div class="footer-white clearfix">
						<div class="pull-left"><fmt:message key="${lang.localeId}.website.designCompany"/></div>
						<div class="pull-right"><fmt:message key="${lang.localeId}.general.allRightsReserved"/></div>
					</div>
				</div>
			</footer>
			
			<script>window.jQuery || document.write('<script src="/js/jquery-1.11.0.min.js"><\/script>')</script>
			<script src="/js/jquery.popupoverlay.js"></script>

		<jsp:include page="${templateScripts}" />
			
	</body>
</html>
