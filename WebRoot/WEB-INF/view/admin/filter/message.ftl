<link href="/Longyan/static/css/admin/message.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="message-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">留言管理</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-danger" href="javascript:;" id="del-message">删除</a>
					<span class="start-tip">开始时间：</span>
					<input id="start-date" type="text" class="date-pick form-control" value=""/>
					<span class="end-tip">结束时间：</span>
					<input id="end-date" type="text" class="date-pick form-control" value=""/>
					<span class="status-tip">回复状态：</span>
					<select id="reply-status" class="form-control">
						<option value="-1" selected="selected">全部</option>
						<option value="0">未回复</option>
						<option value="1">已回复</option>
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
	<div class="message-info-pop">
		<div class="message-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>留言信息</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<div class="message-wrap">
					<span class="customer-say">tracy说：</span>
					<span class="customer-content">这个系统做得真是太好用了这个系统做得真是太好用了这个系统做得真是太好用了这个系统做得真是太好用了这个系统做得真是太好用了</span>
				</div>
				<div class="reply-wrap">
					<textarea id="reply-content"></textarea>
					<div class="reply-info">回复人：Tracy  时间：2014-9-23</div>
				</div>
				<div class="btn-wrap">
					<a class="btn btn-success" href="javascript:;" id="sure">回复</a>
					<a class="btn btn-default" href="javascript:;" id="cancel">取消</a>
				</div>
				<label id="result">添加成功</label>
			</div>
		</div>
	</div>
</div>

<script src="/Longyan/static/js/admin/message/message.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>
