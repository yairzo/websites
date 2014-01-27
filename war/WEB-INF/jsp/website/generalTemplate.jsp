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
        <link rel="stylesheet" href="/style/style_${lang.nameLowerCase}.css">
        <script src="/js/modernizr-2.6.2.min.js"></script>
       	<jsp:include page="${templateCss}" />
       	<link rel="icon" type="image/gif" href="/image/website/logo2trans.gif"/>
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
						<li><a href="#" onclick="changeLanguage();"><img src="/image/website/menu_icon_${lang.nameShortOpp}.png" alt="Change language to ${lang.nameCapitalized}" /></a></li>
						<li><a href="/search/"><img src="/image/website/menu_icon_magnifying.png" alt="Search this website" /></a></li>
						<li><a href="/page/${contactsPageUrlTitle}"><img src="/image/website/menu_icon_envelope.png" alt="" /></a></li>
					</ul>
				</nav>
				<div class="login <c:if test="${lang.rtl=='false'}">flipped</c:if>">
					<c:choose>
					<c:when test="${userAuthorized}">
						<div class="login_left">
							<c:choose>
							<c:when test="${lang.rtl}">
								<a href="/welcome.html" target="_blank"><c:out value="${userPersonBean.degreePartialNameHebrew}"/></a>
							</c:when>
							<c:otherwise>
								<a href="/welcome.html" target="_blank"><c:out value="${userPersonBean.degreePartialNameEnglish}"/></a>
							</c:otherwise>
							</c:choose>
							&nbsp;&nbsp;<a href="/j_acegi_logout" class="logout" title="<fmt:message key="${lang.localeId}.general.login.logout"/>"><img src="/image/website/login_x.png" alt='<fmt:message key="${lang.localeId}.general.login.logout"/>'></a>
						</div>
					</c:when>	
					<c:otherwise>				
						<div class="login_left"><fmt:message key="${lang.localeId}.general.login.enter"/> &nbsp;&nbsp;&nbsp; <a class="login_help"><img class="login_help_img" src="/image/website/login_help.png" alt=""></a></div>
						<div class="login_box" id="login_box">
							<div class="login_box_top"></div>
							<div class="login_box_bottom">
								<form action="/j_acegi_security_check" method="post">									
									<input type="hidden" name="ilr" value="${ilr}"/>
									<input type="hidden" name="mts" value="post"/>
									<div class="clearfix">
										<div class="login_box_col">
											<label class="login_label"><fmt:message key="${lang.localeId}.general.login.username"/></label>
											<input type="text" htmlEscape="true" placeholder="<fmt:message key="${lang.localeId}.website.loginPlaceholder"/>" id="j_username" name="j_username" class="login_input">
										</div>
										<div class="login_box_col pull-${lang.alignOpp}">
											<label for="password" class="login_label"><fmt:message key="${lang.localeId}.general.login.password"/></label>
											<input type="password" name="j_password" class="login_input">
										</div>
										<div class="login_box_col mar_15">
											<input type="submit" value="<fmt:message key="${lang.localeId}.general.login.login"/>" class="login_submit">
										</div>										
									</div>
									<div class="login_register mar_15 clearfix"><fmt:message key="${lang.localeId}.general.login.toSubscribe"/> <a href="/sign_up"><fmt:message key="${lang.localeId}.general.login.clickHere"/></a></div>
								</form>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				<div class="login_help_popup" dir="${lang.dir}" style="display:none">
					<div class="clearfix">
						<h3 class="login_help_popup_title login_help_popup_title_<c:if test="${lang.rtl}">hebrew</c:if><c:if test="${!lang.rtl}">english</c:if>"><fmt:message key="${lang.localeId}.website.helpTitle"/></h3>
						<a onclick="$('.login_help_popup').hide();return false;" class="popup_close popup_close_<c:if test="${lang.rtl}">hebrew</c:if><c:if test="${!lang.rtl}">english</c:if>">סגור</a>
					</div>
					<p><fmt:message key="${lang.localeId}.website.loginHelpText"/></p>
				</div>
			</div>
        	<div class="container clearfix">
				<a href="/" class="logo_authority">
				<c:choose>
				<c:when test="${lang.localeId=='en_US'}"><img src="/image/website/logo_authority_eng.png" alt="הרשות למחקר ופיתוח, האוניברסיטה העברית בירושלים" /></c:when>
				<c:otherwise><img src="/image/website/logo_authority.png" alt="הרשות למחקר ופיתוח, האוניברסיטה העברית בירושלים" /></c:otherwise>
				</c:choose>
				</a>
				<a href="http://new.huji.ac.il/" class="logo" target="_blank">
				<c:choose>
				<c:when test="${lang.localeId=='en_US'}"><img src="/image/website/logo_eng.png" alt="האוניברסיטה העברית בירושלים" /></c:when>
				<c:otherwise><img src="/image/website/logo.png" alt="האוניברסיטה העברית בירושלים" /></c:otherwise>
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
					<div class="pull-${lang.align}"><a href="/sitemap/"><fmt:message key="${lang.localeId}.general.siteMap"/></a>&nbsp; /  &nbsp;<a href="/page/${contactsPageUrlTitle}"><fmt:message key="${lang.localeId}.general.contact"/></a>&nbsp; /  &nbsp;<a href="javascript:bookmarksite();"><fmt:message key="${lang.localeId}.general.addToFavorites"/></a></div>
					<div class="pull-${lang.alignOpp}"><fmt:message key="${lang.localeId}.general.lastUpdate"/> - ${updateTime}</div>
				</div>
				<div class="footer_bottom clearfix mar_10">
					<div class="pull-${lang.align}"><fmt:message key="${lang.localeId}.general.allRightsReserved"/></div>
					<div class="pull-${lang.alignOpp}"><fmt:message key="${lang.localeId}.website.designCompany"/></div>
				</div>
			</div>
        </footer>
        
        <script src="/js/jquery-1.10.2.min.js"></script>
        <script  language="Javascript">
        	function changeLanguage(){
        		/*var location = "" + window.location;
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
    			console.log(location);*/
    			var location = "/";
        		<c:choose>        		
        			<c:when test="${lang.rtl}">
        				window.location = location + "en_US";
        			</c:when>
        			<c:otherwise>
        				window.location = location + "iw_IL";
        			</c:otherwise>
        		</c:choose>       		
        	}
        	function bookmarksite() {
        		if ('${lang.rtl}'=='true'){
        			title = "הרשות למחקר ופיתוח, האוניברסיטה העברית";
            		url = "https://ard.huji.ac.il/homePage.html?locale=iw_IL";
        		}
        		else{
        			title = "The Authority for Research and Development. The Hebrew University of Jerusalem";
            		url = "https://ard.huji.ac.il/homePage.html?locale=en_US";
       			}
        		if (window.sidebar) // firefox
        			window.sidebar.addPanel(title, url, "");
        		else if(window.opera && window.print){ // opera
        			var elem = document.createElement('a');
        			elem.setAttribute('href',url);
        			elem.setAttribute('title',title);
        			elem.setAttribute('rel','sidebar');
        			elem.click();
        		} 
        		else if(document.all)// ie
        			window.external.AddFavorite(url, title);
        	} 
        			var mouse_is_inside = false;
        			$(document).ready(function() {
        				
        				<c:if test="${!userAuthorized}">
        				$(".login_left").click(function() {
        					var loginBox = $(".login_box");
        					$(".login_box").css({
        						position: 'absolute',
        						top:44,
        						left:-85
        					});
        					if (loginBox.is(":visible"))
        						loginBox.fadeOut("fast");
        					else
        						loginBox.fadeIn("fast");
        					return false;
        				});
        				</c:if>
        				$(".logout").click(function(e) {
        					e.stopPropagation();
        				});
        				$(".login_box").hover(function(){
        					mouse_is_inside=true;
        				}, function(){
        					mouse_is_inside=false;
        				});
        				$("body").click(function(){
        					if(! mouse_is_inside) $(".login_box").fadeOut("fast");
        				});
        				
        				$('.login_help').click(function(e){
      	        		  e.preventDefault();
      	        		  e.stopPropagation();
       	        			if ($(".login_help_popup").is(":visible"))
      	        				$(".login_help_popup").hide();
    						else
    							$(".login_help_popup").show();     	        	 
      	        		});
        			}); 
        		</script>

		<jsp:include page="${templateScripts}" />
		<%@ include file="/WEB-INF/jsp/website/googleAnalytics.jsp" %>	
    </body>
</html>
