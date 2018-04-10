<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="UserImg">
	<head>
	    <title>新增用户信息</title>
	    <%@ include file="../../../resources/common.jsp"%>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/exif.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/image.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/angular/angular.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imageFile/angular/image-crop.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/imageFile/angular/image-crop-styles.css">
		<script type="text/javascript">
		var myUserImg = null;
	    (function() {
	    	angular.module('UserImg', ['ImageCropper']).controller('UserController', ['$scope', '$http', function($scope, $http) {
	    		var infiles = null;
	    		$scope.fileChanged = function(e,value) {
	    			if(value!=null){
		    			var files = e.target.files;
		         		var fileReader = new FileReader();
		    			fileReader.readAsDataURL(files[0]);
		    			fileReader.onload = function(e) {
		    				$scope.imgSrc = this.result;
		    				$scope.guid = (new Date()).valueOf();
		    				$scope.$apply();
		    			};
		    			showSave("add");
	    			}
	    		}
	    		$scope.saveUserImg = function() {
	    	        $('#fileid').val($scope.guid);
	    	        $('#filetext').val($scope.imgSrc);
	    	        showSave("rel");
	    	    };
				$scope.clear = function() {
					$scope.imageCropStep = 1;
	    			delete $scope.imgSrc;
	    			delete $scope.guid;
	    			delete $scope.result;
	    			delete $scope.resultBlob;
	    			$('#userFile').val('');
	    			$('#fileid').val('');
	     	        $('#filetext').val('');
	    			showSave("no");
	    		};
			}]);
	    })();
		</script>
	</head>
<body>
	<ul class="dialog-toolbar">
		<li class="tl">
			<a href="#" onclick="submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
			<span class="dialog-tool-separator"></span>
			<a href="#" onclick="topWindowColse();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
			<span class="dialog-tool-separator" id="a"></span>
			<a href="#" onclick="document.getElementById('onFiles').click(); document.getElementById('onsaveFiles').click();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" id="b">保存修改头像</a>
			<span class="dialog-tool-separator" id="c"></span>
			<a href="#" onclick="document.getElementById('onclearFiles').click();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'" id="d">退出编辑头像</a>
			<span class="dialog-tool-separator" id="e"></span>
			<a href="#" onclick="document.getElementById('onclearFiles').click();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" id="f">重新上传头像</a>
		</li>
	</ul>
	<div class="gridSearch">
		<form id="userInfoFrom" method="POST">
		<table class="tab">
			<tr class="bor">
				<th>用户编号：</th>
				<td>
				<input type="text" id="usercode" name="usercode" class="intext" onchange="valideUserInfoCode()">
				</td>
				<th>登录账号：</th>
				<td>
				<input type="hidden" id="loginpass" name="loginpass" class="intext">
				<input type="text" id="loginname" name="loginname" onclick="openuserLoginInfo()" placeholder="添加登录账号" class="intext"
					readonly="readonly" checked="checked" style="cursor: pointer;" >
				</td>
			</tr>
			<tr class="bor">
				<th>姓名：</th>
				<td>
				<input type="text" id="username" name="username" class="intext">
				</td>
				<th>性别：</th>
				<td>
				<input type="text" id="sex" name="sex" class="intext easyui-combobox" data-options="valueField: 'label',textField: 'value',
				data: [{label: '男',value: '男'},{label: '女',value: '女'},{label: '保密',value: '保密'}],value:'男',editable:false"> 
				</td>
			</tr>
			<tr class="bor">
				<th>年龄：</th>
				<td>
				<input type="text" id="age" name="age" class="intext"> 
				</td>
				<th>出生日期：</th>
				<td>
				<input type="text" id="birthday" name="birthday" class="intext easyui-datebox" value="${ nowDate }"> 
				</td>
			</tr>
			<tr class="bor">
				<th>手机号：</th>
				<td>
				<input type="text" id="phone" name="phone" class="intext"> 
				</td>
				<th>固定电话：</th>
				<td>
				<input type="text" id="telephone" name="telephone" class="intext"> 
				</td>
			</tr>
			<tr class="bor">
				<th>邮箱地址：</th>
				<td>
				<input type="text" id="emil" name="emil" class="intext"> 
				</td>
				<th>是否有效：</th>
				<td>
				<label style="cursor: pointer;"><input type="radio" name="state" value="1" checked="checked">是</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label style="cursor: pointer;"><input type="radio" name="state" value="0">否</label>
				</td>
			</tr>
			<tr class="bor">
				<th>家庭地址：</th>
				<td colspan="3">
				<input type="text" id="address" name="address" class="intext" style="width: 585px;"> 
				</td>
			</tr>
			<tr class="bor">
				<th>用户头像：</th>
				<td colspan="3" >
				<div ng-controller="UserController">
					<input type="hidden" id="fileid" name="fileid" >
					<input type="hidden" id="filetext" name="filetext">
					<button type="button" ng-click="clear()" style="display: none;" id="onclearFiles"></button>
					<button type="button" ng-click="initCrop = true" style="display: none;" id="onFiles"></button>
					<button type="button" ng-click="saveUserImg()" style="display: none;" id="onsaveFiles"></button>
					<div ng-show="imageCropStep == 1" style="text-align: center;">
						<img style="width: 290px;height: 290px;" class="divcircle" 
							src="${pageContext.request.contextPath}/resources/images/user.png" title="点击上传头像图片"
							onclick="document.getElementById('userFile').click();" ></img>
						<input type="file" id="userFile" name="userFile" accept="image/*" 
							style="display:none;" onchange="angular.element(this).scope().fileChanged(event,this.value)">
					</div>
					<div ng-show="imageCropStep == 2">
						<image-crop data-height="290" data-width="290" data-shape="square"
							data-step="imageCropStep" src="imgSrc" data-result="result"
							data-result-blob="resultBlob" crop="initCrop" padding="40"
							max-size="1024"></image-crop>
					</div>
					<div ng-show="imageCropStep == 3" style="text-align: center;">
						 <img style="border-radius: 50%;" ng-src="{{result}}"></img>
					</div>
				</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="userLoginInfo" class="easyui-window"  title="设置用户登录账号密码"
		data-options="modal:true,shadow:false,minimizable:false,maximizable:false,resizable:false,collapsible:false,closed:true"
		style="width: 400px; height: 200px;background-color: transparent;">
		<table style="text-align:center;width: 100%">
			<tr style="height: 40px;">
				<td>登录账号：&nbsp;<input type="text" id="logName" class="intext"></td>
			</tr>
			<tr style="height: 40px;">
				<td>登录密码：&nbsp;<input type="password" id="logPass" class="intext"></td>
			</tr>
		</table>
		<div style="margin-top: 5%;text-align: center;">
			<a href="#" onclick="addUserLogin()" class="easyui-linkbutton" iconCls="icon-ok">确认</a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="$('#userLoginInfo').window('close');" class="easyui-linkbutton" iconCls="icon-no">关闭</a>
		</div>
	</div>
	<div id="topDialog"></div>
<script type="text/javascript">
$(function(){
	showSave("no");
	$("#usercode").validatebox({   
		required: true,
		validType: ['codes'],
		missingMessage:"请输入用户编号。。。",
	});
	$("#username").validatebox({   
		required: true,
		validType: ['name'],
		missingMessage:"请输入姓名。。。",
	});
	$("#age").validatebox({   
		required: false,
		validType: ['age'],
		missingMessage:"请输入年龄。。。"
	});
	$("#phone").validatebox({   
		required: true,
		validType: ['mobile']
	});
	$("#telephone").validatebox({   
		required: false,
		validType: ['phone'],
		missingMessage:"请输入固定电话。。。"
	});
	$("#emil").validatebox({   
		required: false,
		validType: ['emil'],
		missingMessage:"请输入邮箱地址。。。",
	});
	$("#address").validatebox({   
		required: false,
		validType: 'length[0,60]',
		invalidMessage:"最多只能输入60个字符。。。",
	});
});
//按钮显示设置
function showSave(flag){
	if(flag=="no"){$("#a").hide();$("#b").hide();$("#c").hide();$("#d").hide();$("#e").hide();$("#f").hide();}
	if(flag=="add"){$("#a").show();$("#b").show();$("#c").show();$("#d").show();}
	if(flag=="rel"){$("#a").hide();$("#b").hide();$("#c").hide();$("#d").hide();$("#e").show();$("#f").show();}
}
function valideUserInfoCode() {
	if($("#usercode").val()!=null){
		$.ajax({
			url : '${pageContext.request.contextPath}/userInfoController/getUserInfoCode.do?usercode='+ $("#usercode").val(),
			type : 'POST',
			success : function(data) {
				if (data == "false") {
					$.messager.alert("提示","编号已存在");
					$("#usercode").val("");
					$("#usercode").focus();
				}
			},
			error : function(data) {
				alert("未知错误！");
			}
		});
	}
}
function openuserLoginInfo(){
	$("#userLoginInfo").window("open");
	$("#logName").focus();
}
function addUserLogin(){
	if(expIsNull($("#logName").val())){$("#logName").focus();return;}
	if(expIsNull($("#logPass").val())){$("#logPass").focus();return;}
	$("#loginname").val($("#logName").val());
	$("#loginpass").val($("#logPass").val());
	$("#logName").val("");$("#logPass").val("");
	$("#userLoginInfo").window("close");
}
function submitForm(){
	if(expIsNull($("#loginname").val())&&expIsNull($("#loginpass").val())){topMsg('请添加登录账号...');return;}
	$("#userInfoFrom").form("submit", {
	    url:"${pageContext.request.contextPath}/userInfoController/saveUserInfoDatas.do",
	    dataType: "JSON",
	    onSubmit: function(){
	    	var flag = $(this).form("validate");
			if(flag){
				showLoading("正在处理数据,请稍等...");
			}
			return flag;
	    },
	    success:function(data){
		    closeLoading();
		    var datas = $.parseJSON(data);
	    	if(datas.msg){
	    		top.excitation.call();
		    	topWindowColse();
		    	parSussmsg("保存成功！");
	    	}else{
	    		parErrmsg("保存失败！");
	    	}
	    }   
	});
}
</script>
</body>
</html>
