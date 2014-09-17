
   <script src="/js/jquery-1.10.2.min.js"></script>
   <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
		<script src="/js/form.js"></script>
		<script type="text/javascript" src="/js/jquery.form.js"></script>	
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			$('.search_content_notready').each(function(){
				$(this).removeClass("search_content_notready").addClass("search_content");
			});
			
			$(".viewProposal").click(function(e) {
				e.preventDefault();
				var id=$(this).attr("id");	   		
				$.ajax({
				    url : '/call_for_proposal/' + id + '/popup',
				    type: 'GET',
				    success : handleData
				 })
			});
			
			$(function() {
				$(".popup_placeholder" ).draggable();
			});	
			
			$(document).click(function() {
			    $(".popup_placeholder").hide();
			});
			
			$(".popup_placeholder").click(function(e) {
				e.stopPropagation();
			});
			
			$(".advanced_clear").click(function(){
				$("#advanced_subject").val('');
				$("#form").append("<input type=\"hidden\" name=\"action\" value=\"cleanSearch\"/>");
				$("#form").ajaxSubmit();
		    	return true;
		 	});
		});
		function handleData(data) {
			$(".popup_placeholder").html(data);
			$(".popup_placeholder").show();
			var pTop = ($(window).height() - $(".popup_placeholder").height())/2;
			var pLft = ($(window).width() - 600)/2;
			$(".popup_placeholder").css({
		    	position: 'fixed',
		    	left: pLft,
		    	top: pTop
			});
		}
		</script>