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
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        startH2Server();
        return application.sources(CommunityApplication.class);
    }
    private static void startH2Server() {
        try {
            Server h2Server = Server.createTcpServer().start();
            if (h2Server.isRunning(true)) {
                log.info("H2 server was started and is running.");
            } else {
                throw new RuntimeException("Could not start H2 server.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to start H2 server: ",e);
        }
    }

}
