/**
 * 内容管理前端逻辑
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
			me.jQdelContent = $('#del-content');
			me.jQaddContent = $('#add-content');
			me.jQcontentInfoPop = $('.content-info-pop');
			me.jQclose = $('#close');
			me.jQsure = $('#sure');
			me.jQcancel = $('#cancel');
			me.jQtableContainer = $('#table-container');
			me.jQcolumnForm = $('#column-form');
			me.jQcolumnId = $('#column_id');
			me.jQsearch = $('#search');
			me.jQupload = $('#upload');
			me.flag = '';   //表示回复还是查看
			
			me._initEvent();
			me._initTable('/Longyan/admin/filter/get-all-content', {});
			me._initColumn();
		},
		_initEvent: function(){
			var me = this;
			
			me.jQaddContent.on('click', function(){
				me._addContent($(this));
			});
			me.jQclose.on('click', function(){
				me.jQcontentInfoPop.hide();
			});
			me.jQcancel.on('click', function(){
				me.jQcontentInfoPop.hide();
			});
			me.jQsure.on('click', function(){
				me._submitForm($(this));
			});
			me.jQdelContent.on('click', function(){
				me._delContent();
			});
			me.jQtableContainer.on('click', '.edit-content', function(e){
				me._editContent($(e.currentTarget));
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
			me.jQcolumnId.on('change', function(){
				me._columnIdChange($(this));
			});
		},
		_initColumn: function(){
			var me = this;
			
			$.ajax({
				url: '/Longyan/admin/filter/get-all-column',
				type: 'get',
			}).done(function(data){
				var json = typeof data == 'string' ? JSON.parse(data) : data;

				if(json){
					me._renderColumn(json);
				}else {
					me._showErrorTip('初始化栏目列表失败');
				}
			}).fail(function(){
				me._showErrorTip('初始化栏目列表失败');
			});
		},
		_initTable: function(url, data){
			var me = this,
				newfields = me.config.table_fileds.splice(0);  //深克隆新数组

			$('#table-container').toTable({
				url: url,
				data: data,
				datafields: newfields,
				tableName: '内容信息列表',
				page: 'content'
			});
		},
		_renderColumn: function(data){
			var me = this,
				i = 0,
				len = data.length,
				template = [],
				searchStr = '<option value="-1">全部</option>',
				editStr = '<option value="">请选择</option>';
			
			if(!len) return;
			for(; i < len; i++){
				template.push('<option value="' + data[i]['id'] + '" style-item="' + data[i]['style'] +' ">' + data[i]['name'] + '</option>');
			}
			
			me.jQcolumnForm.html(searchStr + template.join(''));
			me.jQcolumnId.html(editStr + template.join(''));
		},
		_addContent: function(tar){
			var me = this;
			me.flag = 'add';
			me.msgId = tar.attr('id');
			me._resetForm(tar);
			me.jQcontentInfoPop.show();
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
				url: me.flag == 'add' ? "/Longyan/admin/filter/add-content" : "/Longyan/admin/filter/update-content",
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
		_delContent: function(){
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
					url: "/Longyan/admin/filter/del-content",
					type: 'get',
					data: {
						contentIds: ids.join(',')
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
		_editContent: function(tar){
			var me = this,
				parent = tar.parent().parent(),
				jQcolumnSelected = $("#column_id option[value='" + parent.find('.column_id').html() + "']");

			me.flag = 'edit';
			me.id = tar.attr('id');
			jQcolumnSelected.attr("selected", true);
			if(+jQcolumnSelected.attr('style-item') == 1){
				$('#img-list', me.jQcontentInfoPop).removeClass('required');
			} else {
				$('#img-list', me.jQcontentInfoPop).addClass('required');
			}
			$('#title', me.jQcontentInfoPop).val(parent.find('.title').html());
			$('#code', me.jQcontentInfoPop).val(parent.find('.code').html());
			$('#img-list', me.jQcontentInfoPop).html(me._renderImgs(parent.find('.img_url').html()));

			$('.err-lable').hide();
			me.jQcontentInfoPop.show();
		},
		_editTemplate: function(tar){
			var me = this;
		},
		_resetForm: function(tar){
			var me = this,
				parent = tar.parent().parent();

			$('#title', me.jQcontentInfoPop).val('');
			$('#code', me.jQcontentInfoPop).val('');
			$('#img_url', me.jQcontentInfoPop).val('');
			$('#img-list', me.jQcontentInfoPop).html('');
			$('.err-lable').hide();
		},
		_search: function(tar){
			var me = this;

			$.ajax({
				url: '/Longyan/static/conf/content.json'
			}).done(function(json){
				me.config = $.extend(true, {}, json);
				
				me._initTable('/Longyan/admin/filter/get-content-by-columnid', {
					column_id: me.jQcolumnForm.val()
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
                    	$('#img-list', me.jQcontentInfoPop).append(template);
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
		},
		_columnIdChange: function(tar){
			var me = this,
				style = tar.find('option:selected').attr('style-item');
			
			if(style == 1){
				$('#img-list', me.jQcontentInfoPop).removeClass('required');
			} else {
				$('#img-list', me.jQcontentInfoPop).addClass('required');
			}
		}
	};
	
	$.ajax({
		url: '/Longyan/static/conf/content.json'
	}).done(function(json){
		Page.run(json);
	}).fail(function(){
		alert('页面初始化失败');
	});
});