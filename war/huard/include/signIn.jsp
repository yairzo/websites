<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />
<jsp:setProperty name="language" property="*"/>

<jsp:useBean id="authorizer" scope="session" class="huard.website.session.Authorizer" />
<jsp:setProperty name="authorizer" property="*"/>

<jsp:useBean id="subscriber" scope="page" class="huard.website.session.Subscriber" />
<jsp:setProperty name="subscriber" property="*"/>

<jsp:useBean id="infoPageViewer" scope="request" class="huard.website.viewer.page.InfoPageViewer" />



<form action="infoPageViewer.jsp" method="get">
  	<input type="hidden" name="ardNum" value="<%=infoPageViewer.getArdNum() %>"/>
  	<table border=0 dir="<%= language.isHebrewVer() ? "rtl" : "ltr" %>" align="<%= language.isHebrewVer() ? "right" : "left" %>">
	<tr>
	<td colspan="2">
		<%= language.isHebrewVer() ? "המידע המלא ניתן לסגל האוניברסיטה העברית בלבד." : "The full details are accessible only to Hebrew University staff." %>
		<br>
		<h3><%= language.isHebrewVer() ? "משתמש רשום" : "Sign in." %></h3>

	</td>
	</tr>


		<tr>
			<td width=100>
			 <%= language.isHebrewVer() ? "כתובת דואל:" : "Mail Address:" %>
			 </td>
			<td>
			<input type="text" name="mailAddress" value="<%=authorizer.getMailAddress() %>" size=15 >
			</td>

		</tr>
		<tr>

			<td>
			<%= language.isHebrewVer() ? "סיסמא:" : "Password:" %>
			</td>
			<td>
			<input type="password" name="password" size=6 >
			</td>

		</tr>
		<% if (authorizer.isAuthorizationFailure() && ! authorizer.isASignup()){ %>
		<tr>
			<td colspan="2"><font color="red"><%= language.isHebrewVer() ? "כתובת הדואל ו/או הסיסמא אינם מתאימים, הרשמו למטה לקבלת תזכורת לסיסמא" : "Authorization failure, use the sign up form below to get a password reminder." %></font>
			</td>
		</tr>
		<%authorizer.resetAll(); %>
		<%} %>
	<tr>

	<td>

	<input name="submit" type="image" src="<%= language.isHebrewVer() ? "images/button_send.gif" : "images/e_button_send.gif"%>">
	</td>
	</tr>
	<tr>
	<td colspan=2>
	<br>
	<h3><%= language.isHebrewVer() ? "משתמש חדש / תזכורת לסיסמא" : "Sign up / Password reminder" %></h3>
	<%= language.isHebrewVer() ? "לקבלת סיסמא הכניסו כתובת דואל של האוניברסיטה העברית ." : "To get a password enter a HUJI mail address." %>
	</td>
</tr>
<tr>
<td width=100>
			 <%= language.isHebrewVer() ? "כתובת דואל:" : "Mail Address:" %>
			 </td>
			<td>
			<input type="text" name="signup" size=15 >
			</td>
			</tr>
			<tr>
			<td>
			<input name="submit" type="image" src="<%= language.isHebrewVer() ? "images/button_send.gif" : "images/e_button_send.gif"%>">
			</td>
</tr>

<% if (authorizer.isASignup()){%>
		<tr>
			<td colspan="2">

<% if (authorizer.isAllowedSignup()){%>
				<%= language.isHebrewVer() ? "שולח דואל עם הסיסמא לכתובת: " : "Sending Mail with a password to:" %> <%=subscriber.getSignup()%>
				<% subscriber.subscribe();%>

			<%}else{%>
				<font color="red"><%= language.isHebrewVer() ? "ההרשמה לבעלי כתובות דואל של האוניברסיטה העברית בלבד." : "Only HUJI mail Addresses are allowed." %></font>

			<%}%>


			</td>
		</tr>
		<% authorizer.resetAll();%>
		<%}%>






	</table>

</form>
