package com.tc.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@Configuration
@EnableKnife4j
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        	//	.host("localhost:8081") 
                .securitySchemes(security())
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(BaseConfig.ROOT))
                .paths(PathSelectors.any())
                .build();
    }

    private List<ApiKey> security() {
        return Lists.newArrayList(new ApiKey("token", "token", "header"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mud")
                .description("Restful Api")
                .version(BaseConfig.version)
                .license("泥头车")
                .licenseUrl("https://tongchuangnet.com")
                .build();
    }
}
