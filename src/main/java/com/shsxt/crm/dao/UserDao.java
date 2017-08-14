package com.shsxt.crm.dao;

import com.shsxt.crm.model.User;
import com.shsxt.crm.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {
    @Select("select id,user_name,password,true_name,email,phone,is_valid,create_date,update_date from t_user "
            + " where id = #{id}")
    User findById(@Param(value = "id") Integer id);

    @Select("select id,user_name,password,true_name,email,phone,is_valid,create_date,update_date from t_user ")
    List<User> find();

    @Select("select id,user_name,password,true_name,email from t_user where user_name= #{userName} ")
    User findByUserName(@Param(value = "userName") String userName);

    @Select("SELECT t1.id, t1.true_name FROM t_user t1 LEFT JOIN t_user_role t2 "
            + " on t1.id = t2.user_id WHERE t2.role_id = 3")
    List<UserVO> findCutomerManager();

}
