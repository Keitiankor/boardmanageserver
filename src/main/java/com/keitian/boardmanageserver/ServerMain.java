package com.keitian.boardmanageserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ServerMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerMain.class)
                .properties("Spring.Config.locatoin=classpath:starter.yml")
                .run(args);
    }
}
