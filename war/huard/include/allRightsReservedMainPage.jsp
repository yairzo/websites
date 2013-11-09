<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="mainPageViewer" scope="page" class="huard.website.viewer.page.MainPageViewer" />
<jsp:setProperty name="language" property="*"/>

<TABLE align="center" cellSpacing=0 cellPadding=0 border=0 width=100%>
	<tr>

<script language="JavaScript1.2" type="text/javascript">
function CreateBookmarkLink() {
<%if (language.isHebrewVer()){%>
title = "הרשות למחקר ופיתוח, האוניברסיטה העברית";
<%}else{%>
title = "The Authority for Research and Development. The Hebrew University of Jerusalem";
<%}%>
url = "http://ard.huji.ac.il";
if (window.sidebar) {
// Mozilla Firefox Bookmark

window.sidebar.addPanel(title, url,"");
} else if( window.external ) {
// IE Favorite

window.external.AddFavorite( url, title);
}
else if(window.opera && window.print) {
// Opera Hotlist
return true; }
}
</script>


<%if (language.isHebrewVer()){%>

<td  background="images/colors_bg.gif" height="35" bgcolor="#E3DEC3" valign="bottom" align="center" dir="<%=language.isHebrewVer() ? "rtl" : "ltr" %>">© <small>כל הזכויות שמורות&nbsp;<a href="http://www.huji.ac.il/huji/copy.htm" target=_blank><!-------------->לאוניברסיטה העברית בירושלים<!-------------------------------------------------------------------------------------------------------------></a>&nbsp;&nbsp;עודכן לאחרונה ב-<!---------------><%= mainPageViewer.getSiteLastUpdate() %>&nbsp;&nbsp;<a href="" onclick="CreateBookmarkLink()">הוסף למועדפים</a></small></td>
<%}else{%>

	<td  background="images/colors_bg.gif" height="35" bgcolor="#E3DEC3" valign="bottom" align="center" dir="<%=language.isHebrewVer() ? "rtl" : "ltr" %>"><small>© All rights reserved to <a href = "http://www.huji.ac.il/huji/eng/copy_e.htm" target=_blank>The Hebrew University of Jerusalem</a>&nbsp;&nbsp;Last Updated: <%= mainPageViewer.getSiteLastUpdate() %>&nbsp;&nbsp;<a href="" onclick="CreateBookmarkLink()">Add to Bookmarks</a></small></td>
<%}%>


</tr>
</TABLE>


