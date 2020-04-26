package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.PageUtils;
import com.design.common.utils.Query;
import com.design.common.utils.R;
import com.design.system.domain.OrderDO;
import com.design.system.domain.ShopingCertDO;
import com.design.system.domain.UserDO;
import com.design.system.service.CertService;
import com.design.system.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/sys/cert")
@Controller
public class CertController extends BaseController {
    private String prefix="system/cert"  ;
    @Autowired
    CertService certService;
    @Autowired
    OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(CertController.class);

    @GetMapping("")
    public String user(HttpServletRequest request,Model model) {
        if (null == getUser()) {
            model.addAttribute("redirect_url",request.getServletPath());
            return "business_login";
        }
        Map<String,Object> params = new HashMap<>();
        params.put("userIdCreate",getUserId());
        model.addAttribute("loginUser",getUser());
        List<ShopingCertDO> list = certService.list(params);
        model.addAttribute("list", list);
        Double totalAmount = 0.0;
        for (ShopingCertDO certDO : list) {
            totalAmount = totalAmount + Double.parseDouble(certDO.getPrice());
        }
        model.addAttribute("totalAmout", totalAmount);
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
        OrderDO orderDO = new OrderDO();
        String goodsId =  "" ;
        Double amout = 0.0;
        for (ShopingCertDO certDO : list) {
            certService.remove(certDO.getId()); // 删除购物车
            goodsId += certDO.getGoodsId() + ",";
            amout += Double.parseDouble(certDO.getPrice());
        }
        orderDO.setGoodsIds(goodsId.equals("") ? goodsId : goodsId.substring(0, goodsId.length() - 1));
        orderDO.setPrice(amout);
        orderDO.setUserIdCreate(getUserId());
        orderDO.setGmtCreate(System.currentTimeMillis());
        orderDO.setPayTime(System.currentTimeMillis());
        orderDO.setPayType(1);
        orderDO.setPayStatus(1);
        int save = orderService.save(orderDO);
        logger.error("\n >> 加入订单信息 {}", Objects.equals(1, save) ? "成功" : "失败");
        return R.ok();
    }
}
