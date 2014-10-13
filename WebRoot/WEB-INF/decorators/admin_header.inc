<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>欢迎访问龙颜后台系统</title>

	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" defer></script>
	<script src="/Longyan/static/js/lib/bootstrap.min.js" defer></script>
	<script src="/Longyan/static/js/lib/jquery.dataTables.js" defer></script>
	<script src="/Longyan/static/js/lib/jquery.dataTables.tableTools.js" defer></script>
	<link href="/Longyan/static/css/lib/bootstrap.min.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/bootstrap-theme.min.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="/Longyan/static/css/lib/bootstrap-switch.min.css"/>
	<link href="/Longyan/static/css/lib/jquery.dataTables.css" rel="stylesheet" />
	<link href="/Longyan/static/css/lib/jquery.dataTables.tableTools.css" rel="stylesheet" />
	<link href="/Longyan/static/css/admin/main.css" rel="stylesheet" />
</head>
<body>
	<div class="navbar navbar-inverse avbar-static-top" id="J-top-nav" role="navigation">
		<div class="navbar-header">
			 <a class="navbar-brand" href="/Longyan/admin/filter/main">龙颜集团后台</a>
		</div>
		<div class="user-bar"> 您好，${username} <a href="/Longyan/admin/logout">退出</a> </div>
	</div>
	<div class="left-nav">左侧菜单</div>
	<div class=content>
	${menu}
