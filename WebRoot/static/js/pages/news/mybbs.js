/**
 * 用户帖子管理
 * @author tracyqiu
 */
(function($){
	var Page = {
		run: function(){
			var me = this;
			me._init();
		},
		_init: function(){
			var me = this;
			me.jQbbsListWrap = $('.bbs-list-wrap');
			
			me._initEvent();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQbbsListWrap.on('click', '.delete', function(e){
				if(confirm('删除之后将不能恢复，确认删除？')){
					me._deleteBbs($(e.currentTarget));
				}
			});
			
			me.jQbbsListWrap.on('click', '.close-bbs', function(e){
				if(confirm('结贴之后将不能修改，确认结贴？')){
					me._closeBbs($(e.currentTarget));
				}
			});
		},
		_deleteBbs: function(tar){
			var me = this,
				id = +tar.data('id'),
				status = +tar.data('status');
			
			$.ajax({
				url: '/Longyan/bbs/delete?_=' + new Date().getTime(),
				data: {
					id: id,
					status: status
				}
			}).done(function(res){
				res = typeof res == 'string' ? $.parseJSON(res) : res;
				if(res.code == 2001){
					alert('成功删除');
					window.location.reload();
				}else if(res.code == 1004){
					alert('登录验证已失效，请重新登录');
					window.location.href = '/Longyan/login';
				}else{
					alert('删除失败，请重试');
				}
			}).fail(function(){
				alert('删除失败，请重试');
			});
		},
		_closeBbs: function(tar){
			var me = this,
				id = +tar.data('id'),
				status = +tar.data('status');
			
			$.ajax({
				url: '/Longyan/bbs/close?_=' + new Date().getTime(),
				data: {
					id: id,
					status: status
				}
			}).done(function(res){
				res = typeof res == 'string' ? $.parseJSON(res) : res;
				if(res.code == 2001){
					alert('已完成封贴');
					window.location.reload();
				}else if(res.code == 1004){
					alert('登录验证已失效，请重新登录');
					window.location.href = '/Longyan/login';
				}else{
					alert('操作失败，请重试');
				}
			}).fail(function(){
				alert('操作失败，请重试');
			});
		}
	};
	
	Page.run();
})(jQuery);

