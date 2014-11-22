<link href="/Longyan/static/css/pages/usercenter/update-pass.css" rel="stylesheet" />

<div class="param-wrap">
	<table class="pass-table">
		<tr>
			<td class="name">原密码：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="password" id="password" name="password" class="required form-control validate" placeholder="请输入旧密码"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">新密码：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="password" id="newpassword" name="newpassword" class="required form-control validate" placeholder="请输入新密码"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">确认新密码：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="password" id="renewpassword" name="renewpassword" class="required form-control validate"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
	</table>
	<div class="btn-wrap">
		<a class="btn btn-success" href="javascript:;" id="update">确认修改</a>
	</div>
	<script src="/Longyan/static/js/pages/usercenter/update-pass.js"></script>
</div>
