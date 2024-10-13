package com.qiu.library.controller;

import com.qiu.library.common.Result;
import com.qiu.library.common.dto.CategoryDTO;
import com.qiu.library.common.vo.CategoryPageVo;
import com.qiu.library.entity.Category;
import com.qiu.library.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @GetMapping("list")
    public Result SelectAllCategory(){
        List<Category> list = categoryService.SelectAllCategory();
        return Result.success(list);
    }

    @GetMapping("tree")
    public Result tree(){
        List<CategoryDTO> list = categoryService.getCategoryTree();
        return Result.success(list);
    }


    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @GetMapping("getByParentId/{id}")
    public Result getByParentId(@PathVariable Integer id){
        List<Category> list = categoryService.getByParentId(id);
        return Result.success(list);
    }

    @GetMapping("page")
    public Result page(CategoryPageVo categoryPageVo){
        return Result.success(categoryService.page(categoryPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("getAllParentCategory")
    public Result getAllParentCategory(){
        List<Category> list = categoryService.SelectAllParentCategory();
        return Result.success(list);
    }


    @GetMapping("getCategoryByMap")
    public Result getCategoryByMap(){
       List<CategoryDTO> list = categoryService.getCategoryByMap();
        return Result.success(list);
    }

}
