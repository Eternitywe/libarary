package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.entity.Book;
import com.qiu.library.entity.Borrow;

import java.util.List;
import java.util.Map;

public interface IBorrowService {

    List<Borrow> SelectAllBorrow();

    PageInfo<BorrowPageVo> page(BorrowPageVo borrowPageVo);

    void save(Borrow borrow);

    void update(Borrow borrow);

    void deleteById(Integer id);

    Borrow getById(Integer id);

    Map<String,Object> lineCharts(String timeRange);
}
