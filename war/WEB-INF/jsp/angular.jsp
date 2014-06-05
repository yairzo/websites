<!DOCTYPE html>
<html>
<head>
<title>Learning AngularJS</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js" type="text/javascript"></script>
	
<script type="text/javascript">

var app = angular.module('helloApp',[]);
app.controller('HelloCtrl', function ($scope) {
$scope.greeting = 'Hello';
$scope.person = 'World';
$scope.blessing = 'Have a nice';

});
</script>

</head>

<body ng-app="helloApp">
<div ng-controller="HelloCtrl">
<p>{{greeting}} {{person}}</p>
<input ng-model="person">
<p>{{blessing}} {{what}}</p>
<input ng-model="what">
</div>
</body>

</html>