<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
<link href="/Longyan/static/css/pages/content.css" rel="stylesheet" />
<div class="h1">
	<div class="center-wrap">
		<div class="content-wrap style${style}">
			<div class="catalog_title_wrap">
				<div class="catalog_title clearself">
					<span>${pageCode?cap_first}</span><br>${pageTitle!''} ▪ ${column.name!''}
				</div>
			</div>
			<ul>
				<#if style == 1> <#-- 纯文本列表展示 -->
					<#if contents?exists && contents?size!=0>
						<#list contents as content>
							<li class="content-item">
								<i>▪&nbsp;</i>
								<a href="/Longyan/pages/${pageCode}/${columnCode}/content/${content.id}">${content.title}</a>
								<span class="time-wrap">${content.ctime!''}</span>
							</li>
						</#list>
					</#if>
				<#elseif style == 2> <#-- 图文混排列表展示 -->
					<#if contents?exists && contents?size!=0>
						<#list contents as content>
							<li class="content-item">
								<div class="img-wrap">
									<a href="/Longyan/pages/${pageCode}/${columnCode}/content/${content.id}">
										<img src="${content.img_url!''}"/>
									</a>
								</div>
								<div class="title-desc">
									<p class="title-link">
										<a href="/Longyan/pages/${pageCode}/${columnCode}/content/${content.id}">${content.title}</a>
									</p>
									<p class="describe">${content.describe!''}</p>
								</div>
								
								<span class="time-wrap">${content.ctime!''}</span>
							</li>
						</#list>
					</#if>
				</#if>
			</ul>
		</div>
		<div class=PageNum>
			<#import "pagination.ftl" as com>
			<#--前一个参数是总记录数，后一个参数是页面记录数-->
			<@com.pagination Request.totalCount Request.pageSize/>
		</div>
	</div>
</div>