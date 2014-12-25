<link href="/Longyan/static/css/pages/news/bbs.css" rel="stylesheet" />
<div class="wrap">
	<div class="catalog_title clearself">
		<span>BBS</span><br>${pageTitle!''} ▪发布帖子
	</div>
	<div class="add-bbs-box">
		<div class="type-nav">
			<span class="text">请选择分类：</span>
			<select id="bbs-type">
				<option value="1" <#if type == 1>selected="selected"</#if>>健康中心</option>
			</select>
		</div>
		<div class="content-box">
			<p class="title-box"><input class="bbs-title" type="text" placeholder="请输入标题" value="${title!''}"></p>
			<div class="ueditor">
				<script id="editor" type="text/plain" style="width:975px;height:300px;"></script>
			</div>
		</div>
		<div class="btn-box">
			<button id="publish">发布</button>
			<span class="err"></span>
		</div>
	</div>
</div>
<script src="/Longyan/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="/Longyan/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="/Longyan/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript" charset="utf-8"> </script>
<script src="/Longyan/static/js/pages/news/bbs.js" ></script>
<script type="text/javascript" charset="utf-8"> 
	$(document).ready(function(){
		$.initTemplateData({
			method: '${method}',
			id: ${id},
			content: '${content}',
			status: ${status}
		});
		$.run();
	});
</script>