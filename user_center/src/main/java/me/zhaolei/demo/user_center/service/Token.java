package me.zhaolei.demo.user_center.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhaolei.demo.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <b> Token信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 * @author jesion
 * @date 2017/9/12
 * @time 13:30
 * @Path org.superboot.entity.Token
 */
@Data
@ApiModel("TOKEN信息")
public class Token extends BaseRequest {

    @ApiModelProperty(value = "Token")
    @NotBlank
    private String token;

    public Token() {
        super();
    }

    public Token(String token ) {
        this.token = token;
    }
}
