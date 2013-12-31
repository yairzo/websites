<script language="Javascript">

	var submission = false;

	function showRightMultipleSelectImg(parent){
		var isAnyChecked = false;
		var isAllChecked = true;

		var id = $(parent).attr("id");

		$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
			if (this.checked){
				isAnyChecked = true;
			}
			else{
				isAllChecked = false;
			}

		});

		if (isAnyChecked && ! isAllChecked){
			$(parent).children("td").children("img.empty").hide();
			$(parent).children("td").children("img.v").hide();
			$(parent).children("td").children("img.partly").show();
			openSubject($(parent));


		}
		else if (isAllChecked){
			$(parent).children("td").children("img.empty").hide();
			$(parent).children("td").children("img.partly").hide();
			$(parent).children("td").children("img.v").show();

			  $("#dialogRooms").click(function(e) {
					$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
					$("#genericDialog").dialog({ modal: false });
					$("#genericDialog").dialog({ height: 200 });
					$("#genericDialog").dialog({ width: 400 });
					var texts='<p>';
					texts='החדרים/אולמות שועדת הכנסים מאשרת אינם בבית בלגיה, בית מאירסדורף ובית צרפת שהנם גופים מסחריים עצמאיים.';
					texts+='</p>';	    
				    openHelp("#dialogRooms",texts);
				    return false;
				   });
	}
		else{
			$(parent).children("td").children("img.partly").hide();
			<%@ page  pageEncoding="UTF-8" %>
$(parent).children("td").children("img.v").hide();
			$(parent).children("td").children("img.empty").show();

		}
	}

	function openSubject(element){
		$(element).children("img.plus").hide();
		$(element).children("img.minus").show();
		var targetId = $(element).parent().attr("id");
			$("tbody#"+targetId+"Sub").show();
	}

	function closeSubject(element){
		$(element).children("img.minus").hide();
		$(element).children("img.plus").show();
		var targetId = $(element).parent().attr("id");
			$("tbody#"+targetId+"Sub").hide();
	}

	function toggleSubject(element){
			$(element).children("img.minus").toggle();
			$(element).children("img.plus").toggle();
			var targetId = $(element).parent().attr("id");
			$("tbody#"+targetId+"Sub").toggle();
	}

	function closeAll(){
		$("tr.darker").each(function(){
				showRightMultipleSelectImg($(this));
				closeSubject($(this).children("td.toggleSubject"));
		});
	}

	function onEdit()
	{
   	 document.form.action = "person.html?action=edit"
  	  document.form.submit();
   	 return true;
	}

	function onDelete()
	{
  	  document.form.action = "person.html?action=delete"
   	  document.form.submit();
  	  return true;
	}

	function onSave()
	{
	 document.form.action = "person.html?action=save"
	 document.form.submit();
   	 return true;
	}


$(document).ready(function() {
	<c:if test="${!isConference}">
	$(".receivePosts").prop('checked', true);
	</c:if>
	
	$("form#form").submit(function(){
			return submission;
		});

	$.alerts.okButton = '×××©××¨';
	$.alerts.cancelButton = '×××××';


	<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		userMessage = userMessage.replace(/'/,"\'");
		$.alerts.alert(userMessage,'<fmt:message key="iw_IL.eqfSystem.editProposal.alert.title"/>');
	</c:if>

	<c:if test="${command.postReceiveImmediately}">
		$(".postReceiveTime").hide();
	</c:if>

		$("#postReceiveImmediately").click(function(){
			$(".postReceiveTime").toggle();
		});

		$("#postReceiveEveryday").click(function(){
			var everydayCheckbox = this;
			$(".postReceiveDay").each(function(){
				this.checked = everydayCheckbox.checked;
			});
		});

				$('tbody.subSubjects').hide();

		$(".minus").hide();
		$(".plus").show();




		<c:forEach items="${rootSubject.subSubjects}" var="subject">
			showRightMultipleSelectImg($('tr#subject${subject.id}'));
		</c:forEach>

		$("#selectAll").click(function(){
			$("input:checkbox.subSubject").each(function(){
				this.checked = true;
			});
			$("tr.darker").each(function(){
				showRightMultipleSelectImg($(this));
				openSubject($(this).children("td.toggleSubject"));
			});
			this.checked = !this.checked;

		});

		$("#diselectAll").click(function(){
			$("input:checkbox.subSubject").each(function(){
				this.checked = false;
			});
			closeAll();
			this.checked = !this.checked;
		});




		$(".toggleSubject").click(function(){
			toggleSubject($(this));
		});

		$("input:checkbox.subSubject").click(function(){
			var id = $(this).attr("id");
			var parentSubjectId = id.substr(0, id.indexOf("."));
			showRightMultipleSelectImg($(this).parents().find("tr#subject"+parentSubjectId));
		});



		$(".selectSubject").click(function(){
				saved = false;
				var id = $(this).parent().attr("id");
				var isAnyChecked = false;
				var isAllChecked = true;
				var isUniteAction = false;

				$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {

					if (this.checked){
						isAnyChecked = true;
					}
					else{
						isAllChecked = false;
					}
				});

				if (isAnyChecked && ! isAllChecked){
					$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
						if (!this.checked){
							this.checked = true;
							isUniteAction=true;
						}
					});
					isAllChecked = true;
				}

				if (!isUniteAction){
					$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
						this.checked = !this.checked;
					});
					isAllChecked = ! isAllChecked;
					isAnyChecked = isAllChecked;
				}

				showRightMultipleSelectImg($(this).parent());

				if (isAllChecked){
					openSubject($(this).parent().children("td.toggleSubject"));
				}
				else if (!isAnyChecked){
					closeSubject($(this).parent().children("td.toggleSubject"));
				}
				return false;
			});


			$('input#email').blur(function(){
				var value = $('input#email').val();
				$('input#email').attr("value", $.trim(value));
			});
			
			   
		     $("#genericDialog").dialog({
		           autoOpen: false,
		           show: 'fade',
		           hide: 'fade',
		           modal: true,
		           open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
		     });
		     $(".ui-dialog-titlebar").hide();
		     
			$("#dialogDetails").click(function(e) {
					$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
					$("#genericDialog").dialog({ modal: false });
					$("#genericDialog").dialog({ height: 300 });
					$("#genericDialog").dialog({ width: 400 });
					var text='<p>';
					text='חוקר/ת יקרים,<br>';
					text+='השימוש במערכת זו מצריך רישום חד פעמי בבסיס הנתונים שלה כמפורט להלן:<br>';
					text+='מלא/י נא את הפרטים ולחצ/י על "שמור".<br>';
					text+='באופן מיידי תישלח הודעת אישור לתיבת הדוא"ל שלך.<br>';
					text+='בלחיצה על הקישורית שבהודעה תיכנס/י למערכת לשם הגשת בקשתך לוועדת הכנסים.<br>'; 
					text+='במקרה של קושי תוכל להעזר ב<a style="text-decoration: underline;" href="mailto:mop@ard.huji.ac.il">רשות למו"פ</a>.';
					text+='</p>';	
					$("#genericDialog").dialog("option", "position", "center");
				    $("#genericDialog").html(text).dialog("open");
				    return false;
			 });

});

</script>