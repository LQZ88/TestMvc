<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
request.setAttribute("nowDate", sdf.format(new Date()));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/easyui/easyui.extend.resource.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/vue.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my_ui.js"></script>

<!-- css -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/my_ui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/ztree/css/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/easyui/themes/default/easyui.css">

<script type="text/javascript">
var winWidth = 0;
var winHeight = 0;
function findDimensions() {//函数：获取尺寸
	// 获取窗口宽度
   	if (window.innerWidth)
   	winWidth = window.innerWidth;
   	else if ((document.body) && (document.body.clientWidth))
   	winWidth = document.body.clientWidth;
 	// 获取窗口高度
   	if (window.innerHeight)
   	winHeight = window.innerHeight;
   	else if ((document.body) && (document.body.clientHeight))
   	winHeight = document.body.clientHeight;
	// 通过深入Document内部对body进行检测，获取窗口大小
 	if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth) {
	   winHeight = document.documentElement.clientHeight;
	   winWidth = document.documentElement.clientWidth;
	}
}

$(function(){
	$.fn.extend({
		resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) { 
			findDimensions();
			var height = winHeight - heightMargin;  
			var width = winWidth - widthMargin;  
			height = minHeight;  
			width =  minWidth;  
			$(this).datagrid('resize', {  
		  		height :height,  
		    	width : width  
			});  
		}  
	});
	findDimensions();// 调用函数，获取数值
	window.onresize=findDimensions;
});

function winModalFullScreen(strURL){
    var sheight = screen.height;
    var swidth = screen.width;
    var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes;help=no";
    var tmp=window.showModalDialog("${pageContext.request.contextPath}/"+strURL,window,winoption);
    return tmp;
}
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
            var ed = $(this).datagrid('getEditor', param);
            if (ed){
                if ($(ed.target).hasClass('textbox-f')){
                    $(ed.target).textbox('textbox').focus();
                } else {
                    $(ed.target).focus();
                }
            }
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	},
    enableCellEditing: function(jq){
        return jq.each(function(){
            var dg = $(this);
            var opts = dg.datagrid('options');
            opts.oldOnClickCell = opts.onClickCell;
            opts.onClickCell = function(index, field){
                if (opts.editIndex != undefined){
                    if (dg.datagrid('validateRow', opts.editIndex)){
                        dg.datagrid('endEdit', opts.editIndex);
                        opts.editIndex = undefined;
                    } else {
                        return;
                    }
                }
                dg.datagrid('selectRow', index).datagrid('editCell', {
                    index: index,
                    field: field
                });
                opts.editIndex = index;
                opts.oldOnClickCell.call(this, index, field);
            }
        });
    }
});
</script>
</head>
</html>
