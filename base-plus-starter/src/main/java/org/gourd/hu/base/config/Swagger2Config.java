package org.gourd.hu.base.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger接口文档
 * 访问：http://localhost:10001/doc.html#/
 *
 * @author gourd.hu
 */
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {
    @Bean
    public Docket gourdHuDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("模块demo接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.gourd.hu.demo.controller"))
                .paths(PathSelectors.any()).build();
    }

    public static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-cloud-plus")
                .description("spring-cloud-plus 是以spring-cloud-alibaba为基础并整合一些常用框架的分布式基础开发平台")
                .termsOfServiceUrl("https://blog.csdn.net/HXNLYW")
                .contact(new Contact("葫芦胡 ", "https://blog.csdn.net/HXNLYW", "13584278267@163.com"))
                .version("1.0.0").build();
    }
}