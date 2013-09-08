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
        <link rel="stylesheet" href="style/style1.css">
        <script src="js/modernizr-2.6.2.min.js"></script>
    </head>
    <body style="background:#7b7b7b;">
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
		
		<div class="popup">
			<div class="clearfix">
				<h3 class="popup_title">${command.title}</h3>
				<a href="#" onclick="self.close();" class="popup_close">סגור</a>
			</div>
			<span class="clearfix search_icons">
				<span class="search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/></strong>${selectedFund}</span>
				<span class="search_date"><fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> 
				<strong>
				<c:if test="${command.allYearSubmission}">
					<fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/>
				</c:if>
				<c:if test="${!command.allYearSubmission}">
					${finalSubmissionTime}
				</c:if> 
				</strong>
				</span>
			</span>
			<div class="clearfix popup_sum">
				<p>${command.description}</p>
			</div>
			<a href="#" onclick="window.opener.location.href='callForProposal.html?id=${command.id}&t=1';self.close();" class="popup_more">לפרטים נוספים</a>
		</div>
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>
     </body>
</html>