package top.lishuoboy.dependency.sb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.lishuoboy.dependency.sb.sb.MySbUtil;

@SpringBootApplication
public class LishuoboyDependencySbApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = MySbUtil.run(LishuoboyDependencySbApplication.class, args);

//        MySpringUtil.printBeans(context, true);
//        MySpringUtil.printBeans(context);
//        MySpringUtil.printBeansDisjunction(context,true);

    }
}