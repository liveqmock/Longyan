<!DOCTYPE html>
<html>
  <head>
    <title>龙颜集团--用户注册</title>
    <meta http-equiv="keywords" content="龙颜,连锁超市,社区医院">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/Longyan/static/css/pages/join.css">
    <link href="/Longyan/static/css/lib/bootstrap.min.css" rel="stylesheet" />
    <link href="/Longyan/static/css/lib/jquery-ui.css" rel="stylesheet" />
  </head>
  <body>
  	<div class="top">
  		<div class="join-center">
  			<a href="/Longyan/" class="logo">
				<img alt="logo" src="/Longyan/static/img/pages/common/nav-logo.png">
			</a>
  		</div>
  	</div>
  	
	<div class="join-center">
		<div class="join-content">
			<div class="join-content-top">
				<p class="h4_f">会员注册</p>
				<p class="sub_h4_f">
					如果您已经拥有龙颜会员账号，则可
					<a href="/Longyan/login" class="login">在此登录</a>
				</p>
				<div class="param-wrap">
					<table class="register-table">
						<tr>
							<td class="name">用户名：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="username" name="username" class="required form-control validate" placeholder="请输入用户名"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">真是姓名：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="realname" name="realname" class="required form-control validate" placeholder="请输入真实姓名"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">登录密码：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="password" id="password" name="password" class="required form-control validate" placeholder="请输入登录密码"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">重复密码：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="password" id="repassword" name="repassword" class="required form-control validate" placeholder="重复密码"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">邮箱：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="email" name="email" class="required form-control validate email" placeholder="xxx@xx.com"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">联系电话：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="telephone" name="telephone" class="required form-control validate"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">性别：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="radio" name="sex" class="validate" value="女" checked="checked">女
								<input type="radio" name="sex" class="validate" value="男">男
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">生日：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="birthday" name="birthday" class="form-control validate"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">家庭住址：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="address" name="address" class="form-control validate"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">qq：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="qq" name="qq" class="form-control validate" />
								<span class="err-wrap"></span>
							</td>
						</tr>
					</table>
					<div class="btn-wrap">
						<a class="btn btn-success" href="javascript:;" id="sure">立即注册</a>
					</div>
				</div>
			</div>
			<div class="join-content-bottom"></div>
		</div>
	</div>
	<div class="footer">
		<p class="right-wrap">
			@2015 版权所有 龙颜集团  long yan group Co.,Ltd. 地址：重庆市南岸区江南大道1610 电话：023-58881002
		</p>
	</div>
	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
	<script src="/Longyan/static/js/lib/jquery-ui.min.js" ></script>
	<script src="/Longyan/static/js/pages/join.js" ></script>
  </body>
</html>
