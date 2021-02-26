package com.watermelon.config;

import com.watermelon.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-Security的基本配置
 */
@Order(2)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置PasswordEncoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置spring-security的安全控制策略(此处待完善)
     * 目前所有用户可访问任何url
     * @param security
     * @throws Exception
     * @version 1.0
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
                //禁用csrf功能
        security.csrf().disable()
                //限定请求
                .authorizeRequests()
                //手动添加被限制的请求路径
                .antMatchers("/admin/**").hasRole("user:admin")
                .antMatchers("/teacher/**").hasRole("user:teacher")
                .antMatchers("/supervisor/**").hasRole("user:supervisor")
                .antMatchers("/user/login").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().permitAll()
                //对于没有配置权限的其他请求允许匿名访问
                .and().anonymous()
                //使用默认登录和退出页面
                .and().formLogin()
                .and().logout();
    }

    /**
     * 将AuthenticationManager认证管理器注入至Spring进行托管，
     * 以便在AuthorizationServerConfig授权服务配置中进行调用
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 将获取用户信息的服务注入至Spring进行托管
     * @return UserDetailsServiceImpl
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

}
