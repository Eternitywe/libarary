package com.qiu.library.mapper;

import com.qiu.library.common.dto.CategoryDTO;
import com.qiu.library.common.vo.CategoryPageVo;
import com.qiu.library.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryMapper {
    List<Category> SelectAllCategory();

    List<CategoryPageVo> listByCondition(CategoryPageVo categoryPageVo);

    void save(Category category);

    Category getById(Integer id);

    void updateById(Category category);

    void deleteById(Integer id);

    List<Category> SelectAllParentCategory();

    List<Category> getByParentId(Integer id);

    List<CategoryDTO> listByMap();

    List<CategoryDTO> getCategoryTree();
}
