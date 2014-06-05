<%@ page  pageEncoding="UTF-8" %>

    <div ng-app="GalleryApp">
    <div ng-controller="galleryController">
		<table align="center">
		<tr ng-repeat="pictureLine in pictureLines">
 		<td ng-repeat="picture in pictureLine">
			<img src="/imageViewer?urlTitle={{picture.url}}&amp;attachType=bodyImage" height="100px" width="100px"/><br>
			{{picture.title}}
		</td>
		</tr>
		</table>
		
		<div style="text-align:right;direction:rtl;">
		הוספת תמונה חדשה<br>
		<input type="file"/>&nbsp;תיאור קצר: <input name="pictureText"/><button name="add">הוספה</button><br>
	בחירה מתוך מאגר התמונות הקיימות
		<table>
		<tr ng-repeat="poolpicturesLine in poolpicturesLines">
 		<td ng-repeat="poolpicture in poolpicturesLine">
			<img src="/imageViewer?urlTitle={{poolpicture.url}}&amp;attachType=bodyImage" height="50px" width="50px"/><br>
			{{poolpicture.title}}
		</td>
		</tr>
		</table>
		</div>
		
	</div>
    </div>
 