package com.design.system.controller;

import com.design.common.controller.BaseController;
import com.design.common.utils.PageUtils;
import com.design.common.utils.Query;
import com.design.common.utils.R;
import com.design.system.domain.GoodsDO;
import com.design.system.service.GoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/goods")
@Controller
public class GoodsController extends BaseController {
    private String prefix="system/goods"  ;
    @Autowired
    GoodsService goodsService;

    @RequiresPermissions("sys:goods:goods")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/goods";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        if (!"1".equals(getUserId().toString())) {
            params.put("userIdCreate",getUserId());
        }
        Query query = new Query(params);
        List<GoodsDO> sysGoodsList = goodsService.list(query);
        int total = goodsService.count(query);
        PageUtils pageUtil = new PageUtils(sysGoodsList, total);
        return pageUtil;
    }

    @RequiresPermissions("sys:goods:add")
    @GetMapping("/add")
    public String add(Model model) {
        return prefix + "/add";
    }

    @RequiresPermissions("sys:goods:edit")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        GoodsDO goodsDO = goodsService.get(id);
        model.addAttribute("goods", goodsDO);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:goods:add")
    @PostMapping("/save")
    @ResponseBody
    public R save(GoodsDO goodsDO) {
        goodsDO.setUserIdCreate(getUserId());
        goodsDO.setStatus(1); // 下架中
        goodsDO.setGmtCreate(System.currentTimeMillis());
        if (goodsService.save(goodsDO) > 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",goodsDO.getId());
            return R.ok(map);
        }
        return R.error();
    }

    @RequiresPermissions("sys:goods:edit")
    @PostMapping("/update")
    @ResponseBody
    public R update(GoodsDO goodsDO) {
        if (goodsService.update(goodsDO) > 0) {
            return R.ok();
        }
        return R.error();
    }


    @RequiresPermissions("sys:goods:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (goodsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:goods:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Long[] ids) {
        int r = goodsService.batchremove(ids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    public boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !goodsService.exit(params);
    }
    @ResponseBody
    @PostMapping("/uploadImg")
    public R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, Long id,HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = goodsService.updatePersonalImg(file, avatar_data,id);
        } catch (Exception e) {
            return R.error("更新图像失败！");
        }
        if(result!=null && result.size()>0){
            return R.ok(result);
        }else {
            return R.error("更新图像失败！");
        }
    }
}
