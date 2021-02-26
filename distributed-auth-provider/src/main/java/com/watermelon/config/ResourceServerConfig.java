package com.watermelon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * ResourceServer资源服务的基本配置
 * <p>由于资源服务器和认证服务器集中于同一微服务实体,
 * 因此需要设置spring-security和资源服务的优先级，
 * spring-security优先级高于资源服务
 */
@Order(3)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 设置Token存储方式
     * tokenStore为AuthorizationServerConfig中tokenStore()方法注入的Bean
     * @param resources ResourceServerSecurityConfigurer
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }

    /**
     * 配置受资源服务器保护的资源链接,仅接受签名校验
     * Session生成策略
     * always – 如果session不存在总是需要创建
     * ifRequired – 仅当需要时，创建session(默认配置)
     * never – 框架从不创建session，但如果已经存在，会使用该session
     * stateless – Spring Security不会创建session，或使用session
     *
     * "migrateSession()"，即认证时，创建一个新http session，原session失效，属性从原session中拷贝过来
     * "none()"，原session保持有效；
     * "newSession()"，新创建session，且不从原session中拷贝任何属性。
     * @param http HttpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.sessionManagement().sessionFixation().none();

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/actuator/hystrix.stream").permitAll()
                .anyRequest().authenticated();//校验所有请求
    }

}
