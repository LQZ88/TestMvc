<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	    <title>新增角色信息</title>
	    <%@ include file="../../../resources/common.jsp"%>
	</head>
<body class="easyui-layout" style="width: 986px;height: 564px;">
	<div data-options="region:'north',title:''">
	<ul class="dialog-toolbar">
		<li class="tl">
			<a href="#" onclick="submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
			<span class="dialog-tool-separator"></span>
			<a href="#" onclick="topWindowColse();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
		</li>
	</ul>
	</div>
	<div data-options="region:'center',title:'角色信息'" style="background:#eee;">
		<div class="gridSearch">
			<form id="roleInfoFrom" method="POST">
			<input type="hidden" id="roleId" name="roleId" value="${ roleInfo.id }">
			<input type="hidden" id="dicIds" name="dicIds" value="">
			<table class="tab">
				<tr class="bor">
					<th>角色名称：</th>
					<td>
					<input type="text" id="rolename" name="rolename" class="intext" value="${ roleInfo.rolename }">
					</td>
					<th>是否有效：</th>
					<td style="width: 100px;">${userInfo.state==true?'是':'否' }</td>
				</tr>
				<tr class="bor">
					<th>描述：</th>
					<td colspan="3">
					<textarea id="rolenote" name="rolenote" class="intextarea" style="height: 100px;">${roleInfo.rolenote }</textarea>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	<div data-options="region:'east',title:'资源分配',split:true" style="width: 400px;background:#eee;">
	 	<ul id="rTree" class="ztree" ></ul>
	</div>
	<div id="topDialog"></div>
<script type="text/javascript">
var setting = {
	view: {
		addDiyDom: addDiyDom
	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "s" }
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};
$(document).ready(function(){
	$.ajax({
    	url: "${pageContext.request.contextPath}/roleInfoController/getTreeMenu.do?roleId=${ roleInfo.id }",
        type: 'POST',
        async:false,
        dataType: "JSON",
        success: function(data) {
        	var zNodes = data;
			$.fn.zTree.init($("#rTree"), setting, zNodes);
       	},
        error: function(msg) {
        	$.messager.alert("","加载树失败");
       	}
  	});
});
function addDiyDom(treeId, treeNode){
	if(treeNode.url!=null&&treeNode.url!=""){
		$("#" + treeNode.tId + "_a").attr("target","resIframe");
		$("#" + treeNode.tId + "_a").attr("rel","resourceContent");
		var _box=null;
		var $p = $(_box || document);
		$("a[target=navTab]", $p).each(function(){
			$(this).click(function(event){
				var $this = $(this);
				var title = $this.attr("title") || $this.text();
				var tabid = $this.attr("rel") || "_blank";
				var fresh = eval($this.attr("fresh") || "true");
				var external = eval($this.attr("external") || "false");
				var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
				DWZ.debug(url);
				if (!url.isFinishedTm()) {
					alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
					return false;
				}
				navResource.openTab(tabid, url,{title:title, fresh:fresh, external:external});
				event.preventDefault();
			});
		});
	}
}
$(function(){
	$("#rolename").validatebox({
		required: true,
		validType: ['name'],
		missingMessage:"请输入角色名称。。。",
	});
	$("#rolenote").validatebox({   
		required: false,
		validType: 'length[0,100]',
		invalidMessage:"最多只能输入100个字符。。。",
	});
});
function submitForm(){
	var treeObj = $.fn.zTree.getZTreeObj("rTree");
    var nodes = treeObj.getCheckedNodes(true);
    var dicIds="";
    for (var i = 0; i < nodes.length; i++) {
    	dicIds += nodes[i].id+",";
    }
    $("#dicIds").val(dicIds);
	$("#roleInfoFrom").form("submit", {
	    url:"${pageContext.request.contextPath}/roleInfoController/editRoleInfoDatas.do",
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
