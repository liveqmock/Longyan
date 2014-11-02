/**
 * 留言管理前端逻辑
 * @author tracyqiu
 */

$(document).ready(function(){
	var Page = {
		run: function(config){
			var me = this;

			me.config = $.extend(true, {}, config);
			me._init();
		},
		_init: function(){
			var me = this;
			me.jQdelMessage = $('#del-message');
			me.jQmessageInfoPop = $('.message-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQsearch = $('#search');
			me.flag = '';   //表示回复还是查看
			
			me._initEvent();
			me._initDate();
			me._initTable('/Longyan/admin/filter/get-all-message', {});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQclose.on('click', function(){
				me.jQmessageInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQmessageInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm($(this));
			});
			me.jQdelMessage.on('click', function(){
				me._delMessage();
			});
			me.jQtableContainer.on('click', '.reply-message', function(e){
				me._replyMessage($(e.currentTarget));
			});
			me.jQtableContainer.on('click', '.look-reply', function(e){
				me._lookReply($(e.currentTarget));
			});
			me.jQsearch.on('click', function(){
				me._search($(this));
			});
		},
		_initTable: function(url, data){
			var me = this,
				newfields = me.config.table_fileds.splice(0);  //深克隆新数组

			$('#table-container').toTable({
				url: url,
				data: data,
				datafields: newfields,
				tableName: '留言信息列表',
				page: 'message'
			});
		},
		_initDate: function(){
			var me = this,
				jQstartDate = $('#start-date'),
				jQendDate = $('#end-date');

			jQstartDate.datepicker({
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
			    //appendText: '(yyyy-mm-dd)',
			});
			
			jQendDate.datepicker({
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
			    //appendText: '(yyyy-mm-dd)',
			});
		},
		_replyMessage: function(tar){
			var me = this;
			me.flag = 'reply';
			me.msgId = tar.attr('id');
			me._resetForm(tar);
			me.jQmessageInfoPop.show();
		},
		_submitForm: function(tar){
			var me = this,
				replyContent = $('#reply-content').val();

			if(!replyContent){
				me._showErrorTip('回复内容不能为空!');
				return;
			}

			$.ajax({
				url: "/Longyan/admin/filter/add-message-reply",
				type: 'get',
				data: {
					message_id: me.msgId,
					reply_content: replyContent
				}
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code == 1001){   //通过登录验证
						window.location.reload();
					}else{
						me._showErrorTip('回复失败');
					}
				}else {
					me._showErrorTip('回复失败');
				}
			}).fail(function(){
				me._showErrorTip('回复失败');
			});
		},
		_showErrorTip: function(msg){
			$('#result').html(msg).show();
		},
		_delMessage: function(){
			var me = this,
				ids = [];
				jQselectItem = $('#table-container .select-item');
			
			$.each(jQselectItem, function(index, item){
				item.checked && ids.push($(item).val());
			});
			
			if(!ids.length){
				alert('请选择要删除的选项!');
				return;
			}
			
			if(confirm("确认删除所选项？")){
				$.ajax({
					url: "/Longyan/admin/filter/del-message",
					type: 'get',
					data: {
						messageIds: ids.join(',')
					}
				}).done(function(data){
					var json = typeof data == 'string' ? JSON.parse(data) : data;

					if(json){
						if(json.code == 3001){   //通过登录验证
							window.location.reload();
						}else{
							me._showErrorTip('删除失败');
						}
					}else {
						me._showErrorTip('删除失败');
					}
				}).fail(function(){
					me._showErrorTip('删除失败');
				});
			}
		},
		_lookReply: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'look';
			me.id = tar.attr('id');
			$('.customer-say', me.jQmessageInfoPop).html(parent.find('.customer_name').html() + '说：');
			$('.customer-content', me.jQmessageInfoPop).html(parent.find('.content').html());
			$('#reply-content', me.jQmessageInfoPop).attr('disabled', true).val(parent.find('.reply_content').html());
			$('.reply-info').html('回复人：' + parent.find('.reply_emplyoee_name').html() + '  时间：' + parent.find('.reply_ctime').html()).css('visibility', 'visible');
			me.jQsure.hide();
			me.jQmessageInfoPop.show();
		},
		_resetForm: function(tar){
			var me = this,
				parent = tar.parent().parent();

			$('.customer-say', me.jQmessageInfoPop).html(parent.find('.customer_name').html() + '说：');
			$('.customer-content', me.jQmessageInfoPop).html(parent.find('.content').html());
			$('#reply-content', me.jQmessageInfoPop).attr('disabled', false).val('');
			$('.reply-info').css('visibility', 'hidden');
			$('.err-lable').hide();
			me.jQsure.show();
		},
		_search: function(tar){
			var me = this,
				jQstartDate = $('#start-date'),
				jQendDate = $('#end-date'),
				jQreplyStatus = $('#reply-status');
			
			if(!jQstartDate.val() || !jQendDate.val()){
				alert('起始时间或者终止时间不能为空');
				return;
			}

			$.ajax({
				url: '/Longyan/static/conf/message.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-message-by-date', {
					start_date: jQstartDate.val(),
					end_date: jQendDate.val(),
					status: jQreplyStatus.val()
				});
			}).fail(function(){
			});
			
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/message.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});