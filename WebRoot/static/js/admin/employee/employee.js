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
			me.jQaddEmployee = $('#add-employee');
			me.jQdelEmployee = $('#del-employee');
			me.jQemployeeInfoPop = $('.employee-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQrightTip = $('#right-td .right-tip');
			me.flag = '';   //表示编辑还是新增
			
			me._initEvent();
			me._initDate();
			me._initTable();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddEmployee.on('click', function(){
				me._addEmployee($(this));
			});
			me.jQclose.on('click', function(){
				me.jQemployeeInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQemployeeInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm();
			});
			me.jQdelEmployee.on('click', function(){
				me._delEmployee();
			});
			me.jQtableContainer.on('click', '.edit-employee', function(e){
				me._editEmployee($(e.currentTarget));
			});
			me.jQrightTip.on('mouseover', function(){
				$('.tip-content').show();
			}).on('mouseout', function(){
				$('.tip-content').hide();
			});
		},
		_initTable: function(){
			var me = this;

			$('#table-container').toTable({
				url: '/Longyan/admin/filter/get-all-employee',
				datafields: me.config.table_fileds,
				tableName: '员工列表',
				page: 'employee'
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
		_addEmployee: function(tar){
			var me = this;
			me.flag = 'add';
			me._resetForm();
			me.jQemployeeInfoPop.show();
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
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-employee" : "/Longyan/admin/filter/update-employee",
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
		_delEmployee: function(){
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
					url: "/Longyan/admin/filter/del-employee",
					type: 'get',
					data: {
						employeeIds: ids.join(',')
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
		_editEmployee: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'edit';
			me.id = tar.attr('id');
			$('#name', me.jQemployeeInfoPop).val(parent.find('.name').html());
			$('#id_card', me.jQemployeeInfoPop).val(parent.find('.id_card').html());
			$('#telephone', me.jQemployeeInfoPop).val(parent.find('.telephone').html());
			me.jQemployeeInfoPop.find('input[type="radio"]').each(function(i, item){
				if($(item).val() == parent.find('.sex').html()){
					item.checked = true;
				}else {
					item.checked = false;
				}
			});
			$('#birthday', me.jQemployeeInfoPop).val(parent.find('.birthday').html());
			$('#address', me.jQemployeeInfoPop).val(parent.find('.address').html());
			$('#qq', me.jQemployeeInfoPop).val(parent.find('.qq').html());
			$('#email', me.jQemployeeInfoPop).val(parent.find('.email').html());
			$('#province', me.jQemployeeInfoPop).val(parent.find('.province').html());
			$("#right_level option[value='" + parent.find('.right_level').html() + "']").attr("selected", true);

			$('.err-lable').hide();
			me.jQemployeeInfoPop.show();
		},
		_resetForm: function(){
			var me = this;

			$('#name', me.jQemployeeInfoPop).val('');
			$('#id_card', me.jQemployeeInfoPop).val('');
			$('#telephone', me.jQemployeeInfoPop).val('');
			$('#birthday', me.jQemployeeInfoPop).val('');
			$('#address', me.jQemployeeInfoPop).val('');
			$('#qq', me.jQemployeeInfoPop).val('');
			$('#email', me.jQemployeeInfoPop).val('');
			$('#province', me.jQemployeeInfoPop).val('');
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/employee.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});