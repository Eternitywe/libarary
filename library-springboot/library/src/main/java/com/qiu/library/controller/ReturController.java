package com.qiu.library.controller;

import com.qiu.library.common.Result;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.common.vo.ReturPageVo;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;
import com.qiu.library.service.IBorrowService;
import com.qiu.library.service.IReturService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/retur")
public class ReturController {

    @Resource
    private IReturService returService;

    @GetMapping("list")
    public Result SelectAllRetur(){
        List<Retur> list = returService.SelectAllRetur();
        return Result.success(list);
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        Retur retur = returService.getById(id);
        return Result.success(retur);
    }


    @GetMapping("page")
    public Result page(ReturPageVo returPageVo){
        return Result.success(returService.page(returPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody Retur retur){
        returService.save(retur);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Retur retur){
        returService.update(retur);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        returService.deleteById(id);
        return Result.success();
    }

}
