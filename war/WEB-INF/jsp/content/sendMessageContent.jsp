<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript" src="js/tiny_mce/jquery.tinymce.js"></script>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>

<script type="text/javascript">

<c:if test="${aNewMessage}">
	window.location = "sendMessage.html?id=${command.id}&listId=${command.listId}&cp=${cp}&cpoi=${cpoi}";
</c:if>
	var ckeditor;

	function ShowCKEDITOR()
	{
		$('textarea:tinymce').tinymce().remove();
		var config=	{
			uiColor: '#F4EEE4',
			contentsCss : 'assets/output_xhtml.css',
			height:"190", width:"700",
			coreStyles_bold : { element : 'span', attributes : {'class': 'Bold'} },
			coreStyles_italic : { element : 'span', attributes : {'class': 'Italic'}},
			coreStyles_underline : { element : 'span', attributes : {'class': 'Underline'}},
			coreStyles_strike : { element : 'span', attributes : {'class': 'StrikeThrough'}, overrides : 'strike' },
			coreStyles_subscript : { element : 'span', attributes : {'class': 'Subscript'}, overrides : 'sub' },
			coreStyles_superscript : { element : 'span', attributes : {'class': 'Superscript'}, overrides : 'sup' },
			font_names : 'Comic Sans MS/FontComic;Courier New/FontCourier;Times New Roman/FontTimes',
			font_style :
			{
			element : 'span',
			attributes : { 'class' : '#(family)' },
			overrides : [ { element : 'span', attributes : { 'class' : /^Font(?:Comic|Courier|Times)$/ } } ]
			},
			fontSize_sizes : 'Smaller/FontSmaller;Larger/FontLarger;8pt/FontSmall;14pt/FontBig;Double Size/FontDouble',
			fontSize_style :
			{
			element : 'span',
			attributes : { 'class' : '#(size)' },
			overrides : [ { element : 'span', attributes : { 'class' : /^Font(?:Smaller|Larger|Small|Big|Double)$/ } } ]
			},
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
			justifyClasses : [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull' ],
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
		//CKEDITOR.config.height = 190;
		//CKEDITOR.config.width = 700;
	}

	function ShowTinyMCE()
	{


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

$().ready(function() {

	$("input.rdoTypeTinymce").attr("cheked",true);
		ShowTinyMCE();



		$("input[@name='radios']").change(function(){
		    if ($("input[@name='radios']:checked").val() == 'rdoTypeCKEDITOR')
   		 	{
	   	    ShowCKEDITOR();
       		 }
	   		 else if ($("input[@name='radios']:checked").val() == 'rdoTypeTinymce')
    		{
       		 $('textarea.tinymce').ckeditor(function(){
 		   		this.destroy();
			 });
       		 ShowTinyMCE();
       		 }
		});

$("button.send").click(function(){
	var messageSubject = $("input#messageSubject").val();
	if (messageSubject == ""){
		$.alerts.alert('<fmt:message key="iw_IL.iws.general.sendMessage.subjectCompulsory"/>');
		return false;
	}
	var messageBody = $("textarea#body").val();
	if (messageBody != ""){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"send\">");
		$("#form").submit();
	}
	else {
		$.alerts.confirm('<fmt:message key="iw_IL.iws.general.sendMessage.noBody.confirm"/>',
     	 		'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
     	 		function(confirm){
     	 			if (confirm==1){
     	 				$("#form").append("<input type=\"hidden\" name=\"action\" value=\"send\">");
						$("#form").submit();
    				}
    	 });
     }
     return false;
});

$("button.sendMeTest").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"sendMeTest\">");
	$("#form").submit();
});

$("button.cancel").click(function(){
	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"cancel\">");
	$("#form").submit();
});

$(".addFile").hide();
$(".addFile:first").show();

$(".addAddFile").click(function(){
	$(".addFile:hidden:first").show();
	if ($(".addFile:hidden").size() == 0){
		$(".addAddFile").hide();
	}
});

});




</script>



<tr>
		<td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="שליחת הודעה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          	</td>

        </tr>
	</table>

    </td>
  </tr>
  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">


        <tr>
          <td valign="top" align="center">
				<form:form id="form" name="form" method="POST" action="sendMessage.html" commandName="command" enctype="multipart/form-data">
					<form:hidden path="id"/>
					<form:hidden path="listId"/>
					<input type="hidden" name="cp" value="${cp}"/>
					<input type="hidden" name="cpoi" value="${cpoi}"/>
					<form:hidden path="senderPersonId"/>
					<c:forEach items="${command.recipientsPersonsIds}" var="recipientPersonId">
						<input type="hidden" name="recipientsPersonsIds" value="${recipientPersonId}"/>
					</c:forEach>
			<table width="80%">
				<tr>
      				<td>
      					&nbsp;
      				</td>
      			</tr>
				<tr>



		<td>
			נושא:
			<form:input cssClass="green" path="messageSubject" cssStyle="width: 80%;" />
		</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>


		<c:if test="${command.listId > 0 }">
			<tr>
				<td>
			ההודעה תשלח לרשימה: ${command.listBean.name}
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
		<tr>
			<td>
				רשימת הנמענים:
				<c:forEach items="${command.recipientPersons}" var="recipientPerson" varStatus="varStatus">
					<c:if test="${varStatus.index>0}">,</c:if>${recipientPerson.email}
				</c:forEach>
			</td>
		</tr>
		<tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>

			<td width="90%">
			<INPUT TYPE="radio"  NAME="radios" class="rdoTypeTinymce" VALUE="rdoTypeTinymce" CHECKED checked="checked" >
              <fmt:message key="${lang.localeId}.general.oldEditor"/>
            &nbsp;
            <INPUT TYPE="radio" NAME="radios" class="rdoTypeCKEDITOR" VALUE="rdoTypeCKEDITOR">
            <fmt:message key="${lang.localeId}.general.newEditor"/>

			</td>

		</tr>
		<tr>

			<td width="90%">
				<form:textarea id="body" path="message" cssClass="tinymce" cssStyle="width: 100%; height: 300px;"/>
			</td>

		</tr>

	<tr>
		<td>
			&nbsp;
		</td>
	</tr>

	<tr>
		<td>
			<c:forEach items="${command.attachments}" var="attachment" varStatus="i">
				<input type="checkbox" name="filech${i.index}" value="true" checked="checked">${attachment.title}
				<br/>
			</c:forEach>
		</td>
	</tr>

	<tr>
		<td class="addFile">
			:צרף קובץ
			<input class="green" type="file" name="file1"  />
		</td>
	</tr>
	<tr>
		<td class="addFile">
			:צרף קובץ
			<input class="green" type="file" name="file2"  />
		</tr>
	</tr>
	<tr>
		<td class="addFile">
			:צרף קובץ
			<input class="green" type="file" name="file3"  />
		</tr>
	</tr>
	<tr>
		<td>
			<span class="addAddFile">הוסף קובץ נוסף</span>
		</td>
	</tr>


	<tr>
		<td>
			&nbsp;
		</td>
	</tr>

	<tr>
		<td>
			כתב גם אותי
			 (לצורך תיעוד בתיבת הדואר שלי)
			<form:checkbox path="ccMe" cssClass="green"/>
		</td>
	</tr>

	<tr>
		<td>
			<button class="sendMeTest grey">שלח לי לבדיקה</button>

			<button class="send grey">שלח לנימענים</button>

			<button class="cancel grey">ביטול</button>

		</td>
	</tr>
	</table>



</td>
</tr>
</table>
</form:form>
</td>
</tr>
</table>

</body>
</html>