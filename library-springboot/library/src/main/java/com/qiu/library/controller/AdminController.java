package com.qiu.library.controller;

import com.qiu.library.common.Result;
import com.qiu.library.common.dto.LoginDTO;
import com.qiu.library.common.request.LoginRequest;
import com.qiu.library.common.request.changePasswordRequest;
import com.qiu.library.common.vo.AdminPageVo;
import com.qiu.library.entity.Admin;
import com.qiu.library.service.IAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @GetMapping("list")
    public Result SelectAllUsers(){
        List<Admin> list = adminService.selectAllUsers();
        return Result.success(list);
    }


    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    @GetMapping("page")
    public Result page(AdminPageVo adminPageVo){
        return Result.success(adminService.page(adminPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody Admin admin){
        adminService.save(admin);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    @PostMapping("login")
    public Result login(@RequestBody LoginRequest admin){
        LoginDTO user = adminService.login(admin);
        return Result.success(user);
    }

    @PostMapping("changePassword")
    public Result changePassword(@RequestBody changePasswordRequest admin){
        adminService.changePassword(admin);
        return Result.success();
    }

    @PostMapping("changeStatus/{id}")
    public Result changeStatus(@PathVariable Integer id){
        adminService.changeStatus(id);
        return Result.success();
    }

}
