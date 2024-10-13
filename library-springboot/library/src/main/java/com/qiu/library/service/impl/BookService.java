package com.qiu.library.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.CategoryDTO;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.common.vo.CategoryPageVo;
import com.qiu.library.entity.Book;
import com.qiu.library.entity.Category;
import com.qiu.library.exception.ServiceException;
import com.qiu.library.mapper.BookMapper;
import com.qiu.library.mapper.CategoryMapper;
import com.qiu.library.service.IBookService;
import com.qiu.library.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BookService implements IBookService {

    @Resource
    private BookMapper bookMapper;


    @Override
    public List<Book> SelectAllBook() {
        return bookMapper.SelectAllBook();
    }

    @Override
    public PageInfo<BookPageVo> page(BookPageVo bookPageVo) {
        PageHelper.startPage(bookPageVo.getPageNum(), bookPageVo.getPageSize());
        List<BookPageVo> books = bookMapper.listByCondition(bookPageVo);
        PageInfo<BookPageVo> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public void save(Book book) {
        try {
            bookMapper.save(book);
        }
        catch (Exception e){
            log.error("保存图书信息失败，{}",e.getMessage());
            throw new ServiceException("保存图书信息失败",e);
        }
    }

    @Override
    public BookPageVo getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book book) {
        try {
            book.setUpdatetime(new Date());
            bookMapper.updateById(book);
        }catch (Exception e){
            log.error("更新图书信息失败，{}",e.getMessage());
            throw new ServiceException("更新图书信息失败",e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

}
