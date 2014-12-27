<link href="/Longyan/static/css/admin/bbs.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="column-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">论坛管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<span class="site-tip">选择类型：</span>
					<select id="type-select" class="form-control">
						<option value="0" <#if type == 0>selected=selected</#if>>全部</option>
						<option value="1" <#if type == 1>selected=selected</#if>>健康生活</option>
						<option value="2" <#if type == 2>selected=selected</#if>>活动专区</option>
					</select>
					<span class="site-tip">帖子状态：</span>
					<select id="status-select" class="form-control">
						<option value="0" <#if status == 0>selected=selected</#if>>全部</option>
						<option value="1" <#if status == 1>selected=selected</#if>>待审核</option>
						<option value="2" <#if status == 2>selected=selected</#if>>审核通过</option>
						<option value="3" <#if status == 3>selected=selected</#if>>审核不通过</option>
						<option value="4" <#if status == 4>selected=selected</#if>>已封贴</option>
						<option value="5" <#if status == 5>selected=selected</#if>>已屏蔽</option>
					</select>
					<a class="btn btn-info" href="javascript:;" id="search">查询</a>
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
	<div class="reply-pop">
		<div class="reply-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>帖子回复</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table id="reply-table"></table>
			</div>
		</div>
	</div>
</div>
<script src="/Longyan/static/js/admin/bbs/bbs.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>
