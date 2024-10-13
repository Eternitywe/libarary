package com.qiu.library.controller;

import com.qiu.library.common.Result;
import com.qiu.library.common.dto.UserDTO;
import com.qiu.library.common.vo.UserPageVo;
import com.qiu.library.entity.User;
import com.qiu.library.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("list")
    public Result SelectAllUsers(){
        List<User> list = userService.selectAllUsers();
        return Result.success(list);
    }


    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("page")
    public Result page(UserPageVo userPageVo){
        return Result.success(userService.page(userPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @PostMapping("account")
    public Result account(@RequestBody UserDTO userDTO){
        userService.account(userDTO);
        return Result.success();
    }

    @PostMapping("changeStatus/{id}")
    public Result changeStatus(@PathVariable Integer id){
        userService.changeStatus(id);
        return Result.success();
    }

}
