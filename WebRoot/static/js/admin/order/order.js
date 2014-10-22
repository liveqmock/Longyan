/**
 * 员工管理前端逻辑
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
			me.jQaddOrder = $('#add-order');
			me.jQorderInfoPop = $('.order-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQsearch = $('#search');
			me.jQcustomerName = $('#customer_name');
			me.jQgoodsPrice = $('#goods_price');
			me.jQcount = $('#count');
			me.jQdiscount = $('#discount');
			me.jQprice = $('#price');
			me.customerValidatePass = false;
			
			me._initEvent();
			me._initDate();
			me._initTable('/Longyan/admin/filter/get-all-order', {});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddOrder.on('click', function(){
				me._addOrder($(this));
			});
			me.jQclose.on('click', function(){
				me.jQorderInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQorderInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm();
			});
			me.jQsearch.on('click', function(){
				me._search($(this));
			});
			me.jQcustomerName.on('keyup', function(){
				me._nameChange($(this));
			});
			me.jQcustomerName.on('blur', function(){
				me._customerBlur($(this));
			});
			me.jQorderInfoPop.on('blur', '#goods_price, #count, #discount', function(e){
				me._priceChange($(e.currentTarget));
			});
		},
		_initTable: function(url, data){
			var me = this;

			$('#table-container').toTable({
				url: url,
				datafields: me.config.table_fileds,
				data: data,
				tableName: '订单列表',
				page: 'order'
			});
		},
		_initDate: function(){
			var me = this,
				jQstartDate = $('#start-date'),
				jQendDate = $('#end-date');

			jQstartDate.datepicker({
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
			
			jQendDate.datepicker({
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
		_addOrder: function(tar){
			var me = this;
			me.flag = 'add';
			me._resetForm();
			me.jQorderInfoPop.show();
		},
		_submitForm: function(){
			var me = this,
				ret = false,
				cutomerid = me.jQcustomerName.attr('customerid'),
				postData = {};
			
			ret = $('.validate-table').validate();
			if(!ret.code || !me.customerValidatePass || !cutomerid) return;
			
			for(var i = 0, len = ret.data.length; i < len; i ++){
				postData[ret.data[i][0]] = ret.data[i][1];
			}
			
			postData.customer_id = cutomerid;

			$.ajax({
				url: "/Longyan/admin/filter/add-order",
				type: 'get',
				data: postData
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code == 1001){ 
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
		_resetForm: function(){
			var me = this;

			$('#customer_name', me.jQorderInfoPop).val('');
			$('#goods_name', me.jQorderInfoPop).val('');
			$('#goods_price', me.jQorderInfoPop).val('');
			$('#count', me.jQorderInfoPop).val('');
			$('#discount', me.jQorderInfoPop).val(1);
			$('#price', me.jQorderInfoPop).val(0);
			$('#goods_info', me.jQorderInfoPop).val('');
			$('#remark', me.jQorderInfoPop).val('');
			$('.err-lable').hide();
		},
		_search: function(tar){
			var me = this,
				jQstartDate = $('#start-date'),
				jQendDate = $('#end-date');
			
			if(!jQstartDate.val() || !jQendDate.val()){
				alert('起始时间或者终止时间不能为空');
				return;
			}

			$.ajax({
				url: '/Longyan/static/conf/order.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-order-by-date', {
					start_date: jQstartDate.val(),
					end_date: jQendDate.val()
				});
			}).fail(function(){
			});
		},
		_nameChange: function(tar){
			var me = this,
				timer = 0,
				val = tar.val();

			if(!val) {
				$('.customer-sug').hide();
				return;
			}
			timer && clearTimeout(timer);
			timer = setTimeout(function(){
				me._searchCustomer(val);
			}, 300);
			
		},
		_searchCustomer: function(name){
			var me = this,
				json;
			
			$.ajax({
				url: '/Longyan/admin/filter/get-customer-by-name',
				data: {
					customer_name: name
				}
			}).done(function(res){
				json = typeof res == 'string' ? JSON.parse(res) : res;
				me._renderCustomer(json);
			}).fail(function(){
			});
		},
		_renderCustomer: function(data){
			var me = this,
				i = 0,
				len = data.length,
				template = '',
				jQitemList = $('.item-list'),
				jQcustomerSug = jQitemList.parent();
			
			for(; i < len; i++){
				template += '<li class="customer-item" customerid="' + data[i].id + '">' + data[i].realname + '</li>';
			}
			
			jQitemList.html(template);
			jQcustomerSug.show();
			
			jQitemList.on('click', '.customer-item', function(e){
				var _this = $(e.currentTarget);
				
				me.jQcustomerName.val(_this.html()).attr('customerid', _this.attr('customerid'));
				me.customerValidatePass = true;
				_this.parent().parent().parent().next().hide();
				jQcustomerSug.hide();
			});
		},
		_customerBlur: function(tar){
			var me = this;
			
			if(me.customerValidatePass) return;
			
			tar.parent().next().html('找不到该会员').show();
		},
		_priceChange: function(tar){
			var me = this,
				val = tar.val(),
				jQresult = tar.parent().next(),
				pass = false,
				discount = me.jQdiscount.val(),
				goodsPrice = me.jQgoodsPrice.val(),
				count = me.jQcount.val(),
				type = tar.attr('name');
			
			switch(type){
				case 'discount':
				case 'goods_price':
					if(!(/^([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$/.test(val))){
						jQresult.html('这里需要数字').show();
					}else{
						pass = true;
						jQresult.html('').hide();
					}
					break;
				case 'count':
					if(/^[1-9]\d*$/.test(val)){
						pass = true;
						jQresult.html('').hide();
					}else {
						jQresult.html('这里需要正整数').show();
					}
					break;
			}
			
			if(pass && discount && goodsPrice && count){
				me.jQprice.val((discount * goodsPrice * count / 10).toFixed(4));
			}
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/order.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});