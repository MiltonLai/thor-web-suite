package com.rockbb.thor.app.web.base;

import com.rockbb.thor.app.common.AppMessage;
import com.rockbb.thor.app.common.JsonView;
import com.rockbb.thor.commons.lib.json.JacksonUtils;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BasicController {

    @ModelAttribute("requestBean")
	public RequestBean initRequest(HttpServletRequest request) {
		return (RequestBean)request.getAttribute(RequestBean.ATTR_KEY);
	}

    /**
     * 数据
     */
    protected ResponseEntity<String> data(Object object) {
        return new ResponseEntity<>(JacksonUtils.compressByView(object, JsonView.Public.class), HttpStatus.OK);
    }

    /**
     * 成功信息
     */
    protected ResponseEntity<String> success(String message) {
        AppMessage appMessage = new AppMessage(AppMessage.CODE_SUCCESS, message);
        return new ResponseEntity<>(JacksonUtils.compressByView(appMessage, JsonView.Public.class), HttpStatus.OK);
    }
    protected ResponseEntity<String> success() {
        return success("OK");
    }

    /**
     * 错误输入
     */
    protected ResponseEntity<String> error(int code, String message, String field) {
        AppMessage appMessage = new AppMessage(code, message, field);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 错误输入
     */
    protected ResponseEntity<String> error(String message, String field) {
        AppMessage appMessage = new AppMessage(AppMessage.CODE_GENERAL_ERROR, message, field);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 错误输入
     */
    protected ResponseEntity<String> error(String message) {
        AppMessage appMessage = new AppMessage(AppMessage.CODE_GENERAL_ERROR, message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 业务错误
     */
    protected ResponseEntity<String> serviceError(int code, String message, String field) {
        AppMessage appMessage = new AppMessage(code, message, field);
        return new ResponseEntity<>(JacksonUtils.compressObject(appMessage), HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * 业务错误
     */
    protected ResponseEntity<String> serviceError(String message, String field) {
        AppMessage appMessage = new AppMessage(AppMessage.CODE_GENERAL_ERROR, message, field);
        return new ResponseEntity<>(JacksonUtils.compressObject(appMessage), HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * 业务错误
     */
    protected ResponseEntity<String> serviceError(String message) {
        AppMessage appMessage = new AppMessage(AppMessage.CODE_GENERAL_ERROR, message);
        return new ResponseEntity<>(JacksonUtils.compressObject(appMessage), HttpStatus.NOT_IMPLEMENTED);
    }

}
