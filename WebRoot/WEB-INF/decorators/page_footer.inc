		</div>
		<!-- 右侧悬浮框区域 -->
		<div class="page-side-wrap">
			<div class="online-chat">
				<p>在线咨询</p>
				<#if contacts?exists && contacts?size!=0>
					<#list contacts as contact>
						<a class="contact-item" href="tencent://message/?uin=${contact.qq}&Site=龙颜集团&Menu=yes">
							<i class="page-icon qq"></i>
							<span class="contact-name" title="${contact.name}">${contact.name}</span>
						</a>
					</#list>
				</#if>
			</div>
			<div class="online-phone">
				<p class="tip-wrap">
					<i class="page-icon phone"></i>
					<span class="tip-txt">服务热线</span>
				</p>
				<#if contacts?exists && contacts?size!=0>
					<#list contacts as contact>
						<p>${contact.telephone}</p>
					</#list>
				</#if>
			</div>
		</div>
		<!-- 页脚区域 -->
		<div class="page-footer-bar">
			<div class="center-wrap">
				<p class="link-wrap">
					友情链接：
					<#if friendLinks?exists && friendLinks?size!=0>
						<#list friendLinks as friendLink>
							<a href="${friendLink.url}" target="_blank" class="link-item">${friendLink.name}</a>
						</#list>
					</#if>
				</p>
				<p class="right-wrap">
					@2015 版权所有 龙颜集团  long yan group Co.,Ltd. 地址：中国.重庆.南岸区.弹子石.中海国际社区B座12层 电话：023-58881002
					<a href="/Longyan/add-feedback" target="_blank" class="comment">意见反馈</a>
				</p>
			</div>
		</div>
		
		<script type="text/javascript">
			//初始化banner图
			var imgs = ${img_urls};
				
			$('.page-scroller-wrap').imgFly({
		         "imgs": imgs,
		       	 "height":400
		    });
		</script>
	</body>
</html>