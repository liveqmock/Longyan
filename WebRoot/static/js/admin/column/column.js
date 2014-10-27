/**
 * 栏目管理前端逻辑
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
			me.jQdelColumn = $('#del-column');
			me.jQaddColumn = $('#add-column');
			me.jQcolumnInfoPop = $('.column-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQsearch = $('#search');
			me.flag = '';   //表示回复还是查看
			
			me._initEvent();
			me._initTable('/Longyan/admin/filter/get-all-column', {});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddColumn.on('click', function(){
				me._addColumn($(this));
			});
			me.jQclose.on('click', function(){
				me.jQcolumnInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQcolumnInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm($(this));
			});
			me.jQdelColumn.on('click', function(){
				me._delColumn();
			});
			me.jQtableContainer.on('click', '.edit-column', function(e){
				me._editColumn($(e.currentTarget));
			});
			me.jQtableContainer.on('click', '.look-reply', function(e){
				me._lookReply($(e.currentTarget));
			});
			me.jQsearch.on('click', function(){
				me._search($(this));
			});
		},
		_initTable: function(url, data){
			var me = this,
				newfields = me.config.table_fileds.splice(0);  //深克隆新数组

			$('#table-container').toTable({
				url: url,
				data: data,
				datafields: newfields,
				tableName: '栏目信息列表',
				page: 'column'
			});
		},
		_addColumn: function(tar){
			var me = this;
			me.flag = 'reply';
			me.msgId = tar.attr('id');
			me._resetForm(tar);
			me.jQcolumnInfoPop.show();
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
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-column" : "/Longyan/admin/filter/update-column",
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
		_delColumn: function(){
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
					url: "/Longyan/admin/filter/del-column",
					type: 'get',
					data: {
						columnIds: ids.join(',')
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
		_editColumn: function(tar){
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

			$('.err-lable').hide();
			me.jQcustomerInfoPop.show();
		},
		_resetForm: function(tar){
			var me = this,
				parent = tar.parent().parent();

			$('#name', me.jQcolumnInfoPop).val('');
			$('#code', me.jQcolumnInfoPop).val();
			$('#img_url', me.jQcolumnInfoPop).val('');
			$('.err-lable').hide();
		},
		_search: function(tar){
			var me = this,
				jQsiteForm = $('#site-form');
			
			if(!jQstartDate.val() || !jQendDate.val()){
				alert('起始时间或者终止时间不能为空');
				return;
			}

			$.ajax({
				url: '/Longyan/static/conf/column.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-column-by-siteid', {
					site_id: jQsiteForm.val()
				});
			}).fail(function(){
			});
			
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/column.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});