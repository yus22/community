package life.majiang.community;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.sql.SQLException;

@SpringBootApplication
@Slf4j
@MapperScan("life.majiang.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {


        SpringApplication.run(CommunityApplication.class, args);
    }

}
