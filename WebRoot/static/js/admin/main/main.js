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
			me.jQuserinfoTable = $('.userinfo-table');
			me.jQsave = $('#save');
			me.jQmodifyInfo = $('.modify-info');
			me.jQtableBox = $('.table-box');

			me.canSubmit = {
				name: false,
				telephone: false,
				email: false,
				id_card: false,
				province: false
			};

			me._initEvent();
			me._initDate();
		},
		_initEvent: function(){
			var me = this;

			me.jQuserinfoTable.on('blur', '.required', function(e){
				me._inputBlur($(e.target));
			});

			me.jQsave.on('click', function(){
				me._commit($(this));
			});
			
			me.jQmodifyInfo.on('click', function(){
				me._displayInfoBox($(this));
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
				if(name == 'name' || name == 'telephone' || name == 'id_card' || name == 'province'){
					me.canSubmit[name] = true;
				}else if(name == 'email'){
					if(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val)){
						me.canSubmit[name] = true;
					}else {
						tar.next().html('邮箱格式不正确').css('visibility', 'visible');
						me.canSubmit[name] = false;
					}
				}
			} else {
				tar.next().html('该字段不能为空').css('visibility', 'visible');
				me.canSubmit[name] = false;
			}
				
		},
		_commit: function(tar){
			var me = this,
				jQrequireds = $('.required'),
				len = jQrequireds.length;
			
			for(var i = 0; i < len; i++) {
				var temp = $(jQrequireds[i]),
					name = temp.attr('name'),
					val = temp.val();
				
				if(val){
					tar.next().html('').css('visibility', 'hidden');
					if(name == 'name' || name == 'telephone' || name == 'id_card' || name == 'province'){
						me.canSubmit[name] = true;
					}else if(name == 'email'){
						if(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val)){
							me.canSubmit[name] = true;
						}else {
							tar.next().html('该字段不能为空').css('visibility', 'visible');
							me.canSubmit[name] = false;
						}
					}
				} else {
					temp.next().html('该字段不能为空').css('visibility', 'visible');
					me.canSubmit[name] = false;
				}
			}

			for (var item in me.canSubmit) {
				if(me.canSubmit.hasOwnProperty(item)){
					if(!me.canSubmit[item]) {
						alert('您有必填项校验未通过，请检查');
						return;
					}
				}
			}

			$.ajax({
				url: '/Longyan/admin/filter/update-employee',
				data: {
					id: $('#uid').val(),
					name: $('#name').val(),
					id_card: $('#id_card').val(),
					telephone: $('#telephone').val(),
					sex: $('input[type="radio"]:checked').val(),
					birthday: $('#birthday').val(),
					address: $('#address').val(),
					qq: $('#qq').val(),
					email: $('#email').val(),
					province: $('#province').val()
				}
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 2001){   //通过登录验证
						window.location.reload();
					}else if(json.code == 2004){
						alert('用户登录信息已失效，请重新登录再修改');
						window.location.href = '/Longyan/login';
					}else{
						alert('修改失败，请重试');
					}
				}else {
					alert('修改失败，请重试');
				}
			}).fail(function(){
				alert('修改失败，请重试');
			});
		},
		_displayInfoBox: function(tar){
			var me = this,
				status = +tar.attr('status');
			
			if(!status) {
				tar.attr('status', 1);
				me.jQtableBox.show();
			}else {
				tar.attr('status', 0);
				me.jQtableBox.hide();
			}
		}
	};

	Page.run();
});