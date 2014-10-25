<link href="/Longyan/static/css/admin/friendLinks.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="friendLinks-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">友情链接管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:;" id="add-friendLinks">新增</a>
					<a class="btn btn-danger" href="javascript:;" id="del-friendLinks">删除</a>
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
	<div class="friendLinks-info-pop">
		<div class="friendLinks-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>客服信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">名称</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="name" name="name" class="required form-control validate" placeholder="请输入链接名称"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">链接URL</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="url" name="url" class="required form-control validate" placeholder="请输入链接URL"/></td>
						<td class="err-lable"></td>
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
<script src="/Longyan/static/js/admin/friendLinks/friendLinks.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>