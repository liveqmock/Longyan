<link href="/Longyan/ueditor/themes/default/css/ueditor.css" rel="stylesheet" type="text/css" />
<link href="/Longyan/static/css/admin/template.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="template-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">模板管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<input type="text" name="filename" value="${filename}" id="filename"/>
					<a class="btn btn-success" href="javascript:;" id="publish-template">发布</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<div id="ueditor-wrap"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="/Longyan/ueditor/ueditor.all.js" type="text/javascript"></script>
<script src="/Longyan/static/js/admin/template/template.js" ></script>

