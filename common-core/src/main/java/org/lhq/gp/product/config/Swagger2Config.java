package org.lhq.gp.product.config;

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
 * @author lhq
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
                .title("spring-cloud")
                .description("这是我打算拿来写毕业设计的项目")
                .termsOfServiceUrl("https://baidu.com")
                .contact(new Contact("陆海琦 ", "https://baidu.com", "lhq_hcl@foxmail.com"))
                .version("1.0.0").build();
    }
}