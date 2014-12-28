<link href="/Longyan/static/css/admin/contact.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="contact-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">客服管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:void(0);" id="add-contact">新增</a>
					<a class="btn btn-danger" href="javascript:void(0);" id="del-contact">删除</a>
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
	<div class="contact-info-pop">
		<div class="contact-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>客服信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">客服名称</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="name" name="name" class="required form-control validate" placeholder="请输入客服名称"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">客服QQ</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="qq" name="qq" class="required form-control validate" placeholder="请输入客服QQ"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">客服电话</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="telephone" name="telephone" class="required form-control validate" placeholder="请输入客服电话"/></td>
						<td class="err-lable"></td>
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
<script src="/Longyan/static/js/admin/contact/contact.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>