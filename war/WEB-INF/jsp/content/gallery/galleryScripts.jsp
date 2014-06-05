 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js" type="text/javascript"></script>
 <script type="text/javascript">

       var app = angular.module('GalleryApp', []);

       app.controller('galleryController', function($scope,$http) {
			$http.get("/galleryHelper.html?action=getCategoryPictures&category=1").success(function(data){
				var pictures = data;
				pictures.push([{title:"", url:""}]);
				var pictureLines = [];
				for (var i = 0; i < pictures.length; i++ ) {
					if (i % 4 == 0) pictureLines.push([]);
				    pictureLines[pictureLines.length-1].push(pictures[i]);
				}
				$scope.pictureLines = pictureLines;
			});
			$http.get("/galleryHelper.html?action=getPoolPictures").success(function(data){
				var poolpictures = data;
				poolpictures.push([{title:"", url:""}]);
				var poolpicturesLines = [];
				for (var i = 0; i < poolpictures.length; i++ ) {
					if (i % 8 == 0) poolpicturesLines.push([]);
					poolpicturesLines[poolpicturesLines.length-1].push(poolpictures[i]);
				}
				$scope.poolpicturesLines = poolpicturesLines;
			});
		});

</script>