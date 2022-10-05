package top.lishuoboy.dependency.base.dict;

/**
 * 是否
 *
 * @author lishuoboy
 * @date 2022.10.05 10:55
 */
public enum MyYesNo {
    yes("是"),
    no("否");

    /** 备注 */
    private String remark;

    MyYesNo(String remark) {
        this.remark = remark;
    }
}
