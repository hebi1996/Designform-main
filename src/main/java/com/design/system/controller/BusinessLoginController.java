package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.MD5Utils;
import com.design.common.utils.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/business")
@Controller
public class BusinessLoginController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(BusinessLoginController.class);

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model,@RequestParam String redirectUrl, @RequestParam String username, @RequestParam String password) {
        logger.error(">> redirectUrl : {} <<", redirectUrl);
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            model.addAttribute("redirect_url", redirectUrl);
            model.addAttribute("errorMessage", "账号或者密码错误");
            return "business_login";
        }
        return "redirect:" + redirectUrl;
    }
}
