<link href="/Longyan/static/css/admin/employee.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="employee-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">员工管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:;" id="add-employee">新增</a>
					<a class="btn btn-danger" href="javascript:;" id="del-employee">删除</a>
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
	<div class="employee-info-pop">
		<div class="employee-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>员工信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">用户名</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="name" name="name" class="required form-control validate" placeholder="请输入用户名"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">身份证号</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="id_card" name="id_card" class="required form-control validate" placeholder="请输入身份证号"/></td>
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
						<td class="star"></td>
						<td class="input-wrap"><input type="text" id="email" name="email" class="form-control validate"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">籍贯</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="province" name="province" class="required form-control validate"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">权限</td>
						<td class="star">*</td>
						<td class="input-wrap" id="right-td">
							<select type="select" id="right_level" name="right_level" class="form-control validate">
								<option value="0">0</option>
								<option value="1" selected>1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
							<a href="javascript:;" class="right-tip">权限说明</a>
							<span class="tip-content">
								0-管理员权限<br />
								1-有系统设置权限； <br />
								2-系统设置 + 订单系统； <br />
								3-系统设置 + 订单系统 + 会员管理。
							</span>
						</td>
						<td class="err-lable">aaaa</td>
					</tr>
				</table>
				<div class="btn-wrap">
					<a class="btn btn-success" href="javascript:;" id="sure">提交</a>
					<a class="btn btn-default" href="javascript:;" id="cancel">取消</a>
				</div>
				<label id="result">添加成功</label>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/static/js/admin/employee/employee.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>