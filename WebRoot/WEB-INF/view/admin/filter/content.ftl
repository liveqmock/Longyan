<link href="/Longyan/static/css/admin/content.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="content-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">内容管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:void(0);" id="add-content">新增</a>
					<a class="btn btn-danger" href="javascript:void(0);" id="del-content">删除</a>
					<span class="column-tip">选择栏目：</span>
					<select id="column-form" class="form-control">
					</select>
					<a class="btn btn-default" href="javascript:void(0);" id="search">查询</a>
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
	<div class="content-info-pop">
		<div class="content-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>内容信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">选择栏目</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<select id="column_id" class="required form-control validate" name="column_id">
							</select>
						</td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">内容名称</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="title" name="title" class="required form-control validate" placeholder="请输入内容名称"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">内容编码</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="code" name="code" class="required form-control validate" placeholder="请输入内容编码"/></td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">内容简介</td>
						<td class="star">*</td>
						<td class="input-wrap"><textarea id="describe" name="describe" class="form-control validate"></textarea></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">图片</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<div class="pic-wrap">
								<ul id="img-list" class="validate" flag="img-wrap">
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第一张图</a>
										<a href="javascript:void(0);" class="img-item-del" id="1">删除</a>
									</li>
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第二张图</a>
										<a href="javascript:void(0);" class="img-item-del" id="2">删除</a>
									</li>
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第三张图</a>
										<a href="javascript:void(0);" class="img-item-del" id="3">删除</a>
									</li>
								</ul>
								
							</div>
							<input type="file" name="file" id="img_url">
							<a href="javascript:void(0);" id="upload" class="btn btn-default">上传</a>
							<p id="upload-result">正在上传...</p>
						</td>
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
<script src="/Longyan/static/js/admin/content/content.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>
<script src="/Longyan/static/js/lib/ajaxfileupload.js" ></script>