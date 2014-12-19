/**
 * 前端用户注册逻辑
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
			me.jQregisterTable = $('.register-table');
			me.jQsure = $('#sure');

			me.canSubmit = {
				username: false,
				realname: false,
				telephone: false,
				email: false,
				repassword: false,
				password: false
			};

			me._initEvent();
			me._initDate();
		},
		_initEvent: function(){
			var me = this;

			me.jQregisterTable.on('blur', '.required', function(e){
				me._inputBlur($(e.target));
			});

			me.jQsure.on('click', function(){
				me._commit($(this));
			});
		},
		_initDate: function(){
			var me = this,
				jQbirthday = $('#birthday');

			jQbirthday.datepicker({
			    showMonthAfterYear: true, // 月在年之后显示
			    changeMonth: true,     // 允许选择月份
			    changeYear: true,     // 允许选择年份
			    dateFormat:'yy-mm-dd',    // 设置日期格式
			    closeText:'关闭',     // 只有showButtonPanel: true才会显示出来
			    duration: 'fast',
			    showAnim:'fadeIn',
			    buttonImageOnly: true,                // 不把图标显示在按钮上，即去掉按钮
			    buttonText:'选择日期',
			    showButtonPanel: true,
			    showOtherMonths: true
			});
		},
		_inputBlur: function(tar){
			var me = this,
				name = tar.attr('name'),
				val = tar.val();

			if(val){
				tar.next().html('').css('visibility', 'hidden');
				if(name == 'username' || name == 'realname' || name == 'password' || name == 'telephone'){
					me.canSubmit[name] = true;
				}
			} else {
				tar.next().html('该字段不能为空').css('visibility', 'visible');
				me.canSubmit[name] = false;
			}
				
			if(name == 'repassword') {
				var pass = $('#password').val();

				if(pass && pass === val) {
					tar.next().html('').css('visibility', 'hidden');
					me.canSubmit[name] = true;
				} else {
					tar.next().html('密码不一致').css('visibility', 'visible');
					me.canSubmit[name] = false;
				}
			}

			if(name == 'email'){
				if(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val)){
					tar.next().html('').css('visibility', 'hidden');
					$.ajax({
						url: '/Longyan/customer-email-check',
						data: {
							email: val
						}
					}).done(function(res){
						var json = typeof res == 'string' ? JSON.parse(res) : res;
						if(json.code == 0){
							tar.next().html('').css('visibility', 'hidden');
							me.canSubmit[name] = true;
						} else {
							tar.next().html('邮箱已被注册，换个邮箱吧').css('visibility', 'visible');
							me.canSubmit[name] = false;
						}
					}).fail(function(){
						tar.next().html('邮箱检查失败').css('visibility', 'visible');
						me.canSubmit[name] = false;
					});
				}else {
					tar.next().html('邮箱格式不正确').css('visibility', 'visible');
					me.canSubmit[name] = false;
				}
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
				url: '/Longyan/add-customer',
				data: {
					username: $('#username').val(),
					realname: $('#realname').val(),
					password: $('#password').val(),
					email: $('#email').val(),
					telephone: $('#telephone').val(),
					sex: $('input[type="radio"]:checked').val(),
					birthday: $('#birthday').val(),
					address: $('#address').val(),
					qq: $('#qq').val()
				}
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 1001){   //通过登录验证
						alert('注册成功，跳转至登录页面~~');
						window.location.href = '/Longyan/login';
					}else{
						alert('注册成功，即将进入登录页');
					}
				}else {
					alert('注册失败');
				}
			}).fail(function(){
				alert('注册失败');
			});
		}
	};

	Page.run();
});