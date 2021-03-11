package ru.nstu.upp.minijira;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiniJiraApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniJiraApplication.class, args);
    }

    @Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().mapNulls(false).build();
    }
}