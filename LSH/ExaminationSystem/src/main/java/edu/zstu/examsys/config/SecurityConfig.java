package edu.zstu.examsys.config;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.filter.AuthenticationFilter;
import edu.zstu.examsys.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .loginProcessingUrl("/login");
        //http.rememberMe().rememberMeParameter()
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/user/needLogin")
                .loginProcessingUrl("/formLogin");

        http.authorizeRequests()
                .antMatchers("/user/login", "/user/needLogin").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
        http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8081");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl("/user/login");
        filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                Map<String, Object> res = new HashMap<>();
                res.put("errCode", 0);
                res.put("errMsg", "登录成功");
                User user = (User) authentication.getPrincipal();
                Map<String, Object> userData = new HashMap<>();
                userData.put("id", user.getId());
                userData.put("username", user.getUsername());
                userData.put("nickname", user.getNickname());
                res.put("userData", userData);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSON.toJSONString(res));
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                Map<String, Object> res = new HashMap<>();
                res.put("errCode", 1);
                res.put("errMsg", "登录失败，" + exception.getMessage());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSON.toJSONString(res));
            }
        };
    }
}
