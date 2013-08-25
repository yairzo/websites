<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<div class="rotator">
				<div class="bx-wrapper">
					<div class="bx-viewport">
						  <div class="pictureslider" style="display:none;">
  								<c:forEach items="${images}" var="image" varStatus="imgIndex">
  								<div>
  									<img width="960" height="340" src="imageViewer?imageId=${image.id}&attachType=bodyImage" />
									<div class="bx-caption">
										<h3>מן המחקר באוניברסיטה</h3>
										<span>${image.captionHebrew}</span>
										<div class="bx-pager bx-default-pager">
											<c:forEach items="${images}" var="image" varStatus="bulletIndex">
 												<c:set var="activeClass" value=""/>
 												<c:if test="${imgIndex.index==bulletIndex.index}">
  													<c:set var="activeClass" value="active"/>
  												</c:if>
												<div class="bx-pager-item"><a class="bx-pager-link ${activeClass}" href="">${bulletIndex.index+1}</a></div>
											</c:forEach>
										</div>
									</div>
								</div>
								</c:forEach>
						 </div>
 					</div>
				</div>
			</div>
			
			<div class="container clearfix">
				<div class="side sidelinks">
					<h3>קישורים מהירים</h3>
					<ul>
						<li class="link_research"><a href="#">מערכת המחקרים</a></li>
						<li class="link_budget"><a href="#">תקציבים אישיים</a></li>
						<li class="link_mail"><a href="#">דיוור ישיר</a></li>
						<li class="link_guidelines"><a href="#">הנחיות להגשה</a></li>
						<li class="link_grant"><a href="#">הנחיות למענקים</a></li>
						<li class="link_application"><a href="#">חברת יישום</a></li>
					</ul>
				</div>
				<div class="main">
					<div class="mainbox">
						<div class="title">
							<h1>ברוכים הבאים לרשות למחקר ופיתוח</h1>
							<a href="#">קרא עוד</a>
						</div>
						<div class="mainbox_text">
							<p>של מלא אחרים מיזמים. גם ארץ מחליטה רשימות מיוחדים. קבלו יידיש של אחד. או פנאי המשפט האנציקלופדיה מלא, מיזם לעתים עקרונות כלל אם, מבוקשים ביולוגיה אל בקר.<br />קסאם דרכה ב ארץ, מה רבה הקנאים למאמרים חרטומים. ננקטת קולנוע אדריכלות גם קרן, מה ארץ אדריכלות המקושרים.</p>
						</div>
					</div>
					<div class="mainbox">
						<div class="title">
							<h2>עדכונים אחרונים</h2>
							<a href="textualPages.html">לכל ההודעות</a>
						</div>
						<div class="mainbox_news" style="direction:ltr">
							<div class="bx-wrapper">
								<div class="bx-viewport"  >
									<div class="messageslider" style="display:none;">
  										<c:forEach items="${textualPages}" var="textualPage" varStatus="textualIndex">
  										<c:set var="direction" value="rtl"/>
  										<c:if test="${textualPage.localeId=='en_US'}">
  											<c:set var="direction" value="ltr"/>
  										</c:if>
  										<div style="height:80px;width:396px;direction:${direction};">
  											${textualPage.title}
										</div>
 										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="side">
					<h3 class="board_title">לוח קולות קוראים</h3>
					<div class="board_calendar">
						<!---->
						<div class="date"></div>
						<div class="callForProposalsPerDay" style="display:none;">
							<div class="clearfix"></div>
							<div class="triangle"></div>
						</div>
						<!---->
						<a href="#" class="last_calls">קולות קוראים שעודכנו לאחרונה</a>
						<div class="board_search">
							<h3>חיפוש</h3>
							<a href="searchCallForProposals.html" class="board_search_advanced">חיפוש מתקדם</a>
							<form action="searchCallForProposals.html" method="post" class="board_form">
								<input type="text" name="searchWords" onFocus="if(this.value==this.defaultValue)this.value=''"    
onblur="if(this.value=='')this.value=this.defaultValue" value="הקלידו נושא לחיפוש ..." class="input_text" />
								<input type="submit" value="חיפוש" class="input_submit" />
							</form>
						</div>
					</div>
				</div>
			</div>
