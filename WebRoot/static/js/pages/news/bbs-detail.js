/**
 * 新增回复
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
			me.jQwrap = $('.detail-wrap');
			me.jQerr = $('.err');
			me.jQreply = $('#J_reply_quick_btn');
			
			me._initEvent();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQwrap.on('click', '.add-bbs-reply', function(e){
				me._addReply();
			});
			me.jQreply.on('click', function(){
				me._postReply($(this));
			});
		},
		_addReply: function(){
			var me = this,
				dHeight = $(document).height(),
				wHeight = $(window).height(),
				offset = 0;
			
			if (dHeight > wHeight){
				offset = dHeight - wHeight; 
			} else {
				offset = wHeight - dHeight; 
			}
			$('html, body').animate({scrollTop: offset}, 500);
		},
		_postReply:function(tar){
			var me = this,
				content = $('#reply-area').val(),
				bbs_id = +tar.data('bid');
			
			if(!content){
				me.jQerr.html('回复内容不能为空').css({'visibility': 'visible', 'color': 'red'});
				return;
			}
			me.jQerr.css('visibility', 'hidden');
			
			$.ajax({
				url: '/Longyan/bbs-reply/post?_=' + new Date().getTime(),
				data: {
					bbs_id: bbs_id,
					content: content
				},
				type: 'POST'
			}).done(function(res){
				var json = typeof res == 'string' ? $.parseJSON(res) : res;
				if(json.code == 1001){
					window.location.reload();
				} else if(json.code == 1004) {
					alert('登录验证已失效，请重新登录');
					window.location.href = '/Longyan/login';
				} else if(json.code == 1005) {
					alert('帖子已经不存在，无法回复');
				}else {
					me.jQerr.html('回复失败').css({'visibility': 'visible', 'color': 'red'});
				}
			}).fail(function(){
				me.jQerr.html('回复失败').css('visibility', 'visible');
			});
		}
	};
	
	Page.run();
})(jQuery);

