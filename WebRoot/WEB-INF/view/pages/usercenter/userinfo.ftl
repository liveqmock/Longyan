<link href="/Longyan/static/css/pages/usercenter/userinfo.css" rel="stylesheet" />
<link href="/Longyan/static/css/lib/jquery-ui.css" rel="stylesheet" />

<div class="param-wrap">
	<table class="userinfo-table">
		<tr>
			<td class="name">用户名：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="text" id="username" name="username" class="required form-control validate" value="${customer.username}" placeholder="请输入用户名"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">真是姓名：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="text" id="realname" name="realname" class="required form-control validate" value="${customer.realname}" placeholder="请输入真实姓名" disabled/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">邮箱：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="text" id="email" name="email" class="required form-control validate email" value="${customer.email}" placeholder="xxx@xx.com" disabled/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">联系电话：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="text" id="telephone" name="telephone" class="required form-control validate" value="${customer.telephone}"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">性别：</td>
			<td class="star">*</td>
			<td class="input-wrap">
				<input type="radio" name="sex" class="validate" value="女" <#if customer.sex == '女'>checked="checked"</#if>>女
				<input type="radio" name="sex" class="validate" value="男" <#if customer.sex == '男'>checked="checked"</#if>>男
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">生日：</td>
			<td class="star"></td>
			<td class="input-wrap">
				<input type="text" id="birthday" name="birthday" class="form-control validate" value="${customer.birthday}"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">家庭住址：</td>
			<td class="star"></td>
			<td class="input-wrap">
				<input type="text" id="address" name="address" class="form-control validate" value="${customer.address}"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
		<tr>
			<td class="name">qq：</td>
			<td class="star"></td>
			<td class="input-wrap">
				<input type="text" id="qq" name="qq" class="form-control validate" value="${customer.qq}"/>
				<span class="err-wrap"></span>
			</td>
		</tr>
	</table>
	<div class="btn-wrap">
		<a class="btn btn-success" href="javascript:void(0);" id="save">保存</a>
	</div>
	<script src="/Longyan/static/js/lib/jquery-ui.min.js" ></script>
	<script src="/Longyan/static/js/pages/usercenter/userinfo.js"></script>
</div>
