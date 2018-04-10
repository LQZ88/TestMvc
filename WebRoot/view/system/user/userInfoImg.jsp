<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="myUserImg">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
<title></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/angular/angular.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/angular/image-crop.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/imageFile/angular/image-crop-styles.css">
<script type="text/javascript">
    var myUserImg = null;
    (function() {
    	angular.module('myUserImg', ['ImageCropper']).controller('MainController', ['$scope', function($scope) {
		$scope.fileChanged = function(e) {
			var files = e.target.files;
     		var fileReader = new FileReader();
			fileReader.readAsDataURL(files[0]);
			fileReader.onload = function(e) {
				$scope.imgSrc = this.result;
				$scope.guid = (new Date()).valueOf();
				$scope.$apply();
			};
		}
		$scope.uploadUserImg = function(files) {
			initCrop = true;
	        parent.$('#fileid').val($scope.guid);
	        parent.$('#filetext').val($scope.imgSrc);
	        parent.$('#filetext').click();
	        parent.$('#topDialog').dialog('close');
	    };
		$scope.clear = function() {
			 $scope.imageCropStep = 1;
			 delete $scope.imgSrc;
			 delete $scope.guid;
			 delete $scope.result;
			 delete $scope.resultBlob;
		};
		}]);
    })();
  </script>
<style>
</style>
<body>
	<div ng-controller="MainController">
		<div ng-show="imageCropStep == 1">
			<div id="userShowFile" onclick="getElementById('userFile').click()" title="点击上传头像图片"
				class="divcircle" style="height: 370px;width: 370px;background-color: aliceblue;cursor: pointer;">
			<input type="file" id="userFile" name="userFile" accept="*" 
				style="display:none;" onchange="angular.element(this).scope().fileChanged(event)">
			</div>
		</div>
		<div ng-show="imageCropStep == 2">
			<image-crop data-height="290" data-width="290" data-shape="square"
				data-step="imageCropStep" src="imgSrc" data-result="result"
				data-result-blob="resultBlob" crop="initCrop" padding="70"
				max-size="1024"></image-crop>
		</div>
		<div ng-show="imageCropStep == 2">
			<button type="button" ng-click="clear()" style="margin-top: 8px;border-radius: 6px;margin-left: 50px;cursor: pointer;">返回重新选择</button>
			<button type="button" ng-click="uploadUserImg()" style="margin-top: 8px;border-radius: 6px;margin-left: 65px;cursor: pointer;">保存并上传</button>
		</div>
	</div>
</body>
</html>