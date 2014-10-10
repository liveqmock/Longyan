<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>先富外卖管理平台</title>

<script src="/static/js/jquery-1.9.1.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/libs/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js"></script>
<script src="/static/js/jquery.magnific-popup.min.js"></script>
<script src="/static/js/jquery.form.js"></script>
<script src="/static/js/jquery.autocomplete.util.js"></script>
<script src="/static/js/bootstrap.autocomplete.js"></script>
<script src="/static/js/jquery.validate.js"></script>
<script src="/static/js/jquery.validate.bootstrap.popover.js"></script>
<script src="/static/js/jquery.json-2.4.min.js"></script>
<script src="/static/js/mgt.poi.js"></script>
<script src="/static/js/mgt.order.js"></script>
<script src="/static/js/highcharts.js"></script>
<script src="/static/js/wmHeadertip.js"></script>
<script src="/static/js/jquery.dataTables.js"></script>
<script src="/static/js/jquery.dataTables.scroller.js"></script>
<script src="/static/js/jquery.dataTables.tableTools.js"></script>
<script src="/static/js/chart-init.js"></script>
<script src="/static/js/date-helper.js"></script>
<link href="/static/css/bootstrap.min.css" rel="stylesheet" />
<link href="/static/css/bootstrap-theme.min.css" rel="stylesheet" />
<link href="/static/libs/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet" />
<link href="/static/css/magnific-popup.css" rel="stylesheet" />
<link href="/static/css/main.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/static/css/bootstrap-switch.min.css"/>
<link rel="stylesheet" type="text/css" href="/static/css/module/bootstrap/bootstrap-tour.min.css"/>
<link rel="stylesheet" type="text/css" href="/static/css/gdmap.css"/>
<link rel="stylesheet" type="text/css" href="/static/css/wmpager.css"/>
<link href="/static/css/validate-popover.css" rel="stylesheet" />
<link href="/static/css/jquery.dataTables.css" rel="stylesheet" />
<link href="/static/css/jquery.dataTables.tableTools.css" rel="stylesheet" />
<script type="text/javascript">
            var MT = {};
            MT.BOOTSTAMP = new Date().getTime();
            MT.STATIC_ROOT = "/static/js";
</script>
<!-- 页面的启动脚本配置文件 end -->     
</head>
<body>


	<!-- 顶部导航栏 -->
	<nav class="navbar navbar-inverse avbar-static-top" id="J-top-nav" role="navigation">
		<div class="navbar-header">
			 <a class="navbar-brand" href="/welcome">外卖运营后台</a>
		</div>
		<div class="navbar-header">
			 <a class="navbar-brand" href="/welcome" style="color: #428bca;font-size: 16px;">欢迎页</a>
		</div>
		<div class="navbar-header">
			 <a class="navbar-brand" href="/feedback/bd_feedback/list" style="color: coral;font-size: 16px;">我有话说！</a>
		</div>
		<div style="float:right;color:#FFF;padding-top:10px;margin-right:10px;"><#if _currentUser?exists> 您好，${_currentUser.name} <a href="/logout">退出</a></#if> </div>
	</nav>
	<nav class="navbar navbar-inverse"></nav>
	<!--<div class="container"> -->
	<div class="row" style="overflow:auto;">
		<!-- 左侧导航 -->
		<div class="col-xs-2 col-md-2"  id="J-left-nav">
			<div class="panel-group" id="accordion">
				<#if __menus__?exists && __menus__?size!=0>
					<#list __menus__ as menu>
						<#if menu.menus?exists && menu.menus?size!=0>
							<#if !menu.menus[0].url?starts_with("/fee/")>  <#-- 经费管理的菜单不显示 by sunhao03 -->
								<div class="list-group">
								
										<a class="list-group-item parent-active" data-toggle="collapse" data-toggle="collapse" data-parent="" href="#menu_${menu_index}">${menu.title!''}</a>
										<#list menu.menus as sub_menu>
											<a href="${sub_menu.url!''}" 
												<#if sub_menu?? && sub_menu.type == 2>target="_blank"</#if> 
												<#if request.getQueryString()?? && request.getQueryString()?contains("_from")>
													<#if sub_menu.url == '/wmpoi/find?_from=poimgt_editor_panel' && request.getQueryString()?contains("_from=poimgt_editor_panel")> class="list-group-item active"
													<#elseif sub_menu.url == '/wmpoi/findMy?_from=poimgt_my_panel' && request.getQueryString()?contains("_from=poimgt_my_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmpoi/find?_from=poimgt_bd_panel' && request.getQueryString()?contains("_from=poimgt_bd_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/activity/center/statistics' && request.getQueryString()?contains("_from=act_center")>class="list-group-item active"
													<#elseif sub_menu.url == '/activity/center/list_act_policy' && request.getQueryString()?contains("_from=list_act_policy")>class="list-group-item active"
													<#elseif sub_menu.url == '/taskpoi/listTask' && request.getQueryString()?contains("_from=poi_task_list")>class="list-group-item active"
													<#elseif sub_menu.url == '/order/list_all_valid?_from=order_cs_panel' && request.getQueryString()?contains("_from=order_cs_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/order/list_all_valid?_from=order_bd_panel' && request.getQueryString()?contains("_from=order_bd_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/aor/list' && request.getQueryString()?contains("_from=aor_list")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmctrlarea/list' && request.getQueryString()?contains("_from=ctrlarea_list")>class="list-group-item active"
													<#elseif sub_menu.url == '/bizpoint/v2/manage' && request.getQueryString()?contains("_from=bizpoint_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/building/list' && request.getQueryString()?contains("_from=building_list_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/order/aor/showList?_from=aor_showlist_panel' && request.getQueryString()?contains("_from=aor_showlist_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/order/aor/showListHis?_from=aor_hislist_panel' && request.getQueryString()?contains("_from=aor_hislist_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmlogo/editLogoDic?type=1&_from=icon_brand_panel' && request.getQueryString()?contains("_from=icon_brand_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmlogo/editLogoDic?type=2&_from=icon_cate_panel' && request.getQueryString()?contains("_from=icon_cate_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmlogo/editLogoDic?type=3&_from=icon_other_panel' && request.getQueryString()?contains("_from=icon_other_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/wmorgcity/authority/list' && request.getQueryString()?contains("_from=orgcity_authority_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/datareport/showInitPage?_from=report_time_panel' && request.getQueryString()?contains("_from=report_time_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/datareport/showInitPage?dataType=1&_from=report_his_panel' && request.getQueryString()?contains("_from=report_his_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/activity/lottery/list' && request.getQueryString()?contains("_from=activity_lottery_panel")>class="list-group-item active"
													<#elseif sub_menu.url == '/banner/list' && request.getQueryString()?contains("_from=banner_list_panel")>class="list-group-item active"
													<#else>
														class="list-group-item"
													</#if> 
												<#elseif request.getRequestUri() == sub_menu.url || sub_menu.url?contains(request.getRequestUri()) || request.getRequestUri()?contains(sub_menu.url)>class="list-group-item active"
												<#else>
													class="list-group-item"
											    </#if>
											>${sub_menu.title!''}</a>
									    </#list>
								</div>
							</#if>
						</#if>
					</#list>
				</#if>

				<#--
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#old">旧版功能</a>
					</div>
					<#if navid == 1><ul id="old" class="list-group panel-collapse collapse in">
					<#else><ul id="old" class="list-group panel-collapse collapse">
					</#if>
					    <li class="list-group-item"><a href="/waimai/todayorder">今日订单</a></li>
					    <li class="list-group-item"><a href="/waimai/setpoiimage">设置门店图片</a></li>
				  	</ul>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#today">今日订单</a>
					</div>
					<#if navid==2><ul id="today" class="list-group panel-collapse collapse in">
					<#else><ul id="today" class="list-group panel-collapse collapse">
					</#if>
					    <li class="list-group-item"><a href="/today/order/new">我的新订单</a></li>
					    <li class="list-group-item"><a href="/today/order/handledlist">已处理订单</a></li>
					    <li class="list-group-item"><a href="/today/order/list/detail">自营订单</a></li>
				  	</ul>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#history">历史订单</a>
					</div>
					<ul id="history" class="list-group panel-collapse collapse">
					    <li class="list-group-item">订单详细</li>
					    <li class="list-group-item">订单统计</li>
				  	</ul>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#onlinepoi">门店管理</a>
					</div>
					<#if navid == 4><ul id="onlinepoi" class="list-group panel-collapse collapse in">
					<#else><ul id="onlinepoi" class="list-group panel-collapse collapse">
					</#if>
						<li class="list-group-item"><a href="/poi/mgt/online">在线门店(new)</a></li>
					    <li class="list-group-item"><a href="/poi/managerpoi">在线门店</a></li>
					    <li class="list-group-item"><a href="/poi/addpoipage">新增门店</a></li>
					    <li class="list-group-item"><a href="/poi/stash/list">抓取门店</a></li>
					    <li class="list-group-item"><a href="/poi/poimatchpage">待上线门店</a></li>
					    <li class="list-group-item"><a href="/food/addfoodpage">新增菜品</a></li>
					    <li class="list-group-item"><a href="/poi/distribution">门店分布图</a></li>
				  	</ul>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#userquestion">用户反馈&nbsp;&nbsp</a>
					</div>
					<#if navid==5><ul id="userquestion" class="list-group panel-collapse collapse in">
					<#else><ul id="userquestion" class="list-group panel-collapse collapse">
					</#if>
					    <li class="list-group-item"><a href="/waimai/question/dealpage">处理反馈</a></li>
					    <li class="list-group-item"><a href="">测试</a></li>
				  	</ul>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#data_statistics">数据统计</a>
					</div>
					<#if navid==6><ul id="data_statistics" class="list-group panel-collapse collapse in">
					<#else><ul id="data_statistics" class="list-group panel-collapse collapse">
					</#if>
					    <li class="list-group-item"><a href="/waimai/area/manager">区域管理</a></li>
				  	</ul>
				</div>
				-->

			</div>
		</div>
		<div class="col-xs-2 col-md-2"  id="J-left-nav1"></div>
		<!-- 左侧导航结束 -->
