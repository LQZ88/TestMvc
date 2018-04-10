<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>菜单资源管理</title>
	<%@ include file="../../../resources/common.jsp"%>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'菜单资源列表',split:false" style="width:240px;">
		<ul id="dicTree" class="ztree" style="border:none; margin-left:10px;background: none;overflow: auto;"></ul>
	</div>
	<div data-options="region:'center'" style="padding:5px;background:#eee;">
		<div class="divtitles"></div>
		<table id="datagrid" data-options="autoRowHeight:false,
				width:'100%',
				height:'auto',
				idField:'id',
				nowrap:false,
				toolbar:'#tb',
				fitColumns:true,
				rownumbers:true,
				remoteSort:false,
				singleSelect:true,
				url:''">
			<thead>
				<tr>
					<th data-options="align:'center',width:fixWidthColumn(0.05),field:'operate',formatter:gridedit">操作</th>
					<th data-options="align:'center',width:fixWidthColumn(0.04),field:'code'">资源代码</th>
					<th data-options="align:'center',width:fixWidthColumn(0.10),field:'name'">资源名称</th>
					<th data-options="align:'center',width:fixWidthColumn(0.04),field:'type',formatter:typeText">资源类型</th>
					<th data-options="halign:'center',width:fixWidthColumn(0.20),field:'url'">资源URL</th>
					<th data-options="align:'center',width:fixWidthColumn(0.04),field:'orderNo'">排序号</th>
					<th data-options="align:'center',width:fixWidthColumn(0.04),field:'isLevel',formatter:dicisLevel">重要</th>
					<th data-options="align:'center',width:fixWidthColumn(0.04),field:'isavaliable',formatter:dicisavaliable">有效</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
		<a href="#" onclick="add_res()" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a>
		</div>
		<div id="topDialog"></div>
	</div>
<script type="text/javascript">
var zNodes;
var thdicId;
var setting = {
	view : {
		dblClickExpand : false,
		showLine : true,
		selectedMulti : false,
		expandSpeed : (!$.support.leadingWhitespace) ? "" : "fast"
	},
	async: {
	enable: true,
	url: "${pageContext.request.contextPath}/dicInfoController/getTreeMenu.do",
	autoParam: ["id"]
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId",
			rootPId : ""
		}
	},
	callback : {
		onClick : function(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("dicTree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				reloadDatagridList(treeNode.id, false);
				return true;
			} else {
				reloadDatagridList(treeNode.id, false);
				return true;
			}
		}
	}
};
/**
 * 加载导航树数据
 */
function createTree(url,set) {
	$.ajax({
    	url: url,
        type: 'POST',
        async:false,
        dataType: "JSON",
        success: function(data) {
        	zNodes = data;
            $.fn.zTree.init($("#dicTree"), set, zNodes);
       	},
        error: function(msg) {
        	$.messager.alert("","加载导航树失败");
       	}
  	});
}
var zTree;
$(document).ready(function(){
	var url="${pageContext.request.contextPath}/dicInfoController/getTreeMenu.do?id=0";
	createTree(url,setting);
	zTree = $.fn.zTree.getZTreeObj("dicTree");
	var checkNode= zTree.getNodesByParam("id", $("#resParId").val(), null);
	zTree.selectNode(checkNode);
	//加载跟节点数据grid
	reloadDatagridList(1, true);
	resizeGrid("#datagrid", $(".divtitles").height()+15,"",false);
	refreshGrid("excitation", "#datagrid");
});
/**
 * 加载选中节点下数据grid
 */
function reloadDatagridList(dicId, istype){
	if(istype){
		$('#datagrid').datagrid({
			url :"${pageContext.request.contextPath}/dicInfoController/getDicInfoList.do",
			queryParams:{
				dicId: dicId
			}
		});
	}else{
		$('#datagrid').datagrid({
			queryParams:{
				dicId: dicId
			}
		});
	}
	thdicId = dicId;
}
/**
 * 添加完资源信息后重新加载菜单树
 */
function treeFresh(){
	$.fn.zTree.getZTreeObj("dicTree").reAsyncChildNodes(null, "refresh");
}
/**
 * 到添加数据界面
 */
function add_res() {
	openTopDialog("${pageContext.request.contextPath}/dicInfoController/toAddDicInfo.do?dicId=" + thdicId, "资源增加",650,500);
}
/**
 * 到修改数据界面
 */
function edit_res(resId) {
	openTopDialog("${pageContext.request.contextPath}/dicInfoController/toEditDicInfo.do?dicId=" + resId, "资源修改",650,500);
}
function del_res(resId) {
	//delInfo("${pageContext.request.contextPath}/DicInfoAction/toEdit.do?resourceId=" + resId);
	datagridUpdate();
}
function gridedit(value, rec, rowIndex) {
	return "<a href='#' title='修改' onclick=\"edit_res('" + rec.id + "')\">修改</a>";
}
function dicisavaliable(value, rec, rowIndex) {
	if (value == 1) {
		return "是";
	}
	return "否";
}
function dicisLevel(value, rec, rowIndex) {
	if (value == 1) {
		return "重要";
	}
	return "不重要";
}
function typeText(value, rec, rowIndex) {
	if (value == "SYS_RES_1") {
		return "菜单";
	} else if (value == "SYS_RES_2") {
		return "按钮";
	} else {
		return "资源";
	}
}
</script>
</body>
</html>