<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>用户管理</title>
	    <%@ include file="../../../resources/common.jsp"%>
	</head>
  
<body>
	<div >
	
	</div>
	<div>
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
				url:''">
			<thead>
				<tr>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'stuid'">账号</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'name'">名称</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'sex'">性别</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'age'">年龄</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'address'">地址</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'stupwd'">密码</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'loginTime'">登录时间</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<a href="#" onclick="add_res()" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a>
		</div>
	</div>
<script type="text/javascript">
function createTime(value, rec, rowIndex){
	
}
$(function(){
	resizeGrid("#datagrid", $(".gridSearch").height()+20,"",false);
	refreshGrid("excitation", "#datagrid");
});
</script>
</body>
</html>
