<link href="/Longyan/static/css/admin/order-detail.css" rel="stylesheet" />

<div class="col-xs-10 col-md-10 col-xs-8" id="order-page-content">
	<div class="row">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li></li>
	  			<li class="active">订单管理  / 订单详情</li>
			</ol>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="row order-info">
			<div class="col-md-12">
				<p><span>订单编号：</span>${code!''}</p>
				<p><span>下单时间：</span>${ctime!''}</p>
				<p><span>购买顾客：</span>${customer_name!''}</p>
				<p><span>顾客电话：</span>${customer_phone!''}</p>
				<p><span>顾客地址：</span>${customer_add!''}</p>
				<p><span>商品名称：</span>${goods_name!''}</p>
				<p><span>商品单价：</span>${goods_price!0}元</p>
				<p><span>商品数量：</span>${count!0}</p>
				<p><span>打折力度：</span>${discount!10}折</p>
				<p><span>订单总价：</span>${price!0}元</p>
				<p><span>商品信息：</span>${goods_info!''}</p>
				<p><span>备注信息：</span>${remark!''}</p>
				<p><span>操作人员：</span>${employee_name!''}</p>
			</div>
			<a href="/Longyan/admin/filter/order" class="go-back">返回</a>
		</div>
	</div>
</div>