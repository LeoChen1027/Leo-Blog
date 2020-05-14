package cn.leo.leoblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//有效的配置类
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Path/**").addResourceLocations("file:D:\\leoproject\\leo-blog\\src\\main\\resources\\static\\images\\");
        registry.addResourceHandler("/Static/**").addResourceLocations("file:D:\\leoproject\\leo-blog\\src\\main\\resources\\static\\");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
