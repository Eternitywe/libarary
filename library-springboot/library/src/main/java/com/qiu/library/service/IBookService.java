package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.entity.Book;

import java.util.List;

public interface IBookService {

    List<Book> SelectAllBook();

    PageInfo<BookPageVo> page(BookPageVo bookPageVo);

    void save(Book book);

    BookPageVo getById(Integer id);

    void update(Book book);

    void deleteById(Integer id);
}
