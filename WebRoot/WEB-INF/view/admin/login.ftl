<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>欢迎登录后台CMS</title>
		<link rel="stylesheet" type="text/css" href="/Longyan/static/css/admin/login.css">
	</head>
	<body class="loginPageBody">
		<div class="loginWrapbg">
			<div style="width: 100%; height: 300px; left: 0; top: 0; z-index: -1;"></div>
			<div class="loginCt" style="">
			<div class="welcome">欢迎登录
			龙颜后台管理系统</div>
			<div class="loginBar" id="loginBar">
				<form id="login-form" method="post">
					<div class="">
						<p class="tip">用户名或密码错误!</p>
						<div class="fieldWrap">
							<div class="lable userName">用户名：</div>
							<div class="inputWrap">
								<input name="UserName" type="text" class="inputText" id="userName" onfocus="this.select();" placeholder="输入用户名">
							</div>
						</div>

						<div class="fieldWrap">
							<div class="lable passWord">密码：</div>
							<div class="inputWrap">
								<input name="Password" type="password" class="inputText" id="password" onfocus="this.select();" placeholder="输入密码">
							</div>
						</div>

						<div id="loginBtnWrap">
							<a href="javascript:;" class="inline-block" id="loginBtn">登录</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="copyright">
			<div class="center">Copyright © 2007-2014 Longyan.com Inc. All rights reserved.龙颜集团版权所有</div>
		</div>
		<script type="text/javascript" src="/Longyan/static/js/lib/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="/Longyan/static/js/lib/jquery.MD5.js"></script>
		<script type="text/javascript" src="/Longyan/static/js/admin/login/login.js"></script>
	</body>
</html>