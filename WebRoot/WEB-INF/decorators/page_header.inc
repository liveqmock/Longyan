<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>龙颜集团--${pageTitle!""}</title>
	<link href="/Longyan/static/css/pages/page-common.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/jquery.img-scroller.css" rel="stylesheet" />
	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
	<script src="/Longyan/static/js/lib/jquery.img-scroller.js" ></script>
	<script src="/Longyan/static/js/pages/common/common.js" ></script>
</head>
<body>
	<!-- 顶部登录信息 -->
	<div class="page-top-bar">
		<div class="center-wrap">
			<div class="info-wrap fl">
				<span class="hot-line-icon page-icon"></span>
				<span class="hot-line-txt">咨询热线：400 800 6833</span>
				<span class="mail-icon page-icon"></span>
				<span class="mail-txt">咨询邮箱：sales@longyanb.com</span>
			</div>
			<div class="login-wrap fr">
				<#if customer_id == -1> <#-- 未登录 -->
					<a href="/Longyan/login" id="btn-login">登录</a>
					<a href="/Longyan/join" target="_blank" id="btn-register">注册</a>
				<#else>
					<a href="/Longyan/usercenter/userinfo" target="_blank" id="btn-user-center" data-cid="${customer_id}">${customer_name}</a>
					<a href="javascript:;" target="_blank" id="btn-logout">退出</a>
				</#if>
			</div>
		</div>
	</div>
	<!-- 导航条区域 -->
	<div class="page-nav-bar">
		<div class="center-wrap">
			<a href="/Longyan/" class="logo page-icon"></a>
			<ul class="nav-list">
				<li>
					<a href="/Longyan/pages/${pageCode}" class="<#if dim == 'index'>active</#if>">主页</a>
				</li>
				<#if columns?exists && columns?size!=0>
					<#list columns as column>
						<li>
							<a href="/Longyan/pages/${pageCode}/${column.code}" class="<#if dim == column.code>active</#if>">${column.name}</a>
						</li>
					</#list>
				</#if>
			</ul>
		</div>
	</div>
	<!-- 图片轮播区域 -->
	<div class="page-scroller-wrap"></div>
	<!-- 页面内容区域 -->
	<div class="page-main">
