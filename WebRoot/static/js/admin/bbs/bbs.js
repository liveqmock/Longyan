/**
 * 论坛帖子管理
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
			me.jQtableContainer = $('#table-container');
			me.jQsearch = $('#search');
			me.jQtypeSelect = $('#type-select');
			me.jQstatusSelect = $('#status-select');
			me.jQreplyPop = $('.reply-pop');
			me.jQclose = $('#close');
			
			me._initEvent();
			me._initTable('/Longyan/admin/filter/get-all-bbs', {
				type: me.jQtypeSelect.val(),
				status: me.jQstatusSelect.val()
			});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQsearch.on('click', function(){
				me._search($(this));
			});
			me.jQtableContainer.on('click', '.bbs-status-change', function(e){
				if(confirm('确认该操作?')){
					me._changeStatus($(e.currentTarget));
				}
			});
			me.jQreplyPop.on('click', '.bbs-reply-status-change', function(e){
				if(confirm('确认该操作?')){
					me._changeReplyStatus($(e.currentTarget));
				}
			});
			me.jQtableContainer.on('click', '.bbs-reply-check', function(e){
				me._showReplyPop($(e.currentTarget));
			});
			me.jQclose.on('click', function(){
				me._closePop();
			});
		},
		_initTable: function(url, data){
			var me = this;

			$('#table-container').toTable({
				url: url,
				datafields: me.config.table_fileds,
				data: data,
				tableName: '论坛帖子列表',
				page: 'bbs'
			});
		},
		_changeStatus: function(tar){
			var me = this,
				type = me.jQtypeSelect.val(),
				status = me.jQstatusSelect.val(),
				id = +tar.attr('id'),
				status = +tar.attr('target-status');

			$.ajax({
				url: '/Longyan/admin/filter/change-status',
				data: {
					id: id,
					status: status
				}
			}).done(function(res){
				res = typeof res == 'string' ? $.parseJSON(res) : res;
				if(res.code == 2001){
					window.location.href = '/Longyan/admin/filter/bbs?type=' + type + '&status=' + status;
				}else {
					alert('操作失败');
				}
			}).fail(function(){
				alert('操作失败');
			});
		},
		_changeReplyStatus: function(tar){
			var me = this,
				id = +tar.attr('id'),
				bbs_id = +tar.parent().parent().find('.bbs_id').text(),
				status = +tar.attr('target-status');

			$.ajax({
				url: '/Longyan/admin/filter/change-reply-status',
				data: {
					id: id,
					status: status
				}
			}).done(function(res){
				res = typeof res == 'string' ? $.parseJSON(res) : res;
				if(res.code == 2001){
					me._renderPopTable(bbs_id);
				}else {
					alert('操作失败');
				}
			}).fail(function(){
				alert('操作失败');
			});
		},
		_search: function(tar){
			var me = this;

			$.ajax({
				url: '/Longyan/static/conf/bbs.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-bbs-by-param', {
					type: me.jQtypeSelect.val(),
					status: me.jQstatusSelect.val()
				});
			}).fail(function(){
			});
		},
		_showReplyPop: function(tar){
			var me = this,
				id = +tar.attr('id');

			me._renderPopTable(id);
			me.jQreplyPop.show();
		},
		_renderPopTable: function(bbs_id){
			var me = this,
				fields = [
					{"title":"ID", "name":"id", "type":"string","align":"right", "sClass": "id td-hidden"},
					{"title":"BBS ID", "name":"bbs_id", "type":"string","align":"right", "sClass": "bbs_id td-hidden"},
					{"title":"内容", "name":"content", "type":"string","align":"right", "sClass": "content"},
			    	{"title":"状态", "name":"status_text", "type":"string","align":"right", "sClass": "status_text"},
			    	{"title":"状态Code", "name":"status", "type":"number","align":"right", "sClass": "status td-hidden"},
			    	{"title":"会员", "name":"customer_name", "type":"string","align":"right", "sClass": "customer_name"},
			    	{"title":"评论时间", "name":"ctime", "type":"string","align":"right", "sClass": "ctime"}
			    ];

			$('#reply-table').toTable({
				url: '/Longyan/admin/filter/get-bbsreply-by-bbsid',
				datafields: fields,
				data: {bbsId: bbs_id},
				tableName: '跟帖列表',
				page: 'bbs_reply'
			});
		},
		_closePop: function(){
			var me = this;

			me.jQreplyPop.hide();
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/bbs.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});