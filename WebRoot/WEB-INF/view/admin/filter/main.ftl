<div class="col-xs-10 col-md-10 col-xs-8" id="main-page-content">
	<div class="row welcome-tip">
		<div class="col-md-12">
		  欢迎使用龙颜后台系统
		</div>
	</div>
	<div class="row login-info">
		<div class="col-md-12">
			<pre>
登录地点：${addr!''}
登录时间：${time!''}
登录IP：${ip!''}
员工姓名：${employee.name!''}
联系电话：${employee.telephone!''}
公司邮箱：${employee.email!''}
权限级别：${role!''}
性别：${employee.sex!''}
籍贯：${employee.province!''}
			</pre>
		</div>
		<div class="col-md-12 ">
			<div class="update-wrap">
				<a class="modify-info" href="javascript:void(0);" status="0">展开修改用户个人信息</a>
				<div class="table-box">
					<a class="modify-pass" href="javascript:void(0);">修改密码</a>
					<table class="userinfo-table">
						<tr>
							<td class="name">用户名：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="name" name="name" class="required form-control validate" value="${employee.name!''}" placeholder="请输入用户名"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">邮箱：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="email" name="email" class="required form-control validate email" value="${employee.email!''}" placeholder="xxx@xx.com"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">身份证：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="id_card" name="id_card" class="required form-control validate" value="${employee.id_card!''}" disabled/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">联系电话：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="telephone" name="telephone" class="required form-control validate" value="${employee.telephone!''}"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">籍贯：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="text" id="province" name="province" class="required form-control validate" value="${employee.province!''}"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">性别：</td>
							<td class="star">*</td>
							<td class="input-wrap">
								<input type="radio" name="sex" class="validate" value="女" <#if employee.sex == '女'>checked="checked"</#if>>女
								<input type="radio" name="sex" class="validate" value="男" <#if employee.sex == '男'>checked="checked"</#if>>男
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">生日：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="birthday" name="birthday" class="form-control validate" value="${employee.birthday!''}"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">家庭住址：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="address" name="address" class="form-control validate" value="${employee.address!''}"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
						<tr>
							<td class="name">qq：</td>
							<td class="star"></td>
							<td class="input-wrap">
								<input type="text" id="qq" name="qq" class="form-control validate" value="${employee.qq!''}"/>
								<span class="err-wrap"></span>
							</td>
						</tr>
					</table>
					<div class="btn-wrap">
						<a class="btn btn-success" href="javascript:void(0);" id="save">保存</a>
					</div>
				</div>
			<div>
		</div>
	</div>
</div>

<div class="pass-wrap-pop">
	<div class="pass-wrap-bg"></div>
	<div class="pass-content-wrap">
		<table class="pass-table">
			<tr>
				<td class="name">原密码：</td>
				<td class="star">*</td>
				<td class="input-wrap">
					<input type="password" id="password" name="password" class="pass-required form-control validate" placeholder="请输入旧密码"/>
					<span class="err-wrap"></span>
				</td>
			</tr>
			<tr>
				<td class="name">新密码：</td>
				<td class="star">*</td>
				<td class="input-wrap">
					<input type="password" id="newpassword" name="newpassword" class="pass-required form-control validate" placeholder="请输入新密码"/>
					<span class="err-wrap"></span>
				</td>
			</tr>
			<tr>
				<td class="name">确认新密码：</td>
				<td class="star">*</td>
				<td class="input-wrap">
					<input type="password" id="renewpassword" name="renewpassword" class="pass-required form-control validate"/>
					<span class="err-wrap"></span>
				</td>
			</tr>
		</table>
		<div class="btn-wrap">
			<a class="btn btn-success pass-btn" href="javascript:void(0);" id="update">确认修改</a>
			<a class="btn btn-success pass-btn" href="javascript:void(0);" id="cancel">取消</a>
		</div>
	</div>
</div>

<script src="/Longyan/static/js/lib/jquery-ui.min.js" ></script>
<script src="/Longyan/static/js/admin/main/main.js"></script>
<script src="/Longyan/static/js/admin/main/update-pass.js"></script>