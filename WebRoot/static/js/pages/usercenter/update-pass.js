/**
 * 用户修改基本信息
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
			me.jQpassTable = $('.pass-table');
			me.jQupdate = $('#update');

			me.canSubmit = {
				password: false,
				newpassword: false,
				renewpassword: false
			};

			me._initEvent();
		},
		_initEvent: function(){
			var me = this;

			me.jQpassTable.on('blur', '.required', function(e){
				me._inputBlur($(e.target));
			});

			me.jQupdate.on('click', function(){
				me._commit($(this));
			});
		},
		_inputBlur: function(tar){
			var me = this,
				name = tar.attr('name'),
				val = tar.val();

			if(val){
				tar.next().html('').css('visibility', 'hidden');
				if(name == 'password' || name == 'newpassword'){
					me.canSubmit[name] = true;
				} else if(name == 'renewpassword') {
					if(val == $('#newpassword').val()){
						me.canSubmit[name] = true;
					}else {
						tar.next().html('新密码不一致').css('visibility', 'visible');
						me.canSubmit[name] = false;
					}
				}else if(name == 'newpassword') {
					if(val == $('#renewpassword').val()){
						me.canSubmit[name] = true;
					}else {
						tar.next().html('新密码不一致').css('visibility', 'visible');
						me.canSubmit[name] = false;
					}
				}
			} else {
				tar.next().html('该字段不能为空').css('visibility', 'visible');
				me.canSubmit[name] = false;
			}
				
		},
		_commit: function(tar){
			var me = this;
			
			for (var item in me.canSubmit) {
				if(me.canSubmit.hasOwnProperty(item)){
					if(!me.canSubmit[item]) {
						alert('您有必填项校验未通过，请检查');
						return;
					}
				}
			}

			$.ajax({
				url: '/Longyan/customer/update-password',
				data: {
					password: $('#password').val(),
					newPassword: $('#newpassword').val()
				}
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 2001){   //通过登录验证
						alert('修改成功，请重新登录');
						window.location.href = '/Longyan/login';
					}else if(json.code == 2004){
						alert('用户登录信息已失效，请重新登录再修改');
						window.location.href = '/Longyan/login';
					}else if(json.code == 2005){
						alert('修改失败，原密码错误');
					}else {
						alert('修改失败，请重试');
					}
				}else {
					alert('修改失败，请重试');
				}
			}).fail(function(){
				alert('修改失败，请重试');
			});
		}
	};

	Page.run();
});