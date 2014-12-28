<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
<link href="/Longyan/static/css/pages/news/mybbs.css" rel="stylesheet" />
<div class="wrap">
	<div class="catalog_title clearself">
		<span>${pageCode?cap_first}</span><br>${pageTitle!''} ▪ ${column.name!''}
	</div>
	<div class="top-bar">
		<a class="add-bbs" href="/Longyan/add-bbs?dim=mybbs">发帖</a>
		<div class="top-pager">
			<div class="PageNum">
				<#import "../pagination.ftl" as com>
				<#--前一个参数是总记录数，后一个参数是页面记录数-->
				<@com.pagination Request.totalCount Request.pageSize/>
			</div>
		</div>
	</div>
	<div class="bbs-list-wrap">
		<ul>
			<#if bbsList?exists && bbsList?size!=0>
				<#list bbsList as bbs>
					<li class="bbs-item">
						<p class="title-link">
							<a href="/Longyan/pages/bbs/${bbs.id}?dim=${columnCode}">${bbs.title}</a>
						</p>
						<p class="info">
							<span class="customer gray">楼主：${customer.username}</span>
							<span class="gray">更新时间：${bbs.utime?string("yyyy-MM-dd HH:mm:ss")}</span>
							<span class="bbs-status gray">帖子状态：
								<#if bbs.status == 1>
									<span class="yellow">审核中</span>
								<#elseif bbs.status == 2>
									<span class="green">审核通过</span>
								<#elseif bbs.status == 3>
									<span class="red">审核不通过</span>
								<#elseif bbs.status == 4>
									<span class="yellow">已封帖</span>
								<#elseif bbs.status == 5>
									<span class="red">已屏蔽</span>
								</#if>
							</span>
							
							<span class="bbs-op">
								<#if bbs.status == 2>
									<a class="update" href="/Longyan/pages/update-bbs?id=${bbs.id}">修改</a>
									<a class="close-bbs" href="javascript:void(0);" data-id="${bbs.id}" data-status="${bbs.status}">封贴</a>
								<#elseif bbs.status == 3>
									<a class="update" href="/Longyan/paes/update-bbs?id=${bbs.id}">修改</a>
								</#if>
							</span>
						</p>
						
						<span class="view-count">
							浏览
							<em>${bbs.view_count}</em>
						</span>
						<span class="reply-count">
							回复
							<em>${bbs.reply_count}</em>
						</span>
					</li>
				</#list>
			<#else>
				<p>还没有任何帖子哦，赶快去发帖吧~</p>
			</#if>
		</ul>
	</div>
	<div class="footer-bar">
		<a class="add-bbs" href="/Longyan/add-bbs?dim=mybbs">发帖</a>
		<div class="footer-pager">
			<div class="PageNum">
				<#import "../pagination.ftl" as com>
				<#--前一个参数是总记录数，后一个参数是页面记录数-->
				<@com.pagination Request.totalCount Request.pageSize/>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/static/js/pages/news/mybbs.js" ></script>