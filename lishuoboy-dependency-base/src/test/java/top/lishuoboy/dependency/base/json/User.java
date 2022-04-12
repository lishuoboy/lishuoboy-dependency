package top.lishuoboy.dependency.base.json;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lishuoboy
 * @date 2022/2/8 13:24
 */
@Data
@Accessors(chain = true)
public class User {
    private int id = 1;
    private String name = "硕";
}