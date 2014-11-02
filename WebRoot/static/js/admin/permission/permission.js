/**
 * 权限管理前端逻辑
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
			me.jQtableContainer = $('#table-container');
			me.flag = '';   //表示回复还是查看
			
			me._initEvent();
			me._initTable('/Longyan/admin/filter/get-all-permission', {});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQtableContainer.on('click', '.edit-permission', function(e){
				me._editPermission($(e.currentTarget));
			});
		},
		_initTable: function(url, data){
			var me = this,
				newfields = me.config.table_fileds.splice(0);  //深克隆新数组

			$('#table-container').toTable({
				url: url,
				data: data,
				datafields: newfields,
				tableName: '权限信息列表',
				page: 'permission'
			});
		},
		_editPermission: function(tar){
			var me = this,
				columns = tar.parent().parent().find('.column-box'),
				employee_id = tar.attr('emp_id'),
				len = columns.length,
				column_ids = [];
			
			for(var i = 0; i < len; i++){
				if(columns[i].checked){
					column_ids.push($(columns[i]).val());
				}
			}
			
			$.ajax({
				url: '/Longyan/admin/filter/update-or-add-permission',
				type: 'get',
				data: {
					column_ids: column_ids.join('##'),
					employee_id: employee_id
				}
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if((+json.code == 1001) || (+json.code == 2001)){
					alert('操作成功');
					window.location.reload();
				}else {
					alert('操作失败');
				}
			}).fail(function(){
				alert('操作失败');
			});
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/permission.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});