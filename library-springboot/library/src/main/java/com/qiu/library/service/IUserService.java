package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.UserDTO;
import com.qiu.library.common.vo.UserPageVo;
import com.qiu.library.entity.User;
import java.util.List;

public interface IUserService {

    List<User> selectAllUsers();

    PageInfo<User> page(UserPageVo userPageVo);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void account(UserDTO userDTO);

    void changeStatus(Integer id);
}
