package com.qiu.library.mapper;

import com.qiu.library.common.dto.BorrowReturCountDTO;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.common.vo.ReturPageVo;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ReturMapper {
    List<Retur> SelectAllRetur();

    List<ReturPageVo> listByCondition(ReturPageVo returPageVo);

    void save(Retur retur);

    void updateById(Retur retur);

    void deleteById(Integer id);

    Retur getById(Integer id);

    List<BorrowReturCountDTO> getCountByTimeRange(String timeRange);
}
