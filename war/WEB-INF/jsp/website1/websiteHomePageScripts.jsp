<%@ page  pageEncoding="UTF-8" %>
   <script src="js/modernizr-2.6.2.min.js"></script>
   <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
   <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>
   <script type="text/javascript" src="/iws/js/jquery.bxslider.js"></script>
   <!-- <link href="/iws/style/jquery.bxslider.css" rel="stylesheet" type="text/css"/> -->
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
				
				$('.messageslider').show();
				$('.messageslider').bxSlider({
					controls:false,
					mode:'horizontal',
					auto: true ,
					slideWidth:406,
					auto_direction: 'prev' 
					});
				$('.pictureslider').show();
				$('.pictureslider').bxSlider({
					controls:false,
					mode:'fade',
					auto: true,
					video:true,
					pager:false
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
			        $.datepicker.setDefaults($.datepicker.regional['he']);
			        $(".date").datepicker("refresh");
			    });
				
				
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

				$(".date").change(function(e){
					$.get('homePageCalendar.html?type=callForProposalsPerDay&date='+$(this).val(), function(data) {
						$(".callForProposalsPerDay").html(
								"<div class=\"clearfix\">"+data+"</div><div class=\"triangle\"></div>");
						if(data.length>2){
							$('.callForProposalsPerDay').show();
							$(".callForProposalsPerDay").css({
								position:"absolute",
								top:e.pageY, 
								left: e.pageX
							});
						}
						else
							$('.callForProposalsPerDay').hide();
				 	});
				});
				
						

			});
	</script>
