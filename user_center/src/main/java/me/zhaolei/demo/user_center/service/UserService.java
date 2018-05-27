package me.zhaolei.demo.user_center.service;


import me.zhaolei.demo.base.BaseResponse;
import me.zhaolei.demo.base.SuperBootException;

public interface UserService {

    /**
     * 注册普通用户
     *
     * @param regUser
     * @return
     */
    BaseResponse register(RegisterUser regUser) throws SuperBootException;

    /**
     * 注册管理员
     *
     * @param regUser
     * @return
     */
    BaseResponse register_admin(RegisterUser regUser) throws SuperBootException;

    /**
     * 获取TOKEN
     *
     * @param loginUser 登录用户
     * @return
     */
    BaseResponse login(LoginUser loginUser);

}

