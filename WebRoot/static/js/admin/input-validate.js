/**
 * 简单的表单验证
 * @author tracyqiu
 */

(function($){
	$.extend($.fn, {
		validate: function(options){
			var inputs = this.find('.validate'),
				i = 0,
				type = "",
				val = "",
				ret = {
					code: true,
					data: []
				},
				name = '',
				item = null,
				len = inputs.length;
			
			if(!len){
				ret.code = false;
				return ret;
			}
			
			for(; i < len; i++){
				item = $(inputs[i]);
				type = item.attr('type');
				name = item.attr('name');
				val = item.val();
				
				if(item.hasClass('required')){
					if(!val){  //数据为空
						item.parent().next().html('该字段不能为空').show();
						ret.code = false;
					} else if(item.hasClass('email')){
						if(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val)){
							item.parent().next().html('').hide();
						}else {
							item.parent().next().html('邮箱格式不正确').show();
							ret.code = false;
						}
					}else {
						item.parent().next().html('').hide();
					}
				}
				
				if(type == 'radio' && !item[0].checked){
					continue;
				}
				
				ret.data.push([name, val]);
			}
			
			return ret;
		}
	});
})(jQuery);
