package cn.bxait.zhxy.pojo;

import lombok.Data;

/**
 * @author ChenCheng
 * @create 2022-06-08 19:21
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
