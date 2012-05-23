<%@ page  pageEncoding="UTF-8" %>
		
<style>
	.ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
		/* add padding to account for vertical scrollbar */
		padding-right: 20px;
		direction: rtl;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}


</style>

<!-- <script type="text/javascript" src="js/tiny_mce/jquery.tinymce.js"></script> -->
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>

<script language="Javascript">

	var ckeditor;
	var currentEditor;

	function ShowCKEDITOR()
	{
		currentEditor = "ckeditor";
		//$('textarea:tinymce').tinymce().remove();
		var config=	{
			uiColor:'#F4EEE4',
			contentsCss:'js/ckeditor/_samples/assets/output_xhtml.css',
			height:"190", width:"700",
			//coreStyles_bold : { element : 'span', attributes : {'class': 'Bold'} },
			//coreStyles_italic : { element : 'span', attributes : {'class': 'Italic'}},
			//coreStyles_underline : { element : 'span', attributes : {'class': 'Underline'}},
			//coreStyles_strike : { element : 'span', attributes : {'class': 'StrikeThrough'}, overrides : 'strike' },
			//coreStyles_subscript : { element : 'span', attributes : {'class': 'Subscript'}, overrides : 'sub' },
			//coreStyles_superscript : { element : 'span', attributes : {'class': 'Superscript'}, overrides : 'sup' },
			/*font_names : 'Comic Sans MS/FontComic;Courier New/FontCourier;Times New Roman/FontTimes',
			font_style :
			{
			element : 'span',
			attributes : { 'class' : '#(family)' },
			overrides : [ { element : 'span', attributes : { 'class' : /^Font(?:Comic|Courier|Times)$/ } } ]
			},*/
			fontSize_sizes : '10/10px;12/12px;14/14px;16/16px;24/24px;48/48px;',
			/*fontSize_sizes : 'Smaller/FontSmaller;Larger/FontLarger;8pt/FontSmall;14pt/FontBig;Double Size/FontDouble',
			fontSize_style :
			{
			element : 'span',
			attributes : { 'class' : '#(size)' },
			overrides : [ { element : 'span', attributes : { 'class' : /^Font(?:Smaller|Larger|Small|Big|Double)$/ } } ]
			},*/
			colorButton_enableMore : false,
			colorButton_colors : 'FontColor1/FF9900,FontColor2/0066CC,FontColor3/F00',
			colorButton_foreStyle :
			{
			element : 'span',
			attributes : { 'class' : '#(color)' },
			overrides : [ { element : 'span', attributes : { 'class' : /^FontColor(?:1|2|3)$/ } } ]
			},
			colorButton_backStyle :
			{
			element : 'span',
			attributes : { 'class' : '#(color)BG' },
			overrides : [ { element : 'span', attributes : { 'class' : /^FontColor(?:1|2|3)BG$/ } } ]
			},
			indentClasses : ['Indent1', 'Indent2', 'Indent3'],
			justifyClasses : [ 'AlignLeft', 'AlignCenter', 'AlignRight', 'AlignJustify' ],
			stylesSet :
			[
			{ name : 'Strong Emphasis', element : 'strong' },
			{ name : 'Emphasis', element : 'em' },
			{ name : 'Computer Code', element : 'code' },
			{ name : 'Keyboard Phrase', element : 'kbd' },
			{ name : 'Sample Text', element : 'samp' },
			{ name : 'Variable', element : 'var' },
			{ name : 'Deleted Text', element : 'del' },
			{ name : 'Inserted Text', element : 'ins' },
			{ name : 'Cited Work', element : 'cite' },
			{ name : 'Inline Quotation', element : 'q' }
			]
		};
		ckeditor = $('textarea.tinymce').ckeditor(config);
	}

	function ShowTinyMCE()
	{
		currentEditor = "tinymce";
		$('textarea.tinymce').tinymce
		({

		script_url : 'js/tiny_mce/tiny_mce.js',
		theme : "advanced",
		plugins : "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
		theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
		heme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
		theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : true
		});
	}


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
	
	function resetAutocomplete(callOfProposals){
		$("input.callOfProposal").autocomplete( 
				{source: callOfProposals,
				 minLength: 2,
				 highlight: true				 
			    }
		);
	}

	var formExit=true;

	window.onunload = function() {
		if (formExit){
			var checkedSubject=false;
			$('input.subSubject').each(function(){
				if (this.checked){
					checkedSubject=true;
				}
			});
			if ($('.messageSubject').val()==''  && $('.callOfProposal').val()=='' && $('.message').val()=='' && checkedSubject==false){
		 		$('form#form').append('<input type="hidden" name="action" value="delete"/>');
				$('form#form').submit();
 			}
		}
	};


	$(document).ready(function() {

		ShowCKEDITOR();
		/*$("input.rdoTypeTinymce").attr("cheked",true);
		ShowTinyMCE();
		$("input.radios").click(function(){
	   	 if ($(this).val() == 'rdoTypeCKEDITOR' && currentEditor != 'ckeditor')
	 		{
	   		    ShowCKEDITOR();
   			 }
   		 else if ($(this).val() == 'rdoTypeTinymce' && currentEditor != 'tinymce')
  			{
   			 $('textarea.tinymce').ckeditor(function(){
	 	   		this.destroy();
			 });
       		 ShowTinyMCE();
    	 	}
		});*/

		$("a.addAttachEditor").click(function(){
			var html = $("td.attach").html();
			$("textarea#body").append(" #@# " + html);
		});

		<c:if test="$false && ${command.typeId == 2}">
			$("textarea#body").html('');
			$("textarea#body").append('${callOfProposalTemplate}');
		</c:if>

		$("button.action").click(function(){
			scroll(0,0);
		});


		var typeId = $("select.postType").val();
		if (typeId <=3){
			$.get('selectBoxFiller',{type:'callOfProposal',localeId:'${command.localeId}'},function(data){
   	   				callOfProposals = data.split(",,");
   	   				resetAutocomplete(callOfProposals);   	   				
   	             });
		}
		else{
			$("tr.selectCallOfProposal").hide();
		}


		$("a.importCallOfProposal").click(function(){
			if ($("input.callOfProposal").val() == "") return;
			var callOfProposalTitle = $("input.callOfProposal").val();
			var id = callOfProposalTitle.replace(/.+ - /,"");
			$("div.callOfProposalImportBox").load("objectQuery?type=callOfProposal&id="+id, function(data){
				$("textarea.tinymce").val(data);
			});
			$("div.callOfProposalImportBox").load("objectQuery?type=callOfProposalTitle&id="+id, function(data){
				$("input.messageSubject").val(data);
			});
		});

		$("a.reloadCallOfProposalsList").click(function(){
			$.get('selectBoxFiller',{type:'callOfProposal',localeId:'${command.localeId}'},function(data){
   	   				callOfProposals = data.split(",,");
   	   				resetAutocomplete(callOfProposals);   
   	             });
   	     });


		$("select.callOfProposal").change(function(){
			var message = $("textarea#body").html();
			var callOfProposalId = $(this).val();

			if (message == ""){
				$("textarea#body").html('');
				$("textarea#body").load("objectQuery?type=callOfProposal&id=" + callOfProposalId);
			}
			$.alerts.confirm('<fmt:message key="${lang.localeId}.post.changeCallOfProposal.confirm"/>',
    			'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    			function(confirm){
    				if (confirm==1){
    					$("textarea#body").html('');
						$("textarea#body").load("objectQuery?type=callOfProposal&id=" + callOfProposalId);
					}
				}
			);
		});


		$(".langSelect").change(function(){
				$.alerts.confirm('<fmt:message key="${lang.localeId}.post.changeLanguage.confirm"/>',
		    			'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
		    			function(confirm){
		    				if (confirm==1){
		   						$("textarea#body").html('');
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

		$('button.cancel').click(function(){
			$('form#form').append('<input type="hidden" name="action" value="cancel"/>');
			$('form#form').submit();
		});
		
		$('button.save').click(function(){
			formExit=false;
			if ($('.messageSubject').val()==''){
			   	$("#genericDialog").dialog({ modal: true });
	 	    	$("#genericDialog").dialog('option', 'buttons',{"סגור" : function() {$(this).dialog("close");return false;}});
	    	    $("#genericDialog").text("יש להזין טקסט מזהה לשימוש פנימי").dialog("open");
		        return false;
	 		}
			else{
				$('form#form').append('<input type="hidden" name="action" value="save"/>');
				var ids="";
				$('input.subSubject').each(function(){
					if (this.checked){
						var id = this.id;
						id = id.substring(id.indexOf('.') + 1);
						if (ids !="")
							ids = ids + ","
						ids = ids +id;
					}
				});
				$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" value=\"'+ids+'\"/>');
				$('form#form').submit();
			}
		});
		
		$('button.sendme').click(function(){
			formExit=false;
			if ($('.messageSubject').val()==''){
			   	$("#genericDialog").dialog({ modal: true });
	 	    	$("#genericDialog").dialog('option', 'buttons',{"סגור" : function() {$(this).dialog("close");return false;}});
	    	    $("#genericDialog").text("יש להזין טקסט מזהה לשימוש פנימי").dialog("open");
		        return false;
	 		}
			else{
				var messageText = $('.message').val();
				if(messageText.indexOf("xxxxx")>0 ){
				   	$("#genericDialog").dialog({ modal: true });
		 	    	$("#genericDialog").dialog('option', 'buttons',{
						"המשך לשליחת ההודעה": function() {
							$('form#form').append('<input type="hidden" name="action" value="sendme"/>');
							var ids="";
							$('input.subSubject').each(function(){
								if (this.checked){
									var id = this.id;
									id = id.substring(id.indexOf('.') + 1);
									if (ids !="")
										ids = ids + ","
									ids = ids +id;
								}
							});
							$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" value=\"'+ids+'\"/>');
							$('form#form').submit();
		 	    		},
						"בטל": function() {
							$( this ).dialog( "close" );
						}
		 	    	});
		    	    $("#genericDialog").text("הודעה זו מכילה את המקטע xxxxx. האם ברצונך להמשיך לשליחת ההודעה?").dialog("open");
			        return false;
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
				openSubject($(this).children("td.toggleSubject"));
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
		
		
	});

</script>
</html>