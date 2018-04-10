<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>菜单资源管理</title>
	<%@ include file="../../../resources/common.jsp"%>
</head>
<body>
	<ul class="dialog-toolbar">
		<li class="tl">
			<a href="#" onclick="submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
			<span class="dialog-tool-separator"></span>
			<a href="#" onclick="topDialogColse();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
		</li>
	</ul><!-- toolBar -->
	<br>
	<div class="update_dialog_div">
		<form id="dicInfoFrom" method="post">
			<input id="dicEditid" name="id" value="${dicInfo.id }"type="hidden">
			<table class="tab">
				<tr class="bor">
					<td>资源代码:</td>
					<td>
						<c:if test="${ not empty dicInfo.code }">
							<input type="text" name="code" value="${dicInfo.code }" readonly="readonly" style="border-width: 0px;">
						</c:if>
						<c:if test="${ empty dicInfo.code }">
							<input type="text" id="dicInfoCode" name="code" value="" class="intext" onblur="validedicInfoCode()">
						</c:if>
					</td>
					<td>资源名称:</td>
					<td>
						<input type="text" name="name" id="resName" value="${dicInfo.name }" class="intext">
					</td>
				</tr>
				<tr class="bor">
					<td>资源路径:</td>
					<td colspan="1"> 
						<input type="text" name="url" id="resUrl" value="${dicInfo.url }" class="intext">
					</td>
					<td>资源重要度:</td>
					<td colspan="1"> 
						<label style="cursor: pointer;">
						<input type="radio" name="isLevel" value="1" ${dicInfo.isLevel==1?'checked':''}/>重要</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label style="cursor: pointer;">
						<input type="radio" name="isLevel" value="0" ${dicInfo.isLevel==0?'checked':''}/>非重要</label>
					</td>
				</tr>
				<tr class="bor">
					<td>资源类型:</td>
					<td>
						<input type="text" id="type" name="type" style="width: 100px;"/>
					</td>
					<td>访问方式:</td>
					<td>
						<c:choose>
							<c:when test="${dicInfo.target eq 'tabs' }">
								<label style="cursor: pointer;"><input type="radio" name="target" value="tabs" checked="checked">选项卡</label>&nbsp;&nbsp;&nbsp;&nbsp;	
								<label style="cursor: pointer;"><input type="radio" name="target" value="_self">弹窗</label>
					  		 </c:when>
							<c:otherwise>
								<label style="cursor: pointer;"><input type="radio" name="target" value="tabs">选项卡</label>&nbsp;&nbsp;&nbsp;&nbsp;
								<label style="cursor: pointer;"><input type="radio" name="target" value="_self" checked="checked">弹窗</label>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr class="bor">
					<td><label>父资源:</label></td>
					<td>
						<input type="text" id="parId" name="parId" style="width: 175px;"/> 
					</td>
					<td>排序号:</td>
					<td>
						<input name="orderNo" value="${dicInfo.orderNo }" class="intext">
					</td>
				</tr>
				<tr class="bor">
					<td>是否受控:</td>
					<td>
						<label style="cursor: pointer;"><input type="radio" name="ispermissions" value="1" ${dicInfo.ispermissions==1?'checked':''}/>是</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label style="cursor: pointer;"><input type="radio" name="ispermissions" value="0" ${dicInfo.ispermissions==0?'checked':''}/>否</label>
					</td>
					<td>是否有效:</td>
					<td>
						<label style="cursor: pointer;"><input type="radio" name="isavaliable" value="1" ${dicInfo.isavaliable==1?'checked':''}/>是</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label style="cursor: pointer;"><input type="radio" name="isavaliable" value="0" ${dicInfo.isavaliable==0?'checked':''}/>否</label>
					</td>
				</tr>
				<tr class="bor">
					<td colspan="1" valign="top">
					<label>资源描述:</label>
					</td>
					<td colspan="3">
						<textarea name="description" class="intextarea">${dicInfo.description }</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function submitForm(){
	var oper;
	var dicEditid=$("#dicEditid").val();
	if(dicEditid==null||dicEditid==""){
		oper="save";
	}else{
		oper="update";
	}
	if($('#type').combobox('getValue')=='-1'){
		topMsg('请选择资源类型...');
		return;
 	}
	if($('#parId').combobox('getValue')=='-1'){
		topMsg('请选择父资源...');
		return;
	}
	$('#dicInfoFrom').form('submit', {
	    url:'${pageContext.request.contextPath}/dicInfoController/editDicInfo.do?oper='+oper,
	    onSubmit: function(){
	    	var flag = $(this).form('validate');
			if(flag){
				showLoading('正在处理数据,请稍等...');
			}
			return flag;
	    },
	    success:function(data){
	    	closeLoading();
	    	top.excitation.call();
	    	topDialogColse()
			if(oper=="save"){
			   parent.treeFresh();
			}
	    }   
	});
}
//表单验证
$(function(){
	findDicData();
	$("#dicInfoCode").validatebox({   
		required: true,   
		validType: ['englishOrNum','length[0,20]']
	});
	$("#resName").validatebox({   
	    required: true,
	    validType: 'length[0,50]'
	});
	$("input[name='orderNo']").validatebox({   
	    required: true
	});
});
function findDicData(){
	$('#type').combobox({    
	    url:'${pageContext.request.contextPath}/dicInfoController/findDicdigByTypeJson.do',
	    required: true,
	    valueField:'dicCode',
	    textField:'value',
	    validType:"type",
	    editable:false,
	    value:'${not empty dicInfo.type ? dicInfo.type: -1 }'
	});
	$('#parId').combobox({    
	    url:'${pageContext.request.contextPath}/dicInfoController/findDicInfoByNameJson.do',
	    required: true,
	    valueField:'id',
	    textField:'name',
	    validType:"parId",
	    value:'${not empty dicId ? dicId : -1 }'
	});
}
function validedicInfoCode() {
	if($("#dicInfoCode").val()!=null){
		$.ajax({
			url : '${pageContext.request.contextPath}/dicInfoController/getdicInfoCode.do?dicInfoCode='+ $("#dicInfoCode").val(),
			type : 'POST',
			success : function(data) {
				if (data == "false") {
					$.messager.alert("提示","编号已存在");
					$("#dicInfoCode").attr("value", "");
				}
			},
			error : function(data) {
				alert("未知错误！");
			}
		});
	}
}
</script>
</body>
</html>