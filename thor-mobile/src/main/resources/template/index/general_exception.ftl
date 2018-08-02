<#include "includes/overall_header.ftl">

	<div class="weui_msg">
		<div class="weui_icon_area">
			<i class="weui_icon_msg weui_icon_warn"></i>
		</div>
		<div class="weui_text_area">
			<h2 class="weui_msg_title">${se_data.msgTitle}</h2>
			<p class="weui_msg_desc">${se_data.msgDesc}</p>
		</div>
		<div class="weui_opr_area">
			<p class="weui_btn_area">
				<a href="${sys_config.root_path}/index.html" class="weui_btn weui_btn_warn">返回</a>
			</p>
		</div>
	</div>

<#include "includes/overall_footer.ftl">