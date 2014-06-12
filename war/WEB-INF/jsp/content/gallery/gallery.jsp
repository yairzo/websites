<%@ page  pageEncoding="UTF-8" %>

    <div ng-app="GalleryApp">
    <div ng-controller="galleryController">
  
 <!-- <span ng-repeat="picture in pictures">
 	<img src="/imageViewer?urlTitle={{picture.url}}&amp;attachType=bodyImage" height="100px" width="100px"/>
    <span ng-show="$index%4==0"><br></span>
  </span>  -->
    	
		<table dir="rtl" align="center">
		<tr ng-repeat="pictureLine in pictureLines">
 		<td ng-repeat="picture in pictureLine" style="vertical-align: top;">
			<img ng-class="{'selected': picture.counter == selectedIndex }" ng-click="itemClicked(picture)" 
			src="/imageViewer?urlTitle={{picture.url}}&amp;attachType=bodyImage" height="100px" width="100px"/><br>
			{{picture.title}}
		</td>
		</tr>
		<tr>
		<td colspan="4">
		<button ng-click="addPicture()">הוספת תמונה לקטגוריה </button>
		<button ng-click="save()">שמירה </button>
		<button ng-click="cancel()">ביטול </button>
		</td>
		</tr>
		</table>
	
  	
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
 