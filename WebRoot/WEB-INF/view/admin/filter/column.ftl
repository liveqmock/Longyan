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
						<option value="2">健康社区</option>
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
							<select id="site_id" class="required form-control validate" name="site_id">
								<option value="1">集团战略</option>
								<option value="2">健康社区</option>
								<option value="3">支柱产业</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="right-align">栏目样式</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<select id="style" class="required form-control validate" name="style">
								<option value="1">文本样式</option>
								<option value="2">图文结合</option>
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
							<div class="pic-wrap">
								<ul id="img-list" class="validate required" flag="img-wrap">
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第一张图</a>
										<a href="javascript:;" class="img-item-del" id="1">删除</a>
									</li>
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第二张图</a>
										<a href="javascript:;" class="img-item-del" id="2">删除</a>
									</li>
									<li>
										<a href="http://www.baidu.com" target="_blank" title="点击预览" class="img-item">第三张图</a>
										<a href="javascript:;" class="img-item-del" id="3">删除</a>
									</li>
								</ul>
								
							</div>
							<input type="file" name="file" id="img_url">
							<a href="javascript:;" id="upload" class="btn btn-default">上传</a>
							<p id="upload-result">正在上传...</p>
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
<script src="/Longyan/static/js/lib/ajaxfileupload.js" ></script>