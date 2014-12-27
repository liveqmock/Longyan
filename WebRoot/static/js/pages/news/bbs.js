/**
 * 新增，修改帖子
 * @author tracyqiu
 */
(function($){
	var ue = UE.getEditor('editor');
	$.extend($, {
		initTemplateData: function(options){
			var me = this;
			me.method = options.method;
			me.id = options.id;
			me.content = options.content;
			me.status = options.status;
			me.pageCode = options.pageCode;
			me.dim = options.dim;
		},
		run: function(){
			var me = this;
			me._init();
		},
		_init: function(){
			var me = this;
			me.jQtitle = $('.bbs-title');
			me.jQerr = $('.err');
			me.jQpublish = $('#publish');
			me.jQbbsType = $('#bbs-type');
			
			me._initEvent();
			$(document).scrollTop(541);
		},
		_initEvent: function(){
			var me = this;
			
			ue.addListener( 'ready', function( editor ) {
			     me._initData();
			} );
			
			me.jQpublish.on('click', function(){
				me._publish();
			});
		},
		_initData: function(){
			var me = this;
			ue.execCommand('insertHtml', me.content)
		},
		_publish:function(){
			var me = this,
				title = me.jQtitle.val(),
				type = +me.jQbbsType.val(),
				content = ue.getContent(),
				timer;
			
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
					me.jQerr.html('发布成功，正在跳转至帖子列表页...').css({'visibility': 'visible', 'color': 'green'});
					timer = setTimeout(function(){
						window.location.href = '/Longyan/pages/' + me.pageCode + '/' + me.dim;
					}, 500);
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
	});
})(jQuery);

