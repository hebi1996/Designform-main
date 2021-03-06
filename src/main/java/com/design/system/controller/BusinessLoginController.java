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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/business")
@Controller
public class BusinessLoginController extends BaseController{
    @Autowired UserService userService;

    @GetMapping("/businessLogin")
    public String businessLogin(){ return "business_login";}

    @GetMapping("/businessRegister")
    public String businessRegister(){ return "business_register";}

    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request,@RequestParam String redirectUrl, @RequestParam String username, @RequestParam String password) {
        JsonResult result = new JsonResult();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            result.setCode(-1);
            result.setMessage("账号或者密码不能为空");
        }
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            HttpSession session=request.getSession();//获取session并将userName存入session对象
            session.setAttribute("userName", username);
            return JsonResult.buildResp(new LoginVo(username, password, StringUtils.isBlank(redirectUrl) ? "/" : redirectUrl));
        } catch (AuthenticationException e) {
            result.setCode(-1);
            result.setMessage("账号或者密码错误");
        }
        return result;
    }
    @PostMapping("/register")
    @ResponseBody
    public JsonResult register(
            @RequestParam String username, @RequestParam String password,
            @RequestParam String name, @RequestParam String mobile, @RequestParam String liveAddress, HttpServletRequest request) {
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)) return JsonResult.buildResp(ResultCode.ACCOUNT_PWD_ERROR);
        password = MD5Utils.encrypt(username, password);
        UserDO user = new UserDO();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(1);
        user.setType(2);
        user.setName(name);
        user.setMobile(mobile);
        user.setLiveAddress(liveAddress);
        if (userService.save(user) != 1) return JsonResult.error();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            HttpSession session=request.getSession();//获取session并将userName存入session对象
            session.setAttribute("userName", user.getUsername());
            return JsonResult.buildResp(new LoginVo(username, password, ""));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return JsonResult.buildResp("注册失败");
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/business_login";
    }
}
