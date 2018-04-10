/**
 * add by cgh
 * 针对panel window dialog三个组件拖动时会超出父级元素的修正
 * 如果父级元素的overflow属性为hidden，则修复上下左右个方向
 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
 * @param left
 * @param top
 * @returns
 */
var easyuiPanelOnMove = function(left, top) {
	var parentObj = $(this).panel('panel').parent();
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
	var width = $(this).panel('options').width;
	var height = $(this).panel('options').height;
	var right = left + width;
	var buttom = top + height;
	var parentWidth = parentObj.width();
	var parentHeight = parentObj.height();
	if(parentObj.css("overflow")=="hidden"){
		if(left > parentWidth-width){
			$(this).window('move', {
				"left":parentWidth-width
			});
		}
		if(top > parentHeight-height){
			$(this).window('move', {
				"top":parentHeight-height
			});
		}
	}
};

$.extend($.fn.datagrid.methods, {      
    /**
     * 开打提示功能    
     * @param {} jq    
     * @param {} params 提示消息框的样式    
     * @return {}    
     */     
    doCellTip:function (jq, params) {      
        function showTip(showParams, td, e, dg) {      
            //无文本，不提示。      
            if ($(td).text() == "") return;      
               
            params = params || {};   
            var options = dg.data('datagrid');      
            showParams.content = '<div class="tipcontent">' + showParams.content + '</div>';      
            $(td).tooltip({      
                content:showParams.content,      
                trackMouse:true,      
                position:params.position,      
                onHide:function () {      
                    $(this).tooltip('destroy');      
                },      
                onShow:function () {      
                    var tip = $(this).tooltip('tip');      
                    if(showParams.tipStyler){      
                        tip.css(showParams.tipStyler);      
                    }      
                    if(showParams.contentStyler){      
                        tip.find('div.tipcontent').css(showParams.contentStyler);      
                    }   
                }      
            }).tooltip('show');      
     
        };      
        return jq.each(function () {      
            var grid = $(this);      
            var options = $(this).data('datagrid');      
            if (!options.tooltip) {      
                var panel = grid.datagrid('getPanel').panel('panel');      
                panel.find('.datagrid-body').each(function () {      
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;      
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {      
                        'mouseover':function (e) {   
                            //if($(this).attr('field')===undefined) return;      
                            var that = this;   
                            var setField = null;   
                            if(params.specialShowFields && params.specialShowFields.sort){   
                                for(var i=0; i<params.specialShowFields.length; i++){   
                                    if(params.specialShowFields[i].field == $(this).attr('field')){   
                                        setField = params.specialShowFields[i];   
                                    }   
                                }   
                            }   
                            if(setField==null){   
                                options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');      
                                var factContentWidth = options.factContent.width();      
                                params.content = $(this).text();      
                                if (params.onlyShowInterrupt) {      
                                    if (factContentWidth > $(this).width()) {      
                                        showTip(params, this, e, grid);      
                                    }      
                                } else {      
                                    showTip(params, this, e, grid);      
                                }    
                            }else{   
                                panel.find('.datagrid-body').each(function(){   
                                    var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');   
                                    trs.each(function(){   
                                        var td = $(this).find('> td[field="' + setField.showField + '"]');   
                                        if(td.length){   
                                            params.content = td.text();   
                                        }   
                                    });   
                                });   
                                showTip(params, this, e, grid);   
                            }   
                        },      
                        'mouseout':function (e) {      
                            if (options.factContent) {      
                                options.factContent.remove();      
                                options.factContent = null;      
                            }      
                        }      
                    });      
                });      
            }      
        });      
    },      
    /**
     * 关闭消息提示功能    
     * @param {} jq    
     * @return {}    
     */     
    cancelCellTip:function (jq) {      
        return jq.each(function () {      
            var data = $(this).data('datagrid');      
            if (data.factContent) {      
                data.factContent.remove();      
                data.factContent = null;      
            }      
            var panel = $(this).datagrid('getPanel').panel('panel');      
            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')      
        });      
    }      
});

/**
*
* @requires jQuery,EasyUI
*
* 扩展validatebox，添加验证两次密码功能
*/
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	},
	idcard : { // 验证身份证
		validator : function(value) {
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message : '身份证号码格式不正确'
	},
	minLength : {
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '请输入至少（2）个字符.'
	},
	length : {
		validator : function(value, param) {
			var len = $.trim(value).length;
			return len >= param[0] && len <= param[1];
		},
		message : "输入内容长度必须介于{0}和{1}之间."
	},
	phone : { // 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:010-88888888'
	},
	mobile : { // 验证手机号码
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
		message : '手机号码格式不正确'
	},
	intOrFloat : { // 验证整数或小数
		validator : function(value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message : '请输入数字，并确保格式正确'
	},
	currency : { // 验证货币
		validator : function(value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	qq : { // 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确'
	},
	integer : { // 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入整数'
	},
	age : { // 验证年龄
		validator : function(value) {
			return /^(?:[1-9][0-9]?|1[01][0-9]|150)$/i.test(value);
		},
		message : '年龄必须是0到150之间的整数'
	},
	chinese : { // 验证中文
		validator : function(value) {
			return /^[\Α-\￥]+$/i.test(value);
		},
		message : '请输入中文'
	},
	english : { // 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	unnormal : { // 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	username : { // 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	faxno : { // 验证传真
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '传真号码不正确'
	},
	zip : { // 验证邮政编码
		validator : function(value) {
			return /^[0-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	ip : { // 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确'
	},
	name : { // 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
		},
		message : '姓名只能为中文或英文'
	},
	emil : {
		validator : function(value) {
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		},
		message : '请输入有效的邮箱账号(例：abc@hotnail(msn/live).com)'
	},
	codes : {
		validator : function(value) {
			return /^[A-Z][A-Z0-9_]{4,7}$/i.test(value);
		},
		message : '编号格式错误(大写字母开头，允许3-8字节，允许字母数字下划线)'
	}
});

function doCellTips(datagrid,onlyShowInterrupt){
	$(datagrid).datagrid('doCellTip',{   
		onlyShowInterrupt:onlyShowInterrupt,   
		position:'bottom',
        //specialShowFields:[{field:'status',showField:'statusDesc'}],
		tipStyler:{/*'backgroundColor':'#fff000',*/ width:'300px',/*borderColor:'#ff0000',*/ boxShadow:'1px 1px 3px #292929'}
	}); 
}

$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

/*$.fn.pagination.defaults.beforePageText = '第';
$.fn.pagination.defaults.afterPageText = '页/共{pages}页';
$.fn.pagination.defaults.displayMsg = '显示{from}到{to}条,共{total}条记录';
$.fn.datagrid.defaults.pageSize=20;
$.fn.datagrid.defaults.pageList=[20,30,40,50,60];
*/

function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^-\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	obj.value = obj.value.replace(/\-{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace("-","$#$").replace(/\-/g,"").replace("$#$","-");
}

function showTitle(value, rec, rowIndex){
	return "<span class='grid_tip' title="+value+">"+value+"</span>";
}
function showTip(datagrid){
	$(datagrid).datagrid('options').onLoadSuccess=function(data){
        $('.grid_tip').tooltip({    
        	position: 'bottom',    
        	//content: '<span style="color:#fff">This is the tooltip message.</span>',    
       		trackMouse:true,
        	onShow: function(){        
        		$(this).tooltip('tip').css({            
            		//backgroundColor: '#666',            
            		//borderColor: '#666' 
            		width:'300',
            		boxShadow: '1px 1px 3px #292929'     
        		});    
        	}
        });
    }
}
