/**
 * 栏目管理前端逻辑
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
			me.jQdelColumn = $('#del-column');
			me.jQaddColumn = $('#add-column');
			me.jQcolumnInfoPop = $('.column-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQsearch = $('#search');
			me.jQupload = $('#upload');
			me.flag = '';   //表示回复还是查看
			
			me._initEvent();
			me._initTable('/Longyan/admin/filter/get-all-column', {});
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddColumn.on('click', function(){
				me._addColumn($(this));
			});
			me.jQclose.on('click', function(){
				me.jQcolumnInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQcolumnInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm($(this));
			});
			me.jQdelColumn.on('click', function(){
				me._delColumn();
			});
			me.jQtableContainer.on('click', '.edit-column', function(e){
				me._editColumn($(e.currentTarget));
			});
			me.jQtableContainer.on('click', '.edit-template', function(e){
				me._editTemplate($(e.currentTarget));
			});
			me.jQsearch.on('click', function(){
				me._search($(this));
			});
			me.jQupload.on('click', function(){
				me._upload();
			});
		},
		_initTable: function(url, data){
			var me = this,
				newfields = me.config.table_fileds.splice(0);  //深克隆新数组

			$('#table-container').toTable({
				url: url,
				data: data,
				datafields: newfields,
				tableName: '栏目信息列表',
				page: 'column'
			});
		},
		_addColumn: function(tar){
			var me = this;
			me.flag = 'add';
			me.msgId = tar.attr('id');
			me._resetForm(tar);
			me.jQcolumnInfoPop.show();
		},
		_submitForm: function(){
			var me = this,
				ret = false,
				postData = {};
			
			ret = $('.validate-table').validate();
			if(!ret.code) return;
			
			for(var i = 0, len = ret.data.length; i < len; i ++){
				postData[ret.data[i][0]] = ret.data[i][1];
			}
			if(me.flag == 'edit'){
				postData.id = me.id;
			}

			$.ajax({
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-column" : "/Longyan/admin/filter/update-column",
				type: 'get',
				data: postData
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					if(json.code == 1001 || json.code == 2001){   //通过登录验证
						window.location.reload();
					}else{
						me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
					}
				}else {
					me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
				}
			}).fail(function(){
				me._showErrorTip(me.flag == 'add' ? '添加失败' : '修改失败');
			});
		},
		_showErrorTip: function(msg){
			$('#result').html(msg).show();
		},
		_delColumn: function(){
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
					url: "/Longyan/admin/filter/del-column",
					type: 'get',
					data: {
						columnIds: ids.join(',')
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
		_editColumn: function(tar){
			var me = this,
				parent = tar.parent().parent();

			me.flag = 'edit';
			me.id = tar.attr('id');
			$("#site_id option[value='" + parent.find('.site_id').html() + "']").attr("selected", true);
			$("#style option[value='" + parent.find('.style').html() + "']").attr("selected", true);
			$('#name', me.jQcolumnInfoPop).val(parent.find('.name').html());
			$('#code', me.jQcolumnInfoPop).val(parent.find('.code').html());
			$('#img-list', me.jQcolumnInfoPop).html(me._renderImgs(parent.find('.img_url').html()));

			$('.err-lable').hide();
			me.jQcolumnInfoPop.show();
		},
		_editTemplate: function(tar){
			var me = this;
		},
		_resetForm: function(tar){
			var me = this,
				parent = tar.parent().parent();

			$('#name', me.jQcolumnInfoPop).val('');
			$('#code', me.jQcolumnInfoPop).val('');
			$('#img_url', me.jQcolumnInfoPop).val('');
			$('#img-list', me.jQcolumnInfoPop).html('');
			$('.err-lable').hide();
		},
		_search: function(tar){
			var me = this,
				jQsiteForm = $('#site-form');

			$.ajax({
				url: '/Longyan/static/conf/column.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-column-by-siteid', {
					site_id: jQsiteForm.val()
				});
			}).fail(function(){
			});
		},
		_upload: function(){
			var me = this,
				template = '';
			
			if(!($('#img_url').val())) {
				alert('请选择上传文件');
				return;
			}
			$("#upload-result").html('正在上传...').show(); 
			$.ajaxFileUpload({  
                url: '/Longyan/admin/filter/upload',  
                secureuri: false,  
                fileElementId: 'img_url',  
                dataType: 'json',  
                beforeSend: function() {  
                    $("#upload-result").html('正在上传...').show();  
                },  
                success: function(data, status) {  
                    if (typeof (data.error) != 'undefined') {  
                    	$("#upload-result").html('上传失败').show();  
                    } else {  //上传成功
                    	data = data.trim();
                    	template = '<li>' + 
										'<a href="' + data + '" target="_blank" title="点击预览" class="img-item">' + data + '</a>' + 
										'<a href="javascript:;" class="img-item-del" path="' + data + '" onclick="$(this).parent().remove();">删除</a>' +
									'</li>';
                    	$("#upload-result").hide();
                    	$('#img-list', me.jQcolumnInfoPop).append(template);
                    } 
                },  
                error: function(data, status, e) {  
                	$("#upload-result").html('上传失败').show(); 
                }  
            });
		},
		_renderImgs: function(img_urls){
			var me = this,
				ret = '',
				imgArr = [];

			if(!img_urls) return img_urls;
			imgArr = img_urls.split('##');
			for(var i = 0, len = imgArr.length; i < len; i++){
				ret += '<li>' + 
							'<a href="' + imgArr[i] + '" target="_blank" title="点击预览" class="img-item">' + imgArr[i] + '</a>' + 
							'<a href="javascript:;" class="img-item-del" path="' + imgArr[i] + '" onclick="$(this).parent().remove();">删除</a>' +
						'</li>';
			}

			return ret;
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/column.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});