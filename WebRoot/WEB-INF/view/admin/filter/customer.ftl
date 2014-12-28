<link href="/Longyan/static/css/admin/customer.css" rel="stylesheet" />

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
					<a class="btn btn-success" href="javascript:void(0);" id="add-customer">新增</a>
					<a class="btn btn-danger" href="javascript:void(0);" id="del-customer">删除</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<table id="table-container" class="DataView"></table>
				</div>
			</div>
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
				<table class="validate-table">
					<tr>
						<td class="right-align">用户名</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="username" name="username" class="required form-control validate" placeholder="请输入用户名"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">真实姓名</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="realname" name="realname" class="required form-control validate" placeholder="请输入真实姓名"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">电话号码</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="telephone" name="telephone" class="required digits form-control validate" placeholder="请输入电话号码"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">性别</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<input type="radio" name="sex" class="validate" value="女">女
							<input type="radio" name="sex" class="validate" value="男" >男
						</td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">生日</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="birthday" name="birthday" class="form-control validate"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">家庭住址</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="address" name="address" class="form-control validate"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">qq</td>
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="qq" name="qq" class="form-control validate" /></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">E-mail</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="email" name="email" class="required email form-control validate"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
				</table>
				<div class="btn-wrap">
					<a class="btn btn-success" href="javascript:void(0);" id="sure">提交</a>
					<a class="btn btn-default" href="javascript:void(0);" id="cancel">取消</a>
				</div>
				<label id="result">添加成功</label>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/static/js/admin/customer/customer.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>