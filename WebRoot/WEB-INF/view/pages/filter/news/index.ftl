<link href="/Longyan/static/css/pages/news/index.css" rel="stylesheet" />
<div class="wrap">
	<div class="news-box section">
		<div class="catalog_title clearself">
			<span>Company news</span><br>公司新闻
		</div>
		<div class="content-box">
			<div class="img-box">
				<img src="/Longyan/static/img/pages/news/index-news-logo.jpg">
				<a href="/Longyan/pages/news/news" target="_blank" class="goto-news">查看更多</a>
			</div>
			<div class="news-list">
				<div class="more-box">
					<a href="/Longyan/pages/news/news" target="_blank" class="goto-news">更多</a>
				</div>
				<div class="list-box">
					<ul>
						<#if contentList?exists && contentList?size!=0>
							<#list contentList as content>
								<li class="item">
									<a href="/Longyan/pages/news/news/content/${content.id}" target="_blank">▪ ${content.title}</a>
									<span class="time">${content.ctime}</span>
								</li>
							</#list>
						<#else>
							<h1>目前还没有任何新闻哦，请静候佳音~</h1>
						</#if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="dividing_line"></div>
	<div class="health-box section">
		<div class="catalog_title clearself">
			<span>Healthy center</span><br>健康养生中心
		</div>
		<div class="content-box">
			<img src="/Longyan/static/img/pages/news/index-health-logo.jpg" class="health-img">
			<div class="news-list">
				<div class="more-box">
					<a href="/Longyan/pages/news/health" target="_blank" class="goto-health">更多</a>
				</div>
				<div class="list-box">
					<ul>
						<#if healthBbs?exists && healthBbs?size!=0>
							<#list healthBbs as health>
								<li class="item">
									<a href="/Longyan/pages/bbs/${health.id}?dim=health" target="_blank">▪ ${health.title}</a>
									<span class="time">${health.utime}</span>
									<span class="reply-count">回复：${health.reply_count}</span>
								</li>
							</#list>
						<#else>
							<h1>目前还没有任何帖子哦，请静候佳音~</h1>
						</#if>
						
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="dividing_line"></div>
	<div class="activity-box section">
		<div class="catalog_title clearself">
			<span>Activity center</span><br>活动中心
		</div>
		<div class="content-box">
			<img src="/Longyan/static/img/pages/news/index-activity-logo.jpg" class="activity-img">
			<div class="news-list">
				<div class="more-box">
					<a href="/Longyan/pages/news/activity" target="_blank" class="goto-activity">更多</a>
				</div>
				<div class="list-box">
					<ul>
						<#if activityBbs?exists && activityBbs?size!=0>
							<#list activityBbs as activity>
								<li class="item">
									<a href="/Longyan/pages/bbs/${activity.id}?dim=activity" target="_blank">▪ ${activity.title}</a>
									<span class="time">${activity.utime}</span>
									<span class="reply-count">回复：${activity.reply_count}</span>
								</li>
							</#list>
						<#else>
							<h1>目前还没有任何活动哦，主办方已经在策划了哦，请静候佳音~</h1>
						</#if>
						
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>