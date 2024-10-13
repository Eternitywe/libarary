package com.qiu.library.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.Result;
import com.qiu.library.common.dto.CategoryDTO;
import com.qiu.library.common.vo.CategoryPageVo;
import com.qiu.library.entity.Category;
import com.qiu.library.exception.ServiceException;
import com.qiu.library.mapper.CategoryMapper;
import com.qiu.library.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> SelectAllCategory() {
        return categoryMapper.SelectAllCategory();
    }

    @Override
    public PageInfo<CategoryPageVo> page(CategoryPageVo categoryPageVo) {
        PageHelper.startPage(categoryPageVo.getPageNum(), categoryPageVo.getPageSize());
        List<CategoryPageVo> categories = categoryMapper.listByCondition(categoryPageVo);
        PageInfo<CategoryPageVo> pageInfo = new PageInfo<>(categories);
        return pageInfo;
    }

    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdatetime(new Date());
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> SelectAllParentCategory() {
        return categoryMapper.SelectAllParentCategory();
    }

    @Override
    public List<Category> getByParentId(Integer id) {
        List<Category> list = categoryMapper.getByParentId(id);
        if (!list.isEmpty()){ //根据前台传来的pid 查询该分类下是否有子分类
            throw new ServiceException(444,"该分类下有子分类,不可删除!");
        }
        return null;
    }

    @Override
    public List<CategoryDTO> getCategoryByMap() {
        return categoryMapper.listByMap();
    }

    @Override
    public List<CategoryDTO> getCategoryTree() {
        List<CategoryDTO> list = categoryMapper.getCategoryTree();
        List<CategoryDTO> tree = createTree(null, list);
        return tree;
    }

    /**
     * 递归生成菜单树***
     */
    private List<CategoryDTO> createTree(Integer pid, List<CategoryDTO> categoryDTOList) {
        List<CategoryDTO> treeList = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOList) {
            if (pid == null){
                if (categoryDTO.getPid() == null){
                    treeList.add(categoryDTO);
                    categoryDTO.setChildren(createTree(categoryDTO.getId(), categoryDTOList));
                }
            }else {
                if (pid.equals(categoryDTO.getPid())) {
                    treeList.add(categoryDTO);
                    categoryDTO.setChildren(createTree(categoryDTO.getId(), categoryDTOList));
                }
            }
            if (CollUtil.isEmpty(categoryDTO.getChildren())){
                categoryDTO.setChildren(null);
            }
        }
        return treeList;
    }


}
