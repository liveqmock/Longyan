/**
 * 客服管理前端逻辑
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
			me.jQaddContact = $('#add-contact');
			me.jQdelContact = $('#del-contact');
			me.jQcontactInfoPop = $('.contact-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.flag = '';   //表示编辑还是新增
			
			me._initEvent();
			me._initTable();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddContact.on('click', function(){
				me._addContact($(this));
			});
			me.jQclose.on('click', function(){
				me.jQcontactInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQcontactInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm();
			});
			me.jQdelContact.on('click', function(){
				me._delContact();
			});
			me.jQtableContainer.on('click', '.edit-contact', function(e){
				me._editContact($(e.currentTarget));
			});
		},
		_initTable: function(){
			var me = this;

			$('#table-container').toTable({
				url: '/Longyan/admin/filter/get-all-contact',
				datafields: me.config.table_fileds,
				tableName: '客服列表',
				page: 'contact'
			});
		},
		_addContact: function(tar){
			var me = this;
			me.flag = 'add';
			me._resetForm();
			me.jQcontactInfoPop.show();
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

			if(me.flag == 'edit'){
				postData.id = me.id;
			}

			$.ajax({
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-contact" : "/Longyan/admin/filter/update-contact",
				type: 'get',
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
		_delContact: function(){
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
					url: "/Longyan/admin/filter/del-contact",
					type: 'get',
					data: {
						contactIds: ids.join(',')
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
		_editContact: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'edit';
			me.id = tar.attr('id');
			$('#name', me.jQcontactInfoPop).val(parent.find('.name').html());
			$('#qq', me.jQcontactInfoPop).val(parent.find('.qq').html());
			$('#telephone', me.jQcontactInfoPop).val(parent.find('.telephone').html());

			me.jQcontactInfoPop.show();
		},
		_resetForm: function(){
			var me = this;

			$('#name', me.jQcontactInfoPop).val('');
			$('#qq', me.jQcontactInfoPop).val('');
			$('#telephone', me.jQcontactInfoPop).val('');
			$('.err-lable').hide();
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/contact.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});