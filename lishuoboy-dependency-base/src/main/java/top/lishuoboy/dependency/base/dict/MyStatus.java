package top.lishuoboy.dependency.base.dict;

/**
 * 状态
 *
 * @author lishuoboy
 * @date 2022.10.05 10:55
 */
public enum MyStatus {
    enabled("正常"),
    disabled("停用");

    /** 备注 */
    private String remark;

    MyStatus(String remark) {
        this.remark = remark;
    }


    // 测试
    public static void main(String[] args) {
        System.out.println(MyStatus.enabled);         // enabled
        System.out.println(MyStatus.enabled.name());  // enabled
        System.out.println(MyStatus.enabled.remark);  // 正常
    }
}
