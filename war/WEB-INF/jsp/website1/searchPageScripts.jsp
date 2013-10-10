
   <script src="js/jquery-1.10.2.min.js"></script>
   <script type="text/javascript" src="/iws/js/jquery-ui-1.10.3.custom.js"></script>
		<script src="js/form.js"></script>
		
		<script type="text/javascript">
		$(document).ready(function() {

			$(".viewProposal").click(function(e) {
				e.preventDefault();
				var proposalId=$(this).attr("id");	   		
				var rowPos = $(this).position();
				bottomTop = rowPos.top - 100;
				bottomLeft = rowPos.left - 100;
				$(".popup_placeholder").css({
					position:'absolute',
					top: bottomTop,
			    	left: bottomLeft
				});
				$.get('callForProposal.html?id='+proposalId+'&p=1&t=1', function(data) {
					$(".popup_placeholder").html(data);
					$(".popup_placeholder").show();
				});
				
			});
		});

		</script>