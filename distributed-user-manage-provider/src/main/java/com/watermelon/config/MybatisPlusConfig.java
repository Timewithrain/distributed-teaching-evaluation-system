package com.watermelon.config;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        GlobalConfig.DbConfig dbconf = new GlobalConfig.DbConfig();
        dbconf.setIdType(IdType.ID_WORKER_STR);
        dbconf.setDbType(DbType.MYSQL);
        //设置仅对非null属性进行写入覆盖操作
        dbconf.setFieldStrategy(FieldStrategy.NOT_NULL);
        conf.setDbConfig(dbconf);
        return conf;
    }

}