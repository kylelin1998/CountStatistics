package web.entity.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class OnlineCountGenerateParamVO implements Serializable {

    @NotBlank(message = "请填写验证码")
    private String code;

    @NotBlank(message = "请填写安全码")
    private String securityCode;

}
