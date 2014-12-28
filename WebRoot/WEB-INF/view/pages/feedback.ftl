<!DOCTYPE html>
<html>
  <head>
    <title>龙颜集团--用户反馈</title>
    <meta http-equiv="keywords" content="龙颜,连锁超市,社区医院">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/Longyan/static/css/pages/feedback.css">
  </head>
  <body>
  	<div class="top">
  		龙颜集团--用户投诉
  	</div>
  	<div class="tip">
  		请畅所欲言，我们会尽快回复
  	</div>
  	<div class="content-wrap">
  		<textarea id="textarea"></textarea>
  		<a href="javascript:;" id="feedback-submit">提交回复</a>
  	</div>
  	<div class="reply-list">
  		<p class="text">我的反馈</p>
  		<div class="list-box">
  			<#if feedbackList?exists && feedbackList?size!=0>
  				<#list feedbackList as feedback>
  					<div class="reply-item">
		  				<div class="message-box">
		  					<span class="time">${feedback.message.ctime}</span>
		  					<div class="message">${feedback.message.content}</div>
		  				</div>
		  				<div class="reply-box">
		  					<span class="time">${feedback.reply.ctime}</span>
		  					<div class="reply">${feedback.reply.reply_content}</div>
		  				</div>
		  			</div>
  				</#list>
  			</#if>
  		</div>
  	</div>
  	<div class="footer">
		<p class="right-wrap">
			@2015 版权所有 龙颜集团  long yan group Co.,Ltd. 地址：重庆市南岸区江南大道1610 电话：023-58881002
		</p>
	</div>
	<script src="/Longyan/static/js/lib/jquery-1.9.1.js" ></script>
	<script src="/Longyan/static/js/pages/feedback.js" ></script>
  </body>
</html>
