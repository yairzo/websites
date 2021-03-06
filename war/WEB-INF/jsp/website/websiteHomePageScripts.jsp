<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

   <script src="/js/jquery-1.10.2.min.js"></script>
   <script type="text/javascript" src="/js/jquery.bxslider.js"></script>
   <script type="text/javascript" src="/js/jquery.fitvids.js"></script>
   <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
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
				//$.get('/callForProposalCalendar.html?h=1', function(data) { 
					//$("div.homePageCalendar").html(data); 
			 	//}); 
				
				$('.homePageCalendar').on('click','.ui-datepicker-prev',function(e){
					e.preventDefault();
					$.get('/calendar/prev/home', function(data) {
						$("div.homePageCalendar").html(data);
				 	});
				});
				$('.homePageCalendar').on('click','.ui-datepicker-next',function(e){
					e.preventDefault();
					$.get('/calendar/next/home', function(data) {
						$("div.homePageCalendar").html(data);
				 	});
				});
				
				$(document).click(function(){
					$('.callForProposalsPerDay').hide();
				});
				
				var ie = (function(){
				    var undef,
				        v = 3,
				        div = document.createElement('div'),
				        all = div.getElementsByTagName('i');
				 
				    while (
				        div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
				        all[0]
				    );
				    return v > 4 ? v : undef;
				}());
				var slidingMode='horizontal';
				if(ie<8)slidingMode = 'fade';
				
				$('.messageslider').show();
				$('.messageslider').bxSlider({
					controls:false,
					mode:slidingMode,
					pager:true,
					<c:choose>
					<c:when test="${fn:length(textualPages)>1}">
					auto: true,
					</c:when>
					<c:otherwise>
					auto: false,
					</c:otherwise>
					</c:choose>
					slideWidth:406,
					pause:5000,
					<c:choose>
					<c:when test="${lang.rtl}">
					startSlide: <c:out value="${fn:length(textualPages)-1}"/>, 
					autoDirection: 'prev'
					</c:when>
					<c:otherwise>
					startSlide: 0,
					autoDirection: 'next'
					</c:otherwise>
					</c:choose>
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
						
							var rowPos = $(this).closest("td").position();
							bottom_top_height = rowPos.top - $(".callForProposalsPerDay", $(this).closest("td")).height();
							
							var triangle_class = "triangle_down_";
							
							bottomCorner = rowPos.left;
							
							<c:if test="${lang.rtl}">
								bottomCorner = bottomCorner - $(this).closest("td").width() - 20;
							</c:if>
							triangle_class += "${lang.opDir}";
							<c:choose>
							<c:when test="${lang.rtl}">
								bottomCorner = bottomCorner + 20;
							</c:when>
							<c:otherwise>
								bottomCorner = bottomCorner - 170;
							</c:otherwise>
							</c:choose>
							
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
					window.open('/call_for_proposal/'+proposalId);
				});
				$('.homePageCalendar').on('click','.allCallForProposals',function(e) {
					e.preventDefault();
					var day=$(this).attr("id");
					window.open('/search_funding/'+day);
				});
				
				$(".messagePage").click(function(e){
					e.preventDefault();
					var messagePageId=$(this).attr("id");	
					window.open('/page/'+messagePageId);
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
					pause:5000,
					video:true,
					pager:true,

<c:choose>
<c:when test="${lang.rtl}"> 
					startSlide: <c:out value="${fn:length(images)-1}"/>, 
					autoDirection: 'prev',
</c:when>
<c:otherwise>
					startSlide: 0, 
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
			$.ajaxSetup({ cache: false });
	</script>
