package top.lishuoboy.dependency.base.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuoboy
 * @date 2022/5/9 17:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /** id */
    private Integer id;
    /** 用户名 */
    private String name;
    /** 密码 */
    private String pwd;
}