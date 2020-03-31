package com.design.system.controller;


import com.design.common.controller.BaseController;
import com.design.common.utils.DateUtils;
import com.design.common.utils.PageUtils;
import com.design.common.utils.Query;
import com.design.common.utils.R;
import com.design.system.dao.ShopingCertDao;
import com.design.system.domain.GoodsDO;
import com.design.system.domain.ShopingCertDO;
import com.design.system.domain.UserDO;
import com.design.system.service.GoodsService;
import com.design.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/shopping")
@Controller
public class ShoppingController extends BaseController{
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ShopingCertDao shopingCertDao;

    @GetMapping()
    String shopping() {
        return "shopping/index/main";
    }

    @ResponseBody
    @GetMapping("/open/list")
    public PageUtils opentList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
        int total = userService.count(query);
        PageUtils pageUtils = new PageUtils(sysUserList, total);
        return pageUtils;
    }

    @GetMapping("/open/post/{id}")
    public String post(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        System.out.println(request.getRequestURL().toString() + "::" + request.getServletPath());
        if (null == getUser()) {
            model.addAttribute("redirect_url",request.getServletPath());
            return "business_login";
        }
        UserDO userDO = userService.get(id);
        model.addAttribute("loginUser",getUser());
        model.addAttribute("user", userDO);
        model.addAttribute("gtmModified", DateUtils.format(userDO.getGmtModified()));
        //查询商品列表
        model.addAttribute("goodsList",handlerGoods(id));
        return "shopping/index/goods_details";
    }

    public List<GoodsDO> handlerGoods(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("userIdCreate",id); //当前点击
        params.put("status", 0); //上架
        return goodsService.list(params);
    }

    @PostMapping("/open/post/save/{goodsId}")
    @ResponseBody
    public R save(@PathVariable("goodsId") Long goodsId) {
        if(Objects.equals(null, goodsId)) return R.error("商品不能为空");
        ShopingCertDO shopingCertDO = shopingCertDao.selectByGoodsIdAndUserIdCreate(goodsId, getUserId());
        if (null == shopingCertDO) {
            shopingCertDO = new ShopingCertDO();
            shopingCertDO.setGoodsId(goodsId);
            shopingCertDO.setUserIdCreate(getUserId());
            shopingCertDO.setGmtCreate(System.currentTimeMillis());
            shopingCertDO.setNumber(1);
            shopingCertDao.save(shopingCertDO);
        }else {
            shopingCertDO.setNumber(shopingCertDO.getNumber() + 1);
            shopingCertDao.update(shopingCertDO);
        }
        return R.ok();
    }
}
