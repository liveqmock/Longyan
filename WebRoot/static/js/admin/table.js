/**
 * 表格渲染封装
 * @author tracyqiu
 */

(function($){
	$.extend($.fn, {
		toTable: function(options){
			var me = this,
		        defaults = {
		            url:'',
		            data:{},
		            datafields: [],
		            tableName: '下载',
		            page: '',
		            index: 1
		        },
		        inputStr = '',
		        opts = jQuery.extend(defaults, options);

			inputStr = function(field){
				return '<input type="checkbox" class="select-item" value="' + 
						field.id +'"/>';
			};
				
			opts.datafields.unshift({ title:'<input type="checkbox" id="select-all" />', name:'number', type:'string', checkbox: inputStr});
			opts.datafields.unshift({ title:'序号', name:'number', type:'number', number: true, sClass: "td-hidden"});
			opts.datafields.push({ "title":"操作", "name":"id", "type":"number","align":"right", "text": me.pageLink(opts.page)});
		    me.renderTable(opts); 
		},
		initEvent: function(){
			var me = this,
				jQselectAll = $('#select-all', me);
		
			jQselectAll.on('click', function(){
				me._selectAll($(this));
			});
		},
		renderTable: function(opts){
			var me = this,
				tableHeader = [],
	            tableBody = [],
	            tempHeaderMap = {},
	            tempBodyArr = [],
	            json;

	        $.ajax({
	            url: opts.url,
	            data:opts.data,
	            success:function(res){
	                if(!res){
	                    alert('表格数据初始化失败');
	                }else{
	                	json = typeof res == 'string' ? JSON.parse(res) : res;
	                    //初始化thead
	                    $.each(opts.datafields,function(j,v){
	                        tempHeaderMap = {
	                            sTitle: v.title,
	                            sClass: v.sClass ? v.sClass : ''
	                        }
	                        tableHeader.push(tempHeaderMap)
	                    });

	                    //初始化tbody
	                    $.each(json,function(i,field){

	                        $.each(opts.datafields,function(j,v){
	                            var tempText = '';
	                            field.fieldname = v.name;

	                            if(v.number){  //展示序列号
	                                tempText = opts.index++;
	                            }else if(v.text) {
	                                tempText = v.text(field);
	                            }else if(v.checkbox){
	                            	tempText = v.checkbox(field);
	                            }else if(v.permission){ //权限
	                            	tempText = me._parsePermission(field);
	                            }else{
	                                if(v.format != null) {
	                                    tempText = null == field[v.name] ? '' : $.formatNumber(field[v.name], {format:v.format});                       
	                                } else {
	                                    tempText = null == field[v.name] ? '' :field[v.name];
	                                }
	                            }

	                            tempBodyArr.push(me.isNotEmpty(tempText) ? tempText : '');
	                        });
	                        tableBody.push(tempBodyArr);  
	                        tempBodyArr = [];              
	                    });
	                    
	                    //todo
	                    me.dataTable({
	                        "aaData": tableBody,         //所有数据
	                        "aoColumns": tableHeader,    //表头
	                        "bDestroy": true,
	                        "bPaginate": true,          //开关，是否显示分页器
	                        "bInfo": true,               //开关，是否显示表格的一些信息
	                        "bJQueryUI": false,          //开关，是否启用JQueryUI风格
	                        "bLengthChange": true,      //开关，是否显示每页大小的下拉框
	                        "bScrollInfinite": false,     //是否初始化滚动条
	                        "bSort": true,               //开关，是否启用各列具有按列排序的功能
	                        "bStateSave": false,         //开关，是否打开客户端状态记录功能。
	                        "bAutoWidth": true,         //自适应宽度
	                        "bScrollCollapse" : true,    //是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变  
	                        "sPaginationType" : "full_numbers",
	                        "bProcessing": false,
	                        "scrollCollapse": true,
	                        "jQueryUI":       true,
	                        "dom": 'T<"clear">lfrtip',
	                        "tableTools": {
	                            "aButtons": [ "xls" ],
	                            "sFileName": opts.tableName + ".xls"
	                        },
	                        "oLanguage": {               //国际化配置  
	                            "bProcessing" : "正在获取数据，请稍后...",    
	                            "sLengthMenu" : "显示 _MENU_ 条",    
	                            "sZeroRecords" : "没有您要搜索的内容",    
	                            "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",    
	                            "sInfoEmpty" : "记录数为0",    
	                            "sInfoFiltered" : "(全部记录数 _MAX_ 条)",    
	                            "sInfoPostFix" : "",    
	                            "sSearch" : "搜索",    
	                            "sUrl" : "",    
	                            "oPaginate": {    
	                                "sFirst" : "第一页",    
	                                "sPrevious" : "上一页",    
	                                "sNext" : "下一页",    
	                                "sLast" : "最后一页"    
	                            } 
	                        },
	                        fnInitComplete: function(){   //表格初始化完成
	                            //调整table－view得高度
	                            $('.table-view').height($('.table-view .dataTables_wrapper').height());
	                            me.initEvent();
	                        } 
	                    });
	                }
	            },
	            error: function(){
	                alert('表格数据初始化失败');
	            }
	        });
		},
		isNotEmpty: function (value){
            return value || value === 0;
        },
        pageLink: function(page){
        	var me = this,
        		link = '';
        	
        	switch(page){
        		case 'customer': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-customer" id="' + 
						field.id + '">编辑</a>';
        			};
        			break;
        		case 'employee': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-employee" id="' + 
						field.id + '">编辑</a>';
        			};
        			break;
        		case 'message': 
        			link = function(field) {
	    				return field.status == 0 ? 
	    						('<a href="javascript:;" class="btn btn-success reply-message" id="' + field.id + '">回复</a>') : 
	    						('<a href="javascript:;" class="btn btn-warning look-reply" id="' + field.id + '">查看</a>');
        			};
        			break;
        		case 'order': 
        			link = function(field) {
	    				return '<a href="/Longyan/admin/filter/order-detail?order_id=' + field.id + '" class="btn btn-success order-detail">订单详情</a>';
        			};
        			break;
        		case 'contact': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-contact" id="' + 
						field.id + '">编辑</a>';
        			};
        			break;
        		case 'friendLinks': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-friendLinks" id="' + 
						field.id + '">编辑</a>';
        			};
        			break;
        		case 'column': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-column" id="' + 
						field.id + '">编辑栏目</a><a href="javascript:;" class="btn btn-warning edit-template" from="column" id="' + 
						field.id + '">编辑模板</a>';
        			};
        			break;
        		case 'content': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-content" id="' + 
						field.id + '">编辑内容</a><a href="javascript:;" class="btn btn-warning edit-template" from="content" id="' + 
						field.id + '">编辑模板</a>';
        			};
        			break;
        		case 'permission': 
        			link = function(field) {
	    				return '<a href="javascript:;" class="btn btn-success edit-permission" emp_id="' + 
						field.employee_id + '">更新</a>';
        			};
        			break;
        	}
        	
        	return link;
        },
        _selectAll: function(tar){
        	var me = this,
        		jQselectItem = me.find('.select-item');

        	$.each(jQselectItem, function(index, item){
        		if(tar[0].checked){
        			item.checked = true;
        		}else {
        			item.checked = false;
        		}
        	});
        },
        _parsePermission: function(field){
        	var me = this,
        		columnIds = field.column_ids,
        		idsArr = columnIds.split('##'),
        		retArr = [
        		    '<input type="checkbox" class="column-box" value="column" ' + (me.judgeCheck(idsArr, 'column') ? "checked" : "") + '/>栏目管理',
        		    '<input type="checkbox" class="column-box" value="content" ' + (me.judgeCheck(idsArr, 'content') ? "checked" : "") + '/>内容管理',
        		    '<input type="checkbox" class="column-box" value="friendLinks" ' + (me.judgeCheck(idsArr, 'friendLinks') ? "checked" : "") + '/>友情链接',
        		    '<input type="checkbox" class="column-box" value="contact" ' + (me.judgeCheck(idsArr, 'contact') ? "checked" : "") + '/>客服管理'
        		];
        	
        	return retArr.join('<br />');
        },
        judgeCheck: function(arr, like){
        	var me = this,
        		i = 0,
        		len = arr.length;
        	if(!len) return false;
        	for(; i < len; i++){
        		if(arr[i] == like){
        			return true;
        		}
        	}
        	return false;
        }
	});
})(jQuery);