package com.qiu.library.mapper;

import com.qiu.library.common.dto.BorrowReturCountDTO;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.entity.Book;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BorrowMapper {
    List<Borrow> SelectAllBorrow();

    List<BorrowPageVo> listByCondition(BorrowPageVo borrowPageVo);

    void save(Borrow borrow);

    void updateById(Borrow borrow);

    void deleteById(Integer id);

    Borrow getById(Integer id);

    void updateStatusById(Retur retur);

    List<BorrowReturCountDTO> getCountByTimeRange(@Param("timeRange") String timeRange);
}
