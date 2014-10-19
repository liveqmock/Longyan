/**
 * 登录页面逻辑处理
 */

!(function($){
	'use strict';

	$(document).ready(function(){
		var jQloginForm = $('#login-form'),
			jQloginBtn = $('#loginBtn'),
			jQtip = jQloginForm.find('.tip');

		function registerEvent() {
			jQloginBtn.on('click', function(){
				loginCheck();
			});
		}

		function loginCheck(){
			var jQuserName = $('#userName'),
				jQpassword = $('#password'),
				username = jQuserName.val(),
				password = jQpassword.val();

			if(username == '' || password == ''){
				showErrorTip(jQtip, '用户名或密码不能为空');
				return;
			}
			post(username, $.md5(password));
		}

		function showErrorTip(el, msg){
			el.css('visibility', 'visible').html(msg);
		}

		function post(username, password){

			$.ajax({
				url: "/Longyan/admin/loginCheck",
				type: 'post',
				data: {
					username: username,
					password: password
				}
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code){   //通过登录验证
						window.location.href = '/Longyan/admin/filter/main';
					}else{
						showErrorTip(jQtip, '用户名或密码错误');
					}
				}else {
					showErrorTip(jQtip, '未知原因，登录失败，请重试!');
				}
			}).fail(function(){
				showErrorTip(jQtip, '未知原因，登录失败，请重试!');
			});
		}

		registerEvent();
	});
})(jQuery);