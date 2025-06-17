package cn.nero.lang.core.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.nero.lang.core.mapper")
public class MybatisConfig {

}
