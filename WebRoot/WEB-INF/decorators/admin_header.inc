<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>欢迎访问龙颜后台系统</title>

	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
	<!--<script src="/Longyan/static/js/lib/r.js" ></script>-->
	<script src="/Longyan/static/js/lib/bootstrap.min.js" ></script>
	<script src="/Longyan/static/js/lib/jquery.dataTables.js" ></script>
	<script src="/Longyan/static/js/lib/jquery.dataTables.tableTools.js" ></script>
	<script src="/Longyan/static/js/lib/jquery-ui.min.js" ></script>
	<script src="/Longyan/static/js/lib/jquery.form.js" ></script>
	<script src="/Longyan/static/js/admin/main.js" ></script>
	
	<link href="/Longyan/static/css/lib/bootstrap.min.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/bootstrap-theme.min.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/bootstrap-switch.min.css" rel="stylesheet"/>
	<link href="/Longyan/static/css/lib/jquery.dataTables.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/jquery.dataTables.tableTools.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/jquery-ui.css" rel="stylesheet" />
	<link href="/Longyan/static/css/admin/data-table.css" rel="stylesheet" />
	<link href="/Longyan/static/css/admin/main.css" rel="stylesheet" />
</head>
<body>
	<div class="navbar navbar-inverse avbar-static-top" id="J-top-nav" role="navigation">
		<div class="navbar-header">
			 <a class="navbar-brand" href="/Longyan/admin/filter/main">龙颜集团后台</a>
		</div>
		<div class="user-bar"> 您好，<a class="user-info" href="/Longyan/admin/filter/main">${username!""}</a> <a class="logout" href="/Longyan/admin/logout">退出</a> </div>
	</div>
	<nav class="navbar navbar-inverse" id="J-top-nav1"></nav>
	<div class="container" id="J-container">
		<div class="row" id="J-center">
			<!-- 左侧导航栏 -->
			<div class="col-md-2 col-xs-4" id="J-left-nav">
				<#if right == 0>
					<a href="/Longyan/admin/filter/permission" class="list-group-item left-nav-item <#if dim == 'permission'>active</#if>">权限管理</a>
					<a href="/Longyan/admin/filter/email" class="list-group-item left-nav-item <#if dim == 'email'>active</#if>">邮件管理</a>
					<a href="/Longyan/admin/filter/message" class="list-group-item left-nav-item <#if dim == 'message'>active</#if>">留言管理</a>
					<a href="/Longyan/admin/filter/employee" class="list-group-item left-nav-item <#if dim == 'employee'>active</#if>">员工管理</a>
				</#if>
				<#if right == 0 || right == 3>
					<a href="/Longyan/admin/filter/customer" class="list-group-item left-nav-item <#if dim == 'customer'>active</#if>">会员管理</a>
				</#if>
				<#if right == 0 || right == 2 || right == 3>
					<a href="/Longyan/admin/filter/order" class="list-group-item left-nav-item <#if dim == 'order'>active</#if>">订单管理</a>
				</#if>
				<#if right == 0 || right == 1 || right == 2 || right == 3>
					<div class="list-group">		
						<a class="list-group-item left-nav-item parent-active glyphicon <#if dim == 'column' || dim == 'content' || dim == 'friendLinks' || dim == 'contact'>glyphicon-minus<#else>glyphicon-plus</#if> system-setting" data-toggle="collapse" data-parent="" href="#menu_system">系统设置</a>
						<#if permission?index_of('column') != -1>
							<a href="/Longyan/admin/filter/column" class="list-group-item left-nav-item system-item <#if dim != 'column' && dim != 'content' && dim != 'friendLinks' && dim != 'contact'>nav-hidden</#if> <#if dim == 'column'>active</#if>">栏目管理</a>
						</#if>
						<#if permission?index_of('content') != -1>
							<a href="/Longyan/admin/filter/content" class="list-group-item left-nav-item system-item <#if dim != 'column' && dim != 'content' && dim != 'friendLinks' && dim != 'contact'>nav-hidden</#if> <#if dim == 'content'>active</#if>">内容管理</a>
						</#if>
						<#if permission?index_of('friendLinks') != -1>
							<a href="/Longyan/admin/filter/friendLinks" class="list-group-item left-nav-item system-item <#if dim != 'column' && dim != 'content' && dim != 'friendLinks' && dim != 'contact'>nav-hidden</#if> <#if dim == 'friendLinks'>active</#if>">友情链接</a>
						</#if>
						<#if permission?index_of('contact') != -1>
							<a href="/Longyan/admin/filter/contact" class="list-group-item left-nav-item system-item <#if dim != 'column' && dim != 'content' && dim != 'friendLinks' && dim != 'contact'>nav-hidden</#if> <#if dim == 'contact'>active</#if>">客服管理</a>
						</#if>					
					</div>
				</#if>
			</div>
			<!-- 左侧导航栏结束 -->
		