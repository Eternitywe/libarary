package com.qiu.library.mapper;

import com.qiu.library.common.dto.LoginDTO;
import com.qiu.library.common.request.LoginRequest;
import com.qiu.library.common.vo.AdminPageVo;
import com.qiu.library.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface AdminMapper {
    List<Admin> selectAllUsers();

    List<Admin> listByCondition(AdminPageVo adminPageVo);

    void save(Admin admin);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    Admin login(LoginRequest admin);

    void changeStatus(Integer id);
}
