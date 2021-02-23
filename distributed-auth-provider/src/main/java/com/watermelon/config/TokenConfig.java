package com.watermelon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenConfig {

    @Bean
    public TokenStore tokenStore(){
        //将token放入内存中，也可以改为放入数据库中
        return new InMemoryTokenStore();
    }

}
