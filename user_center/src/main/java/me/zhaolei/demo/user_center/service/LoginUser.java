package me.zhaolei.demo.user_center.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhaolei.demo.base.BaseRequest;
import org.hibernate.validator.constraints.Length;

/**
 * <b> 用户登录 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 * @author jesion
 * @date 2017/9/12
 * @time 13:27
 * @Path org.superboot.entity.LoginUser
 */
@Data
@ApiModel("登录基本信息")
public class LoginUser extends BaseRequest {

    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    @Length(min=6,max=20)
    private String password;

    public LoginUser() {
        super();
    }

    public LoginUser(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
