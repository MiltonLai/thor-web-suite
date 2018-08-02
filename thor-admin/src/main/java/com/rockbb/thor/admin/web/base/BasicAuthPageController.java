package com.rockbb.thor.admin.web.base;

import com.rockbb.thor.commons.lib.web.PageBean;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by Milton on 2015/11/12 at 21:40.
 */
public class BasicAuthPageController extends BasicSessionPageController implements AuthAware {

    @ModelAttribute
    public void initNavigation(
            HttpServletRequest request,
            @ModelAttribute("sessionBean") SessionBean sb,
            @ModelAttribute("pageBean") PageBean pb) {

    }

}
