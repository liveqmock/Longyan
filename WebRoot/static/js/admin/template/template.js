/**
 * 模板编辑管理前端逻辑
 * @author tracyqiu
 */
(function($){
	var ue = UE.getEditor('editor');
	$.extend($, {
		initTemplateData: function(options){
			var me = this;
			me.dim = options.dim;
			me.id = options.id;
			me.content = options.content;
		},
		run: function(){
			var me = this;
			me._init();
		},
		_init: function(){
			var me = this;
			me.jQfilename = $('#filename');
			me.jQerr = $('.err');
			me.jQpublishTemplate = $('#publish-template');
			
			me._initEvent();
		},
		_initEvent: function(){
			var me = this;
			
			ue.addListener( 'ready', function( editor ) {
			     me._initData();
			} );
			
			me.jQpublishTemplate.on('click', function(){
				me._publish();
			});
		},
		_initData: function(){
			var me = this;
			ue.execCommand('insertHtml', me.content)
		},
		_publish:function(){
			var me = this,
				filename = me.jQfilename.val(),
				content = ue.getContent();
			
			if(!filename){
				me.jQerr.html('文件名不能为空').css('visibility', 'visible');
				return;
			}
			me.jQerr.css('visibility', 'hidden');
			content = content ? content : '正在建设中...';
			
			$.ajax({
				url: '/Longyan/admin/filter/update-template',
				data: {
					dim: me.dim,
					id: me.id,
					filename: filename,
					template_content: content
				},
				type: 'POST'
			}).done(function(res){
				var json = typeof res == 'string' ? $.parseJSON(res) : res;
				if(json.code == 1001 || json.code == 2001){
					me.jQerr.html('操作成功').css({'visibility': 'visible', 'color': 'green'});
				} else if(json.code == 1002) {
					me.jQerr.html('文件名已存在').css({'visibility': 'visible', 'color': 'red'});
				}else {
					me.jQerr.html('操作失败').css({'visibility': 'visible', 'color': 'red'});
				}
			}).fail(function(){
				me.jQerr.html('创建文件失败').css('visibility', 'visible');
			});
		}
	});
})(jQuery);

