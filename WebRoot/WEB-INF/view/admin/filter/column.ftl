<link href="/Longyan/static/css/admin/column.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="column-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">栏目管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:;" id="add-column">新增</a>
					<a class="btn btn-danger" href="javascript:;" id="del-column">删除</a>
					<span class="site-tip">选择站点：</span>
					<select id="site-form" class="form-control">
						<option value="0" selected="selected">全部</option>
						<option value="1">集团战略</option>
						<option value="2">新闻中心</option>
						<option value="3">支柱产业</option>
					</select>
					<a class="btn btn-default" href="javascript:;" id="search">查询</a>
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
	<div class="column-info-pop">
		<div class="column-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>栏目信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">选择站点</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<select id="site_id" class="form-control">
								<option value="1">集团战略</option>
								<option value="2">新闻中心</option>
								<option value="3">支柱产业</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="right-align">栏目名称</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="name" name="name" class="required form-control validate" placeholder="请输入栏目名称"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">栏目编码</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="code" name="code" class="required form-control validate" placeholder="请输入栏目编码"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">banner图片</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<input type="file" name="img_url" id="img_url" class="validate">
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
<script src="/Longyan/static/js/admin/column/column.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>