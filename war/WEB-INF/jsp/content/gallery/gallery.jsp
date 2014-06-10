<%@ page  pageEncoding="UTF-8" %>

    <div ng-app="GalleryApp">
    <div ng-controller="galleryController">
    	
		<table align="center">
		<tr ng-repeat="pictureLine in pictureLines">
 		<td ng-repeat="picture in pictureLine" style="vertical-align: top;">
			<img ng-class="{'selectedPicture': picture.counter == selectedIndex }" ng-click="itemClicked(picture)" 
			src="/imageViewer?urlTitle={{picture.url}}&amp;attachType=bodyImage" height="100px" width="100px"/><br>
			{{picture.title}}
		</td>
		</tr>
		<tr>
		<td colspan="4">
		<button ng-click="addPicture()">הוספת תמונה לקטגוריה </button>
		</td>
		</tr>
		</table>
	
  	
		<div ng-show="selectedIndex > -1" style="text-align:right;direction:rtl;">
		הוספת תמונה חדשה<br>
		<input type="file"/>&nbsp;תיאור קצר: <input name="pictureText"/><button name="add">הוספה</button><br>

	       בחירה מתוך מאגר התמונות הקיימות
		<table>
		<tr ng-repeat="poolpicturesLine in poolpicturesLines">
 		<td ng-repeat="poolpicture in poolpicturesLine">
			<img ng-click="itemPoolClicked(poolpicture)" src="/imageViewer?urlTitle={{poolpicture.url}}&amp;attachType=bodyImage" height="50px" width="50px"/><br>
			{{poolpicture.title}}
		</td>
		</tr>
		</table>
		
		 בחירה מתוך מאגר התמונות הקיימות
		<input auto-complete ui-items="names" ng-model="selected">
   		<img src="/imageViewer?urlTitle={{selected}}&amp;attachType=bodyImage" height="50px" width="50px"/><br>
		
		<button ng-click="deletePicture()">מחיקה מהקטגוריה</button><br>
			
             
       
		</div>
		
	</div>
    </div>
 