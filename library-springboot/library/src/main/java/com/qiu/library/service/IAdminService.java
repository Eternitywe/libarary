package com.qiu.library.service;

import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.LoginDTO;
import com.qiu.library.common.request.LoginRequest;
import com.qiu.library.common.request.changePasswordRequest;
import com.qiu.library.common.vo.AdminPageVo;
import com.qiu.library.entity.Admin;

import java.util.List;

public interface IAdminService {

    List<Admin> selectAllUsers();

    PageInfo<Admin> page(AdminPageVo adminPageVo);

    void save(Admin admin);

    Admin getById(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest admin);

    void changePassword(changePasswordRequest admin);

    void changeStatus(Integer id);
}
