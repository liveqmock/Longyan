/**
 * 页面级别公用js
 * @author tracyqiu
 * @date
 */

(function($){
	$(document).ready(function(){
		// 退出登录
		var jQbtnLogout = $('#btn-logout');
		
		jQbtnLogout.on('click', function(){
			$.ajax({
				url: '/Longyan/logout?_=' + new Date().getTime()
			}).done(function(res){
				var json = typeof res == 'string' ? JSON.parse(res) : res;

				if(json){
					if(json.code == 0){   //未通过登录验证
						window.location.reload();
					}
				}else {
					alert('登出失败');
				}
			}).fail(function(){
				alert('登出失败');
			});
		});
		
		//监听body的scroll事件
		$(window).on('scroll', function(){
			var scroll = $('body').scrollTop(),
				jQsideWrap = $('.page-side-wrap');
			
			if(scroll < 350) {
				jQsideWrap && jQsideWrap.hide();
			}else{
				jQsideWrap && jQsideWrap.show();
			}
			
		});
		
	});
})(jQuery);