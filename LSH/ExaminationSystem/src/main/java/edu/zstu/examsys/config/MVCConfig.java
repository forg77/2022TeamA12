package edu.zstu.examsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET","POST")
//                .allowedHeaders("*")
//                .exposedHeaders("Access-Control-Allow-Origin")
//                .allowCredentials(true);
//    }

//    @Bean
//    public CorsFilter corsFilter() {
//
//        //创建CorsConfiguration对象后添加配置
//        CorsConfiguration config = new CorsConfiguration();
//        //设置放行哪些原始域
//        config.addAllowedOriginPattern("*");
//        //放行哪些原始请求头部信息
//        config.addAllowedHeader("*");
//        //暴露哪些头部信息
//        config.addExposedHeader("*");
//        //放行哪些请求方式
////        config.addAllowedMethod("GET");     //get
////        config.addAllowedMethod("PUT");     //put
////        config.addAllowedMethod("POST");    //post
////        config.addAllowedMethod("DELETE");  //delete
//        config.addAllowedMethod("*");     //放行全部请求
//
//        //是否发送Cookie
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:8081");
//
//        //2. 添加映射路径
//        UrlBasedCorsConfigurationSource corsConfigurationSource =
//                new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", config);
//        //返回CorsFilter
//        return new CorsFilter(corsConfigurationSource);
//    }
}