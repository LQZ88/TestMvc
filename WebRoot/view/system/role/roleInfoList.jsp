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
				<th>角色名称：</th>
				<td>
				<input type="text" id="roleName" name="roleName" class="intext"> 
				</td>
				<td class="dgCtl">
					<a href="#" onclick="re_RoleInfo()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="#" onclick="cl_RoleInfo()" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
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
				url:'${pageContext.request.contextPath}/roleInfoController/getRoleInfoList.do'">
			<thead>
				<tr>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'-1',formatter:gredFomt">操作</th>
					<th data-options="align:'center',width:fixWidthColumn(0.08),field:'rolename'">角色名称</th>
					<th data-options="halign:'center',width:fixWidthColumn(0.30),field:'rolenote'">描述</th>
					<th data-options="align:'center',width:fixWidthColumn(0.05),field:'state',formatter:fomtstate">是否有效</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'createtime',formatter:fomtcreateTime">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<a href="#" onclick="add_RoleInfo()" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a>
		</div>
	</div>
	<div id="topWindow"></div>
<script type="text/javascript">
function add_RoleInfo(){
	openNotMaxTopWindow("${pageContext.request.contextPath}/roleInfoController/toeditRoleInfoPage.do","添加角色",1000,600);
}
function edit_RoleInfo(roleId){
	openNotMaxTopWindow("${pageContext.request.contextPath}/roleInfoController/toeditRoleInfoPage.do?roleId="+roleId,"修改角色",1000,600);
}
function edit_StateInfo(roleId, state){
	$.ajax({
		type: 'POST',
		url: "${pageContext.request.contextPath}/roleInfoController/editRoleInfoDatas.do",
		data: {
			roleId: roleId,
			state: state
		},
		dataType: 'JSON',
		success: function(data) {
	    	if(data.msg){
				re_RoleInfo();
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
function re_RoleInfo(){
	var rolename = $("#roleName").val();
	$("#datagrid").datagrid("load", {
		rolename: rolename
	});
}
function cl_RoleInfo(){
	$("#roleName").val('');
}
function gredFomt(value, rec, rowIndex){
	var html="";
	var sp = "<span class='division'>|</span>";
	html = "<a href='#' title='修改' onclick=\"edit_RoleInfo('" + rec.id + "')\">修改</a>";
	if(rec.state){
		html += sp+"<a href='#' title='禁用' onclick=\"edit_StateInfo('" + rec.id + "','0')\">禁用</a>";
	}else{
		html += sp+"<a href='#' title='启用' onclick=\"edit_StateInfo('" + rec.id + "','1')\">启用</a>";
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
