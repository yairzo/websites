<script language="Javascript">

$(document).ready(function() {


	$('#cancel').click(function(){
		var confirm  = window.confirm('<fmt:message key="eqfSystem.editProposal.confirmCancel"></fmt:message>');
    	if (confirm){
    		document.form.action="editProposal.html?action=cancel";
    		document.form.submit();
    	}
    	//return true;
	});

	$('#save').click(function(){
		document.form.action = "editProposal.html?action=save"
    	document.form.submit();
    	return true;
	});

	$('#proposalAttach').hide();
  $('#toggleProposalAttachUpload').click(function() {
    $('#proposalAttach').toggle();
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

   <c:if test="${command.proposalBean.fundingAgencyApproved==1}">
   		$('#fundingAgencyStatus').hide();
   	</c:if>
   $("input:radio[@id='fundingAgencyApproved']").click(function() {
         var fundApproved = $(this).fieldValue();

         if (fundApproved=='1'){
         	alert('<fmt:message key="eqfSystem.editProposal.fundingAgencyApproval.confirmApproval"/>');
         	$('#fundingAgencyStatus').hide();
         }else{
         	alert('<fmt:message key="eqfSystem.editProposal.fundingAgencyApproval.confirmDisapproval"/>');
         	$('#fundingAgencyStatus').show();
         }
    });

    <c:if test="${ ! command.proposalBean.experimental}">
   		$('#safetyUpload').hide();
   		$('#humanExperiment').hide();
   		$('#animalsExperiment').hide();
   	</c:if>
     $("input:radio[@id='experimental']").click(function() {
        var experimental = $(this).fieldValue();
         if (experimental=='true') {
         	$('#safetyUpload').show();
         	$('#humanExperiment').show();
         	$('#animalsExperiment').show();
         }else{
         	$('#safetyUpload').hide();
         	$('#humanExperiment').hide();
         	$('#animalsExperiment').hide();
         }
     });

     <c:if test="${ ! command.proposalBean.humanExperiment}">
   		$('#humanUpload').hide();
   	</c:if>
     $("input:radio[@id='humanExperiment']").click(function() {
        var humanExperiment = $(this).fieldValue();
         if (humanExperiment=='true') {
         	$('#humanUpload').show();
         }else{
         	$('#humanUpload').hide();
         }
     });

     <c:if test="${ ! command.proposalBean.animalsExperiment}">
   		$('#animalsUpload').hide();
   	</c:if>
     $("input:radio[@id='animalsExperiment']").click(function() {
        var animalsExperiment = $(this).fieldValue();
         if (animalsExperiment=='true') {
         	$('#animalsUpload').show();
         }else{
         	$('#animalsUpload').hide();
         }
     });

     <c:if test="${ ! command.proposalBean.needsDeanApproval}">
   		$('#deanApproval').hide();
   	</c:if>
   $("input:radio[@id='needsDeanApproval']").click(function() {
         var needsDeanApproval = $(this).fieldValue();

         if (needsDeanApproval=='true'){
         	$('#deanApproval').show();
         }else{
         	$('#deanApproval').hide();
         }
    });


    <c:if test="${command.proposalBean.deanApproved}">
   		$('#deanRefusalDetails').hide();
   	</c:if>

    $("#deanSelect").change(function(){
     	 var selectedDean = $("#deanSelect option:selected").text();
     	 var confirmMessage = '<fmt:message key="eqfSystem.editProposal.deanSelect.confirm.begin"></fmt:message> ' + selectedDean + ' <fmt:message key="eqfSystem.editProposal.deanSelect.confirm.end"></fmt:message>';
     	 var confirm  = window.confirm(confirmMessage);
     	 if (confirm){
     	 	document.form.action = "editProposal.htm?action=save"
     	 	document.form.submit();
     	 }
     });

	$("input:radio[@id='deanApproved']").click(function() {
         var deanRefusalDetails = $(this).fieldValue();

         if (deanRefusalDetails=='false'){
         	$('#deanRefusalDetails').show();
         }else{
         	$('#deanRefusalDetails').hide();
         }
    });

	$('#hebrewTitle').autoSave(function(){
	   var options = {
       	 	url:       'editProposal.htm?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
   }, {delay: 5000});

   $('#englishTitle').autoSave(function(){
	   var options = {
       	 	url:       'editProposal.htm?action=autosave' ,        // override for form's 'action' attribute
        	type:      'POST'
     	};
	    $('#form').ajaxSubmit(options);
   }, {delay: 5000});


    var researchers = "<c:forEach items="${researchers}" var="entry"><c:out value="${entry.key}"/>,</c:forEach>".split(",");
    $("#researcherAdd").autocomplete(researchers);

    $('#researcherAddButton').click(function(){

    	var confirm  = window.confirm('<fmt:message key="eqfSystem.editProposal.addResearcher.confirm"></fmt:message>');
   		 if (confirm){
    		document.form.action = "editProposal.htm?action=addResearcher"
    		document.form.submit();
    	}
    	else{
    		return false;
    	}
	});

	$('#researchersRemoveSelect').change(function (){
		var confirm  = window.confirm('<fmt:message key="eqfSystem.editProposal.removeResearcher.confirm"></fmt:message>');
   		 if (confirm){
	     	document.form.action = "editProposal.htm?action=removeResearcher"
    	 	document.form.submit();
    	 }
    });

    $("input:checkbox[@id='mainResearcherApproval']").click(function() {
         var mainResearcherApproved = $(this).fieldValue();
         if (mainResearcherApproved=='true'){
         	var confirm = window.confirm('<fmt:message key="eqfSystem.editProposal.mainResearcherApproval.confirmApproval"/>');
         	if (!confirm) {
         		$(this).attr("checked", "");
         	}
         }
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
    		alert('העמוד ייסגר עקב חוסר שימוש');

     		window.location = '/eqfSystem/welcome.htm' ; },
    29*60*1000, /* 29 minutes */
    true /* start session expiration now */ );


    $("#continentSelect").change(function(){
    	var continentId= $("#continentSelect").val();
    	$("#countrySelect").load("/eqfSystem/selectBoxFiller?personId=<c:out value="${command.proposalBean.personProposalBean.personId}"/>&proposalId=<c:out value="${command.proposalBean.id}"/>&type=country&continentId="+continentId);
    });

    $("#countrySelect").change(function(){
    	var countryId= $("#countrySelect").val();
    	alert (countryId);
    	document.form.action = "editProposal.htm?action=save&countryId="+countryId;
    	document.form.submit();
    	return true;
    });

	<c:if test="${countryId > 0}">
    	$("#institute td").show();
    	var institutes = "<c:forEach items="${institutes}" var="entry"><c:out value="${entry.key}"/>,</c:forEach>".split(",");
    	$("#institute input").autocomplete();
    </c:if>



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