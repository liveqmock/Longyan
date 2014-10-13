$(document).ready(function(){
	if (!$.support.leadingWhitespace) {
		alert('后台系统不支持低版本IE6-8系列浏览器使用，请使用chrome浏览器或高版本IE浏览器');
	}
	
	function registerEvent() {
		var jQsystemSetting = $('.system-setting');
		
		jQsystemSetting.on('click', function(){
			if($(this).hasClass('glyphicon-plus')){
				$(this).removeClass('glyphicon-plus').addClass('glyphicon-minus');
				$(this).parent().find('.system-item').removeClass('nav-hidden');
			}else if($(this).hasClass('glyphicon-minus')){
				$(this).removeClass('glyphicon-minus').addClass('glyphicon-plus');
				$(this).parent().find('.system-item').addClass('nav-hidden');
			}
		});
	}
	
	registerEvent();
});