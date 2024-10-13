package com.qiu.library.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.Result;
import com.qiu.library.common.dto.UserDTO;
import com.qiu.library.common.vo.UserPageVo;
import com.qiu.library.entity.User;
import com.qiu.library.mapper.UserMapper;
import com.qiu.library.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public PageInfo<User> page(UserPageVo userPageVo) {
        PageHelper.startPage(userPageVo.getPageNum(), userPageVo.getPageSize());
        List<User> users = userMapper.listByCondition(userPageVo);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public void save(User user) {
        //会员卡号  日期+随机数
        String username = DateUtil.format(new Date(),"yyyyMMdd") +Math.abs(IdUtil.fastSimpleUUID().hashCode());
        user.setUsername(username);
        userMapper.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void account(UserDTO userDTO) {
        User user = userMapper.getById(userDTO.getId());
        Integer score = userDTO.getScore();
        Integer account = user.getAccount();
        Integer newAccount = account + score;
        user.setAccount(newAccount);
        userMapper.updateById(user);
    }

    @Override
    public void changeStatus(Integer id) {
        userMapper.changeStatus(id);
    }
}
