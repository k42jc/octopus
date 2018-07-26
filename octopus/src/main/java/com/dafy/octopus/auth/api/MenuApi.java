package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.auth.service.IMenuService;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.domain.Request;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 菜单操作 暂时还没用上
 * Created by liaoxudong
 * Date:2018/2/2
 */

//@RestController
//@RequestMapping("menu")
public class MenuApi {

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("menu:list")
    @GetMapping("/list")
    public Response list(@RequestHeader Map header){
        return menuService.list(header);
    }

    @RequiresPermissions("menu:add")
    @PostMapping("/add")
    public Response add(@RequestBody Request request) {
        CommonUtils.assertHasParams(request,"mCode,mName,mIcon");
        return menuService.add(request);
    }


    @RequiresPermissions("menu:delete")
    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long id){
        return menuService.delete(id);
    }


    @RequiresPermissions("menu:update")
    @PutMapping("/update")
    public Response update(@RequestBody Request request){
        CommonUtils.assertHasParams(request,"mCode,mName,mIcon");
        return menuService.update(request);
    }
}
