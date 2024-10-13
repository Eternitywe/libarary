package com.qiu.library.mapper;

import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BookMapper {
    List<Book> SelectAllBook();

    List<BookPageVo> listByCondition(BookPageVo bookPageVo);

    void save(Book book);

    BookPageVo getById(Integer id);

    void updateById(Book book);

    void deleteById(Integer id);

    Book getByNo(String bookNo);

    void updateNumsByNo(String bookNo);
}
