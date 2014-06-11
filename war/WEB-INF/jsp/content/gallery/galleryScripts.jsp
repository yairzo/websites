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
    	                    scope.selected= selectedItem.item.value;
    	                    scope.$apply();
    	                    event.preventDefault();
    	                }
    	            });
    	        }
    	    };
    	});

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
			$http.get("/galleryHelper.html?action=getPoolPictures").success(function(data){
				var poolpictures = data;
				var poolpicturesLines = [];
				for (var i = 0; i < poolpictures.length; i++ ) {
					if (i % 8 == 0) poolpicturesLines.push([]);
					poolpicturesLines[poolpicturesLines.length-1].push(poolpictures[i]);
				}
				$scope.poolpicturesLines = poolpicturesLines;
			});

			  $scope.selectedIndex = -1; // default selected index 

			  $scope.itemClicked = function (picture) {
			    $scope.selectedIndex = picture.counter;
			    $scope.selectedPicture = picture;
			  };
			  
			  $scope.itemPoolClicked = function (poolpicture) {
				  $scope.selectedPicture.url = poolpicture.url;
				  $scope.selectedPicture.title = poolpicture.title;
				  $scope.selectedIndex=-1; 
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