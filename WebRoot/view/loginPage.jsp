<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   <title>登录</title>
   <%@ include file="../../resources/common.jsp"%>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/base64.js" ></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery.cookie.js"></script>
</head>
<body id="bg">
	<div class="loginform">
		<form id="login" method="post">
			<input type="hidden" name="isLogin" id="isLogin" >
			<div class="admin">
	        	<span></span>
	            <input id="loginname" type="text" value="" placeholder="用户名" maxlength="30" name="loginname" onchange="changeloginpass()">
	        </div>
	        <div class="password">
	        	<span></span>
	            <input id="loginpass" type="password" value="" placeholder="密码" maxlength="30" name="loginpass">
	        </div>
	        <div class="rememberpassword">
	        	<input type="checkbox" name="rememberpassword" id="checkuser" >
	            <label for=checkuser>记住密码</label>
	        </div>
	        <a><button type="button" id="loginbutton" class="sure" onclick="login(0)">登&nbsp;&nbsp;录</button></a>
		</form>
	</div>
<script type="text/javascript">
$(function() {
	getLoginInfo();
	$(document).keyup(function(event) {//注册Enter按钮事件给登录按钮
		if (event.keyCode == 13) {
			if ($("#loginname").val() != "" && $("#loginpass").val() != "") {
				$("#loginbutton").trigger("click");
			}
		}
	});
});
var b = new Base64();//设置cookie
function addCookie(name, value){//添加cookie
	// 存储一个带365天期限的 cookie
	//注：在默认情况下，只有设置 cookie的网页才能读取该 cookie。如果想让一个页面读取另一个页面设置的cookie，
	//必须设置cookie的路径。cookie的路径用于设置能够读取 cookie的顶级目录
	$.cookie(name, b.encode(value), { expires: 365, path: '/'  });  
}
function delCookie(name){//移除cookie
	$.removeCookie(name, '/');
}
function getCookie(name){//获取coockie
	var value=$.cookie(name);
	if(value!=null){
		value =b.decode(value);
	}else{
		value="";
	}
	return value;
}
function changeloginpass(){//用户名修改后密码制空
	$("#loginpass").val("");
}
function getLoginInfo(){//进入登录页面时获取cookie的登录信息
	var name = getCookie("loginname");
	var pwd = getCookie("loginpass");
	if(name && pwd){
		$("#loginname").val(name);
		$("#loginpass").val(pwd);
		$("#checkuser").attr("checked",true);
	}
}
function login(isLogin) {//登录实现
	$("#isLogin").val(isLogin);
	var name = $("#loginname");
	var pwd = $("#loginpass");
	if (name.val() == "") {
		show_msg('警告','用户名不能为空！');
		$("#loginname").focus();
		return;
	}
	if (pwd.val() == "") {
		show_msg('警告','密码不能为空！');
		$("#loginpass").focus();
		return;
	}
	$.ajax({
		type: 'POST',
		url: "${pageContext.request.contextPath}/userLoginController/getLoginUserInfo.do",
		data: $("#login").serialize(),
		dataType: 'JSON',
		success: function(data) {
			if (data.msg == 2) {
				$.messager.confirm('确认','用户正在登录是否强行登录？',function(r){    
				    if (r){    
						login(1);
				    }
				});
			} else if (data.msg == 1) {
				delCookie("loginname");
				delCookie("loginpass");
				if($("#checkuser").is(':checked')){//
					addCookie("loginname", name.val());
					addCookie("loginpass", pwd.val())
				}
				window.location.href = "${pageContext.request.contextPath}/view/homePage.jsp";
			} else {
				show_msg_time('警告','用户名或密码错误！',10000);
			}
		},
		beforeSend: function(XMLHttpRequest){
			showLoading("正在登陆中。。。。。");
		},
		complete:function(XMLHttpRequest, textStatus){
			closeLoading();
		},
		error : function() {
			closeLoading();
			$.messager.alert('警告','系统错误请联系管理员！');
			$("#loginbutton").show();
		}
	});
}
</script>
</body>
</html>
