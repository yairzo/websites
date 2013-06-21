<%@ page  pageEncoding="UTF-8" %>
		
<style>
	.ui-autocomplete {
		direction: rtl;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>


<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>


<script>

	function openSubject(element){
		$(element).children("img.plus").hide();
		$(element).children("img.minus").show();
		var targetId = $(element).parent().attr("id");
			$("tbody#"+targetId+"Sub").show();
	}

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

		}
		else{
			$(parent).children("td").children("img.partly").hide();
			$(parent).children("td").children("img.v").hide();
			$(parent).children("td").children("img.empty").show();
		}
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
	
	function resetAutocomplete(callForProposals){
		$("input.callForProposal").autocomplete( 
				{source: callForProposals,
				 minLength: 2,
				 highlight: true				 
			    }
		);
	}
	function insertIds(){
		var ids="";
		$('input.subSubject').each(function(){
				if (this.checked){
					var id = this.id;
					id = id.substring(id.indexOf('.') + 1);
					if (ids !="")
						ids = ids + ",";
					ids = ids +id;
				}
		});
		$('.subjectsIdsString').remove();
		$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" class=\"subjectsIdsString\" value=\"'+ids+'\"/>');
	}

	var formExit=true;//formExit is when form exits by pressing menu or back, and not pressing any form button 

	window.onunload = function() {
		if (formExit && $('.messageSubject').val()==''  && $('#message').val()==''){
			//alert('הודעה ללא נושא ותוכן תמחק');
	 		$('form#form').append('<input type="hidden" name="deletePost" value="true"/>');
			$('form#form').submit();
	        return false;

		}
		else if(formExit){//other exits already contain submit 
			insertIds();			
			$('form#form').append('<input type="hidden" name="action" value="save"/>');
			$('form#form').ajaxSubmit();
			return false;
		} 
	};
	


	$(document).ready(function() {

		if($('.viewSubjects').is(":checked"))
	    	$("#subjectView").show();
		else
	   		$("#subjectView").hide();

		$(".viewSubjects").change(function(){		
		    if($('.viewSubjects').is(":checked"))
		    	$("#subjectView").show();
			else
		    	$("#subjectView").hide();
		});

		
		$(".callForProposal").autocomplete({ position: { my : "right top", at: "right bottom" }});
		$(".callForProposal").autocomplete ("widget").css("width",'auto');  
		
		
		
		CKEDITOR.disableAutoInline = true;
		
		if(CKEDITOR.instances['editable']==null)
			CKEDITOR.inline('editable');

    	CKEDITOR.instances['editable'].on('blur', function() {
    		var text = $("#editable").html();
      		var ceditor   = CKEDITOR.instances['editable'];
      		if(text.length==0) text+="&nbsp;";
      		ceditor.setData(text);
        	$("#message").val(text);
			insertIds();			
			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    	$('#form').ajaxSubmit();
   	 	});  



		$("button.addAttachEditor").click(function(event){			
			var html = $("td.attach").html();
			$('div#editable').append(' ' + '<span>&nbsp;#@# ' + html + '</span>');			
		});

		<c:if test="$false && ${command.typeId == 2}">
			$("textarea#body").html('');
			$("textarea#body").append('${callForProposalTemplate}');
		</c:if>

		$("button.action").click(function(){
			scroll(0,0);
		});


		var typeId = $("select.postType").val();
		if (typeId <=3){
			$.get('selectBoxFiller',{type:'callForProposal',localeId:'${command.localeId}'},function(data){
   	   				callForProposals = data.split(",,");
   	   				resetAutocomplete(callForProposals);   	   				
   	             });
		}
		else{
			$("tr.selectCallForProposal").hide();
		}



		$("button.importCallForProposal").click(function(){

			if ($("input.callForProposal").val() == "") return;
			var callForProposalTitle = $("input.callForProposal").val();
			var id = callForProposalTitle.replace(/.+ - /,"");
			//$.get('selectBoxFiller',{type:'callForProposal',localeId:'${command.localeId}'},function(data){
			//	var cplist=data.split(",,");
			//	var valid=false;
			//	for (var j=0; j<cplist.length; j++) {
        	//		if (cplist[j].substring(cplist[j].length-(id.length+2))=="- "+id){
        	//			valid=true;
     					$.get("objectQuery?type=callForProposal&id="+id, function(data){
    						var senderId= $("select.sender").val();
    						var email = $("#sender" + senderId).val();
    						var name = $('.sender').find(":selected").text();
    						data= data.replace("#mu# #mp##mue#","<a class=\"underline\" href=\"mailto:" + email + "\">"+name+"</a>")
    						$("div#editable").html('<p dir="${lang.dir}"> ' + data + ' </p>');	
    						$("textarea#message").html('<p dir="${lang.dir}"> ' + data + ' </p>');	
    					});
      					$.get("objectQuery?type=callForProposalTitle&id="+id, function(data){
    						$("input.messageSubject").val(data);
    					});
        			//	} 
    			//}  
				//if (!valid){ 
				//	$("textarea#message").val(''); 
				//	$("input.messageSubject").val(''); 
				//} 
	         // }); 
			return false;
		});

		$("button.reloadCallForProposalsList").click(function(){
			$.get('selectBoxFiller',{type:'callForProposal',localeId:'${command.localeId}'},function(data){
   	   			callForProposals = data.split(",,");
   	   			resetAutocomplete(callForProposals);   
   	        });
			return false;
			return false;
   	     });


		$("select.sender").change(function(){
			var label='<fmt:message key="${lang.localeId}.general.callForProposal.deskPrefix"/>';
			//alert(label);
	    	var ceditorData   = CKEDITOR.instances.body.getData();
	    	if(ceditorData.indexOf(label)>0){
	    		var oldContact=ceditorData.substring(ceditorData.indexOf(label)+ label.length);
	    		oldContact = oldContact.substring(0,oldContact.indexOf(","));
		    	//alert(oldContact);
				var senderId= $("select.sender").val();
				var email = $("#sender" + senderId).val();
				var name = $('.sender').find(":selected").text();
		    	var newContact= "<a class=\"underline\" href=\"mailto:" + email + "\">"+name+"</a>";
		    	//alert(newContact);
		    	ceditorData = ceditorData.replace(oldContact,newContact);
		    	CKEDITOR.instances.body.setData( ceditorData);
	    	}
		});

		
		$("select.callForProposal").change(function(){
			var message = $("textarea#body").html();
			var callForProposalId = $(this).val();

			if (message == ""){
				$("textarea#body").html('');
				$("textarea#body").load("objectQuery?type=callForProposal&id=" + callForProposalId);
			}
			$.alerts.confirm('<fmt:message key="${lang.localeId}.post.changeCallForProposal.confirm"/>',
    			'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    			function(confirm){
    				if (confirm==1){
    					$("textarea#body").html('');
						$("textarea#body").load("objectQuery?type=callForProposal&id=" + callForProposalId);
					}
				}
			);
		});


		$(".langSelect").change(function(){
				formExit = false;
				console.log('change language');
				jQuery.alerts.confirm('<fmt:message key="${lang.localeId}.post.changeLanguage.confirm"/>',
		    			'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
		    			function(confirm){
		    				if (confirm==1){
		   						$("textarea#body").html('');
		   						insertIds();			
								$('form#form').append('<input type="hidden" name="action" value="save"/>');
								$('form#form').submit();
							}
						}
				);

		});

		$(".addFile").hide();
		$(".addFile:first").show();

		$(".addAddFile").click(function(){
			$(".addFile:hidden:first").show();
			if ($(".addFile:hidden").size() == 0){
				$(".addAddFile").hide();
			}
		});

		$('#file1').change(function(event){
			insertIds();			
			$('#form').submit();
		});	

		$('button.cancel').click(function(){
			insertIds();			
			$('form#form').append('<input type="hidden" name="action" value="cancel"/>');
			$('form#form').submit();
		});
		
		$('button.save').click(function(){
			formExit=false;
			if ($('.messageSubject').val()==''){
			   	$("#genericDialog").dialog({ modal: true });
	 	    	$("#genericDialog").dialog('option', 'buttons',{"סגור" : function() {$(this).dialog("close");return false;}});
	    	    $("#genericDialog").text("יש להזין נושא").dialog("open");
		        return false;
	 		}
			else{
				insertIds();			
				$('form#form').append('<input type="hidden" name="action" value="save"/>');
				$('form#form').submit();
			}
		});
		
		$('button.sendme').click(function(){
			formExit=false;
			if ($('.messageSubject').val()==''){
			   	$("#genericDialog").dialog({ modal: true });
	 	    	$("#genericDialog").dialog('option', 'buttons',{"סגור" : function() {$(this).dialog("close");return false;}});
	    	    $("#genericDialog").text("יש להזין נושא").dialog("open");
		        return false;
	 		}
			else{
				var messageText = $('#message').val();
				if(messageText.indexOf("xxxxx")>0 ){
				   	$("#genericDialog").dialog({ modal: true });
		 	    	$("#genericDialog").dialog('option', 'buttons',{
						"המשך לשליחת ההודעה": function() {
							$('form#form').append('<input type="hidden" name="action" value="sendme"/>');
							insertIds();			
							$('form#form').submit();
							return false;
		 	    		},
						"בטל": function() {
							$( this ).dialog( "close" );
						}
		 	    	});
		    	    $("#genericDialog").text("הודעה זו מכילה את המקטע xxxxx. האם ברצונך להמשיך לשליחת ההודעה?").dialog("open");
			        return false;
				}
				else{
					$('form#form').append('<input type="hidden" name="action" value="sendme"/>');
					insertIds();			
					$('form#form').submit();

				}
			}
		});


		/* subjects list starts here */

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
			});
			this.checked = !this.checked;
		});

		$("#diselectAll").click(function(){
			$("input:checkbox.subSubject").each(function(){
				this.checked = false;
			});
			$("tr.darker").each(function(){
				showRightMultipleSelectImg($(this));
				closeSubject($(this).children("td.toggleSubject"));
			});
			this.checked = !this.checked;
		});

		$("#openAllSubjects").click(function(e){
			e.preventDefault();
			$("tr.darker").each(function(){
				showRightMultipleSelectImg($(this));
				openSubject($(this).children("td.toggleSubject"));
			});
		});
		$("#closeAllSubjects").click(function(e){
			e.preventDefault();
			$("tr.darker").each(function(){
				showRightMultipleSelectImg($(this));
				closeSubject($(this).children("td.toggleSubject"));
			});
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
				}
				else if (!isAnyChecked){
					closeSubject($(this).parent().children("td.toggleSubject"));
				}
				return false;
			});
		
	
	
	$("#date").datepicker({ dateFormat: 'dd/mm/yy'
    });
	
	$(function() {
        $.datepicker.regional['he'] = {
            closeText: 'סגור',
            prevText: '&#x3c;הקודם',
            nextText: 'הבא&#x3e;',
            currentText: 'היום',
            monthNames: ['ינואר','פברואר','מרץ','אפריל','מאי','יוני',
            'יולי','אוגוסט','ספטמבר','אוקטובר','נובמבר','דצמבר'],
            monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
            dayNames: ['ראשון','שני','שלישי','רביעי','חמישי','שישי','שבת'],
            dayNamesShort: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
            dayNamesMin: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
            weekHeader: 'Wk',
            dateFormat: 'dd/mm/yy',
            firstDay: 0,
            isRTL: true,
            showMonthAfterYear: false,
            yearSuffix: ''
        };
        $.datepicker.setDefaults($.datepicker.regional['he']);
 
    });
		
    $("#genericDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        width: 400,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
    });
    
     <c:if test="${userMessage!=null}">
    var userMessage = "${userMessage}";
    $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    $("#genericDialog").dialog("option", "position", "center");
    $("#genericDialog").html(userMessage).dialog("open");

    </c:if>		
	});

</script>
</html>