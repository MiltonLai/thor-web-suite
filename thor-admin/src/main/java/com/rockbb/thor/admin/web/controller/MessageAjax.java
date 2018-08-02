package com.rockbb.thor.admin.web.controller;

import com.rockbb.thor.admin.common.ExposedResourceBundleMessageSource;
import com.rockbb.thor.commons.lib.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Locale;


@Controller
@RequestMapping("/util")
public class MessageAjax {
    private static final Logger logger = LoggerFactory.getLogger(MessageAjax.class);

    @Resource(name = "messageSource")
    private ExposedResourceBundleMessageSource langRes;

    private static String langCache;

    @RequestMapping(value = {"/i18n"}, method = RequestMethod.GET)
    @ResponseBody
    public String getI18n() {
        if (langCache == null) {
            langCache = JacksonUtils.compressMap(langRes.getBundleMap("language/message", Locale.SIMPLIFIED_CHINESE));;
        }
        return "var sys_i18n = " + langCache + ";";
    }
}
