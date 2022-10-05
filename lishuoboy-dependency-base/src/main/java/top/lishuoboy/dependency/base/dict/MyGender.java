package top.lishuoboy.dependency.base.dict;

/**
 * 性别
 *
 * @author lishuoboy
 * @date 2022.10.05 10:55
 */
public enum MyGender {
    male("男"),
    female("女"),
    unkown("未知");

    /** 备注 */
    private String remark;

    MyGender(String remark) {
        this.remark = remark;
    }
}
