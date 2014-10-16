/**
 * 会员管理前端逻辑
 * @author tracyqiu
 */

$(document).ready(function(){
	var Page = {
		run: function(){
			var me = this;
			me._init();
		},
		_init: function(){
			var me = this;
			me.jQaddCustomer = $('#add-customer');
			me.jQdelCustomer = $('#del-customer');
			me.jQcustomerInfoPop = $('.customer-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			
			me._initEvent();
			me._initDate();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddCustomer.on('click', function(){
				me._addCustomer($(this));
			});
			me.jQclose.on('click', function(){
				me.jQcustomerInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQcustomerInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm();
			});
		},
		_initDate: function(){
			var me = this,
				jQbirthday = $('#birthday');

			jQbirthday.datepicker({
			    showMonthAfterYear: true, // 月在年之后显示
			    changeMonth: true,     // 允许选择月份
			    changeYear: true,     // 允许选择年份
			    dateFormat:'yy-mm-dd',    // 设置日期格式
			    closeText:'关闭',     // 只有showButtonPanel: true才会显示出来
			    duration: 'fast',
			    showAnim:'fadeIn',
			    buttonImageOnly: true,                // 不把图标显示在按钮上，即去掉按钮
			    buttonText:'选择日期',
			    showButtonPanel: true,
			    showOtherMonths: true
			    //appendText: '(yyyy-mm-dd)',
			});
		},
		_addCustomer: function(tar){
			var me = this;
			me.jQcustomerInfoPop.show();
		},
		_submitForm: function(){
			var me = this,
				ret = false,
				postData = {};
			
			ret = $('.validate-table').validate();
			if(!ret.code) return;
			
			for(var i = 0, len = ret.data.length; i < len; i ++){
				postData[ret.data[i][0]] = ret.data[i][1];
			}
			postData.flag = 1;   //管理员操作
			$.ajax({
				url: "/Longyan/admin/filter/add-customer",
				type: 'get',
				data: postData
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code == 1001){   //通过登录验证
						window.location.reload();
					}else{
						me._showErrorTip('添加失败');
					}
				}else {
					me._showErrorTip('添加失败');
				}
			}).fail(function(){
				me._showErrorTip('添加失败');
			});
		},
		_showErrorTip: function(msg){
			$('#result').html(msg).show();
		}
	};
	
	Page.run();
});