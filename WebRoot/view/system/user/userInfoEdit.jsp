<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>新增用户信息</title>
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
		<form id="userInfoFrom" method="POST">
		<input type="hidden" name="roleId" value="${ roleInfo.id }">
		<table class="tab">
			<tr class="bor">
				<th>用户编号：</th>
				<td>${ userInfo.usercode }
				</td>
				<th>登录账号：</th>
				<td>${userInfo.loginname }
				</td>
			</tr>
			<tr class="bor">
				<th>姓名：</th>
				<td>
				<input type="text" id="username" name="username" class="intext" value="${userInfo.username }">
				</td>
				<th>性别：</th>
				<td>
				<input type="text" id="sex" name="sex" class="intext easyui-combobox" data-options="valueField: 'label',textField: 'value',
				data: [{label: '男',value: '男'},{label: '女',value: '女'},{label: '保密',value: '保密'}],value:'${userInfo.sex }',editable:false"> 
				</td>
			</tr>
			<tr class="bor">
				<th>年龄：</th>
				<td>
				<input type="text" id="age" name="age" class="intext" value="${userInfo.age }"> 
				</td>
				<th>出生日期：</th>
				<td>
				<input type="text" id="birthday" name="birthday" class="intext easyui-datebox"> 
				</td>
			</tr>
			<tr class="bor">
				<th>手机号：</th>
				<td>
				<input type="text" id="phone" name="phone" class="intext" value="${userInfo.phone }"> 
				</td>
				<th>固定电话：</th>
				<td>
				<input type="text" id="telephone" name="telephone" class="intext" value="${userInfo.telephone }"> 
				</td>
			</tr>
			<tr class="bor">
				<th>邮箱地址：</th>
				<td>
				<input type="text" id="emil" name="emil" class="intext" value="${userInfo.emil }"> 
				</td>
				<th>是否有效：</th>
				<td>${userInfo.state==true?'是':'否' }
				</td>
			</tr>
			<tr class="bor">
				<th>家庭地址：</th>
				<td colspan="3">
				<input type="text" id="address" name="address" class="intext" style="width: 585px;" value="${userInfo.address }"> 
				</td>
			</tr>
			<tr class="bor">
				<th>用户头像：</th>
				<td colspan="3" style="text-align: center;" >
					<c:if test="${ not empty fileInfo }">
					<img style="width: 290px;height: 290px;" class="divcircle" title="头像图片" src="${fileInfo.filetexts }"></img>
					</c:if>
					<c:if test="${ empty fileInfo }">
					<img style="width: 290px;height: 290px;" class="divcircle" title="未设置头像图片" src="${pageContext.request.contextPath}/resources/images/user.png" ></img>
					</c:if>
				</td>
			</tr>
		</table>
		</form>
	</div>
<script type="text/javascript">
$(function(){
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
	$('#birthday').datebox('setValue', format_Data('${userInfo.birthday}'));
});
function submitForm(){
	$("#userInfoFrom").form("submit", {
	    url:"${pageContext.request.contextPath}/userInfoController/editUserInfoDatas.do",
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
