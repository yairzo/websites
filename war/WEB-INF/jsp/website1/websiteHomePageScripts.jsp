<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

   <script src="js/modernizr-2.6.2.min.js"></script>
   <script src="js/jquery-1.10.2.min.js"></script>
   <script type="text/javascript" src="/iws/js/jquery.bxslider.js"></script>
   <script type="text/javascript" src="/iws/js/jquery.fitvids.js"></script>
   <script type="text/javascript" src="/iws/js/jquery-ui-1.10.3.custom.js"></script>
   <script type="text/javascript">
			$(function(){
				$('ul.menu li').has('ul')
				.hover(
				function() {
				$(this).find("ul").stop(true, true).slideDown(300);
				},
				function() {
				$(this).find("ul").stop(true, true).hide();
				})
				.each(function(){
				$(this).find("ul").hide();
				});
			}); 
			
			
			$(document).ready(function() {
				$.get('callForProposalCalendar.html?h=1', function(data) {
					$("div.homePageCalendar").html(data);
			 	});

				$('.homePageCalendar').on('click','.ui-datepicker-prev',function(e){
					e.preventDefault();
					$.get('callForProposalCalendar.html?h=1&action=prevMonth', function(data) {
						$("div.homePageCalendar").html(data);
				 	});
				});
				$('.homePageCalendar').on('click','.ui-datepicker-next',function(e){
					e.preventDefault();
					$.get('callForProposalCalendar.html?h=1&action=nextMonth', function(data) {
						$("div.homePageCalendar").html(data);
				 	});
				});
				
				$(document).click(function(){
					$('.callForProposalsPerDay').hide();
				});
				
				$('.messageslider').show();
				$('.messageslider').bxSlider({
					controls:false,
					mode:'horizontal',
					auto: true ,
					slideWidth:406,
<c:if test="${lang.rtl}">
					startSlide: <c:out value="${fn:length(textualPages)-1}"/>, 
					autoDirection: 'prev'
</c:if>
					});
				
				
				
			

				$('.homePageCalendar').on('click','.viewAll',function(e) {
					e.stopPropagation();
					e.preventDefault();
					if($(".callForProposalsPerDay", $(this).closest("td")).css('display')=='block')
						$(".callForProposalsPerDay").hide();
					else{
						$(".callForProposalsPerDay").hide();
						if(jQuery.trim($(".clearfix", $(this).closest("td")).html())){			
							$(".callForProposalsPerDay", $(this).closest("td")).show();
							
							var top_bottom = $(".callForProposalsPerDay", $(this).closest("td")).attr('class');
							top_bottom = top_bottom.split(' ')[1];
							var edge_middle = $(".callForProposalsPerDay", $(this).closest("td")).attr('class');
							edge_middle = edge_middle.split(' ')[2];
							
							var rowPos = $(this).closest("td").position();
							
							var triangle_class = "triangle_";
							
							if (top_bottom == "bottom"){
								bottom_top_height = rowPos.top - $(".callForProposalsPerDay", $(this).closest("td")).height();
								triangle_class += "down_";
							}
							else{
								bottom_top_height = rowPos.top + $(this).closest("td").height();
								triangle_class += "up_";
							}
							
							bottomCorner = rowPos.left;
							
							<c:if test="${lang.rtl}">
								bottomCorner = bottomCorner - $(this).closest("td").width() - 20;
							</c:if>
							
							
							
							if (edge_middle == "edge"){
								triangle_class += "${lang.opDir}";
								<c:choose>
								<c:when test="${lang.rtl}">
									bottomCorner = bottomCorner + 120;
								</c:when>
								<c:otherwise>
									bottomCorner = bottomCorner - 120;
								</c:otherwise>
								</c:choose>
							}
							else{
								triangle_class += "${lang.dir}";
							}
							
							$(".callForProposalsPerDay", $(this).closest("td")).find(".triangle").addClass(triangle_class);
							
							$(".callForProposalsPerDay").css({
						   	 	top: bottom_top_height,
						    	left: bottomCorner
							});
						}
					}
					e.stopPropagation()
				});	
				
				
				$('.homePageCalendar').on('click','.viewProposal',function(e) {
					e.preventDefault();
					var proposalId=$(this).attr("id");	
					window.open('callForProposal.html?id='+proposalId+'&t=1');
				});
				
				$(".messagePage").click(function(e){
					e.preventDefault();
					var messagePageId=$(this).attr("id");	
					window.open('textualPage.html?id='+messagePageId+'&t=1');
				});
			});
			
			$(window).load(function(){
				window.setTimeout(function(){start_slider()}, 2000);				
			});
			
			function start_slider(){
				$('div.default').hide();
				$('.pictureslider').show().bxSlider({
					controls:false,
					mode:'fade',
					auto: true,
					video:true,
					pager:true,

<c:choose>
<c:when test="${lang.rtl}"> 
					startSlide: <c:out value="${fn:length(images)-1}"/>, 
					autoDirection: 'prev',
</c:when>
<c:otherwise>
					startSlide: <c:out value="${fn:length(images)-1}"/>, 
					autoDirection: 'next',
</c:otherwise>
</c:choose>
					onSlideBefore: function($slideElement, oldIndex, newIndex){
						
						//console.log("on slide before. element: " + $slideElement.html());
						var $lazy = $slideElement.find('.lazy');
						
						
						//console.log("img class: " + $lazy.attr('class'));
			            var $load = $lazy.attr('data-src');
			            
			            $lazy.attr("src",$load).removeClass('lazy');
			            
			        }
				});
			}
	</script>
