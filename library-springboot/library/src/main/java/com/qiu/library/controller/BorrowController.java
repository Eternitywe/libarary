package com.qiu.library.controller;

import com.qiu.library.common.Result;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.entity.Borrow;
import com.qiu.library.service.IBorrowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    private IBorrowService borrowService;

    @GetMapping("list")
    public Result SelectAllBorrow(){
        List<Borrow> list = borrowService.SelectAllBorrow();
        return Result.success(list);
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        Borrow borrow = borrowService.getById(id);
        return Result.success(borrow);
    }


    @GetMapping("page")
    public Result page(BorrowPageVo borrowPageVo){
        return Result.success(borrowService.page(borrowPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody Borrow borrow){
        borrowService.save(borrow);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Borrow borrow){
        borrowService.update(borrow);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }


    @GetMapping("lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(borrowService.lineCharts(timeRange));
    }

}
