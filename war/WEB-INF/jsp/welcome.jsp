<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<script>

function resetAutocomplete(){		
	$("#searchResearcher").autocomplete( 
		{source: "/iws/personsHelper.html?role=ROLE_CONFERENCE_RESEARCHER",
		 minLength: 2,
		 highlight: true,
		 select: function(event, ui) {
			$("#searchResearcher").val(ui.item.label);
			window.location='/iws/conferenceProposal.html?action=new&researcherId='+ui.item.id
			event.preventDefault();					
		 }
	    }
	);
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
      modal: true,
      width: 400,
      height:300,
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
		<p class="white">הרשות למו"פ > תפריט ראשי</p>		
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
								<a style="text-decoration: none" href="post.html?action=new">מערכת דיוור - שליחת הודעה חדשה</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR,ROLE_POST_SENDER,ROLE_POST_READER">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="posts.html">מערכת דיוור - דפדוף במאגר ההודעות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_POST_ADMIN">
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
						<authz:authorize ifAnyGranted="ROLE_IMAGE_ADMIN,ROLE_IMAGE_RESEARCHER">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="uploadImage.html">עריכת תמונות</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_APPROVER">
						<tr>
						  <th align="right">
								<a style="text-decoration: none" href="conferenceProposal.html?action=new">הגשת בקשה למימון כנס</a>
							</th>
						</tr>
						</authz:authorize>
						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
						<tr>
						  <th align="right">
								<a style="text-decoration: none" href="conferenceProposal.html?action=new" class="chooseResearcher">הגשת בקשה למימון כנס</a>
							</th>
						</tr>
						</authz:authorize>

						<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_COMMITTEE">
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
						<authz:authorize ifAnyGranted="ROLE_WEBSITE_ADMIN">
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="urls.html">צפייה בקישורים </a>
							</th>
						</tr>
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="active.html">צפייה במשתמשים הפעילים </a>
							</th>
						</tr>
						</authz:authorize>
						<tr>
							<th align="right">
								<a style="text-decoration: none" href="person.html?id=${userPersonBean.id}&cp=welcome.html">עדכון פרטים אישיים</a>
							</th>
						</tr>
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