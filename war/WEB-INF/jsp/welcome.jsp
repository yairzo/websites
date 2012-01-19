<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<tr>
	<td align="right" bgcolor="#787669" height="20">
	<table width="100%" border="0" dir="rtl">
	<tr>
	<td>
		<authz:authorize ifAnyGranted="ROLE_POST_READER">
		<p class="white">מערכת דיוור ישיר הרשות למו"פ > כניסה</p>
		</authz:authorize>
		<authz:authorize ifNotGranted="ROLE_POST_READER">
		<p class="white">מערכת משולבת הרשות למו"פ > כניסה</p>
		</authz:authorize>
	</td>
	<td>
		<p class="white">משתמש: <c:out value="${userPersonBean.degreeFullNameHebrew}"/></p>
	</td>
	<td align="left">
		<a style="text-decoration: none; color: white; font-size:9pt;"  href="j_acegi_logout">צא</a>
	</td>
	</tr>
	</table>
</td>
</tr>
</table>
</td>
</tr>
<tr>
	<td>
		<table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
			<tr>
				<td valign="top" align="center"><br>
					<table width="400" border="0" align="center" cellpadding="3" dir="rtl">
						<tr>
							<authz:authorize ifAnyGranted="ROLE_POST_READER">
							<td colspan="2" align="center"><h1>מערכת דיוור ישיר - הרשות למו"פ</h1>
							</td>
							</authz:authorize>
							<authz:authorize ifNotGranted="ROLE_POST_READER">
							<td colspan="2" align="center"><h1>מערכת משולבת - הרשות למו"פ</h1>
							</td>
							</authz:authorize>
						</tr>
					</table>
					<table width="400" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="person.html?id=${userPersonBean.id}&cp=welcome.html">עדכון פרטים אישיים</a>
							</th>
						</tr>
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="persons.html">טיפול במאגר בעלי התפקידים</a>
							</th>
						</tr>
						<tr>
							<th  align="right">
								<a  style="text-decoration: none" href="lists.html">מערכת רשימות - טיפול במאגר הרשימות</a>
							</th>
						</tr>
						<tr>
							<th  align="right">
								<a  style="text-decoration: none" href="organizationUnits.html">מערכת רשימות - טיפול במאגר היחידות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_LISTS_MOP">
						<tr>
							<th  align="right">
								<a  style="text-decoration: none" href="lists.html">מערכת רשימות - צפייה ברשימות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_EQF_ADMIN,ROLE_EQF_MOP">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="proposals.html">טופס מרובע - טיפול במאגר ההצעות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="post.html">מערכת דיוור - שליחת הודעה חדשה</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR,ROLE_POST_SENDER">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="posts.html">מערכת דיוור - דפדוף במאגר ההודעות</a>
							</th>
						</tr>
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="persons.html?rf=1">מערכת דיוור - דפדוף במאגר הרשומים</a>
							</th>
						</tr>
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="postReports.html">מערכת דיוור - מסך בקרה</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_EQF_DEAN,ROLE_EQF_YISSUM,ROLE_EQF_RESEARCHER">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="proposals.html">טופס מרובע - הצעות מחקר לאישור</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_READER,ROLE_POST_CREATOR,ROLE_POST_ADMIN">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="personPost.html?id=${userPersonBean.id}">עריכת פרטי רישום לדיוור הישיר</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="uploadImage.html">עריכת תמונות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_ADMIN">
						<tr>
						<th align="right">
								<a style="text-decoration: none" href="editConferenceProposal.html?action=new">הגשת בקשה למימון כנס</a>
							</th>
						</tr>
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="conferenceProposalAffirmation.html">הגשת בקשה למימון כנס</a>
							</th>
						</tr>
						</authz:authorize>

						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_ADMIN">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="conferenceProposals.html">צפייה בבקשות למימון כנסים</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="conferenceProposalsGrade.html">דירוג בקשות למימון כנסים</a>
							</th>
						</tr>
						</authz:authorize>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
<br>
</td>
</tr>
</table>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
</body>
</html>