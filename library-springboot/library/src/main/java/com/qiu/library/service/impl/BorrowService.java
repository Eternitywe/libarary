package com.qiu.library.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.BorrowReturCountDTO;
import com.qiu.library.common.enums.StatusEnum;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.common.vo.BorrowPageVo;
import com.qiu.library.entity.Book;
import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.User;
import com.qiu.library.exception.ServiceException;
import com.qiu.library.mapper.BookMapper;
import com.qiu.library.mapper.BorrowMapper;
import com.qiu.library.mapper.ReturMapper;
import com.qiu.library.mapper.UserMapper;
import com.qiu.library.service.IBookService;
import com.qiu.library.service.IBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class BorrowService implements IBorrowService {

    @Resource
    private BorrowMapper borrowMapper;

    @Resource
    private ReturMapper returMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public List<Borrow> SelectAllBorrow() {
        return borrowMapper.SelectAllBorrow();
    }

    @Override
    public PageInfo<BorrowPageVo> page(BorrowPageVo borrowPageVo) {
        PageHelper.startPage(borrowPageVo.getPageNum(), borrowPageVo.getPageSize());
        List<BorrowPageVo> borrowPageVos = borrowMapper.listByCondition(borrowPageVo);
        for (BorrowPageVo borrow : borrowPageVos) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)){
                borrow.setNote("即将到期");
            }else if (now.isEqual(returnDate)){
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已过期");
            }else {
                borrow.setNote("正常");
            }
        }
        PageInfo<BorrowPageVo> pageInfo = new PageInfo<>(borrowPageVos);
        return pageInfo;
    }

    @Override
    @Transactional  //添加事务,所有操作完成才能进行数据更新,一但有错误,则进行数据回滚
    public void save(Borrow borrow) {
        // 校验用户的积分是否足够
        String userNo = borrow.getUserNo();
        User user = userMapper.getByNo(userNo);
        if (Objects.isNull(user)){
            throw new ServiceException(StatusEnum.NOTFOUND_USER_CODE.getCode(), StatusEnum.NOTFOUND_USER_CODE.getDesc());
        }
        //校验所借出的书籍数量是否大于0
        String bookNo = borrow.getBookNo();
        Book book = bookMapper.getByNo(bookNo);
        if (Objects.isNull(book)){
            throw new ServiceException(StatusEnum.NOTFOUND_BOOK_CODE.getCode(), StatusEnum.NOTFOUND_BOOK_CODE.getDesc());
        }
        if (book.getNums() < 1){
            throw new ServiceException(StatusEnum.NOT_BOOK_NUM_CODE.getCode(), "图书数量不足，无法借阅");
        }
        Integer account = user.getAccount();
        //Integer score = book.getScore();
        Integer score = book.getScore() * borrow.getDays();  // 借书天数*书籍积分 = 借书积分
        //校验用户账户余额
        if (account < score){
            throw new ServiceException(StatusEnum.NOT_USER_ACCOUNT_CODE.getCode(), "用户积分不足，无法借阅");
        }
        //更新用户余额
        Integer newAccount = account - score;
        user.setAccount(newAccount);
        userMapper.updateById(user);
        //更新图书数量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

        borrow.setScore(score);
        LocalDate returnDate = DateUtil.offsetDay(DateUtil.date(), borrow.getDays()).toLocalDateTime().toLocalDate();
        borrow.setReturnDate(returnDate);
        //新增借书记录
        borrowMapper.save(borrow);
    }

    @Override
    public void update(Borrow borrow) {
        borrow.setUpdatetime(new Date());
        borrowMapper.updateById(borrow);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public Map<String, Object> lineCharts(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date now = new Date();
        List<DateTime> dateRange;
        switch (timeRange){
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(now,-6), now, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(now,-29), now, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(now,-59), now, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateList = getDateList(dateRange);
        map.put("date",dateList);
        List<BorrowReturCountDTO> borrowCount = borrowMapper.getCountByTimeRange(timeRange);
        List<BorrowReturCountDTO> returCount = returMapper.getCountByTimeRange(timeRange);
        map.put("borrow",getCountList(borrowCount,dateList));
        map.put("retur",getCountList(returCount,dateList));
        return map;
    }

    private List<String> getDateList(List<DateTime> dateTimeList){
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)){
            return list;
        }
        dateTimeList.forEach(dateTime -> list.add(DateUtil.formatDate(dateTime)));
        return list;
    }

    private List<Integer> getCountList(List<BorrowReturCountDTO> countDTOList,List<String> dateList){
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countDTOList)){
            return list;
        }
        for (String date : dateList) {
            //map(BorrowReturCountDTO::getCount)取出count属性值
            //orElse 没匹配给赋值为0
            Integer count = countDTOList.stream().filter(countDTO -> date.equals(countDTO.getDate()))
                    .map(BorrowReturCountDTO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

}
