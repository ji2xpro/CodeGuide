package com.xxx.yyy.springbootguide.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author: maoyan
 * @date: 2020/2/22 13:57:57
 * @description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_OK).message("http响应成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_BAD_REQUEST).message("Bad Request 请求出现语法错误,一般是请求参数不对").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_UNAUTHORIZED).message("Unauthorized 访问被拒绝").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_FORBIDDEN).message("Forbidden 资源不可用").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_NOT_FOUND).message("Not Found 无法找到指定位置的资源").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message("服务器内部错误,请联系Java后台开发人员!!!").build());

        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo指定测试文档基本信息，这部分将在页面展示
                .apiInfo(apiInfo())
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, responseMessages)
//                .globalResponseMessage(RequestMethod.POST, responseMessages)
//                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .groupName("默认分组")
                .select()
                /**
                 * apis()控制哪些接口暴露给swagger
                 * 1. RequestHandlerSelectors.any() 所有都暴露
                 * 2. RequestHandlerSelectors.basePackage("com.info.*")  指定包位置
                 */
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 基本信息，页面展示
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试项目标题")
                .description("接口描述")
                .termsOfServiceUrl("http://www.icarus.com")
                //联系人实体类
                .contact(new Contact("名字", "网址", "邮箱"))
                //版本号
                .version("1.0.0-SNAPSHOT")
                .license("The Apache License")
                .licenseUrl("http://www.baidu.com")
                .build();
    }
}
