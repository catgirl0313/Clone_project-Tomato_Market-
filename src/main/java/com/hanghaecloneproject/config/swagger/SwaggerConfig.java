package com.hanghaecloneproject.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
              .select()
//              .apis(RequestHandlerSelectors.any()) // 모든 RequestMapping URI 추출
              //.paths(PathSelectors.ant("/v2/**")) // 경로 패턴 URI만 추출
              .apis(RequestHandlerSelectors.basePackage("com")) // 패키지 기준 추출
              .paths(PathSelectors.any())
              .build()
              .apiInfo(apiInfo());
//              .useDefaultResponseMessages(false); // Response 응답 메시지 디폴트값 적용 X
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
              .title("REST API")
              .description("항해 클론 프로젝트: 토마토마켓")
              .version("v1")
              .termsOfServiceUrl("서비스 약관 URL")
              .license("License")
              .licenseUrl("ec2-54-180-105-24.ap-northeast-2.compute.amazonaws.com")
              .build();
    }
}