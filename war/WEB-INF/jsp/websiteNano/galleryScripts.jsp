<%@ page  pageEncoding="UTF-8" %>
 
 <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
 <link href="/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
 <script src="/js/angular.min.js" type="text/javascript"></script>

 <script type="text/javascript">

	$(document).ready(function () {
		$('#my_popup').popup();
	});
	
       var app = angular.module('GalleryApp', []);
       
       app.factory('autoCompleteDataService', [function() {
    	    return {
    	        getSource: function(action) {
     	  		   return "/galleryHelper.html?action="+action;
    	        }
    	    }
    	}]);
   	
       app.directive('autoComplete', function(autoCompleteDataService) {
    	    return {
    	        restrict: 'A',
    	        link: function(scope, elem, attr, ctrl) {
    	            elem.autocomplete({
    	                source: autoCompleteDataService.getSource('getPoolPictureNames'), 
    	                minLength: 2,
    	                select: function (event, selectedItem) {
    	                    // Do something with the selected item, e.g. 
    	                    scope.selectedAutocomplete= selectedItem.item.label;
    	                    scope.selectedAutocompletePictureTitle=selectedItem.item.id;
    	                    scope.$apply();
    	                    event.preventDefault();
    	                }
    	            });
    	        }
    	    };
    	});

       app.directive('autoCompletePage', function(autoCompleteDataService) {
   	    return {
   	        restrict: 'A',
   	        link: function(scope, elem, attr, ctrl) {
   	            elem.autocomplete({
   	                source: autoCompleteDataService.getSource('getTextualPageNames'), 
   	                minLength: 2,
   	                select: function (event, selectedItem) {
   	                    // Do something with the selected item, e.g. 
   	                    scope.selectedAutocompletePage= selectedItem.item.label;
   	                    scope.$apply();
   	                    event.preventDefault();
   	                }
   	            });
   	        }
   	    };
   	});
        app.directive('fileModel', ['$parse', function ($parse) {
    	    return {
    	        restrict: 'A',
    	        link: function(scope, element, attrs) {
    	            var model = $parse(attrs.fileModel);
    	            var modelSetter = model.assign;
    	            
    	            element.bind('change', function(){
    	                scope.$apply(function(){
    	                    modelSetter(scope, element[0].files[0]);
    	                });
    	            });
    	        }
    	    };
    	}]);

 
        app.controller('galleryController', function($scope,$http) {

 			 $scope.category=${pictureCategory};
 			 $scope.level=1;
			 
	   	     $http.get("/galleryHelper.html?action=getCategoryPictures&category="+$scope.category).success(function(data){
				$scope.pictures = data;
			  });


			  $scope.selectedPictureId =-1;
			  $scope.selectedPictureTitle = ""; 
			  $scope.selectedPictureSrc = "";  

			  $scope.itemClicked = function (picture) {
				$scope.selectedPictureId =picture.id;
			    $scope.selectedPictureTitle =picture.text;
			    $scope.selectedPictureSrc =picture.title;
				if($scope.level==1){//if first level  
					if(${isLink}){//open page 
						window.open("/page/"+picture.textualPageUrlTitle);
					}
					else{// show subitems 
				     $http.get("/galleryHelper.html?action=getCategoryPictures&category="+picture.id).success(function(data){
				    	$scope.pictures = data;
						$scope.category=picture.id;
						$scope.level=2;
				     });
					}
				}
			  };
			  

			  //admin 
			  $scope.deletedPictures=[];
				
			  $scope.save = function() {
				    var fd = new FormData();
				    //alert(JSON.stringify($scope.pictures));
				    fd.append("pictures", JSON.stringify($scope.pictures));
				    fd.append("deletedPictures", JSON.stringify($scope.deletedPictures));
				    fd.append("category", $scope.category);
				    fd.append("level", $scope.level);
				    //alert("level:"+$scope.level);
				    $http.post("/galleryHelper.html?action=save", fd, {
				        withCredentials: true,
				        headers: {'Content-Type': undefined },
				        transformRequest: angular.identity
				    }).success(function(){
						 $scope.deletedPictures=[];
						 
				   	     $http.get("/galleryHelper.html?action=getCategoryPictures&category="+$scope.category).success(function(data){
					   	   	$scope.pictures = data;
					   	 });
			        })
			        .error(function(){
					    //alert("error when trying to save");
			        });

			  };
			  $scope.cancel = function() {
					 $scope.deletedPictures=[];
					 
			   	     $http.get("/galleryHelper.html?action=getCategoryPictures&category="+$scope.category).success(function(data){
			   	    	$scope.pictures = data;
			   	     });
			  };
			  $scope.addPicture = function () {
				  var picture={text:"new",title:"",id:"0",textualPageUrlTitle:""};
				  $scope.pictures.push(picture);
			  };
			  
			  $scope.addPage = function (selectedAutocompletePage) {
				  $scope.pictures[$scope.selectedIndex].textualPageUrlTitle=selectedAutocompletePage;
				  $scope.selectedAutocompletePage="";
				  $scope.pageLabel="הקישור לדף נשמר בהצלחה";
			  };
				  
			  $scope.deletePicture = function (id,index) {
				  $scope.deletedPictures.push(id);
				  $scope.pictures.splice(index,1);//local 
				  $scope.selectedIndex=-1; 
			  };	
			  $scope.selectedIndex = -1; // default selected index 

			  $scope.selectPicture = function (index) {
			    $scope.selectedIndex = index;
			  };

			  $scope.selectedAutocompletePictureTitle="";
			  
			  $scope.replacePicture = function (selectedAutocomplete,selectedAutocompletePictureTitle) {
				  $scope.pictures[$scope.selectedIndex].title=selectedAutocompletePictureTitle;
				  $scope.pictures[$scope.selectedIndex].text = selectedAutocomplete;
				  $scope.selectedIndex=-1; 
		 		  $scope.selectedAutocomplete="";
		 		  $scope.selectedAutocompletePictureTitle="";
		 		  $scope.pageLabel="";
		      };
			  $scope.uploadFile = function() {
				    var file = $scope.pictureFile;
				    var fd = new FormData();
				    //Take the first selected file
				    fd.append("file", file);
				    $http.post("/galleryHelper.html?action=addPoolPicture&newPoolPictureName="+$scope.newPoolPictureName, fd, {
				        withCredentials: true,
				        headers: {'Content-Type': undefined },
				        transformRequest: angular.identity
				    }).success(function(){
					    $scope.pictureFile="";
					    $scope.newPoolPictureName="";
			        })
			        .error(function(){
			        });

			  };
			  $scope.closeDialog=function () {
				  $scope.selectedIndex=-1; 
		 		  $scope.selectedAutocomplete="";
		 		  $scope.selectedAutocompletePictureTitle="";
		 		  $scope.pageLabel="";
		      };
		});

</script>