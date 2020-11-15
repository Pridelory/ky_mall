package ltd.newbee.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("ltd.wangmeng.mall.dao")
@SpringBootApplication
public class NewBeeMallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewBeeMallManageApplication.class, args);
    }

}
