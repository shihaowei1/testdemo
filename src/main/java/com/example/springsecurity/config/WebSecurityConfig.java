package com.example.springsecurity.config;

//Spring Security配置

import com.example.springsecurity.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//允许进入页面方法前检验
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //与图片验证码有关
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Autowired
    private MyAuthenticationProvider provider;

    @Autowired
    HttpServletRequest request;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/index.html","/getImg","/getAllUserInfo","/addNewGoods","/getUserInfo",
                        "/getVerifyCode", "/login/error", "/login.html",
                        "/css/**", "/register.html", "/register")
                .permitAll()
                .anyRequest()//其他url需要权限验证
                .authenticated()
                .antMatchers("/admin.html").hasAnyRole("ROLE_ADMIN")
                .antMatchers("/user.html").hasAnyRole("ROLE_USER")
                .and()
                .formLogin()
                .loginPage("/login.html")//自定义登录页url
                .loginProcessingUrl("/login")//登录请求拦截的url
                .usernameParameter("username")//用户名的请求字段
                .passwordParameter("password")//密码的请求字段
                .authenticationDetailsSource(authenticationDetailsSource)
                .successForwardUrl("/login-success")//登录成功url
                .failureUrl("/login/error");//登录出错url
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth){
        //将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }
}
