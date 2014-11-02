<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<script src="/js/jquery-ui-1.10.3.custom.js"></script>
<link href="/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
 <script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>

<style>
	.ui-autocomplete {
	}

	.ui-autocomplete li {
		list-style-type: none;
	}


</style>
			<script type="text/javascript">
		
			function resetAutocomplete(organizationUnits){
				$("#searchPhrase").autocomplete( 
						{source: organizationUnits,
						 minLength: 0,
						 highlight: true,
						 select: function(event, ui) {
							$("#searchPhrase").val(ui.item.label);
						 }
					    }
				);
			}
			$(document).ready(function(){
				
			var organizationUnits = [];
			<c:forEach items="${completeListForFilter.viewableBeans}" var="viewableBean" varStatus="varStatus">					
				organizationUnits.push("${viewableBean.fields[1].text}");
			</c:forEach>
			resetAutocomplete(organizationUnits);

			$(".select").click(function(e){
				$("#searchPhrase").val('');
				if (!$(e.target).hasClass('fancyTextAutocomplete')) {
					resetAutocomplete(organizationUnits);
					$('#searchPhrase').keydown();
				}
			});
			
				//$("select.fancySelect").unbind("change").change(function(event){
				//	var t = $(this).find("option:selected").text();
				//	$(this).prev(".fancyText").text(t);
				//});
				//$("select.fancySelect").trigger("change");

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
				 
				$('.box-contact').click(function(e) {
					e.preventDefault();
					e.stopPropagation();
					//alert($(".contacts", $(this).closest(".box-links")).css('display'));
					if($(".contacts", $(this).closest(".box-links")).css('display')=='none')
						$(".contacts", $(this).closest(".box-links")).show();
				});
				$(document).click(function(e) {
					if(!$(e.target).hasClass('contacts')) { 
						$(".contacts").each(function (){
							//alert($(this).css('display'));
							if($(this).css('display')=='block')
								$(this).hide();
						});
					}
				});
				
				});
				</script>