 <style>
	.selectedPicture {
		border: solid 5px black;
	}

</style>
 
  <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
   		<link href="/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
  
 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js" type="text/javascript"></script>
 <script type="text/javascript">

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

       app.controller('galleryController', function($scope,$http) {
 			$scope.selectedAutocompletePictureTitle="";
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
			  
			  $scope.replacePicture = function (selectedAutocompletePictureTitle,selectedAutocompletePictureTitle) {
				  $scope.selectedPicture.url = selectedAutocompletePictureTitle;
				  $scope.selectedPicture.title = selectedAutocompletePictureTitle;
				  $scope.selectedIndex=-1; 
		 		  $scope.selectedAutocomplete="";
		 		  $scope.selectedAutocompletePictureTitle="";
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
		});

</script>