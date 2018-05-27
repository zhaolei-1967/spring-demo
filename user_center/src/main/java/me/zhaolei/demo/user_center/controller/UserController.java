package me.zhaolei.demo.user_center.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhaolei.demo.base.BaseRequest;
import me.zhaolei.demo.base.BaseResponse;
import me.zhaolei.demo.base.MethedNotValidateToken;
import me.zhaolei.demo.base.SuperBootException;
import me.zhaolei.demo.user_center.entity.User;
import me.zhaolei.demo.user_center.repositories.UserRepository;
import me.zhaolei.demo.user_center.service.LoginUser;
import me.zhaolei.demo.user_center.service.RegisterUser;
import me.zhaolei.demo.user_center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 用户管理 </b>
 * <p>
 * 功能描述:提供用户基本操作，CRUD操作
 * </p>
 *
 * @author jesion
 * @date 2017/9/7
 * @time 21:32
 * @Path org.superboot.controller.UserController
 */
@RequestMapping("/users")
@Api(tags = "用户管理", description = "提供用户信息维护功能")
@RestController
public class UserController {

    @Autowired
    private UserRepository userReository;


    @Autowired
    private UserService userService;

    /**
     * 添加管理员，要求用户必须是管理员才可以添加
     *
     * @param regUser
     * @return
     * @throws
     */
    @ApiOperation(value = "注册管理用户", notes = "注册管理用户，只有管理员才可以访问")
    @RequestMapping(value = "/register_admin", method = RequestMethod.POST)
    public BaseResponse addAdmin(@RequestBody @Validated RegisterUser regUser) throws SuperBootException {
        return userService.register_admin(regUser);
    }

    @MethedNotValidateToken
    @ApiOperation(value = "注册普通用户", notes = "注册普通用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse addUser(@RequestBody @Validated RegisterUser regUser) throws SuperBootException {
        return userService.register(regUser);
    }


    @ApiOperation(value = "获取TOKEN", notes = "获取TOKEN")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @MethedNotValidateToken
    public BaseResponse createToken(
            @RequestBody @Validated LoginUser loginUser) throws SuperBootException {
        return userService.login(loginUser);
    }

    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息，用户只有是系统管理员才可以访问")
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
    public BaseResponse getAllUsers(@RequestBody @Validated BaseRequest request) throws SuperBootException {
        List<User> user = userReository.findAll();
        return new BaseResponse(user);
    }

    @ApiOperation(value = "获取单个用户", notes = "获取单个用户，用户只有是系统管理员才可以访问")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public BaseResponse getUser(@PathVariable Long id, @RequestBody @Validated BaseRequest request) throws SuperBootException {
        return new BaseResponse(userReository.findById(id));
    }
}

