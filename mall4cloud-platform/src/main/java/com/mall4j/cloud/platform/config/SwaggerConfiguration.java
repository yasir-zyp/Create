package com.mall4j.cloud.platform.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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

/**
 * Swagger文档，只有在测试环境才会使用
 *
 * @author lhd
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {

	@Bean
	public Docket baseRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.mall4j.cloud.platform.controller")).paths(PathSelectors.any())
				.build();
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("质量基础设施一站式服务平台接口文档").description("质量基础设施一站式服务平台接口文档Swagger版").termsOfServiceUrl("")
				.contact(new Contact("安徽质量云科技有限公司", "", "")).version("1.0").build();
	}

}
