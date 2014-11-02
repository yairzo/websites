<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>


			<div class="container" ng-app="GalleryApp">
				<div ng-controller="galleryController">	
					
					<div class="breadcrumbs clearfix">
						<a href="/">Home</a> <img src="/image/nano/arrow-bread.png" alt="" /><span>Gallery</span>
					</div>
				
					<h1 class="title">${title}</h1>
					<nav class="gallery clearfix" ng-class="{largeGallery:level==1}">
						<ul ng-repeat="picture in pictures" ng-class="{lastInLine : (level==1 && ($index+1)%2==0) || (level==2 && ($index+1)%4==0)}">
							<li>
								<a ng-class="{'my_popup_open':(picture.id == selectedPictureId && level==2)}" ng_click="itemClicked(picture)">
									<span class="clearfix"><img src="/imageViewer?urlTitle={{picture.title}}&amp;attachType=bodyImage" alt="" ng-class="{largeGalleryImg:level==1}" class="galleryImg" /></span>
									<span class="gallery-details">
										<span class="gallery-details-text">{{picture.text}}</span>
										<span class="gallery-details-zoom"><img src="/image/nano/i-zoom.png" alt="" /></span>
									</span>
								</a>
								<span ng-show="${canEditGallery}">		
									<a href="#" ng-click="selectPicture($index)">עריכת תמונה</a> | <a href="#" ng-click="deletePicture(picture.id,$index)">מחיקה</a>
								</span>
							</li>
						</ul>
					</nav>

					<div id="my_popup" class="my_popup_box">
						<div class="clearfix"><img src="/imageViewer?urlTitle={{selectedPictureSrc}}&amp;attachType=bodyImage" alt="" height="480px" width="680px"/></div>
						<div class="my_popup_details clearfix">
							<div class="pull-left">{{selectedPictureTitle}}</div>
							<button class="my_popup_close gallery-close pull-right"><img src="/image/nano/gallery-close.png" alt="" /></button>
						</div>
					</div>
					
					<div ng-show="${canEditGallery}">	
						<br>	
						<button ng-click="addPicture()">הוספת תמונה לקטגוריה </button>
						<button ng-click="save()">שמירה </button>
						<button ng-click="cancel()">ביטול </button>
					</div>
					
					<div class="galleryActions" ng-show="selectedIndex > -1">
					<c:if test="${isLink}">
					בחירת דף המקושר לתמונה 
					<input auto-complete-page ui-items="textualPageNames" ng-model="selectedAutocompletePage">
					<button ng-click="addPage(selectedAutocompletePage)">הוספה</button>
					{{pageLabel}}
					</c:if>
					<br><br>
					 בחירה מתוך מאגר התמונות לפי שם התמונה (לאחר בחירת התמונה יש ללחוץ על התמונה שתופיע)	 
					<input auto-complete ui-items="names" ng-model="selectedAutocomplete">
   					<img ng-show="selectedAutocompletePictureTitle !=''" ng-click="replacePicture(selectedAutocomplete,selectedAutocompletePictureTitle)" src="/imageViewer?urlTitle={{selectedAutocompletePictureTitle}}&amp;attachType=bodyImage" height="50px" width="50px"/>
					<br>
					<br>
					במידה והתמונה אינה נמצאת במאגר ניתן להוסיף תמונה חדשה למאגר ולאחר מכן לבחור אותה:<br>
					<input type="file" file-model="pictureFile"/>
					שם לתמונה: <input ng-model="newPoolPictureName"/>
					<button ng-click="uploadFile()">הוספה</button>
					 <br><div ng-click="closeDialog()" class="close_picture_dialog">X</div>
					</div>

				
				</div>
 			</div>

 