<link href="/Longyan/static/css/admin/order.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="order-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">订单系统</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<div class="tool-bar">
					<a class="btn btn-success" href="javascript:void(0);" id="add-order">新增</a>
					<span class="start-tip">开始时间：</span>
					<input id="start-date" type="text" class="date-pick form-control" value=""/>
					<span class="end-tip">结束时间：</span>
					<input id="end-date" type="text" class="date-pick form-control" value=""/>
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
	<div class="order-info-pop">
		<div class="order-info-pop-bg"></div>
		<div class="bd">
			<div class="hd">
				<span>添加订单</span>
				<button type="button" class="close" id="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
			<div class="content-bd">
				<table class="validate-table">
					<tr>
						<td class="right-align">会员名</td>
						<td class="star">*</td>
						<td class="input-wrap sug-wrap">
							<input type="text" id="customer_name" name="customer_name" class="required form-control validate customer_name" customerid="" placeholder="请输入会员名"/>
							<div class="customer-sug">
								<ul class="item-list">
									<li class="customer-item">Tracy</li>
									<li class="customer-item">Lee</li>
									<li class="customer-item">Freas</li>
								</ul>
							</div>
						</td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">商品名称</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="goods_name" name="goods_name" class="required form-control validate" placeholder="请输入商品名称"/></td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">商品单价</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="goods_price" name="goods_price" class="required float form-control validate" placeholder="请输入商品单价"/>元</td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">商品个数</td>
						<td class="star">*</td>
						<td class="input-wrap">
							<input type="text" id="count" name="count" class="required int form-control validate" placeholder="请输入商品个数"/>
						</td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">商品折扣</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="discount" name="discount" class="form-control float validate" value="10" />折</td>
						<td class="err-lable">aaaa</td>
					</tr>
					<tr>
						<td class="right-align">订单总价</td>
						<td class="star">*</td>
						<td class="input-wrap"><input type="text" id="price" name="price" class="form-control validate" value="0" disabled />元</td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">商品详情</td>
						<td class="star"></td>
						<td class="input-wrap">
							<textarea id="goods_info" class="validate"></textarea>
						</td>
						<td class="err-lable"></td>
					</tr>
					<tr>
						<td class="right-align">备注</td>
						<td class="star"></td>
						<td class="input-wrap"><textarea id="remark" class="validate"></textarea></td>
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
<script src="/Longyan/static/js/admin/order/order.js" ></script>
<script src="/Longyan/static/js/admin/input-validate.js" ></script>
<script src="/Longyan/static/js/admin/table.js" ></script>