<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
			<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
			<!--[if (gte IE 6)&(lte IE 8)]>
			  <script type="text/javascript" src="js/selectivizr-min.js"></script>
			<![endif]-->
			<script src="js/placeholders.min.js"></script>
			<script src="js/idangerous.swiper-2.1.min.js"></script>
			<script>
				var mySwiper = new Swiper('#slider',{
					loop:true,
					grabCursor: true,
					pagination: '.pagination',
					paginationClickable: true,
					autoplay: 6000,
					speed : 500
				})
				$('#slider .arrow-left').on('click', function(e){
					e.preventDefault()
					mySwiper.swipePrev()
				})
				$('#slider .arrow-right').on('click', function(e){
					e.preventDefault()
					mySwiper.swipeNext()
				})

				 var mySwiperNews= new Swiper('#news',{
					slidesPerView: 2,
					loop:true,
					grabCursor: true,
					autoplay: 10000,
					speed : 500
				  })
				$('#news .arrow-left').on('click', function(e){
					e.preventDefault()
					mySwiperNews.swipePrev()
				})
				$('#news .arrow-right').on('click', function(e){
					e.preventDefault()
					mySwiperNews.swipeNext()
				})
				
				
			</script>

