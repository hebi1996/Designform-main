package com.design.common.controller;

import com.design.common.utils.ShiroUtils;
import com.design.system.domain.UserDO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}
	public Long getUserId() {
		return getUser().getUserId();
	}
	public String getUsername() {
		return getUser().getUsername();
	}
}