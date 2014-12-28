/**
 * 用户反馈逻辑
 * @author tracyqiu
 */

$(document).ready(function(){
	var Page = {
		run: function(){
			var me = this;

			me._init();
		},
		_init: function(){
			var me = this;
			me.jQtextarea = $('#textarea');
			me.jQfeedbackSubmit = $('#feedback-submit');

			me._initEvent();
		},
		_initEvent: function(){
			var me = this;

			me.jQfeedbackSubmit.on('click', function(){
				me._commit($(this));
			});
		},
		_commit: function(tar){
			var me = this,
				val = me.jQtextarea.val();

			if(!val){
				alert('请输入反馈内容');
				return;
			}
			$.ajax({
				url: '/Longyan/post-feedback?_=' + new Date().getTime(),
				data: {
					content: val
				}
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 1001){   //通过登录验证
						alert('我们已收到您的反馈，我们会及时处理~');
						window.location.reload();
					}else if(json.code == 1){
						window.location.href = '/Longyan/login';
					}else {
						alert('反馈失败');
					}
				}else {
					alert('反馈失败');
				}
			}).fail(function(){
				alert('反馈失败');
			});
		}
	};

	Page.run();
});