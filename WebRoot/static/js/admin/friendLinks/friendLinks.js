/**
 * 友情链接管理前端逻辑
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
			me.jQaddFriendLinks = $('#add-friendLinks');
			me.jQdelFriendLinks = $('#del-friendLinks');
			me.jQfriendLinksInfoPop = $('.friendLinks-info-pop');
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
			
			me.jQaddFriendLinks.on('click', function(){
				me._addFriendLinks($(this));
			});
			me.jQclose.on('click', function(){
				me.jQfriendLinksInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQfriendLinksInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm();
			});
			me.jQdelFriendLinks.on('click', function(){
				me._delFriendLinks();
			});
			me.jQtableContainer.on('click', '.edit-friendLinks', function(e){
				me._editFriendLinks($(e.currentTarget));
			});
		},
		_initTable: function(){
			var me = this;

			$('#table-container').toTable({
				url: '/Longyan/admin/filter/get-all-friendLinks',
				datafields: me.config.table_fileds,
				tableName: '友情链接列表',
				page: 'friendLinks'
			});
		},
		_addFriendLinks: function(tar){
			var me = this;
			me.flag = 'add';
			me._resetForm();
			me.jQfriendLinksInfoPop.show();
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
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-friendLinks" : "/Longyan/admin/filter/update-friendLinks",
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
		_delFriendLinks: function(){
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
					url: "/Longyan/admin/filter/del-friendLinks",
					type: 'get',
					data: {
						friendLinksIds: ids.join(',')
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
		_editFriendLinks: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'edit';
			me.id = tar.attr('id');
			$('#name', me.jQfriendLinksInfoPop).val(parent.find('.name').html());
			$('#url', me.jQfriendLinksInfoPop).val(parent.find('.url').html());

			me.jQfriendLinksInfoPop.show();
		},
		_resetForm: function(){
			var me = this;

			$('#name', me.jQfriendLinksInfoPop).val('');
			$('#url', me.jQfriendLinksInfoPop).val('');
			$('.err-lable').hide();
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/friendLinks.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});