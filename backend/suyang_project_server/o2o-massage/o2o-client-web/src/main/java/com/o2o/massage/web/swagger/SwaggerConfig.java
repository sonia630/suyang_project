package com.o2o.massage.web.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /*@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.o2o.massage.web.client"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("h5客户端接口")
                .description("h5客户端接口")
                .version("VERSION")
                *//*.termsOfServiceUrl("http://terms-of-services.url")
                .license("LICENSE")
                .licenseUrl("http://url-to-license.com")*//*
                .build();
    }*/

    @Bean
    public Docket backendRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("管理后台").apiInfo(backendManagement()).select()
                .apis(RequestHandlerSelectors.basePackage("com.o2o.massage.web.backend")).paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo backendManagement() {
        return new ApiInfoBuilder().title("O2O RESTful APIs").description("管理后台接口")
                .termsOfServiceUrl("http://xxx").contact(new Contact("lawrence.lee", "", "")).version("1.0").build();
    }

    @Bean
    public Docket h5RestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("客户端").apiInfo(h5Client()).select()
                .apis(RequestHandlerSelectors.basePackage("com.o2o.massage.web.client")).paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo h5Client() {
        return new ApiInfoBuilder().title("O2O RESTful APIs").description("H5客户端接口")
                .termsOfServiceUrl("http://xxx").contact(new Contact("lawrence.lee", "", "")).version("1.0").build();
    }

}
