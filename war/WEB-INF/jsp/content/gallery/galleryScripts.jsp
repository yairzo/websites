 <style>
	.selected {
		border: solid 5px black;
	}
	.actionsDiv{
		display:block;
		position:absolute;
		top:400px;
		left:400px;
		z-index:2;
		width:650px;
		font-size:14px;
		color:#ffffff;
		direction:rtl;
		text-align:right;
		background: 0 0 rgba(80, 80, 80, 0.9);
		border: 0;
		height:auto !important; 
	}
</style>
 
  <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
   		<link href="/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
  
 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js" type="text/javascript"></script>
 <script type="text/javascript">
 
 	$(function() {
		$(".actionsDiv" ).draggable();
	});	
	
       var app = angular.module('GalleryApp', []);
       
       app.factory('autoCompleteDataService', [function() {
    	    return {
    	        getSource: function() {
     	  		   return "/galleryHelper.html?action=getPoolPictureNames";
    	        }
    	    }
    	}]);
   	
       app.directive('autoComplete', function(autoCompleteDataService) {
    	    return {
    	        restrict: 'A',
    	        link: function(scope, elem, attr, ctrl) {
    	            elem.autocomplete({
    	                source: autoCompleteDataService.getSource(), 
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

 
	   	     $http.get("/galleryHelper.html?action=getCategoryPictures&category=1").success(function(data){
				var pictures = data;
				var pictureLines = [];
				for (var i = 0; i < pictures.length; i++ ) {
					if (i % 4 == 0) pictureLines.push([]);
					pictures[i].counter=i+1;
				    pictureLines[pictureLines.length-1].push(pictures[i]);
				}
				$scope.pictures = pictures;
				$scope.pictureLines = pictureLines;
			  });

			  $scope.selectedIndex = -1; // default selected index 

			  $scope.itemClicked = function (picture) {
			    $scope.selectedIndex = picture.counter;
			    $scope.selectedPicture = picture;
			  };
			  

			  $scope.addPicture = function () {
				  var pictures=$scope.pictures;
				  pictures.push([]);
				  var pictureLines = [];
				  for (var i = 0; i < pictures.length; i++ ) {
					if (i % 4 == 0) pictureLines.push([]);
					pictures[i].counter=i+1;
					pictureLines[pictureLines.length-1].push(pictures[i]);
				  }
				  $scope.pictures = pictures;
				  $scope.pictureLines = pictureLines;
			  };
			  
			  $scope.deletePicture = function () {
				  var pictures=$scope.pictures;
				  pictures.splice($scope.selectedIndex-1,1);
				  var pictureLines = [];
				  for (var i = 0; i < pictures.length; i++ ) {
					if (i % 4 == 0) pictureLines.push([]);
					pictures[i].counter=i+1;
					pictureLines[pictureLines.length-1].push(pictures[i]);
				  }
				  $scope.pictures = pictures;
				  $scope.pictureLines = pictureLines;
				  $scope.selectedIndex=-1;
			  };			

			  $scope.selectedAutocompletePictureTitle="";
			  
			  $scope.replacePicture = function (selectedAutocomplete,selectedAutocompletePictureTitle) {
				  $scope.selectedPicture.url = selectedAutocompletePictureTitle;
				  $scope.selectedPicture.title = selectedAutocomplete;
				  $scope.selectedIndex=-1; 
		 		  $scope.selectedAutocomplete="";
		 		  $scope.selectedAutocompletePictureTitle="";
		      };

			  $scope.uploadFile = function() {
				    var file = $scope.pictureFile;
				    var fd = new FormData();
				    //Take the first selected file
				    fd.append("file", file);
				    $http.post("galleryHelper.html?action=addPoolPicture&newPoolPictureName="+$scope.newPoolPictureName, fd, {
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
			  $scope.save = function() {
				    var pictures = $scope.pictures;
				    var fd = new FormData();
				    fd.append("pictures", JSON.stringify(pictures));
				    $http.post("galleryHelper.html?action=save&category=1", fd, {
				        withCredentials: true,
				        headers: {'Content-Type': undefined },
				        transformRequest: angular.identity
				    }).success(function(){
					    //alert("gallery saved successfuly");
			        })
			        .error(function(){
					    //alert("error when trying to save");
			        });

			  };           
		});

</script>