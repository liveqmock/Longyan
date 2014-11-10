<#macro pagination totalCount pageSize>
    <#--声明一个函数transform 转换uri,在新的uri上pager_offset参数  -->
	<#assign transform = "com.longyan.util.TransformURI"?new()>
	<#--声明一个函数，得到当前页码-->
	<#assign pagerOffset = "com.longyan.util.PagerOffset"?new()>
	<#--声明一个函数，根据传入的totalCount,pageSize得到总页数-->
	<#assign pagerCount = "com.longyan.util.PageCount"?new()>
	<#assign pageCount=pagerCount(totalCount,pageSize)>
	<#--得到当前的URI和请求参数,得到当前的页码-->
	<#if request.queryString?exists>
		<#assign uri=request.requestURI+"?"+request.queryString>
		<#assign pageIndex=pagerOffset(uri)>
		<#assign new_uri=transform(uri)>
	<#else>
		<#assign uri=request.requestURI>
		<#assign pageIndex=pagerOffset(uri)>
		<#assign new_uri=transform(uri)>
	</#if>
	<#if (pageIndex>pageCount)>
		<#assign pageIndex=pageCount>
	</#if>
    <#if (pageIndex>1)>
        <a href="${new_uri+1}" title="首页">&lt;&lt;</a>
    </#if>
    <#--如果前面页数过多,显示"..."-->
    <#if (pageIndex>5)>
        <#assign prevPages=pageIndex-9>
        <#if prevPages lt 1>
        	<#assign prevPages=1>
        </#if>
        <#assign start=pageIndex-4>
        <a href="${new_uri+prevPages}" title="向前5页">...</a>
    <#else>
        <#assign start=1>
     </#if>
    <#-- 显示当前页附近的页-->
    <#assign end=pageIndex+4>
    <#if (end>pageCount)>
    	<#assign end=pageCount>
    </#if>
    <#list start..end as index>
    	<#if pageIndex==index>
    		<b>${index}</b>
    	<#else>
    		<a href="${new_uri+index}">${index}</a>
    	</#if>
    </#list>
    <#--如果后面页数过多,显示"...":-->
    <#if (end lt pageCount)>
    	<#assign end=end+5>
    	<#if (end>pageCount)>
    		<#assign end=pageCount>
    	</#if>
    	<a href="${new_uri+end}" title="向后5页">...</a>
    </#if>
    <#-- 显示"下一页":-->
    <#if (pageIndex lt pageCount)>
    	<a href="${new_uri+pageCount}" title="末页">&gt;&gt;</a>
    </#if>
</#macro>
