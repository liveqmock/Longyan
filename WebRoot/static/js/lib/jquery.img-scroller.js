(function($) {
	// 图片轮滑插件
	/**
	 * 
	 * option = { "imgs":[], "height":300, "timer":1000 };
	 * 
	 */
	$.fn.imgFly = function(option) {
		// 构造dom节点
		var imgDom = "", aDom = "", self = this, timer = option.timer ? option.timer
				: 3000, height = option.height ? option.height : 300;

		$.each(option.imgs,function(index, value) {
			var count = index + 1;
			if (index == 0) {
				imgDom += '<img class="J-img" style="opacity:1;filter:alpha(opacity=100);" src="'
						+ value + '" />';
				aDom += '<a href="javascript:void(0);" class="J-page active" index="'
					+ count + '"></a>';
			} else {
				imgDom += '<img class="J-img" src="' + value
						+ '" />';
				aDom += '<a href="javascript:void(0);" class="J-page" index="'
					+ count + '"></a>';
			}
		});
		
		// 给dom加高度
		$(self).css({
			"height" : height
		});
		// 把构造好的dom加入到页面中
		var dom = '<div class="J-imgMain">'
				+ '<a class="btnArrow btnLeft" href="javascript:void(0);"></a>'
				+ '<a class="btnArrow btnRight"  href="javascript:void(0);"></a>'
				+ '<div class="box">' + imgDom + '<div class="page">' + aDom
				+ '</div>' + '</div>';
		$(dom).appendTo(self);

		// 如果只有一张图片就没有js效果，并且隐藏操作按钮
		if (option.imgs.length <= 1) {
			$(self).find(".page").css({
				"display" : "none"
			});
			$(self).find(".btnArrow").css({
				"display" : "none"
			});
			return;
		}

		// 分页按钮
		var aPage = $(self).find('.J-page'),
		// 图像集合
		aImg = $(self).find('.J-img'),
		// 图像个数
		iSize = aImg.size(),
		// 切换索引
		index = 0;
		// 左边按钮点击
		$(self).find('.btnLeft').click(function() {
			index--;
			if (index < 0) {
				index = iSize - 1
			}
			change(index)
		});
		$(self).find('.btnRight').click(function() { // 右边按钮点击
			index++;
			if (index > iSize - 1) {
				index = 0
			}
			change(index);
		})
		// 分页按钮点击
		aPage.click(function() {
			index = $(this).index();
			change(index)
		});
		// 切换过程
		function change(index) {
			aImg.stop();
			aPage.removeClass('active');
			aPage.eq(index).addClass('active');
			// 隐藏除了当前元素所有元素
			$(aImg).each(function(index1) {
				if (index1 != index) {
					$(this).animate({
						opacity : 0
					}, timer);
				}
			});
			// 显示当前图像
			aImg.eq(index).animate({
				opacity : 1
			}, timer);
		}
		function autoshow() {
			index = index + 1;
			if (index <= iSize - 1) {
				change(index);
			} else {
				index = 0;
				change(index);
			}

		}
		int = setInterval(autoshow, timer);
		function clearInt() {
			$(self).find('.btnArrow,.page a').mouseover(function() {
				clearInterval(int);
			})
		}
		function setInt() {
			$(self).find('.btnArrow,.page a').mouseout(function() {
				int = setInterval(autoshow, timer);
			})
		}
		clearInt();
		setInt();
	};
})(jQuery);