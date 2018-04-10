/**
 * 当窗口大小改变时grid的大小也随之改变
 * grid 要改变的窗口id
 * searchHeight 改变的高度
 * searchWidth 改变的宽度不写默认为0
 * isWidth 是否要改变宽度 默认true
 */
function resizeGrid(grid,searchHeight,searchWidth,isWidth){
	if(searchWidth==undefined||searchWidth==""){
		searchWidth=0;
	}else if(isWidth==undefined||isWidth==""){
		isWidth=true;
	}
	$(grid).datagrid('resize',{//初始时的高度
		height:$(window).height()-searchHeight
	});
	if(isWidth){
		$(window).resize(function(){
			$(grid).datagrid('resize',{
				width:$(window).width()-searchWidth,
				height:$(window).height()-searchHeight
			});
			//alert($(window).height()-searchHeight)
		});
	}else{
		$(window).resize(function(){
			$(grid).datagrid('resize',{
				height:$(window).height()-searchHeight
			}); 
			//alert($(window).height()-searchHeight)
		});
	}
}
/**
 * 设置datagrid表格列的宽度
 * @param percent 宽度比例值
 */
function fixWidthColumn(percent) {
	return (1000 * percent);
}
/**
 * 添加easyui添加的进度条控件运行
 * @param text 进度条内容
 * @param title 标题
 * @param msg 文本内容
 */
function showLoading(text, title, msg) {
	if(title==undefined){
		title="";
	}else if(msg==undefined){
		msg="";
	}else if(text==undefined){
		text="";
	}
	$.messager.progress({
		title : title,
		msg : msg,
		text : text
	});
}
/**
 * 停止easyui添加的进度条控件运行
 */
function closeLoading() {
	$.messager.progress('close');
}
/**
 * 在窗口的头部显示提示信息
 * @param msg 提示信息
 */
function topMsg(msg) {
	$.messager.show({
		title:'提示',
		msg: msg,
		timeout:3000,
		showType:'slide',
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:''
		}
	});
}
/**
 * 右下角显示提示窗口 2秒后消失
 * @param title 标题
 * @param msg 内容
 */
function show_msg(title,msg){
	$.messager.show({
		title:title,
		msg:msg,
		timeout:2000,
		showType:'slide'
	});
}

/**
 * 右下角显示提示信息
 * @param title 标题
 * @param msg 内容
 * @param timeout 多长时间消失
 */
function show_msg_time(title,msg,timeout){
	$.messager.show({
		title:title,
		msg:msg,
		timeout:timeout,
		showType:'slide'
	});
}
/**
 * 在父级页面右下角显示提示信息 2秒后消失
 */
function parSussmsg(msg){
	parent.$.messager.show({
		title:"提示",
		msg: msg,
		timeout:2000,
		showType:'slide'
	});
}
function parErrmsg(msg){
	parent.$.messager.show({
		title:"提示",
		msg: msg,
		timeout:2000,
		showType:'slide'
	});
}
/**
 * 打开一个基于topWindow div的窗口具有最大化按钮操作
 * @param url 请求地址
 * @param title 窗口标题
 * @param width 窗口宽度
 * @param height 窗口高度
 * @param fit 窗口是否自适应父容器不设置为false
 */
function openTopWindow(url,title,width,height,fit){
	var winWidth = top.screenWinWidth-250;
	var winHeight = top.screenWinHeight;
	if(width==undefined){
		width=winWidth;
	}else {
		width=width>winWidth?winWidth:width;
	}
	if(height==undefined){
		height=winHeight
	}else {
		height=height>winHeight?winHeight:height;
	}
	parent.$('#topWindow').dialog({
		content:'<iframe id="contentIframe" src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
		width:width,   
	    height:height,   
	    modal:true,
	    maximizable:true,
	    maximized:false,
	    resizable:true,
	    collapsible:false,
	    cache:false,
	    //minimizable:true,
	    fit: fit==undefined?false:fit
	});
}
/**
 * 打开一个基于topWindow div的窗口没有最大化按钮操作
 * @param url 请求地址
 * @param title 窗口标题
 * @param width 窗口宽度
 * @param height 窗口高度
 * @param fit 窗口是否自适应父容器不设置为false
 */
function openNotMaxTopWindow(url,title,width,height,fit){
	var winWidth = top.screenWinWidth-250;
	var winHeight = top.screenWinHeight;
	if(width==undefined){
		width=winWidth;
	}else {
		width=width>winWidth?winWidth:width;
	}
	if(height==undefined){
		height=winHeight
	}else {
		height=height>winHeight?winHeight:height;
	}
	parent.$('#topWindow').dialog({
		content:'<iframe id="contentIframe" src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
		width:width,   
	    height:height,   
	    modal:true,
	    maximizable:false,
	    maximized:false,
	    resizable:false,
	    collapsible:false,
	    cache:false,
	    //minimizable:true,
	    fit: fit==undefined?false:fit
	});
}
/**
 * 打开一个最大化的基于topWindow div的窗口
 * @param url 请求地址
 * @param title 窗口标题
 */
function openMaximizedTopWindow(url,title){
	parent.$('#topWindow').dialog({
		content:'<iframe id="contentIframe" src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
	    modal:true,
	    maximized:true,
	    maximizable:false,
	    resizable:false,
	    collapsible:false,
	    cache:false,
	    fit:false
	});
}
/**
 * 关闭居于topWindow打开的窗口
 */
function topWindowColse(){
	parent.$('#topWindow').dialog('close');
}
/**
 * 打开一个基于topDialog div的dialog窗口
 * 不具有最大化操作 不可调整大小
 * @param url 请求地址
 * @param title 窗口标题
 * @param width  窗口宽度
 * @param height 窗口高度
 */
function openTopDialog(url,title,width,height){
	$('#topDialog').window({   
		content:'<iframe src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
		width:width,
	    height:height,
	    modal:true,
	    maximizable:false,
	    resizable:false,
	    collapsible:false,
	    minimizable:false
	});  
}
/**
 * 打开一个基于win_dialog div的dialog窗口
 * 具有最大化操作
 * @param url   请求地址
 * @param title  标题
 * @param width  窗口宽度
 * @param height 窗口高度
 */
function openMaximizedTopDialog_w(url,title,width,height){
	$('#topDialog').window({ 
		content:'<iframe src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
		width:width,
	    height:height,
	    modal:true,
	    maximizable:true,
	    resizable:true,
	    collapsible:false,
	    minimizable:false
	});  
}
/**
 * 打开一个最大化基于win_dialog div的窗口
 * @param url 请求地址
 * @param title 窗口标题
 */
function openMaximizedTopDialog(url,title){
	$('#topDialog').window({
		content:'<iframe src="'+url+'" scrolling="auto" frameborder="0" style="width: 100%; height: 99%;"></iframe>',			    
		title:title,
	    fit:true,
	    modal:true,
	    resizable:true,
	    maximizable:false,
	    collapsible:false,
	    minimizable:false
	});  
}
/**
 * 关闭居于topDialog打开的窗口
 */
function topDialogColse(){
	parent.$('#topDialog').dialog('close');
}
/**
 * 格式化状态 1是0否 其他未知
 * @param val Date格式的值
 */
function format_Status(val){
	if(val==1){
		return "<spen style='color: red;'>是</spen>";
	}else if(val==0){
		return "否";
	}else{
		return "未知";
	}
}
/**
 * 提交成功后刷新datagrid表格数据
 * @param param 参数名称
 * @param grid 表格id
 */
function refreshGrid(param,grid){
	window.top[param]=function(){
		$(grid).datagrid('reload');
		$(grid).datagrid('clearChecked');
	};
}
/**
 * 格式化时间 为YYYY-MM-DD
 * @param val Date格式的值
 */
function format_Data(val) {
	if(val==undefined || val==""){
		return "";
	}
	var time;
	if(val != null && val != ""){
		var year  = parseInt(val.year) + 1900;
		var month = parseInt(val.month) + 1;
		var date  = parseInt(val.date);
		month = month > 9 ? month : ('0' + month);
		date  = date > 9 ? date : ('0' + date);
		time  = year + '-' + month + '-' + date;
	}
	return time;
}
/**
 * 格式化时间 为YYYY-MM-DD HH:MM:DD
 * @param val Date格式的值
 */
function format_Time(val) {
	if(val==undefined || val==""){
		return "";
	}
	var time;
	if(val != null && val != ""){
		var year  = parseInt(val.year) + 1900;
		var month = parseInt(val.month) + 1;
		var date  = parseInt(val.date);
		month = month > 9 ? month : ('0' + month);
		date = date > 9 ? date : ('0' + date);
		var hours   = parseInt(val.hours);
		var minutes = parseInt(val.minutes);
		var seconds = parseInt(val.seconds);
		hours   = hours > 9 ? hours : ('0' + hours);
		minutes = minutes > 9 ? minutes : ('0' + minutes);
		seconds = seconds > 9 ? seconds : ('0' + seconds);
		time = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds;
	}
	return time;
}
/**
 * 验证是否为空
 */
function expIsNull(val){
	if(val==null||val==undefined||val==""){
		return true;
	}else{
		return false;
	}
}