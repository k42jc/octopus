package com.dafy.octopus.rental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by liaoxudong
 * Date:2017/11/13
 */

@Configuration
@EnableSwagger2
public class Swagger2Config extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("userInfo", "用户信息"), getTags())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dafy.octopus.rental.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private Tag[] getTags() {
        Tag[] tags = {
                new Tag("goodsType", "商品类型，五级目录"),
                new Tag("dealStatus", "解决选项"),
                new Tag("productType", "产品类型"),
                new Tag("serverInfo", "客服记录信息"),
                new Tag("orderHandleInfo", "订单操作，用户信息操作接口"),
                new Tag("orderInfo", "订单详细信息、订单物流信息接口"),
                new Tag("sourceType", "通话记录来源类型，呼入、呼出、在线"),
        };
        return tags;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .version("1.0")
                .description("认证服务器API文档")
                .build();
    }
}
