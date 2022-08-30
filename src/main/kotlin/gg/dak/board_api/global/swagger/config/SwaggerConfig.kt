package gg.dak.board_api.global.swagger.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun docket(): Docket = Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("gg.dak.board_api"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))

    @Bean
    fun securityContext(): SecurityContext =
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            .operationSelector { true }
            .build()

    private fun defaultAuth(): List<SecurityReference> =
        arrayOf(AuthorizationScope("global", "accessEverything"))
            .let { listOf(SecurityReference("Authorization", it)) }

    private fun apiKey() = ApiKey("Authorization", "Authorization", "header")

    private fun apiInfo() = ApiInfoBuilder()
            .title("Board API")
            .description("소규모 게시글 서비스 API입니다. (Authorization 헤더 사용시 'Bearer 'prefix를 붙여주세요!)")
            .contact(Contact("Raul", "https://www.github.com/key-del-jeeinho", "recruit.raul@gmail.com"))
            .version("1.0").build()
}