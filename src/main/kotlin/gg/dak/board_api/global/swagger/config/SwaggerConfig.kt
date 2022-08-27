package gg.dak.board_api.global.swagger.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
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
}