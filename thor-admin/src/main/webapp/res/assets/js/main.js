// 可直接用于bootstrap table的formatter START

function time_formatter_recent(value, row) {
	return moment(value).fromNow();
}

function time_formatter_ymd(value, row) {
	if (typeof value == 'undefined') return '';
	if (value == null) return '';
	return moment(value).format('YYYY-MM-DD');
}

function time_formatter_ymd_slash(value, row) {
	if (typeof value == 'undefined') return '';
	if (value == null) return '';
	return moment(value).format('YYYY/MM/DD');
}

function time_formatter_ymdhm(value, row) {
	return time_str_ymdhm(value);
}

function time_formatter_ymdhms(value, row) {
	if (typeof value == 'undefined') return '';
	if (value == null) return '';
	return moment(value).format('YYYY-MM-DD HH:mm:ss');
}

function money_formatter(value, row) {
	if (typeof value == 'undefined') return '';
	if (value == null) return '';
	return parseFloat(value).toFixed(2);
}

function rate_formatter_percent(value, row) {
	if (typeof value == 'undefined') return '';
	if (value == null) return '';
	return (parseFloat(value) * 100).toFixed(2) + "%";
}

// 可直接用于bootstrap table的formatter END


// 以下为普通工具方法

/**
 * 将时间格式化为YYYY-MM-DD HH:mm
 */
function time_str_ymdhm(time) {
	if (isBlank(time)) return '';
	return moment(time).format('YYYY-MM-DD HH:mm');
}

function money_str_fix2(value) {
	if (isBlank(value)) return '';
	return parseFloat(value).toFixed(2);
}


function kv_map_formatter2(map) {
	if (isBlank(map)) return '';
	var html = '<ul>';
	for(var key in map){
		html += '<li>' + key + ': ' + map[key] + '</li>'
	}
	html += '</ul>';
	return html;
}

function kv_map_formatter(map) {
	if (isBlank(map)) return '';
	var html = '';
	for(var key in map){
		html += '<b>' + key + '</b>: ' + map[key] + ' / ';
	}
	html += '</ul>';
	return html;
}

/**
 * 从链接中提取域名, 返回的是数组, 域名位于第二个元素
 */
function extract_domain(url) {
	if (typeof url == 'undefined' || url == null) return '';
	r = url.match('[\\w]+:\\/\\/(([\\w-]+\\.)+[\\w-]+)(:\\d+)?\\/');
	if (r == null) return '';
	return r[1];
}

/**
 * 从链接中提取域名, 并返回文本是域名的链接HTML
 */
function extract_domain_url(url) {
	var domain = extract_domain(url);
	if (typeof domain == 'undefined') return '';
	if (domain == null) return '';
	return '<a target="_blank" href="'+url+'">'+domain+'</a>';
}

/**
 * 生成是否通过手机号验证的图标
 */
function cellphoneCheckedIcon(time) {
	var timeStr = time_str_ymdhm(time);
	if (isBlank(timeStr)) return '';
	return '<span class="glyphicon glyphicon-phone" title="手机号验证 '+timeStr+'"></span>';
}

function identityCheckedIcon(time) {
	var timeStr = time_str_ymdhm(time);
	if (isBlank(timeStr)) return '';
	return '<span class="glyphicon glyphicon-user" title="实名验证 '+timeStr+'"></span>';
}

function cardBoundIcon(time) {
	var timeStr = time_str_ymdhm(time);
	if (isBlank(timeStr)) return '';
	return '<span class="glyphicon glyphicon-credit-card" title="绑卡 '+timeStr+'"></span>';
}

function investCurrentIcon(time) {
	var timeStr = time_str_ymdhm(time);
	if (isBlank(timeStr)) return '';
	return '<span class="glyphicon glyphicon-credit-usd" title="投资活期 '+timeStr+'"></span>';
}

function investTermIcon(time) {
	var timeStr = time_str_ymdhm(time);
	if (isBlank(timeStr)) return '';
	return '<span class="glyphicon glyphicon-credit-gbp" title="投资定期 '+timeStr+'"></span>';
}

function urlIcon(url) {
	if (isBlank(url)) return '';
	return '<a href="'+url+'" target="_blank"><span class="glyphicon glyphicon-link"></span></a>';
}

function isBlank(obj) {
	return (typeof obj == 'undefined' || obj == null || obj == '');
}

function user_link_formatter(rootPath, userId, userName) {
	if (isBlank(userId)) return '';
	if (isBlank(userName)) userName = '用户';
	return '<a href="' + rootPath + '/operation/user/info.html?id=' + userId + '">' + userName + '</a>';
}

function ajaxModalFormSubmit(url, formId, modalId, tableId, notifyId) {
	$.ajax({
		type: 'POST',
		url: url,
		cache: false,
		data: $(formId).serialize(),
		dataType: "json"
	}).done(function (data) {
		if (data.success) {
			$(notifyId).notify({
				message: {text: data.success},
				fadeOut: {enabled: true, delay: 500},
				onClosed: function () {
					$(modalId).modal('hide');
					$(tableId).bootstrapTable('refresh');
				}
			}).show();
		} else {
			$(notifyId).notify({
				type: 'danger',
				message: {text: data.info}
			}).show();
		}
	}).fail(function () {
		$(notifyId).notify({
			type: 'danger',
			message: {text: "服务器错误"}
		}).show();
	});
}