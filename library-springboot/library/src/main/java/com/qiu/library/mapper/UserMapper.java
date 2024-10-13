package com.qiu.library.mapper;

import com.qiu.library.common.vo.UserPageVo;
import com.qiu.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();

    List<User> listByCondition(UserPageVo userPageVo);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    User getByNo(String userNo);

    void updateScoreByNo(@Param("score") int score,@Param("userNo") String userNo);

    void changeStatus(Integer id);
}
