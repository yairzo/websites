 			<script src="js/jquery-1.11.0.min.js"></script>
			<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
			<!--[if (gte IE 6)&(lte IE 8)]>
			  <script type="text/javascript" src="/js/selectivizr-min.js"></script>
			<![endif]-->
			<script src="js/placeholders.min.js"></script>
 
 <script type="text/javascript">
			$(document).ready(function(){
				$("select.fancySelect").unbind("change").change(function(event){
					var t = $(this).find("option:selected").text();
					$(this).prev(".fancyText").text(t);
				});
				$("select.fancySelect").trigger("change");

				$(".radiobutton").each(function(){
					if($(this).is(':checked'))
						$(this).parent("div").addClass("active");
				});
				
				$(".radio").click(function(e){
					$(this).parent("div").children(".radio").removeClass("active");
					$(this).addClass("active");
					$(this).children(".radiobutton").prop("checked", true)
				});
				
				$( "#form").submit(function( e ) {
					if($("#contactEmail").val()==''){
						alert("Please enter your contact email. You will be sent an email by which to edit your registration form.")
						e.preventDefault();
					}
					else if('${newForm}'=='true'){
						alert("You will be sent an email with a link, by which you may edit your registration form.")
					}
					
				});
				
			});
			
			function deleteAbstract(attachmentId){
        		$("#form").append("<input type=\"hidden\" name=\"attachmentId\" value=\""+attachmentId +"\"/>");
			    $('#form').submit();
			}
			
</script>