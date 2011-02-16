<%@ page  pageEncoding="UTF-8" %>

<%--  javascript --%>

<script type="text/javascript" src="js/jquery.autosave.js"></script>

<script language="Javascript">

<c:if test="${aNewProposal}">
	window.location = "proposal.html?id=${command.proposalBean.id}";
</c:if>

var unsaved = false;

function contains(a, obj) {
  var i = a.length;
  while (i--) {
    if (a[i] === obj) {
      return true;
    }
  }
  return false;
}

var fundsNamesMap = '';


function handleNewFund(){

	if ($('#fundingAgencyAdd').val() == '') return;
	if (contains(fundsNamesMap, $('#fundingAgencyAdd').val())){
	   		var options = {
       	 			url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        			type:      'POST'
     			};
	    	$('#form').ajaxSubmit(options);
	    	unsaved = false;
	    	$("span.fundName").empty();
	    	$("span.fundName").append($('#fundingAgencyAdd').val());
	    }
	    else{
	   $.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.userMessage.researcherAddedUnknownFund.confirm"/>',
    	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    	function(confirm){
    		if (confirm==1){
    			var options = {
       	 			url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        			type:      'POST'
     			};
	    	$('#form').ajaxSubmit(options);
	    	unsaved = false;
	    	$("span.fundName").empty();
	    	$("span.fundName").append($('#fundingAgencyAdd').val());
	    	$("span.userMessage").empty();
	    	$("span.userMessage").append('<fmt:message key="iw_IL.eqfSystem.editProposal.userMessage.researcherAddedUnknownFund"/>')
	    	}
    	});
    }
    if (isBasicDetailsAllFilled()){
	    $("button.moveOn").show();
	 }
}



function makeTabsBright() {
  $(".tab").attr("background","image/leshonit_bright_left.gif");
  $(".tabEdge img").after("<img src=image/leshonit_bright_side_right.gif width=7 height=35>").remove();
}

function isBasicDetailsAllFilled(){
	var hasFund = false;
	<c:if test="${command.proposalBean.fundId>0}">
		hasFund = true;
	</c:if>
	var isBasicDetailsAllFilled =  ($(".hebrewTitle").val()!='' && $(".englishTitle").val()!='' &&  ($('#fundingAgencyAdd').val()!='' || hasFund));
	return isBasicDetailsAllFilled;
}

this.sleep = function mySleep(naptime){
      naptime = naptime * 1000;
      var sleeping = true;
      var now = new Date();
      var alarm;
      var startingMSeconds = now.getTime();

      while(sleeping){
         alarm = new Date();
         alarmMSeconds = alarm.getTime();
         if(alarmMSeconds - startingMSeconds > naptime){ sleeping = false; }
      }

    }


    function handleTab( tabName ){
    	$(".editableTable").hide();
		$("table#"+tabName+"Table").show();
		makeTabsBright();
		$("td."+tabName+"Tab").attr("background","image/leshonit_dark_left.gif");
		$("td."+tabName+"TabEdge img").after("<img src=image/leshonit_dark_side_right.gif width=7 height=35>").remove();
		$.post('sessionVariablesHandler',{name:'${command.proposalBean.id}_currentTab', value:tabName, action:'set'});
	}

	function save(){
		$('form#form').append("<input type=\"hidden\" name=\"action\" value=\"save\"/>");
		$('form#form').submit();
	}

$(document).ready(function() {

	$("td.proposalFullDetails").hide();
	$(".editableTable").hide();

	if (isBasicDetailsAllFilled()){
		$("button.moveOn").show();
	}

	$.get('sessionVariablesHandler',{name:'${command.proposalBean.id}_currentTab'},function(data){

		if (data == 'null' ){
			data = 'proposalDetails';
		}
		$("table#"+data+"Table").show();
		$("td."+data+"Tab").attr("background","image/leshonit_dark_left.gif");
		$("td."+data+"TabEdge img").after("<img src=image/leshonit_dark_side_right.gif width=7 height=35>").remove();
	 });

	$("td.proposalDetailsTab").click(function(){
		handleTab ("proposalDetails");
	});

	$("td.proposalFilesTab").click(function(){
		handleTab ("proposalFiles");
	});

	$("td.proposersTab").click(function(){
		handleTab ("proposers");
	});

	$("td.partnersTab").click(function(){
		handleTab ("partners");
	});

	$("td.fundingAgencyResponseTab").click(function(){
		handleTab ("fundingAgencyResponse");
	});

	$("td.experimentApprovalsTab").click(function(){
		handleTab ("experimentApprovals");
	});

	$("td.yissumTab").click(function(){
		handleTab ("yissum");
	});

	$("td.deanApprovalTab").click(function(){
		handleTab ("deanApproval");
	});

	$("td.mopTab").click(function(){
		handleTab ("mop");
	});

	<%@ include file="/WEB-INF/jsp/content/userMessageScripts.jsp" %>

	$('.cancel').click(function(){
		if (unsaved==true){
			$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.confirmCancel"/>'
			,'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
			function(confirm){
				if (confirm ==1){
					document.form.action="proposal.html?action=cancel";
		    		document.form.submit();
				}
			});
		}
    	else{
    		document.form.action="proposal.html?action=cancel";
    		document.form.submit();
    	}
    	return false;
     });

	$('button.moveOn').click(function(){
		$.post('sessionVariablesHandler',{name:'${command.proposalBean.id}_currentTab', action:'moveOn'});
    	sleep(2);
    	location.reload(true);
    	return false;

	});

   $('#safetyAttach').hide();

   $('#toggleSafetyAttachUpload').click(function() {
   		$('#safetyAttach').toggle();
    	return false;
   });

   $('#humanAttach').hide();
  	$('#toggleHumanAttachUpload').click(function() {
    	$('#humanAttach').toggle();
    	return false;
   });

   $('#animalsAttach').hide();
  	$('#toggleAnimalsAttachUpload').click(function() {
    	$('#animalsAttach').toggle();
    	return false;
   });

	$.get('selectBoxFiller',{proposalId:'${command.proposalBean.id}',type:'fund'},function(data){
   	   	fundsNamesMap = data.split(",,");
   	   	$("#fundingAgencyAdd").autocomplete(fundsNamesMap, {align: 'left', scroll: 'true', scrollHeight: 90});
   	});


   $("select.fundingAgencyApproved").change(function() {
         var fundApproved = $(this).val();
         if (fundApproved=='1'){
         	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.fundingAgencyApproval.confirmApproval"/>',
         	'<fmt:message key="iw_IL.eqfSystem.editProposal.alert.title"/>',
         		function(confirm){
         			if (confirm == 1){
         				save();
         			}
         });
         }else{
         	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.fundingAgencyApproval.confirmDisapproval"/>',
         	'<fmt:message key="iw_IL.eqfSystem.editProposal.alert.title"/>',
         		function(confirm){
         			if (confirm==1){
         				save();
         			}
         	});
         }
         return false;
    });

    <c:if test="${ ! command.proposalBean.experimental}">
   		$('.safetyAttach').hide();
   		$('#humanExperiment').hide();
   		$('#animalsExperiment').hide();
   	</c:if>
     $("input:radio[@id='experimental']").click(function() {
        var experimental = $(this).fieldValue();
         if (experimental=='true') {
         	$('.safetyAttach').show();
         	$('#humanExperiment').show();
         	$('#animalsExperiment').show();
         }else{
         	$('.safetyAttach').hide();
         	$('#humanExperiment').hide();
           	$('#animalsExperiment').hide();
         }
     });

     <c:if test="${ ! command.proposalBean.humanExperiment}">
   		$('.humanAttach').hide();
   	</c:if>
     $("input:radio[@id='humanExperiment']").click(function() {
        var humanExperiment = $(this).fieldValue();
         if (humanExperiment=='true') {
         	$('.humanAttach').show();
         }else{
         	$('.humanAttach').hide();
         }
     });

     <c:if test="${ ! command.proposalBean.animalsExperiment}">
   		$('.animalsAttach').hide();
   	</c:if>
     $("input:radio[@id='animalsExperiment']").click(function() {
        var animalsExperiment = $(this).fieldValue();
         if (animalsExperiment=='true') {
         	$('.animalsAttach').show();
         }else{
         	$('.animalsAttach').hide();
         }
     });

    <c:if test="${command.proposalBean.deanApproved}">
   		$('#deanRefusalDetails').hide();
   	</c:if>

    $("#deanSelect").change(function(){
     	 var selectedDean = $("#deanSelect option:selected").text();
     	 $.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.deanSelect.confirm.begin"/> ' + selectedDean + ' <fmt:message key="iw_IL.eqfSystem.editProposal.deanSelect.confirm.end"/>',
     	 '<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
     	 function(confirm){
     	 	if (confirm==1){
     	 		save();
     	 	}
     	 	else{
     	 		document.getElementById("deanSelect").selectedIndex=0;
     	 	}
     	 });
     	 return false;
     });

	$("input:radio[@id='deanApproved']").click(function() {
         var deanRefusalDetails = $(this).fieldValue();
         if (deanRefusalDetails=='false'){
         	$('#deanRefusalDetails').slideDown();
         }else{
         	$('#deanRefusalDetails').slideUp();
         }
    });

	$('#hebrewTitle').autoSave(function(){
	   var options = {
       	 	url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
	    unsaved = false;
   }, {delay: 5000});

   $('#hebrewTitle').blur(function(){
	   var options = {
       	 	url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
	    if (isBasicDetailsAllFilled()){
	    	$("button.moveOn").show();
	    }
   });

   $('#englishTitle').autoSave(function(){
	   var options = {
       	 	url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
	    unsaved = false;
   }, {delay: 5000});

   $('#englishTitle').blur(function(){
	   var options = {
       	 	url:       'proposal.html?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
	    if (isBasicDetailsAllFilled()){
	    	$("button.moveOn").show();
	    }
   });

   $("#fundingAgencyAdd").blur(function(){
		setTimeout('handleNewFund()',1500);
	});
	$("button.addFund").click(function(){
		return false;
	});


    var researchers = "<c:forEach items="${researchers}" var="entry"><c:out value="${entry.key}"/>,</c:forEach>".split(",");
    $("#researcherAdd").focus(function(){
    	$("#researcherAdd").unautocomplete();
    	$("#researcherAdd").autocomplete(researchers, {align: 'right'});
    });

    var researcherAddConfirmed = 0;

    $('#researcherAddButton').click(function(e){
    	$.alerts.confirm('<p><fmt:message key="iw_IL.eqfSystem.editProposal.addResearcher.confirm"/> '+ $("#researcherAdd").val() +' <fmt:message key="iw_IL.eqfSystem.editProposal.addResearcher.confirm1"/></p>',
    	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    	function(confirm){
    		if (confirm==1){
    			researcherAddConfirmed = 1;
      			document.form.action = "proposal.html?action=addResearcher"
	    		document.form.submit();
	    	}
    	});
   		 return false;
	});

	$('#researchersRemoveSelect').change(function (){
		$.alerts.confirm('<p><fmt:message key="iw_IL.eqfSystem.editProposal.removeResearcher.confirm"/> '+ $("#researchersRemoveSelect option:selected").text() +' <fmt:message key="iw_IL.eqfSystem.editProposal.removeResearcher.confirm1"/></p>',
    	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    	function(confirm){
    		if (confirm==1){
    			document.form.action = "proposal.html?action=removeResearcher"
	    		document.form.submit();
	    	}
    	});
   		 return false;
    });


    $("button.approveDetails").click(function(){
    	<c:choose>
    	<c:when test="${command.proposalBean.mainResearcher.id == userPersonBean.id}">
    		$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.mainResearcherApproval.confirmApproval"/>'
         	,'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
         	function(confirm) {
				$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.researcherApproval.confirmApprovalFormal"/>'
         		,'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
         		function(confirm) {
					if (confirm==1){
						$("#form").append('<input type=\"hidden\" name=\"proposalBean.researcherApproved\" value=\"true\"');
						save();
					}
				});
			});
		</c:when>
		<c:otherwise>
			$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.researcherApproval.confirmApprovalFormal"/>'
         		,'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
         		function(confirm) {
					if (confirm==1){
						$("#form").append('<input type=\"hidden\" name=\"proposalBean.researcherApproved\" value=\"true\"');
						save();
					}
			});
		</c:otherwise>
		</c:choose>
		return false;
	});

 	<c:if test="${! command.proposalBean.experimental}">
 	   $("#experimentApproved").hide();
 	 </c:if>

 	 $("input:radio[@id='experimental']").click(function() {
         var experimental = $(this).fieldValue();
         if (experimental=='true'){
         	$('#experimentApproved').show();
         }else{
         	$('#experimentApproved').hide();
         }
    });

    $.ajaxExpire(
    	function() {
    		$.alerts.alert('העמוד ייסגר עקב חוסר שימוש');

     		window.location = 'welcome.html' ; },
    29*60*1000, /* 29 minutes */
    true /* start session expiration now */ );


    $("#country").hide();
    $("select#continent").change(function(){
    	$("#country").slideDown();
    	var continentId= $("select#continent").val();
    	$("#countrySelect").load("selectBoxFiller?personId=<c:out value="${command.proposalBean.personProposalBean.personId}"/>&proposalId=<c:out value="${command.proposalBean.id}"/>&type=country&continentId="+continentId);
    });

    $("#institute").hide();
    $("#countrySelect").change(function(){
    	$("#institute").slideDown();
    	$("#instituteInput").unautocomplete();
    	var countryId= $("#countrySelect").val();
    	$.get('selectBoxFiller',{personId:<c:out value="${command.proposalBean.personProposalBean.personId}"/>,proposalId:<c:out value="${command.proposalBean.id}"/>,type:'institute',countryId: countryId},function(data) {$("#instituteInput").autocomplete(data.split(","), {align: 'left'});});
    });



	var partners = "<c:forEach items="${partners}" var="partner"><c:out value="${partner}"/>,</c:forEach>".split(",");
    $("#partnerInput").autocomplete(partners,{align: 'left'});

    $('#partnerAddButton').click(function(){
    	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.addPartner.confirm"/>',
    	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    	function(confirm){
    		if(confirm==1){
    			document.form.action = "proposal.html?action=addPartner"
	    		document.form.submit();
	    	}
	    });
    	return false;
	});

	$('#partnerRemoveSelect').change(function(){
		$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.removePartner.confirm"/>',
    	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    	function(confirm){
    		if(confirm==1){
    			document.form.action = "proposal.html?action=removePartner"
	    		document.form.submit();
	    	}
	    });
    	return false;
    });

    $("input:radio[@id='yissum']").click(function() {
         var needsYissum = $(this).fieldValue();
		 var confirm;
         if (needsYissum=='1'){
         	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.needsYissum.confirm"/>',
         	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    		function(confirm){
    			if(confirm==1){
    				document.form.action = "proposal.html?action=sendYissum";
	 	         	$('form#form').append('<input type="hidden" name="proposalBean.yissumResearcherHandled" value="true"/>');
        		 	$('form#form').submit();
    			}
    		});
         }
         else{
         	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.doesntNeedYissum.confirm"/>',
         	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    		function(confirm){
    			if(confirm==1){
    				document.form.action = "proposal.html?action=sendYissum";
	 	         	$('form#form').append('<input type="hidden" name="proposalBean.yissumResearcherHandled" value="true"/>');
        		 	$('form#form').submit();
    			}
    		});
         }
         return true;
    });

    $("input:radio[@id='yissumApproval']").click(function() {
		document.form.action = "proposal.html?action=yissumApproval";
		$("#form").submit();
	});

	$("button.deleteAttach").click(function(){
		var id = this.id;
		$('form#form').append("<input type=\"hidden\" name=\"action\" value=\"deleteAttach\"/>");
		$('form#form').append("<input type=\"hidden\" name=\"attachId\" value=\""+id+"\"/>");
		$('form#form').submit();
	});
	$("button.moveUpAttach").click(function(){
		var id = this.id;
		$('form#form').append("<input type=\"hidden\" name=\"action\" value=\"moveUpAttach\"/>");
		$('form#form').append("<input type=\"hidden\" name=\"attachId\" value=\""+id+"\"/>");
		$('form#form').submit();
	});
	$("button.moveDownAttach").click(function(){
		var id = this.id;
		$('form#form').append("<input type=\"hidden\" name=\"action\" value=\"moveDownAttach\"/>");
		$('form#form').append("<input type=\"hidden\" name=\"attachId\" value=\""+id+"\"/>");
		$('form#form').submit();
	});

	$("button.save").click(function(){
		save();
		return false;
	});

	$("button.experimentApproval").click(function(){
		$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.experimentApproved.confirm"/>',
         		'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    			function(confirm){
    				if(confirm==1){
						$("#form").append("<input type=\"hidden\" name=\"proposalBean.experimentApproved\" value=\"true\"/>");
    					save();
    				}
    	});
    	return false;
    });

    $("button.deanApproval").click(function() {
         var deanApproval = $("input:radio[id='deanApproved']").fieldValue();
         alert(deanApproval);
         if (deanApproval=='true'){
         	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.deanApproval.confirm"/>',
         	'<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
    			function(confirm){
    				if(confirm==1){
    					save();
    				}
    		});
         }
         return false;
    });

    $("select#budgetOfficerSelect").change(function(){
    	save();
    });

    $("button.budgetApproved").click(function(){
    	$("#form").append("<input type=\"hidden\" name=\"proposalBean.budgetApproved\" value=\"true\"/>");
    	save();
    	return false;
    });

});

(function( $ ) {
    $.expire = function( fn, callback, interval, start ) {
        var timer;
        function set() {
            timer = setTimeout( callback, interval );
        }
        if( start ) set();
        return function() {
            clearTimeout( timer );
            set();
            fn.apply( this, arguments );
        };
    };

    $.ajaxExpire = function( callback, interval, start ) {
        $.ajax = $.expire( $.ajax, callback, interval, start );
    };
})( jQuery );
</script>