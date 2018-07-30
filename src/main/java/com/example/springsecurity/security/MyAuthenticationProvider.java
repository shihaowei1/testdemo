package com.example.springsecurity.security;

import com.example.springsecurity.entity.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

//AuthenticationProvider 提供用户UserDetails的具体验证方式，在这里可以自定义用户密码的加密、验证方式等等
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService userService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    /**
     * 验证用户信息
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户的输入信息
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);

        //检查用户名和密码
        if (user == null) {
            throw new UsernameNotFoundException("用户名出错！");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("密码错误！");
        }

        //调用检查验证码
//        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
//        String verifyCode = details.getVerifyCode();
//        if (!validateVerify(verifyCode)) {
//            throw new DisabledException("验证码输入错误");
//        }

        /******************************* 返回用户权限信息，固定写法 ********************************/
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, password, authorities);
        return auth;
        /*******************************************************************************************/
    }

        //判断验证码
        private boolean validateVerify(String inputVerify) {
            //获取当前线程绑定的request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
            inputVerify = inputVerify.toLowerCase();

            return validateCode.equals(inputVerify);
        }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
