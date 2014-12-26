<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
<link href="/Longyan/static/css/pages/news/bbs-detail.css" rel="stylesheet" />
<div class="detail-wrap wrap">
	<div class="catalog_title clearself">
		<span>${pageCode?cap_first}</span><br>${pageTitle!''} ▪ 帖子详情
	</div>
	<div class="top-bar">
		<a class="add-bbs-reply" href="javascript:;">回复</a>
		<div class="top-pager">
			<a class="return-list" href="/Longyan/pages/news/${dim}">« 返回列表</a>
		</div>
		<div class="top-pager">
			<div class="PageNum">
				<#import "../pagination.ftl" as com>
				<#--前一个参数是总记录数，后一个参数是页面记录数-->
				<@com.pagination Request.totalCount Request.pageSize/>
			</div>
		</div>
	</div>
	<div class="reply-list-wrap">
		<div class="item lz-item">
			<div class="customer-info">
				<div class="img-wrap">
					<img src="/Longyan/static/img/pages/news/lz-default.jpg">
				</div>
				<div class="name">
					<span class="sex-icon">
						<#if bbsLz.lz.sex == '男'>
							♂
						<#else>
							♀
						</#if>
					</span>
					${bbsLz.lz.username}
				</div>
				<div class="other-info">注册日期    ${bbsLz.lz.ctime}</div>
			</div>
			<div class="content-main">
				<div class="floor-title cc">
					<div class="post-num">
						<span class="hits">阅读：<em class="core_num">${bbsLz.bbs.view_count}</em></span>
						<span class="replies">回复：<em class="core_num">${bbsLz.bbs.reply_count}</em></span>
					</div>
					<h1 id="post-title">${bbsLz.bbs.title}</h1>
					<#if bbsLz.bbs.status == 4>
						<h1 id="post-status">已封帖</h1>
					</#if>
				</div>
				<div class="clear"></div>
				<div class="time-bar cc">
					<div class="floor">
						楼主
						<sup>#</sup>
					</div>
					<span class="create-time">发布于：${bbsLz.bbs.ctime?string("yyyy-MM-dd HH:mm:ss")}</span>
				</div>
				<div class="content-template">
					${bbsLz.bbs.content}
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<#if replyInfoList?exists && replyInfoList?size!=0>
			<#list replyInfoList as info>
				<div class="item">
					<div class="customer-info">
						<div class="img-wrap">
							<#if info.customer.id == bbsLz.lz.id>
								<img src="/Longyan/static/img/pages/news/lz-default.jpg">
							<#else>
								<img src="/Longyan/static/img/pages/news/other-default.jpg">
							</#if>
						</div>
						<div class="name">
							<span class="sex-icon">
								<#if info.customer.sex == '男'>
									♂
								<#else>
									♀
								</#if>
							</span>
							${info.customer.username}
						</div>
						<div class="other-info">注册日期    ${info.customer.ctime}</div>
					</div>
					<div class="content-main">
						<div class="time-bar cc">
							<div class="floor">
								${info_index + 1}楼
								<sup>#</sup>
							</div>
							<span class="create-time">发布于：${info.reply.ctime?string("yyyy-MM-dd HH:mm:ss")}</span>
						</div>
						<div class="content-template">
							${info.reply.content}
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</#list>
		</#if>
	</div>
	<div class="footer-bar">
		<a class="add-bbs-reply" href="javascript:;">回复</a>
		<div class="footer-pager">
			<a class="return-list" href="/Longyan/pages/news/${dim}">« 返回列表</a>
		</div>
		<div class="footer-pager">
			<div class="PageNum">
				<#import "../pagination.ftl" as com>
				<#--前一个参数是总记录数，后一个参数是页面记录数-->
				<@com.pagination Request.totalCount Request.pageSize/>
			</div>
		</div>
	</div>
	<div class="do-reply-wrap">
		<textarea aria-label="快速回复" id="reply-area" placeholder="(^_^) 请遵守“龙颜社区回帖规范”谢谢！"></textarea>
		<div class="fr reply-tips">
            <div class="mb5">龙颜社区回帖规范：</div>
            一、遵纪守法，禁止发布违法乱纪的消息及言论。<br>
            二、不讨论政治、国家及地方领导人的相关话题。<br>
            三、回帖中严禁广告。<br>
            四、回帖中不要恶意诋毁他人、评价要有根有据。<br>
            五、不要使用侮辱、淫秽性词汇，拒绝人身攻击。
        </div>
        <div class="J_reply_ft" id="J_reply_ft">
            <a data-bid="${bbsLz.lz.id}" href="javascript:;" id="J_reply_quick_btn">回复</a>
            <span class="err">eeeee</span>
        </div>
	</div>
</div>
<script src="/Longyan/static/js/pages/news/bbs-detail.js" ></script>