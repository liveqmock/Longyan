<link href="/Longyan/static/css/pages/content.css" rel="stylesheet" />
<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
<div class="h1">
	<div class="center-wrap">
		<div class="content-wrap style${style}">
			<#if contents?exists && contents?size!=0>
				<#list contents as content>
					<a href="/Longyan/pages/${pageCode}/${columnCode}/content/${content.id}">${content.title}</a>
				</#list>
			</#if>
		</div>
		<div class=PageNum>
			<#import "pagination.ftl" as com>
			<#--前一个参数是总记录数，后一个参数是页面记录数-->
			<@com.pagination Request.totalCount Request.pageSize/>
		</div>
	</div>
</div>