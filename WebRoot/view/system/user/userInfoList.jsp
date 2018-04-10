<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>用户管理</title>
	    <%@ include file="../../../resources/common.jsp"%>
	</head>
<body>
	<div class="gridSearch">
		<table class="tab">
			<tr class="bor">
				<th>姓名：</th>
				<td>
				<input type="text" id="userName" name="userName" class="intext"> 
				</td>
				<th>登录账号：</th>
				<td>
				<input type="text" id="loginName" name="loginName" class="intext"> 
				</td>
				<td class="dgCtl">
					<a href="#" onclick="re_UserInfo()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="#" onclick="cl_UserInfo()" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="grid">
		<table id="datagrid" class="easyui-datagrid"
			data-options="autoRowHeight:false,
				width:'100%',
				height:'auto',
				idField:'id',
				toolbar:'#tb',
				remoteSort:false,
				rownumbers:true,
				singleSelect:true,
				fitColumns:true,
				pagination:true,
				nowrap:false,
				url:'${pageContext.request.contextPath}/userInfoController/getUserInfoList.do'">
			<thead>
				<tr>
					<th data-options="align:'center',width:fixWidthColumn(0.06),field:'-1',formatter:gredFomt">操作</th>
					<th data-options="align:'center',width:fixWidthColumn(0.08),field:'username'">姓名</th>
					<th data-options="align:'center',width:fixWidthColumn(0.08),field:'loginname'">登录账号</th>
					<th data-options="align:'center',width:fixWidthColumn(0.05),field:'sex'">性别</th>
					<th data-options="align:'center',width:fixWidthColumn(0.05),field:'age'">年龄</th>
					<th data-options="halign:'center',width:fixWidthColumn(0.15),field:'address'">家庭地址</th>
					<th data-options="align:'center',width:fixWidthColumn(0.08),field:'phone'">手机号</th>
					<th data-options="align:'center',width:fixWidthColumn(0.08),field:'telephone'">固定电话</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'emil'">邮箱地址</th>
					<th data-options="align:'center',width:fixWidthColumn(0.05),field:'state',formatter:fomtstate">是否有效</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'createtime',formatter:fomtcreateTime">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<a href="#" onclick="add_UserInfo()" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a>
			<a href="#" onclick="edit_UserRoleInfo()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">权限分配</a>
			<a href="#" onclick="edit_UserPassInfo()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置密码</a>
		</div>
	</div>
	<div id="userLoginInfo" class="easyui-window"  title="重置密码"
		data-options="modal:true,shadow:false,minimizable:false,maximizable:false,resizable:false,collapsible:false,closed:true"
		style="width: 400px; height: 180px;background-color: transparent;">
		<ul class="dialog-toolbar">
			<li class="tl">
				<a href="#" onclick="submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
				<span class="dialog-tool-separator"></span>
				<a href="#" onclick="updateClose();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
			</li>
		</ul>
		<form id="userLoginInfoFrom" method="POST">
			<input type="hidden" id="userId" name="userId" class="intext">
			<input type="hidden" id="loginId" name="loginId" class="intext">
			<table style="text-align:center;width: 100%;margin-top: 20px;">
				<tr style="height: 40px;">
					<td>密码：&nbsp;<input type="password" id="loginpass" name="loginpass" class="intext easyui-validatebox" data-options="required:true"></td>
				</tr>
			</table>				
		</form>
	</div>
	<div id="topWindow"></div>
<script type="text/javascript">
function add_UserInfo(){
	openNotMaxTopWindow("${pageContext.request.contextPath}/view/system/user/userInfoAdd.jsp","添加用户",800,640);
}
function edit_UserInfo(userId){
	openNotMaxTopWindow("${pageContext.request.contextPath}/userInfoController/toeditUserInfoPage.do?userId="+userId,"添加用户",800,600);
}
function edit_UserPassInfo(){
	var rows = $('#datagrid').datagrid('getSelected');
	if(!expIsNull(rows)){
		$("#userId").val(rows.id);
		$("#loginId").val(rows.loginId);
		$("#userLoginInfo").window("open");
		$("#loginpass").focus();
	}else{
		parSussmsg("请选择要修改数据！");
	}
}
function updateClose(){
	$("#userId").val("");
	$("#loginId").val("");
	$("#userLoginInfo").window("close");
}
function submitForm(){
	$("#userLoginInfoFrom").form("submit", {
	    url:"${pageContext.request.contextPath}/userInfoController/editUserPassInfoData.do",
	    dataType: "JSON",
	    onSubmit: function(){
	    	var flag = $(this).form("validate");
			if(flag){
				showLoading("正在重置密码,请稍等...");
			}
			return flag;
	    },
	    success:function(data){
		    closeLoading();
		    var datas = $.parseJSON(data);
	    	if(datas.msg){
	    		updateClose();
		    	parSussmsg("重置密码成功！");
	    	}else{
	    		parErrmsg("重置密码失败！");
	    	}
	    }   
	});
}
function edit_UserRoleInfo(){
	var rows = $('#datagrid').datagrid('getSelected');
	if(!expIsNull(rows)){
		openNotMaxTopWindow("${pageContext.request.contextPath}/userInfoController/touserRoleInfoEditPage.do?userId="+rows.id,"权限分配",800,600);
	}else{
		parSussmsg("请选择要修改数据！");
	}
}
function edit_StateInfo(userId, longinId, state){
	$.ajax({
		type: 'POST',
		url: "${pageContext.request.contextPath}/userInfoController/editStateInfoData.do",
		data: {
			userId: userId,
			longinId: longinId,
			state: state
		},
		dataType: 'JSON',
		success: function(data) {
	    	if(data.msg){
				re_UserInfo();
				parSussmsg("操作成功！");
	    	}else{
	    		parErrmsg("操作失败！");
	    	}
		},
		error : function() {
			closeLoading();
		}
	});
}
$(function(){
	resizeGrid("#datagrid", $(".gridSearch").height()+20,"",false);
	refreshGrid("excitation", "#datagrid");
});
function re_UserInfo(){
	var userName = $("#userName").val();
	var loginName = $("#loginName").val();
	$("#datagrid").datagrid("load", {
		username: userName,
		loginname: loginName
	});
}
function cl_UserInfo(){
	$("#userName").val('');
	$("#loginName").val('');
}
function gredFomt(value, rec, rowIndex){
	var html="";
	var sp = "<span class='division'>|</span>";
	html = "<a href='#' title='修改' onclick=\"edit_UserInfo('" + rec.id + "')\">修改</a>";
	if(rec.state){
		html += sp+"<a href='#' title='禁用' onclick=\"edit_StateInfo('" + rec.id + "','" + rec.loginId + "','0')\">禁用</a>";
	}else{
		html += sp+"<a href='#' title='启用' onclick=\"edit_StateInfo('" + rec.id + "','" + rec.loginId + "','1')\">启用</a>";
	}
	return html;
}
function fomtstate(value, rec, rowIndex){
	return format_Status(value);
}
function fomtcreateTime(value, rec, rowIndex){
	return format_Time(value);
}
</script>
</body>
</html>
