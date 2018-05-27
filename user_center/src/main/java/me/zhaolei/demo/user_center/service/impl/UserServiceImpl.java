package me.zhaolei.demo.user_center.service.impl;

import me.zhaolei.demo.base.BaseResponse;
import me.zhaolei.demo.base.BaseToken;
import me.zhaolei.demo.base.SuperBootCode;
import me.zhaolei.demo.base.SuperBootException;
import me.zhaolei.demo.user_center.entity.User;
import me.zhaolei.demo.user_center.entity.UserRole;
import me.zhaolei.demo.user_center.repositories.RoleRepository;
import me.zhaolei.demo.user_center.repositories.UserRepository;
import me.zhaolei.demo.user_center.repositories.UserRoleRepository;
import me.zhaolei.demo.user_center.service.LoginUser;
import me.zhaolei.demo.user_center.service.RegisterUser;
import me.zhaolei.demo.user_center.service.UserService;
import me.zhaolei.pub.Pub_Tools;
import me.zhaolei.utils.DateUtils;
import me.zhaolei.utils.MD5Util;
import me.zhaolei.utils.RandomStrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository sysUserRoleRepository;

    @Autowired
    private RoleRepository sysRoleRepository;

    @Autowired
    private Pub_Tools pubTools;

    @Override
    public BaseResponse register(RegisterUser regUser) {
        return saveUser(regUser, 0);
    }

    @Override
    public BaseResponse register_admin(RegisterUser regUser) {
        return saveUser(regUser, -1);
    }

    @Override
    public BaseResponse login(LoginUser loginUser) {
        String usercode = loginUser.getUsername();
        if (null == usercode) {
            throw new SuperBootException(SuperBootCode.USERNAME_NOT_FIND);
        }


        String rawPassword = loginUser.getPassword();
        if (null == rawPassword) {
            throw new SuperBootException(SuperBootCode.PASSWORD_NOT_FIND);
        }

        User user = userRepository.findByUserCode(usercode);
        if (null == user) {
            throw new SuperBootException(SuperBootCode.ACCOUNT_NOT_FIND);
        }

        BaseToken token = new BaseToken();
        token.setUserid(user.getPkUser());
        token.setUsername(usercode);

        return new BaseResponse(token);
    }


    /**
     * 生成用户信息
     *
     * @param regUser   注册提交内容
     * @param user_type 用户类型 -1管理员 其他为普通用户
     * @return
     */
    @Transactional("businessTransactionManager")
    public BaseResponse saveUser(RegisterUser regUser, int user_type) {

        String usercode = regUser.getUsercode();
        if (null == usercode) {
            throw new SuperBootException(SuperBootCode.USERNAME_NOT_FIND);
        }


        String rawPassword = regUser.getPassword();
        if (null == rawPassword) {
            throw new SuperBootException(SuperBootCode.PASSWORD_NOT_FIND);
        }

        if (userRepository.findByUserCode(usercode) != null) {
            throw new SuperBootException(SuperBootCode.ADD_ERROR_EXISTS);
        }

        String random = RandomStrUtils.genRandom();

        //对Rsa加密信息进行解密
        //rawPassword = Pub_Tools.RSAdecrypt(RSAUtils.DEFAULT_PRIVATE_KEY, rawPassword);


        //构造默认用户
        User SysUser = new User();
        //进行密码加密
        SysUser.setUserPassword(genPassWord(rawPassword, random));
        SysUser.setLastPasswordResetDate(DateUtils.getTimestamp());
        SysUser.setUserCode(usercode);
        SysUser.setRandom(random);
        SysUser.setPkUser(pubTools.genUUID());

        SysUser.setTs(DateUtils.getTimestamp());
        SysUser = userRepository.save(SysUser);


        //设置默认用户角色
        UserRole role = new UserRole();
        role.setPkUser(SysUser.getPkUser());
        role.setUserRoleId(pubTools.genUUID());
        if (-1 == user_type) {
            role.setPkRole(sysRoleRepository.findByRoleCode("ROLE_ADMIN").getPkRole());
        } else {
            role.setPkRole(sysRoleRepository.findByRoleCode("ROLE_USER").getPkRole());
        }

        role.setTs(DateUtils.getTimestamp());
        sysUserRoleRepository.save(role);

        BaseToken token = new BaseToken();
        token.setUserid(SysUser.getPkUser());
        token.setUsername(usercode);

        return new BaseResponse(SuperBootCode.ADD_SUCCESS, token);
    }

    /**
     * 生成密码
     *
     * @param passWord 明文密码
     * @param random   随机码
     * @return
     */
    public static String genPassWord(String passWord, String random) {
        String pdOne = MD5Util.MD5(passWord);
        return MD5Util.MD5(pdOne + random);
    }
}
