<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>

<script type="text/javascript">


$(document).ready(function() {
	var daysWithFunds=[${daysWithFunds}];

	$(".date").datepicker({dateFormat: 'yy-mm-dd',
		onChangeMonthYear: function(year, month) {
			 $.ajax({url:'homePageCalendar.html?type=changeMonth&month='+month+'&year='+year,
				async: false,
				success:function(data) {
				    daysWithFunds = data.split(',');
				    for(var i=0; i<daysWithFunds.length; i++){daysWithFunds[i] = parseInt(daysWithFunds[i], 10);} 						
				    $(".date").datepicker("refresh");
			 	}
			 });
		},
		beforeShowDay: function(date){
			//alert(daysWithFunds);
			//alert(daysWithFunds.indexOf(date.getDate()));
			return [true, daysWithFunds.indexOf(date.getDate())>-1?'dayWithFund' : ''];
		}
	});
	
	$(".date").change(function(){
		$.get('homePageCalendar.html?type=callForProposalsPerDay&date='+$(this).val(), function(data) {
			$(".callForProposalsPerDay").html(data);
	 	});
	});

	
	var dlg =$("#genericDialog").dialog({
	    autoOpen: false,
	    show: 'fade',
	    hide: 'fade',
	    modal: true,
	    open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
	});
	dlg.parent().appendTo($("#form"));
	
	$(".viewProposal").click(function(e) {
		alert($(this).attr("id"));	
		e.preventDefault();
		var proposalId=$(this).attr("id");	   		
		dlg.dialog('option', 'buttons', {
        	"סגור" : function() {
        		dlg.dialog("close");
        	}
		});
		dlg.dialog({ modal: true });
		dlg.dialog({ height: 700 });
		dlg.dialog({ width: 750 });
		dlg.dialog("option", "position", "center");
		$.get('callForProposal.html?id='+proposalId, function(data) {
			dlg.html(data).dialog("open");
		});
	});	

});


</script>
