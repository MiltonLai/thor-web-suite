package com.rockbb.thor.admin.web.base;

import com.rockbb.thor.commons.lib.web.PageBean;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BasicPageController extends BasicController implements PageAware
{
	protected String messagePage(PageBean pb, String title, String content, String redirect, int delay)
	{
		if (redirect != null && redirect.length() > 0)
		{
			pb.addMetaExtra("<meta http-equiv=\"refresh\" content=\"" + delay + ";URL=" + redirect + "\">");
		}
		pb.addMessage("title", title);
		pb.addMessage("content", content);

		return "index/message_page";
	}

	@Override
	@ModelAttribute("pageBean")
	public PageBean initPageRender(HttpServletRequest request) {
		return (PageBean)request.getAttribute(PageBean.ATTR_KEY);
	}
}
