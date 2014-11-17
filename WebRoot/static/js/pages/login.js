/**
 * 前端用户登录逻辑
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
			me.jQcommit = $('#commit');
			me.jQloginRet = $('.login-ret');

			me._initEvent();
		},
		_initEvent: function(){
			var me = this;

			me.jQcommit.on('click', function(){
				me._commit($(this));
			});
		},
		_commit: function(tar){
			var me = this,
				username = $('#username').val(),
				password = $('#password').val();
			
			if(username == '' || password == ''){
				me.jQloginRet.html('用户名或密码不能为空').show();
				return;
			}
			
			$.ajax({
				url: '/Longyan/customer-login-check',
				data: {
					username: username,
					password: password
				}
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 1){   //未通过登录验证
						me.jQloginRet.html('用户名或密码错误').show();
					}else{
						var url = document.referrer ? document.referrer : '/Longyan';
						window.location.href = url;
					}
				}else {
					me.jQloginRet.html('登录失败').show();
				}
			}).fail(function(){
				me.jQloginRet.html('登录失败').show();
			});
		}
	};

	Page.run();
});