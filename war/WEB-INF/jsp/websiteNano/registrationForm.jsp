<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

				<div class="container">
					<div class="breadcrumbs clearfix">
						<a href="/">Home</a> <img src="/image/nano/arrow-bread.png" alt="" /><span>Registration</span>
					</div>
					<h1 class="title">Annual Conference</h1>
					<!-- <div class="clearfix">
						<div class="event-details pull-left">
							<div class="pull-left event-mar"><img src="/image/nano/i-pin.png" alt="" /> &nbsp; Royal Rimonim, Dead Sea</div>
							<div class="pull-left"><img src="/image/nano/i-calendar.png" alt="" /> &nbsp; April 23rd and 24th</div>
						</div>
					</div> -->
         			<form:form id="form" name="form" method="POST" action="registrationForm.html" commandName="command" enctype="multipart/form-data">

						<input type="hidden" name="id" value="${command.id}">
						<input type="hidden" name="newForm" value="${newForm}">
						<div class="clearfix">
							<div class="col pull-left">
								<h3>Registration Form</h3>
								<div class="clearfix">
									<label class="label">Title</label>
									<div class="select select-form clearfix">
										<div class="fancyText">${command.title}</div>
      									<form:select path="title" cssClass="fancySelect">
 											<form:option value="">Select</form:option>
											<form:option value="Prof.">Prof.</form:option>
											<form:option value="Dr.">Dr.</form:option>
											<form:option value="Ms.">Ms.</form:option>
											<form:option value="Mr.">Mr.</form:option>
        		    					</form:select>
									</div>
								</div>
								<div class="clearfix">
									<div class="col-two pull-left">
										<label class="label" for="lastName">Surname</label>
										<form:input cssClass="input" path="lastName" id="lastName"/>
									</div>
									<div class="col-two pull-right">
										<label class="label" for="firstName">First name(s)</label>
										<form:input cssClass="input" path="firstName" id="firstName"/>
									</div>
								</div>
								<div class="col-one clearfix">
									<label class="label" for="department">Institute/Department</label>
									<form:input cssClass="input" path="department" id="department"/>
								</div>
								<div class="col-one clearfix">
									<label class="label" for="superviser">Supervisor's name (for students):</label>
									<form:input cssClass="input" path="superviser" id="superviser"/>
								</div>
								<div class="clearfix">
									<div class="col-three pull-left  mar-r-15">
										<label class="label" for="phone">Telephone</label>
										<form:input cssClass="input" path="phone" id="phone"/>
									</div>
									<div class="col-three pull-left mar-r-15">
										<label class="label" for="mobile">Mobile</label>
										<form:input cssClass="input" path="mobile" id="mobile"/>
									</div>
									<div class="col-three pull-left">
										<label class="label" for="fax">Fax</label>
										<form:input cssClass="input" path="fax" id="fax"/>
									</div>
								</div>
								<div class="col-one clearfix">
									<label class="label" for="contactEmail">Contact Email</label>
									<form:input cssClass="input" path="contactEmail" id="contactEmail"/>
								</div>
								
								<div class="col-sep"></div>
								
								<c:if test="${!newForm}">
								<p class="col-text">I am submitting an abstract/s on the following subjects:</p>
        						<c:forEach items="${command.attachments}" var="attachment" varStatus="varStatus">
									<div class="col-numbers clearfix">
										<div class="col-no pull-left">${varStatus.index+1}</div>
										<label class="input-no pull-left">${attachment.subject} <a href="/abstract/${attachment.filename}">${attachment.filename}</a></label><br>
									</div>
									<div>
										Preferred method of presentation:
										<c:if test="${!attachment.methodPresentation}"><label>Oral</label></c:if>
										<c:if test="${attachment.methodPresentation}"><label>Poster</label></c:if>
									</div>
									<div><a href="#" onclick="deleteAbstract(${attachment.id})">Delete file</a></div>
								</c:forEach>
								
								<div class="col-numbers clearfix">
									<div>
									<br>Add subject:<input type="text" name="subject" class="input pull-left">
									</div>
									<div class="col-choose pull-left clearfix">
										<p>Preferred method of presentation:</p>
										<div class="radio"><input type="radio" class="radiobutton" name="methodPresentation" value="false"></div>
										<label class="label label-flexible">Oral</label>
										<div class="radio"><input type="radio" class="radiobutton" name="methodPresentation" value="true"></div>
										<label class="label label-flexible">Poster</label>
									</div>
									<div><input type="file" name="file" class="input-no pull-left"></div>
								</div>
								</c:if>
								
							</div>
							<div class="col pull-right">
								<h3>Accommodation and traveling arrangements</h3>
								
								<div class="clearfix mar-bottom">
									<label class="label">I will participate in one/two days in the conference</label>
									<div class="clearfix">
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="oneDay" value="true"/>									
										</div>
										<label class="label label-flexible">One</label>
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="oneDay" value="false"/>									
										</div>
										<label class="label label-flexible">Two</label>
									</div>
								</div>
								
								<div class="clearfix mar-bottom">
									<label class="label">I will need accommodation</label>
									<div class="clearfix">
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton" path="needAccomodation" value="true"/>									
										</div>
										<label class="label label-flexible">Yes</label>
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="needAccomodation" value="false"/>									
										</div>
										<label class="label label-flexible">No</label>
									</div>
								</div>
								
								<div class="clearfix mar-bottom">
									<label class="label">I will take the organized bus on Wednesday morning</label>
									<div class="clearfix">
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="bus" value="true"/>									
										</div>
										<label class="label label-flexible">Yes</label>
										<div class="radio">					
										<form:radiobutton cssClass="input radiobutton"  path="bus" value="false"/>									
										</div>
										<label class="label label-flexible">No</label>
									</div>
								</div>
								
								<div class="clearfix mar-bottom">
									<label class="label">I will return by the organized bus on Thursday evening</label>
									<div class="clearfix">
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="returnBus" value="true"/>									
										</div>
										<label class="label label-flexible">Yes</label>
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="returnBus" value="false"/>									
										</div>
										<label class="label label-flexible">No</label>
									</div>
								</div>
								<div class="col-sep"></div>
								
								<p class="col-text">Please note your prefered room-mate names</p>
								<div class="col-numbers clearfix">
									<div class="col-no pull-left">1</div>
									<form:input cssClass="input input-short pull-left" path="firstRoommate"/>
								</div>
								
								<div class="col-numbers clearfix">
									<div class="col-no pull-left">2</div>
									<form:input cssClass="input input-short pull-left" path="secondRoommate"/>
								</div>
								
								<div class="clearfix mar-bottom">
									<label class="label">Preferred room occupation <i>(for Pls Only)</i></label>
									<div class="clearfix">
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton" path="roomTypeDouble" value="true"/>									
										</div>
										<label class="label label-flexible">Single</label>
										<div class="radio">
										<form:radiobutton cssClass="input radiobutton"  path="roomTypeDouble" value="false"/>									
										</div>
										<label class="label label-flexible">Double</label>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sep clearfix"></div>
						<div class="clearfix"><button type="submit" class="form-submit pull-right">Submit</button></div>
					</form:form>
				</div>
