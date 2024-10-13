package com.qiu.library.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.enums.StatusEnum;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.common.vo.ReturPageVo;
import com.qiu.library.entity.Book;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;
import com.qiu.library.entity.User;
import com.qiu.library.exception.ServiceException;
import com.qiu.library.mapper.BookMapper;
import com.qiu.library.mapper.BorrowMapper;
import com.qiu.library.mapper.ReturMapper;
import com.qiu.library.mapper.UserMapper;
import com.qiu.library.service.IBorrowService;
import com.qiu.library.service.IReturService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReturService implements IReturService {

    @Resource
    private ReturMapper returMapper;

    @Resource
    private BorrowMapper borrowMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BookMapper bookMapper;


    @Override
    public List<Retur> SelectAllRetur() {
        return returMapper.SelectAllRetur();
    }

    @Override
    public PageInfo<ReturPageVo> page(ReturPageVo returPageVo) {
        PageHelper.startPage(returPageVo.getPageNum(), returPageVo.getPageSize());
        List<ReturPageVo> returPageVos = returMapper.listByCondition(returPageVo);
        PageInfo<ReturPageVo> pageInfo = new PageInfo<>(returPageVos);
        return pageInfo;
    }

    @Override
    @Transactional
    public void save(Retur retur) {
        retur.setStatus(StatusEnum.RETURNED);
        borrowMapper.updateStatusById(retur);
        retur.setRealDate(LocalDate.now());
        returMapper.save(retur);
        bookMapper.updateNumsByNo(retur.getBookNo());//增加图书数量
        Book book = bookMapper.getByNo(retur.getBookNo());
        if (book!= null){
            long until = 0;
            //返还用户积分
            if (retur.getRealDate().isBefore(retur.getReturnDate())){
                 until = retur.getRealDate().until(retur.getReturnDate(), ChronoUnit.DAYS);

            }else if (retur.getRealDate().isAfter(retur.getReturnDate())) {
                until = - retur.getReturnDate().until(retur.getRealDate(), ChronoUnit.DAYS);
            }
            int score = book.getScore() * (int) until;
            User user = userMapper.getByNo(retur.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (score < 0){
                if (account < 0){
                    user.setStatus(false);
                }
            }
            userMapper.updateById(user);
        }

    }

    @Override
    public void update(Retur retur) {
        retur.setUpdatetime(new Date());
        returMapper.updateById(retur);
    }

    @Override
    public void deleteById(Integer id) {
        returMapper.deleteById(id);
    }

    @Override
    public Retur getById(Integer id) {
        return returMapper.getById(id);
    }

}
