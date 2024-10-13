package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.CategoryDTO;
import com.qiu.library.common.vo.CategoryPageVo;
import com.qiu.library.entity.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> SelectAllCategory();

    PageInfo<CategoryPageVo> page(CategoryPageVo categoryPageVo);

    void save(Category category);

    Category getById(Integer id);

    void update(Category category);

    void deleteById(Integer id);

    List<Category> SelectAllParentCategory();

    List<Category> getByParentId(Integer id);

    List<CategoryDTO> getCategoryByMap();

    List<CategoryDTO> getCategoryTree();
}
