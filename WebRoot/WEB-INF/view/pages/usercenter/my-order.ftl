<link href="/Longyan/static/css/pages/usercenter/my-order.css" rel="stylesheet" />

<div class="my-order-wrap">
	<table class="my-order-list">
		<thead>
			<tr>
				<td class="order-num-h">订单类型/订单号</td>
				<td class="order-name-h">订单商品</td>
				<td class="order-fee-h">订单金额(单位：元)</td>
				<td class="order-time-h">订单时间</td>
				<td class="order-remark-h">订单备注</td>
			</tr>
		</thead>
		<tbody>
			<#if orderList?exists && orderList?size!=0>
				<#list orderList as order>
					<tr>
						<td>${order.code}</td>
						<td>${order.goods_name}</td>
						<td>${order.price}</td>
						<td>${order.ctime}</td>
						<td class="order-op-b">${order.remark!''}</td>
					</tr>
				</#list>
			<#else>
				你还没有订单信息~
			</#if>
		</tbody>
	</table>
	<div class=PageNum>
		<#import "../filter/pagination.ftl" as com>
		<#--前一个参数是总记录数，后一个参数是页面记录数-->
		<@com.pagination Request.totalCount Request.pageSize/>
	</div>
</div>
