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
					模板文件名：
					<input type="text" name="filename" value="${filename}" id="filename"/>
					<#if path != ''>
						模板路径：<span class="path">${path}</span>
					</#if>
					<span class="err">文件名已存在</span>
					<a class="btn btn-success" href="javascript:void(0);" id="publish-template">发布</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<script id="editor" type="text/plain" style="width:990px;height:300px;"></script>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="/Longyan/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="/Longyan/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript" charset="utf-8"> </script>
<script src="/Longyan/static/js/admin/template/template.js" ></script>
<script type="text/javascript" charset="utf-8"> 
	$(document).ready(function(){
		$.initTemplateData({
			dim: '${dim}',
			id: ${id},
			content: '${template_content}'
		});
		$.run();
	});
</script>


