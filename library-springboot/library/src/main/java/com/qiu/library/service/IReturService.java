package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.common.vo.ReturPageVo;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;

import java.util.List;

public interface IReturService {

    List<Retur> SelectAllRetur();

    PageInfo<ReturPageVo> page(ReturPageVo returPageVo);

    void save(Retur retur);

    void update(Retur retur);

    void deleteById(Integer id);

    Retur getById(Integer id);
}
