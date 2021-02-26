package com.watermelon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

/**
 * 配置AuthorizationServer认证服务器
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

    @Autowired
    private JwtAccessTokenConverter tokenConverter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public JwtAccessTokenConverter tokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("jwt_key");
        return tokenConverter;
    }

    /**
     * 将token保存在内存中
     * @return RedisTokenStore
     * @version 2.0
     */
    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
//        return new JwtTokenStore(tokenConverter());
    }

    /**
     * 配置客户端详情服务
     * <p>客户端详细信息在这里进行初始化，可以把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * <p>授权类型
     * <p>授权码类型:authorization_code
     * <p>隐式授权类型:implicit
     * <p>密码类型:password
     * <p>客户端凭据类型:client_credentials
     * @param configurer ClientDetailsServiceConfigurer
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
     * 配置令牌端点(Token Endpoint)的安全约束
     * @param securityConfigurer
     * @version 2.0
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer){
        //配置token获取和验证时的策略
        securityConfigurer.tokenKeyAccess("permitAll()")//开启/oauth/token_key接口无权限访问
                  .checkTokenAccess("isAuthenticated()")//开启/oauth/check_token接口认证后访问
                  .allowFormAuthenticationForClients();
    }

    /**
     * 配置授权(authorization)以及令牌(token)的访问端点和令牌服务(token services)
     * @param endpointsConfigurer AuthorizationServerEndpointsConfigurer
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer){
        endpointsConfigurer.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
//                .authorizationCodeServices((new InMemoryAuthorizationCodeServices()))
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }

    /**
     * 配置token服务的存储及token类型等信息
     * @return services DefaultTokenServices
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);

        //设置token的存储方式及其过期时间和刷新时间
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(300);
        services.setRefreshTokenValiditySeconds(300);

        //Jwt方式存储token取消注释
//        TokenEnhancerChain chain = new TokenEnhancerChain();
//        chain.setTokenEnhancers(Arrays.asList(tokenConverter));
//        services.setTokenEnhancer(chain);
        return services;
    }

}
