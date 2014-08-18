<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

				<div class="sliderbox">
					<div class="slider">
						<div id="slider" class="swiper-container">
							<div class="swiper-wrapper">
 								<c:forEach items="${images}" var="image" varStatus="imgIndex">
 								<div class="swiper-slide">
 									<div class="clearfix">
										<img width="975" height="340" src="/homePageImage/${image.title}" />
									</div>
									<div class="swiper-content clearfix">
										<h3>${image.title}</h3>
										<p>${image.captionEnglish}</p>
									</div>
								</div>
								</c:forEach>
							</div>
							<a class="arrow-left" href="#"></a> 
							<a class="arrow-right" href="#"></a>
							<div class="pagination"></div>
						</div>
					</div>
				</div>
			<div class="container clearfix">
					<nav class="boxes clearfix">
						<ul>
							<li>
								<a href="#">
									<span class="box-title clearfix">
										<span class="box-title-icon pull-left"><img src="/image/nano/i-education.png" alt="" /></span>
										<span class="box-title-text pull-left">EDUCATION</span>
									</span>
									<span class="box-content">
										<span class="box-content-text clearfix">Cu nec repudiandae content ones, vero partem dictas at pro  ad eum vero dictas.</span>
										<span class="box-content-link clearfix">read more</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="box-title clearfix">
										<span class="box-title-icon pull-left"><img src="/image/nano/i-science.png" alt="" /></span>
										<span class="box-title-text pull-left">SCIENCE</span>
									</span>
									<span class="box-content">
										<span class="box-content-text clearfix">Vim minim liber harum cu, modo prompta ne pro, vix facete eruditi cotidieque ne.</span>
										<span class="box-content-link clearfix">read more</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="box-title clearfix">
										<span class="box-title-icon pull-left"><img src="/image/nano/i-research.png" alt="" /></span>
										<span class="box-title-text two-lines pull-left">RESEARCH<br />FACILITIES</span>
									</span>
									<span class="box-content">
										<span class="box-content-text clearfix">Vim minim liber harum cu, modo prompta ne pro, vix facete eruditi cotidieque ne.</span>
										<span class="box-content-link clearfix">read more</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="box-title clearfix">
										<span class="box-title-icon pull-left"><img src="/image/nano/i-spotlight.png" alt="" /></span>
										<span class="box-title-text two-lines pull-left">RESEARCH<br />SPOTLIGHT</span>
									</span>
									<span class="box-content">
										<span class="box-content-text clearfix">Quo vivendo definitionem in. Recteque sententiae eos id. Te est congue libris duo.</span>
										<span class="box-content-link clearfix">read more</span>
									</span>
								</a>
							</li>
						</ul>
					</nav>
					<h2 class="title-news"><span>NEWS &amp; EVENTS</span></h2>
					<div class="news">
						<div id="news" class="swiper-container">
							<div class="swiper-wrapper">
								<c:forEach items="${textualPages}" var="message">
 								<div class="swiper-slide">
									<div class="news-content pull-left clearfix">
										<a href="/page/${message.urlTitle}" class="news-title clearfix"><nobr>${message.title}</nobr></a>
										<p class="news-date clearfix">${message.updateTimeFormatted}</p>
										<p>${message.htmlShort}</p>
										<a href="/page/${message.urlTitle}" class="news-more">read more</a>
									<br><br>
									</div>
								</div>
								</c:forEach>
							</div>
							<a class="arrow-left" href="#"></a> 
							<a class="arrow-right" href="#"></a>
						</div>
					</div>
					
					
					
				</div>