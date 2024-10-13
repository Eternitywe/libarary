package com.qiu.library.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.library.common.dto.LoginDTO;
import com.qiu.library.common.enums.StatusEnum;
import com.qiu.library.common.request.LoginRequest;
import com.qiu.library.common.request.changePasswordRequest;
import com.qiu.library.common.utils.TokenUtils;
import com.qiu.library.common.vo.AdminPageVo;
import com.qiu.library.entity.Admin;
import com.qiu.library.exception.ServiceException;
import com.qiu.library.mapper.AdminMapper;
import com.qiu.library.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Resource
    private AdminMapper adminMapper;


    private static final String DEFAULT_PASSWORD = "123456";

    private static final String KEY = "xiaoqiu";


    @Override
    public List<Admin> selectAllUsers() {
        return adminMapper.selectAllUsers();
    }

    @Override
    public PageInfo<Admin> page(AdminPageVo adminPageVo) {
        PageHelper.startPage(adminPageVo.getPageNum(), adminPageVo.getPageSize());
        List<Admin> users = adminMapper.listByCondition(adminPageVo);
        PageInfo<Admin> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public void save(Admin admin) {
        if (StrUtil.isBlank(admin.getPassword())){
            admin.setPassword(DEFAULT_PASSWORD);
        }
        // 加密
        String ciphertext = SaSecureUtil.aesEncrypt(KEY, admin.getPassword());
        admin.setPassword(ciphertext);
        try {
            adminMapper.save(admin);
        }
        catch (DuplicateKeyException e){  //捕获用户名重复数据库DuplicateKeyException错误,对异常进行处理
            log.error("新增管理员失败");
            throw new ServiceException(StatusEnum.MORE_USERNAME_CODE.getCode(),StatusEnum.MORE_USERNAME_CODE.getDesc());
        }

    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest admin) {
        // 加密
        String ciphertext = SaSecureUtil.aesEncrypt(KEY, admin.getPassword());
        admin.setPassword(ciphertext);
        Admin user = adminMapper.login(admin);
        if (user == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.isStatus()){
            throw new ServiceException(StatusEnum.DISPLAY_USER_CODE.getCode(),StatusEnum.DISPLAY_USER_CODE.getDesc());
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(user, loginDTO);
        //生成token
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword());
        loginDTO.setToken(token);

        return loginDTO;
    }

    @Override
    public void changePassword(changePasswordRequest admin) {
        Admin user = adminMapper.getById(admin.getId());
        if (user == null){
            throw new ServiceException("用户不存在");
        }
        // 加密
        String ciphertext = SaSecureUtil.aesEncrypt(KEY, admin.getNewPassword());
        user.setPassword(ciphertext);
        user.setUpdatetime(new Date());
        adminMapper.updateById(user);
    }

    @Override
    public void changeStatus(Integer id) {
        adminMapper.changeStatus(id);
    }
}
