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

		
		 בחירה מתוך מאגר התמונות הקיימות
		<input auto-complete ui-items="names" ng-model="selectedAutocomplete">
   		<img ng-click="replacePicture(selectedAutocomplete,selectedAutocompletePictureTitle)" src="/imageViewer?urlTitle={{selectedAutocompletePictureTitle}}&amp;attachType=bodyImage" height="50px" width="50px"/>
		
		<br>
		הוספת תמונה חדשה למאגר:<input type="file"/>&nbsp;תיאור קצר: <input name="pictureTitle"/>כותרת לקישור: <input name="pictureUrl"/><button name="add">הוספה</button><br>

		<button ng-click="deletePicture()">מחיקה</button><br>
			
	            
       
		</div>
		
	</div>
    </div>
 