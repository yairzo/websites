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
				
				
				
				var daysWithFunds=[${daysWithFunds}];

				$(function() {
			        $.datepicker.regional['he'] = {
			            closeText: 'סגור',
			            prevText: '&#x3c;הקודם',
			            nextText: 'הבא&#x3e;',
			            currentText: 'היום',
			            monthNames: ['ינואר','פברואר','מרץ','אפריל','מאי','יוני',
			            'יולי','אוגוסט','ספטמבר','אוקטובר','נובמבר','דצמבר'],
			            monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
			            dayNames: ['ראשון','שני','שלישי','רביעי','חמישי','שישי','שבת'],
			            dayNamesShort: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
			            dayNamesMin: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
			            weekHeader: 'Wk',
			            dateFormat: 'yy-mm-dd',
			            firstDay: 0,
			            isRTL: false,
			            showMonthAfterYear: false,
			            yearSuffix: ''
			        };
			        if(${lang.localeId=='iw_IL'}){
			        	$.datepicker.setDefaults($.datepicker.regional['he']);
			        	$(".date").datepicker("refresh");
			        }
			    });
				
				
				$(function () {
					$(".date").datepicker({dateFormat: 'yy-mm-dd',
						onChangeMonthYear: function(year, month) {
							$.ajax({url:'homePageCalendar.html?type=changeMonth&month='+month+'&year='+year,
								async: false,
								success:function(data) {
								    daysWithFunds = data.split(',');
								    for(var i=0; i<daysWithFunds.length; i++){
								    	daysWithFunds[i] = parseInt(daysWithFunds[i], 10);
								    } 						
							 	}
							 });
							setTimeout(function() {
								 $('.ui-state-hover').removeClass('ui-state-hover');	
							}, 1);
						},
						beforeShowDay: function(date){
							return [true, daysWithFunds.indexOf(date.getDate())>-1 ? 'dayWithFund' : ''];
						}
					});	
					
					$('.ui-state-hover').removeClass('ui-state-hover');	
					
				});			
				
				
				$(".date").change(function(e){					
					$.get('homePageCalendar.html?type=callForProposalsPerDay&date='+$(this).val(), function(data) {
						$(".callForProposalsPerDay").html(
								"<div class=\"clearfix\">"+data+"</div><div class=\"triangle\"></div>");
						console.log(data.length);
						var height = $(".callForProposalsPerDay").height();
						$(".callForProposalsPerDay").css('top',-(height/2));
												
						if(jQuery.trim(data)){
							$('.callForProposalsPerDay').show();							
						}
						else{
							$('.callForProposalsPerDay').hide();
						}						
				 	});					
				});
				
				$(".callForProposalsPerDay").on("click", ".viewProposal", function(e) {
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
