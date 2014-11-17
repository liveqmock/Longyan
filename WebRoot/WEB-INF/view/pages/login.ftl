<!DOCTYPE html>
<html>
  <head>
    <title>龙颜集团--用户登录</title>
    <meta http-equiv="keywords" content="龙颜,连锁超市,社区医院">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/Longyan/static/css/pages/login.css">
  </head>
  <body>
  	<#--
    <div class="top">
      <div class="login-center">
        <a href="/Longyan" class="logo page-icon"></a>
      </div>
    </div>
    -->
    <div class="login-center">
      <div class="login-content">
        <div class="logo-wrap">
        	<img src="/Longyan/static/img/pages/login-img.jpg" alt="logo">
        </div>
        <div class="login-box">
          <div class="hd">
            <p class="tip">登录</p>
            <p class="login-ret">登录失败</p>
          </div>
          <div class="bd">
            <p>
              <span class="uname icon"></span>
              <input type="text" name="username" id="username" placeholder="用户名"/>
            </p>
            <p>
              <span class="pass icon"></span>
              <input type="password" name="password" id="password" placeholder="密码"/>
            </p>
            <a href="javascript:;" id="commit">立即登录</a>
          </div>
          <div class="ft">
            还没有账号？
            <a href="/Longyan/join" class="goto-join">点我注册</a>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
    <p class="right-wrap">
      @2014 版权所有 龙颜集团  long yan group Co.,Ltd. 地址：重庆市南岸区江南大道1610 电话：023-58881002
    </p>
  </div>
  <script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
  <script src="/Longyan/static/js/pages/login.js" ></script>
  </body>
</html>
