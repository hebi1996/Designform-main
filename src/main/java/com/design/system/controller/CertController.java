package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.PageUtils;
import com.design.common.utils.Query;
import com.design.common.utils.R;
import com.design.system.domain.ShopingCertDO;
import com.design.system.domain.UserDO;
import com.design.system.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/cert")
@Controller
public class CertController extends BaseController {
    private String prefix="system/cert"  ;
    @Autowired
    CertService certService;

    @GetMapping("")
    public String user(HttpServletRequest request,Model model) {
        System.out.println(request.getRequestURL().toString() + "::" + request.getServletPath());
        if (null == getUser()) {
            model.addAttribute("redirect_url",request.getServletPath());
            return "business_login";
        }
        Map<String,Object> params = new HashMap<>();
        params.put("userIdCreate",getUserId());
        model.addAttribute("loginUser",getUser());
        model.addAttribute("list",certService.list(params));
        return prefix + "/cert";
    }

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (certService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Long[] ids) {
        int r = certService.batchremove(ids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/pay")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("userIdCreate",getUserId());
        List<ShopingCertDO> list = certService.list(params);
        for (ShopingCertDO certDO : list) {
            certService.remove(certDO.getId());
        }
        return R.ok();
    }
}
