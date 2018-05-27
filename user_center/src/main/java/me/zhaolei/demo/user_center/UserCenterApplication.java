package me.zhaolei.demo.user_center;

import me.zhaolei.pub.Pub_LocalTools;
import me.zhaolei.pub.Pub_Tools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class UserCenterApplication {
    @Bean
    Pub_Tools createTools() {
        return new Pub_Tools();
    }

    @Bean
    Pub_LocalTools createLocalTools() {
        return new Pub_LocalTools();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
