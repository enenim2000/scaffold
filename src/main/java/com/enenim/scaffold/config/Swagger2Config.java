package com.enenim.scaffold.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class Swagger2Config {
/*

    @Bean
    public Docket apiSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .apiInfo(apiInfo())
                .globalOperationParameters(
                        new ArrayList<Parameter>(){{
                            new ParameterBuilder()
                                    .name("access_token")
                                    .description("Access Token")
                                    .modelRef(new ModelRef("string"))
                                    .parameterType("header")
                                    .required(true)
                                    .build();
                        }}
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("CUREXZONE REST API")
                .contact(new Contact("Enenim Asukwo", "www.technical.curexzone.net", "support@curexzone.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
*/



    @Bean
    public Docket api() {
        
        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(new ArrayList<Parameter>(){{

            new ParameterBuilder()
                    .name("token")
                    .description("Auth token")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(true)
                    .build();

            new ParameterBuilder()
                    .name("api-key")
                    .description("Swagger Api_key")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(true)
                    .build();

        }}).select()
            .apis(RequestHandlerSelectors.basePackage("com.enenim.scaffold.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());

    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("CUREXZONE REST API")
            .contact(new Contact("Enenim Asukwo", "www.technical.curexzone.net", "support@curexzone.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
}