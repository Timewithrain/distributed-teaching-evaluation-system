package com.watermelon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * AuthorizationServer认证服务器的基本配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端详情服务
     * 客户端详细信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @param configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception{
        configurer.inMemory()
                //对client进行配置
                .withClient("client")
                //设置加密方式，此处加密方式为BCryptPasswordEncoder，由SecurityConfig.passwordEncoder()完成注册
                .secret(passwordEncoder.encode("123"))
                //设置授权方式：授权码模式
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                //授权范围：scope1、scope2
                .scopes("scope1","scope2")
                //设置资源名称
                .resourceIds("resource1")
                //设置资源Id
                .redirectUris("http://cn.bing.com")
                .autoApprove(true);
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束(待完善)
     * 目前未对token进行验证
     * 应改为checkTokenAccess("isAuthenticated")
     * @param securityConfigurer
     * @version 1.0
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer){
        //配置token获取和验证时的策略
        securityConfigurer.tokenKeyAccess("permitAll()")//oauth/token_key
                  .checkTokenAccess("permitAll()")//oauth/check_token
                  .allowFormAuthenticationForClients();
    }

    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);

        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(300);
        services.setRefreshTokenValiditySeconds(300);
        return services;
    }

}
