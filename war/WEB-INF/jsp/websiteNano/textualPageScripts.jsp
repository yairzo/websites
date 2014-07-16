<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
			<script type="text/javascript">
			$(document).ready(function(){
				$("select.fancySelect").unbind("change").change(function(event){
					var t = $(this).find("option:selected").text();
					$(this).prev(".fancyText").text(t);
				});
				$("select.fancySelect").trigger("change");

						  $('.box-more').click(function(){
							//get collapse content selector
							var collapse_content_selector = $(this).attr('href');					
				 
							//make the collapse content to be shown or hide
							var toggle_switch = $(this);
							$(collapse_content_selector).toggle(function(){
							  if($(this).css('display')=='none'){
								//change the button label to be 'Show'
								toggle_switch.html('read more <img src="images/arrow-submit.png" alt="" />');
							  }else{
								//change the button label to be 'Hide'
								toggle_switch.html('close <img src="images/arrow-submit.png" alt="" />');
							  }
							});
						  });
				 
						});
				</script>