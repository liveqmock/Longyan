/**
 * 简单的表单验证
 * @author tracyqiu
 */

(function($){
	$.extend($.fn, {
		validate: function(options){
			var items = this.find('.validate'),
				i = 0,
				type = "",
				val = "",
				ret = {
					code: true,
					data: []
				},
				name = '',
				item = null,
				len = items.length;
			
			if(!len){
				ret.code = false;
				return ret;
			}
			
			for(; i < len; i++){
				item = $(items[i]);
				type = item.attr('type');
				name = item.attr('name');
				val = item.val();
				
				if(item.hasClass('required')){
					if(item.attr('flag') == 'img-wrap'){
						if(item.children().length == 0){
							item.parent().parent().next().html('必须添加banner图').show();
							ret.code = false;
						}else {
							ret.data.push(['img_url', this.getImgsUrl(item)]);
						}
						continue;
					}
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
					}else if(item.hasClass('float')){
						if(/^([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$/.test(val)){
							item.parent().next().html('').hide();
						}else {
							item.parent().next().html('请输入正确的数字').show();
							ret.code = false;
						}
					}else if(item.hasClass('int')){
						if(/^[1-9]\d*$/.test(val)){
							item.parent().next().html('').hide();
						}else {
							item.parent().next().html('请输入正整数').show();
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
		},
		getImgsUrl: function(el){
			var childs = el.children(),
				i = 0,
				len = childs.length,
				retArr = [];

			for(; i < len; i++){
				retArr.push($(childs[i]).find('.img-item').attr('href'));
			}

			return retArr.join('##');
		}
	});
})(jQuery);
