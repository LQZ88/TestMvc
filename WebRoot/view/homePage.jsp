<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>主页</title>
<%@ include file="/resources/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home.css">
<style type="text/css">
.easyui-tabs div {
	overflow: hidden;
}
</style>
</head>
<body class="easyui-layout" id="mainToatl">
	<div data-options="region:'north'" style="height:122px;max-height: 122px;">
	   	<div class="systop">
	   		<div class="sysname" >
        		<img class="syslogo" src="" id="UserImage" style="width: 80px;height: 80px;border-radius: 50%">
            	<span>数据管理系统</span>
        	</div>
        	<div class="sysinfo">
		    	<a class="sysd" onclick="gomain()">
		        	<img src="${pageContext.request.contextPath}/resources/images/home.png"></img>
		            <p>首页</p>
		        </a>
		        <a class="sysd" onclick="gouser()">
		        	<img src="${pageContext.request.contextPath}/resources/images/home.png"></img>
		            <p>个人信息</p>
		        </a>
		        <a class="sysd" onclick="loginUserOut()">
		        	<img src="${pageContext.request.contextPath}/resources/images/close.png"></img>
		            <p>退出</p>
		        </a>
	        </div>   
	   	</div>
		<div class="commtop">
    		<span class="userdata">
    			<img style="padding-left: 5px;vertical-align:middle;"src="${pageContext.request.contextPath}/resources/images/user.gif">
    			${sessionScope.userInfo.username } 欢迎登录！
    		</span>
        	<span class="nowtime" >当前时间：<span id="now_time" ></span>&nbsp;&nbsp;
	        	<a onclick="closeTitles()">
	         	<img src="${pageContext.request.contextPath}/resources/js/easyui/themes/icons/up.png" 
	         		style="vertical-align:middle;margin-bottom: 5px;cursor: pointer;">
	        	</a>
        	</span>
    	</div>
	</div>
	<div data-options="region:'west',title:'用户菜单',split:true" style="width:220px;">
		<ul id="userDic" class="ztree"></ul>
	</div>
	<div data-options="region:'center'" style="padding:5px;background:#eee;">
		<div class="easyui-tabs" fit="true" border="false" id="tabs"></div>
	</div>
	<div data-options="region:'east',iconCls:'icon-reload',title:'消息',split:true" style="width:180px;background:#eee;">
	</div>
	<div id="tabsMenu" class="easyui-menu" style="width:120px;display: none;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>
	<div id="topWindow"></div>
<script type="text/javascript">
$(document).ready(function() {
	getUserDic();//加载菜单
	SysSettings();
    gomain();
    getUserImage();
});
function getUserImage(){
	$.ajax({
		url:"${pageContext.request.contextPath}/userInfoController/getUserFileInfoImage.do",
		type: 'POST',
		async: false,
		dataType: 'TEXT',
		success: function(data){
			if(data!=""){
				$("#UserImage").attr('src', data);
			}else{
				$("#UserImage").attr('src', "${pageContext.request.contextPath}/resources/images/user.png");
			}
		}
	});
}

//首页
function gomain() {
    url = "/view/index.jsp";
    addTabPage('首页',url);
}
function gouser(){
	url = "/view/index.jsp";
    addTabPage('个人信息',url);
}
//退出登录
function loginUserOut(){
	$.messager.confirm('确认','确认退出登录？',function(r){
	    if (r){
	    	window.location.href = "${pageContext.request.contextPath}/userLoginController/setLoginUserOut.do";
	    }
	});
}
//系统设置
function SysSettings(){
	//绑定tabs的右键菜单
    $("#tabs").tabs({
        onContextMenu : function (e, title) {
            e.preventDefault();
            $('#tabsMenu').menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data("tabTitle", title);
        }
	});
  	//实例化menu的onClick事件
    $("#tabsMenu").menu({
		onClick : function (item) {
     		CloseTab(this, item.name);
		}
	});
    $('#now_time').html(currentTime);
    setInterval(function(){$('#now_time').html(currentTime)},1000);
}
//ztree菜单设置
var setting = {
	data: {
		simpleData: {
			enable : true,
			idKey: "id",
			pIdKey: "parentId",
			rootPId: 0
		}
	},
	view: {
		dblClickExpand: false //关闭双击事件
	},
	callback: {
		onClick: treeNodeonClick
	}
};
function treeNodeonClick(e,treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj("userDic");
	zTree.expandNode(treeNode);
}
var zNodes ="";
function getUserDic(){
	$.ajax({
		url:"${pageContext.request.contextPath}/userLoginController/getUserDicInfoTree.do",
		type: 'POST',
		async: false,
		dataType: 'TEXT',
		success: function(result){
			zNodes = $.parseJSON(result);
		}
	});
	//加载属性菜单
    $.fn.zTree.init($("#userDic"), setting, zNodes);
}
//菜单点击按钮事件
function addTabPage(title, url) {
	url="${pageContext.request.contextPath}/"+url;
	if ($("#tabs").tabs('exists', title)) {
		$('#tabs').tabs('select', title);
		//add 点击刷新操作
    	var tab = $("#tabs").tabs('getSelected');   
      	$('#tabs').tabs('update',{
      		tab:tab,  
	        options:{  
		  		content:'<iframe id="iframe" scrolling="auto" frameborder="0" src="'+url+'" style="width: 100%; height: 100%"></iframe>',  
		       	closable:true,  
		       	fit:true,  
		       	selected:true
       		}  
		});  
        // add 完成
	} else {
		$('#tabs').tabs('add', {
     		title : title,
        	closable : true,
        	content : '<iframe id="iframe" scrolling="auto" frameborder="0" src="'+url+'" style="width: 100%; height: 100%"></iframe>'
		});
	}
}
function CloseTab(menu, type) {
	var curTabTitle = $(menu).data("tabTitle");
	var tabs = $("#tabs");
	if (type === "close") {
		tabs.tabs("close", curTabTitle);
		return;
	}
	var allTabs = tabs.tabs("tabs");
	var closeTabsTitle = [];
	$.each(allTabs, function() {
		var opt = $(this).panel("options");
		if (opt.closable && opt.title != curTabTitle&& type === "Other") {
			closeTabsTitle.push(opt.title);
		} else if (opt.closable && type === "All") {
			closeTabsTitle.push(opt.title);
		}
	});
	for ( var i = 0; i < closeTabsTitle.length; i++) {
		tabs.tabs("close", closeTabsTitle[i]);
	}
}
//捕获alt+f4
document.onkeydown = function() {
    var oEvent = window.event;
    if (oEvent.altKey && (oEvent.keyCode == 115)) {
    	loginUserOut();
        return true;
    }
}
//关闭上菜单
function closeTitles(){
	$("#mainToatl").layout("collapse","north");
}
//当前时间日期
function currentTime(){
	var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六'); 
    var time=new Date(); 
    var year=time.getFullYear(); 
    var month=time.getMonth()+1; 
    var date=time.getDate(); 
    var day=time.getDay(); 
    var hour=time.getHours(); 
    var minutes=time.getMinutes(); 
    var second=time.getSeconds(); 
    month<10?month='0'+month:month; 
    hour<10?hour='0'+hour:hour; 
    minutes<10?minutes='0'+minutes:minutes; 
    second<10?second='0'+second:second; 
    var xx=show_day[day];
    date<10?date='0'+date:date; 
    var now_time=year+'年'+month+'月'+date+'日'+' '+xx+' '+hour+':'+minutes+':'+second; 
    return now_time; 
}
</script>
</body>
</html>