package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.*;
import com.design.system.domain.UserDO;
import com.design.system.service.UserService;
import com.design.system.vo.LoginVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/business")
@Controller
public class BusinessLoginController extends BaseController{
    @GetMapping("/businessLogin")
    public String businessLogin(){ return "business_login";}

    @GetMapping("/businessRegister")
    public String businessRegister(){ return "business_register";}

    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@RequestParam String redirectUrl, @RequestParam String username, @RequestParam String password) {
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)) return JsonResult.buildResp(ResultCode.ACCOUNT_PWD_ERROR);
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return JsonResult.buildResp(new LoginVo(username, password, StringUtils.isBlank(redirectUrl) ? "/" : redirectUrl));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return JsonResult.error();
    }
    @Autowired
    UserService userService;
    @PostMapping("/register")
    @ResponseBody
    public JsonResult register(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String mobile, @RequestParam String liveAddress) {
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)) return JsonResult.buildResp(ResultCode.ACCOUNT_PWD_ERROR);
        password = MD5Utils.encrypt(username, password);
        UserDO user = new UserDO();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(0);
        user.setType(2);
        user.setName(name);
        user.setMobile(mobile);
        user.setLiveAddress(liveAddress);
        if (userService.save(user) != 1) return JsonResult.error();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return JsonResult.buildResp(new LoginVo(username, password, null));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return JsonResult.error();
    }
}
