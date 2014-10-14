<link href="/Longyan/static/css/admin/customer.css" rel="stylesheet" />
<script src="/Longyan/static/js/admin/customer/customer.js" ></script>

<div class="col-xs-10 col-md-10 col-xs-8" id="customer-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">会员管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:;" id="add-customer">新增</a>
					<a class="btn btn-danger" href="javascript:;" id="del-customer">删除</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			biaoge
		</div>
	</div>
	<div class="customer-info-pop">
		<div class="customer-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>会员信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table>
					<tr>
						<td class="right-align">用户名</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="username" class="required form-control" placeholder="请输入用户名"/></td>
					</tr>
					<tr>
						<td class="right-align">真实姓名</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="realname" class="required form-control" placeholder="请输入真实姓名"/></td>
					</tr>
					<tr>
						<td class="right-align">电话号码</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="telephone" class="required digits form-control" placeholder="请输入电话号码"/></td>
					</tr>
					<tr>
						<td class="right-align">性别</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<input type="radio" name="sex" value="0">女
							<input type="radio" name="sex" value="1">男
						</td>
					</tr>
					<tr>
						<td class="right-align">生日</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="birthday" class="form-control"/></td>
					</tr>
					<tr>
						<td class="right-align">家庭住址</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="address" class="form-control"/></td>
					</tr>
					<tr>
						<td class="right-align">qq</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="qq" class="form-control" /></td>
					</tr>
					<tr>
						<td class="right-align">E-mail</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="email" class="required email form-control"/></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>