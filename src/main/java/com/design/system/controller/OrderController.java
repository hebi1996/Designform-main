package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.PageUtils;
import com.design.common.utils.Query;
import com.design.common.utils.R;
import com.design.system.domain.GoodsDO;
import com.design.system.domain.OrderDO;
import com.design.system.domain.ShopingCertDO;
import com.design.system.service.GoodsService;
import com.design.system.service.OrderService;
import com.design.system.vo.OrderVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/sys/order")
@Controller
public class OrderController extends BaseController {
    private String prefix="system/order"  ;
    @Autowired
    OrderService orderService;

    @RequiresPermissions("sys:order:order")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/order";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<OrderDO> sysOrderList = orderService.list(query);
        int total = orderService.count(query);
        PageUtils pageUtil = new PageUtils(sysOrderList, total);
        return pageUtil;
    }

    @RequiresPermissions("sys:order:edit")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        OrderDO orderDO = orderService.get(id);
        model.addAttribute("order", orderDO);
        return prefix + "/edit";
    }
    @RequiresPermissions("sys:order:edit")
    @PostMapping("/update")
    @ResponseBody
    public R update(OrderDO orderDO) {
        if (orderService.update(orderDO) > 0) {
            return R.ok();
        }
        return R.error();
    }


    @RequiresPermissions("sys:order:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (orderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:order:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Long[] ids) {
        int r = orderService.batchremove(ids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/finish")
    public String user(HttpServletRequest request,Model model) {
        if (null == getUser()) {
            model.addAttribute("redirect_url",request.getServletPath());
            return "business_login";
        }
        List<OrderVO> vos = new ArrayList();
        Map<String,Object> params = new HashMap<>();
        List<OrderDO> list = orderService.list(params);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderDO orderDO : list) {
            OrderVO vo = new OrderVO();
            orderDO.setPrice(orderDO.getPrice());
            vo.setPayType(Objects.equals(1, orderDO.getPayType()) ? "到付" : "微信");
            vo.setPayTime(sdf.format(orderDO.getPayTime()));
            vos.add(vo);
        }
        model.addAttribute("list", vos);
        return prefix + "/finish";
    }
}
