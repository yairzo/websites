<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<table align="center" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<tr>

<%if (language.isHebrewVer()){%>

	<td align="center"  dir="<%=language.isHebrewVer() ? "rtl" : "ltr" %>"><p class="small">© כל הזכויות שמורות&nbsp;<a href="http://www.huji.ac.il/huji/copy.htm" target=_blank><!-------------->לאוניברסיטה העברית בירושלים<!-------------------------------------------------------------------------------------------------------------></a></td>
<%}else{%>

	<td align="center" dir="<%=language.isHebrewVer() ? "rtl" : "ltr" %>"><p class="small">© All rights reserved to <a href = "http://www.huji.ac.il/huji/eng/copy_e.htm" target=_blank>The Hebrew University of Jerusalem</a></td>
<%}%>
</tr>
</table>


