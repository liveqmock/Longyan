/**
 * 简单的表单验证
 * @author tracyqiu
 */

(function($){
	$.extend($.fn, {
		validate: function(options){
			var inputs = this.find('input'),
				i = 0,
				type = "",
				val = "",
				ret = {
					code: false,
					data: []
				},
				hasAdded = 0,
				name = '',
				item = null,
				len = inputs.length;
			
			if(!len) return ret;
			
			for(; i < len; i++){
				item = $(inputs[i]);
				type = item.attr('type');
				name = item.attr('name');
				val = item.val();
				
				if(item.hasClass('required')){
					if(!val){  //数据为空
						item.parent().next().html('该字段不能为空').show();
					} else if(item.hasClass('email')){
						if(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val)){
							ret.code = true;
							item.parent().next().html('').hide();
						}else {
							item.parent().next().html('邮箱格式不正确').show();
						}
					}else {
						item.parent().next().html('').hide();
					}
				}
				
				if(type == 'radio'){
					if(hasAdded){
						continue;
					}else {
						hasAdded = 1;
					}
				}
				ret.data.push([name, val]);
			}
			
			return ret;
		}
	});
})(jQuery);
