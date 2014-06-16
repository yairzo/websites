<%@ page  pageEncoding="UTF-8" %>

    <div ng-app="GalleryApp">
    <div ng-controller="galleryController">
    
 		 <div>
 		 <span ng-repeat="picture in pictures">
 			<img ng-class="{'selected': $index == selectedIndex }" ng-click="itemClicked($index)" src="/imageViewer?urlTitle={{picture.url}}&amp;attachType=bodyImage" height="100px" width="100px"/>	
 			{{picture.title}}  <a href="#" ng-show="category==0" ng-click="showSubitems(picture.id)">פריטים</a>
   		 	<span ng-show="($index+1)%4==0"><br><br></span>
   		</span>  
    	<br>
		<button ng-click="addPicture()">הוספת תמונה לקטגוריה </button>
		<button ng-click="save()">שמירה </button>
		<button ng-click="cancel()">ביטול </button>
    	</div>	

	
  	
		<div class="actionsDiv" ng-show="selectedIndex > -1">
		<ul><li>
		 בחירה מתוך מאגר התמונות 
		<input auto-complete ui-items="names" ng-model="selectedAutocomplete">
   		<img ng-show="selectedAutocompletePictureTitle !=''" ng-click="replacePicture(selectedAutocomplete,selectedAutocompletePictureTitle)" src="/imageViewer?urlTitle={{selectedAutocompletePictureTitle}}&amp;attachType=bodyImage" height="50px" width="50px"/>
		<br><br>
		במידה והתמונה אינה נמצאת במאגר ניתן להוסיף תמונה חדשה למאגר ולאחר מכן לבחור אותה:<br>
		<input type="file" file-model="pictureFile"/>
		שם לתמונה: <input ng-model="newPoolPictureName"/>
		<button ng-click="uploadFile()">הוספה</button><br><br>
		</li>
		<li>מחיקת התמונה המסומנת מהגלריה
		<button ng-click="deletePicture()">מחיקה</button>
		</li>
		</ul>
		</div>
		
	</div>
    </div>
 