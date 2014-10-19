/**
 * 会员管理前端逻辑
 * @author tracyqiu
 */

$(document).ready(function(){
	var Page = {
		run: function(config){
			var me = this;

			me.config = $.extend(true, {}, config);
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
			me.jQtableContainer = $('#table-container');
			me.flag = '';   //表示编辑还是新增
			
			me._initEvent();
			me._initDate();
			me._initTable();
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
			me.jQdelCustomer.on('click', function(){
				me._delCustomer();
			});
			me.jQtableContainer.on('click', '.edit-customer', function(e){
				me._editCustomer($(e.currentTarget));
			});
		},
		_initTable: function(){
			var me = this;

			$('#table-container').toTable({
				url: '/Longyan/admin/filter/get-all-customer',
				datafields: me.config.table_fileds,
				tableName: '用户列表',
				page: 'customer'
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
			me.flag = 'add';
			me._resetForm();
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
			if(me.flag == 'edit'){
				postData.id = me.id;
			}

			$.ajax({
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-customer" : "/Longyan/admin/filter/update-customer",
				type: 'get',
				data: postData
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code == 1001 || json.code == 2001){   //通过登录验证
						window.location.reload();
					}else{
						me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
					}
				}else {
					me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
				}
			}).fail(function(){
				me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
			});
		},
		_showErrorTip: function(msg){
			$('#result').html(msg).show();
		},
		_delCustomer: function(){
			var me = this,
				ids = [];
				jQselectItem = $('#table-container .select-item');
			
			$.each(jQselectItem, function(index, item){
				item.checked && ids.push($(item).val());
			});
			
			if(!ids.length){
				alert('请选择要删除的选项!');
				return;
			}
			
			if(confirm("确认删除所选项？")){
				$.ajax({
					url: "/Longyan/admin/filter/del-customer",
					type: 'get',
					data: {
						customerIds: ids.join(',')
					}
				}).done(function(data){
					var json = typeof data == 'string' ? JSON.parse(data) : data;

					if(json){
						if(json.code == 3001){   //通过登录验证
							window.location.reload();
						}else{
							me._showErrorTip('删除失败');
						}
					}else {
						me._showErrorTip('删除失败');
					}
				}).fail(function(){
					me._showErrorTip('删除失败');
				});
			}
		},
		_editCustomer: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'edit';
			me.id = tar.attr('id');
			$('#username', me.jQcustomerInfoPop).val(parent.find('.username').html());
			$('#realname', me.jQcustomerInfoPop).val(parent.find('.realname').html());
			$('#telephone', me.jQcustomerInfoPop).val(parent.find('.telephone').html());
			me.jQcustomerInfoPop.find('input[type="radio"]').each(function(i, item){
				if($(item).val() == parent.find('.sex').html()){
					item.checked = true;
				}else {
					item.checked = false;
				}
			});
			$('#birthday', me.jQcustomerInfoPop).val(parent.find('.birthday').html());
			$('#address', me.jQcustomerInfoPop).val(parent.find('.address').html());
			$('#qq', me.jQcustomerInfoPop).val(parent.find('.qq').html());
			$('#email', me.jQcustomerInfoPop).val(parent.find('.email').html());

			me.jQcustomerInfoPop.show();
		},
		_resetForm: function(){
			var me = this;

			$('#username', me.jQcustomerInfoPop).val('');
			$('#realname', me.jQcustomerInfoPop).val('');
			$('#telephone', me.jQcustomerInfoPop).val('');
			$('#birthday', me.jQcustomerInfoPop).val('');
			$('#address', me.jQcustomerInfoPop).val('');
			$('#qq', me.jQcustomerInfoPop).val('');
			$('#email', me.jQcustomerInfoPop).val('');
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/customer.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});