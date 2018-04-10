<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	    <title>角色用户管理</title>
	    <%@ include file="../../../resources/common.jsp"%>
	</head>
<body>
	<ul class="dialog-toolbar">
		<li class="tl">
			<a href="#" onclick="submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
			<span class="dialog-tool-separator"></span>
			<a href="#" onclick="topWindowColse();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
		</li>
	</ul>
	<div class="gridSearch">
		<form id="userRoleInfoFrom" method="POST">
		<input type="hidden" name="userId" id="userId" value="${userId }">
			<table style="margin-left: 25px;border-collapse:separate;border-spacing:5px 10px;" >
			<c:forEach items="${roleList}" var="role" >
			    <tr><td>
			  		<c:choose>
					<c:when test="${role.isCheck}">
				 		<label style="cursor: pointer;"><input type="checkbox" name="roleId" value="${role.id }" checked="checked" >${role.rolename }</label>
				 	</c:when>
				  	<c:otherwise>
						<label style="cursor: pointer;"><input type="checkbox" name="roleId" value="${role.id }" >${role.rolename }</label>
				 	</c:otherwise>
					</c:choose>
				</td></tr>
			</c:forEach>
			</table>
		</form>
	</div>
	<div id="topDialog"></div>
<script type="text/javascript">
function submitForm(){
	$("#userRoleInfoFrom").form("submit", {
	    url:"${pageContext.request.contextPath}/userInfoController/editRoleInfoDatas.do",
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
		    	parSussmsg("修改成功！");
	    	}else{
	    		parErrmsg("修改失败！");
	    	}
	    }   
	});
}
</script>
</body>
</html>
