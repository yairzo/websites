<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<style>
	.ui-autocomplete {
		direction: rtl;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>
<script>

function resetAutocomplete(){		
	$("#searchResearcher").autocomplete( 
		{source: "selectBoxFiller?type=conference researchers",
		 minLength: 2,
		 highlight: true,
		 select: function(event, ui) {
			$("#searchResearcher").val(ui.item.label);
			window.location='/conferenceProposal.html?action=new&researcherId='+ui.item.id
			event.preventDefault();					
		 }
	    }
	);
	$("#searchResearcher").autocomplete("option", "appendTo", "#conferenceProposalResearcherDialog");
}


$(document).ready(function() {
   	
	resetAutocomplete();
	
    $("#searchResearcher").click(function(){
    	$("input#searchResearcher").val('');
    	resetAutocomplete();
    });
  

  $("#conferenceProposalResearcherDialog").dialog({
      autoOpen: false,
      show: 'fade',
      hide: 'fade',
      width: 400,
      height:350,
      title: "מערכת הכנסים"
 });
 
  
  
  	$(".chooseResearcher").click(function(e){
			e.preventDefault();
			var targetUrl = $(this).attr("href");
		   	$("#conferenceProposalResearcherDialog").dialog('option', 'buttons', {
		            "בטל" : function() {
		                $(this).dialog("close");
		             },
		            "המשך" : function() {
		            	window.location.href = targetUrl;
		             }
		    });
		   	
		   	$("#conferenceProposalResearcherDialog").dialog({position: { my: "bottom",at: "bottom",of:".chooseResearcher"}});
		   	$("#conferenceProposalResearcherDialog").dialog("open");
		   	return false;
	});	
  
});
</script>
<tr>
	<td align="right" bgcolor="#787669" height="20">
	<table width="100%" border="0" dir="rtl">

	
	<div id="conferenceProposalResearcherDialog" title="" style="display:none" dir="rtl">
		<p>בחר/י את החוקר שבשמו תרצה/י לפתוח בקשה למימון כנס:<br><br><input type="text" style="width: 180px;" class="green" id="searchResearcher" /></p>
	</div>

	<td>
		<c:if test="${titleCode==2}">
		<p class="white">הרשות למו"פ > מערכת כנסים</p>
		</c:if>
		<c:if test="${titleCode==1}">
		<p class="white">הרשות למו"פ > תפריט ראשי</p>		
		</c:if>
		<c:if test="${titleCode==0}">
		<p class="white">הרשות למו"פ > תפריט ראשי</p>		
		</c:if>							
	</td>
	<td>
		<p class="white">משתמש: <c:out value="${userPersonBean.degreeFullNameHebrew}"/></p>
	</td>
	<td align="left">
		<a style="text-decoration: none; color: white; font-size:9pt;"  href="/j_acegi_logout">צא</a>
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
					<table width="<c:choose><c:when test="${titleCode==2}">600</c:when><c:otherwise>400</c:otherwise></c:choose>" border="0" align="center" cellpadding="3" dir="rtl">
						<tr>
						<c:if test="${titleCode==2}">
							<td colspan="2" align="center">
							<h1>מערכת כנסים</h1>
							<h2>
							<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
							מסך כניסה לדיקן
							</authz:authorize>
							<authz:authorize ifAnyGranted="ROLE_CONFERENCE_COMMITTEE">
							מסך כניסה כחבר או יו"ר ועדת כנסים
							</authz:authorize>
							<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
							מסך כניסה לחוקר, מגיש הבקשה
							</authz:authorize>
							</h2>
							</td>
						</c:if>
						<c:if test="${titleCode==1}">
							<td colspan="2" align="center"><h1>מערכת דיוור ישיר - הרשות למו"פ</h1>
							</td>
						</c:if>
						<c:if test="${titleCode==0}">
							<td colspan="2" align="center"><h1>מערכת משולבת - הרשות למו"פ</h1>
							</td>
						</c:if>							
						</tr>
					</table>
					<table width="<c:choose><c:when test="${titleCode==2}">600</c:when><c:otherwise>400</c:otherwise></c:choose>" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">
						<c:if test="${titleCode!=2}">
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR,ROLE_LISTS_MOP">
						<tr>
							<th align="right" style="height:35">
								<h1>מערכת הרשימות</h1>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/persons.html">טיפול במאגר בעלי התפקידים</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a  style="text-decoration: none" href="/lists.html">טיפול במאגר הרשימות</a>
							</th>
						</tr>
						<tr>
							<th  align="right" style="height:35">
								<a  style="text-decoration: none" href="/organizationUnits.html">טיפול במאגר היחידות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_LISTS_MOP">
						<tr>
							<th  align="right" style="height:35">
								<a  style="text-decoration: none" href="/lists.html">צפייה ברשימות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_EQF_ADMIN,ROLE_EQF_MOP">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/proposals.html">טופס מרובע - טיפול במאגר ההצעות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_EQF_DEAN,ROLE_EQF_YISSUM,ROLE_EQF_RESEARCHER">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/proposals.html">טופס מרובע - הצעות מחקר לאישור</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR,ROLE_POST_SENDER,ROLE_POST_READER">
						<tr>
							<th align="right" style="height:35">
								<h1>מערכת דיוור</h1>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/post.html?action=new">שליחת הודעה חדשה</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR,ROLE_POST_SENDER,ROLE_POST_READER">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/posts.html">דפדוף במאגר ההודעות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/persons.html?rf=1">דפדוף במאגר הרשומים</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/postReports.html">מסך בקרה</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_READER,ROLE_POST_CREATOR,ROLE_POST_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/personPost.html?id=${userPersonBean.id}">עריכת פרטי רישום לדיוור הישיר</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_WEBSITE_READ,ROLE_WEBSITE_EDIT">
						<tr>
							<th align="right" style="height:35">
								<h1>אתר האוניברסיטה</h1>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_READ,ROLE_WEBSITE_EDIT,ROLE_WEBSITE_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/homePage.html">אתר האוניברסיטה - דף הבית</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/search.html">אתר האוניברסיטה - חיפוש כללי</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/searchCallForProposals.html">אתר האוניברסיטה - חיפוש קולות קוראים</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/sitemap.html">אתר האוניברסיטה - מפת האתר</a>
							</th>
						</tr>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_EDIT,ROLE_WEBSITE_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/callForProposals.html"> עריכת קולות קוראים</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/textualPages.html"> עריכת דפי טקסט</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/categories.html">עריכת קטגוריות לאתר </a>
							</th>
						</tr>
						</authz:authorize>
						</authz:authorize>

						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN,ROLE_IMAGE_ADMIN,ROLE_IMAGE_RESEARCHER">
						<tr>
							<th align="right" style="height:35">
								<h1>ניהול אתר</h1>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_IMAGE_ADMIN,ROLE_IMAGE_RESEARCHER">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/uploadImage.html?action=new">עריכת תמונות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/urls.html">צפייה בקישורים </a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/active.html">צפייה במשתמשים הפעילים </a>
							</th>
						</tr>
						</authz:authorize>
						
						</c:if><!-- end > not title code 2 (not conference proposal) -->

						<c:if test="${titleCode==2}">
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
						<tr>						
							<th align="right" style="height:35">
								בחר את התפקיד שבמסגרתו ברצונך להיכנס למערכת:
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
						<tr>
							<th align="right" style="height:35">
								בחר את הפעולה שברצונך לבצע במערכת:
							</th>
						</tr>
						</authz:authorize>
						</c:if>
						<c:if test="${titleCode!=2}">
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_COMMITTEE">
						<tr>
							<th align="right" style="height:35">
								<h1>מערכת כנסים</h1>
							</th>
						</tr>
						</authz:authorize>
						</c:if>

						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_COMMITTEE">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposals.html">כחבר בועדת כנסים - ניתן לצפות בבקשות שטרם נדונו ולרשום הערות לגביהן</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposalsGrade.html">כדיקן - צפייה/כתיבת חוות דעת ודירוג הבקשות שהוגשו על ידי חוקרי יחידתך לדיון הקרוב</a>
							</th>
						</tr>
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposals.html">כדיקן - צפייה בכלל הבקשות שהוגשו על ידי חוקרי יחידתך</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposals.html">צפייה בבקשות לתמיכה בכנס מדעי</a>
							</th>
						</tr>
						</authz:authorize>
						
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
						<tr>
						  <th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposal.html?action=new">
								<authz:authorize ifAnyGranted="ROLE_CONFERENCE_COMMITTEE,ROLE_CONFERENCE_APPROVER">כחוקר - </authz:authorize>
								פתיחת בקשה חדשה, לתמיכה בכנס מדעי, בינ"ל
								</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
						<tr>
						  <th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposal.html?action=new" class="chooseResearcher">פתיחת בקשה חדשה, לתמיכה בכנס מדעי, בינ"ל</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/conferenceProposals.html?type=self">
								<authz:authorize ifAnyGranted="ROLE_CONFERENCE_COMMITTEE,ROLE_CONFERENCE_APPROVER">כחוקר - </authz:authorize>
								צפייה בבקשות קודמות שלך במערכת (ואפשרות לעדכן בקשה שטרם מסרת לוועדה או שהוחזרה אליך לתיקון)</a>
							</th>
						</tr>
						</authz:authorize>

						<tr>
							<th align="right" style="height:35">
								&nbsp;
							</th>
						</tr>
					
						<tr>
							<th align="right" style="height:35">
								<a style="text-decoration: none" href="/person.html?id=${userPersonBean.id}&cp=welcome.html">עדכון פרטיך האישיים</a>
							</th>
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