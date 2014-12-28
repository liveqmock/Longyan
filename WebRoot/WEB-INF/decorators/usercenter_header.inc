<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="expires" CONTENT="0">
	<title>龙颜集团--${pageTitle!""}</title>
	<link href="/Longyan/static/css/pages/usercenter/usercenter-common.css" rel="stylesheet" />
	<link href="/Longyan/static/css/pages/pager.css" rel="stylesheet" />
	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
</head>
<body>
	<!-- 顶部登录信息 -->
	<div class="page-top-bar">
		<div class="center-wrap">
			<a href="/Longyan" class="logo">
				<img alt="logo" src="/Longyan/static/img/pages/common/nav-logo.png">
			</a>
		</div>
	</div>
	<!-- 页面内容区域 -->
	<div class="page-main">
		<div class="body">
			<!-- 左侧导航栏 -->
			<div class="aside">
				<div class="user-card">  
					<a href="/Longyan/usercenter/userinfo"> 
						<img class="user-avatar" src="/Longyan/static/img/pages/usercenter/user-img.jpg" alt="${customer_name!''}" /> 
					</a> 
					<div class="user-info"> 
						<span class="user-uname">${customer_name!''}</span>    
					</div> 
				</div>
				<ul class="uc-nav">  
					<li><a class="<#if dim == 'userinfo'>current</#if>" href="/Longyan/usercenter/userinfo">个人资料</a></li> 
					<li><a class="<#if dim == 'password'>current</#if>" href="/Longyan/usercenter/modify-pass">密码修改</a></li> 
					<li><a class="<#if dim == 'order'>current</#if>" href="/Longyan/usercenter/my-order">我的订单</a></li> 
				</ul>
			</div>
			<!-- 左侧导航栏结束 -->
			<!-- 页面显示区域 -->
			<div class="page-detail">
				<div class="detail-bg"></div>
				<div class="mod-setting-profile">
					<div class="setting-profile-title">${pageTitle!""}</div>
					<div class="profile-content">
					