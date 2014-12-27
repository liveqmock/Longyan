<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
<link href="/Longyan/static/css/pages/news/activity.css" rel="stylesheet" />
<div class="wrap">
	<div class="catalog_title clearself">
		<span>${pageCode?cap_first}</span><br>${pageTitle!''} ▪ ${column.name!''}
	</div>
	<div class="top-bar">
		<#if customer_id == -2>
			<a class="add-bbs" href="/Longyan/add-bbs?dim=activity">发帖</a>
		</#if>
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
			<#if dataList?exists && dataList?size!=0>
				<#list dataList as data>
					<li class="bbs-item">
						<p class="title-link">
							<a href="/Longyan/pages/bbs/${data.bbs.id}?dim=${columnCode}">${data.bbs.title}</a>
						</p>
						<p class="info">
							<span class="customer gray">楼主：${data.customer.username}</span>
							<span class="gray">更新时间：${data.bbs.utime?string("yyyy-MM-dd HH:mm:ss")}</span>
						</p>
						
						<span class="view-count">
							浏览
							<em>${data.bbs.view_count}</em>
						</span>
						<span class="reply-count">
							回复
							<em>${data.bbs.reply_count}</em>
						</span>
					</li>
				</#list>
			<#else>
				<h1>目前还没有任何活动哦，主办方已经在策划了哦，请静候佳音~</h1>
			</#if>
		</ul>
	</div>
	<div class="footer-bar">
		<#if customer_id == -2>
			<a class="add-bbs" href="/Longyan/add-bbs?dim=activity">发帖</a>
		</#if>
		<div class="footer-pager">
			<div class="PageNum">
				<#import "../pagination.ftl" as com>
				<#--前一个参数是总记录数，后一个参数是页面记录数-->
				<@com.pagination Request.totalCount Request.pageSize/>
			</div>
		</div>
	</div>
</div>