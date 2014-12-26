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
		_publish:function(){
			var me = this,
				title = me.jQtitle.val(),
				type = +me.jQbbsType.val(),
				content = ue.getContent();
			
			if(!title || !content){
				me.jQerr.html('标题或者内容不能为空').css({'visibility': 'visible', 'color': 'red'});
				return;
			}
			me.jQerr.css('visibility', 'hidden');
			
			$.ajax({
				url: '/Longyan/bbs/post',
				data: {
					method: me.method,
					id: me.id,
					title: title,
					content: content,
					type: type,
					status: me.status
				},
				type: 'POST'
			}).done(function(res){
				var json = typeof res == 'string' ? $.parseJSON(res) : res;
				if(json.code == 1001 || json.code == 2001){
					me.jQerr.html('发布成功，正在跳转至帖子详情页').css({'visibility': 'visible', 'color': 'green'});
				} else if(json.code == 1002) {
					me.jQerr.html('帖子已存在').css({'visibility': 'visible', 'color': 'red'});
				}else if(json.code == 1004) {
					alert('登录验证已失效，请重新登录');
					window.location.href = '/Longyan/login';
				}else {
					me.jQerr.html('发布失败').css({'visibility': 'visible', 'color': 'red'});
				}
			}).fail(function(){
				me.jQerr.html('发布失败').css('visibility', 'visible');
			});
		}
	};
	
	Page.run();
})(jQuery);

