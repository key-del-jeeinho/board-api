package gg.dak.board_api.global.security.config

import gg.dak.board_api.global.security.filter.JwtAuthenticateFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtAuthenticateFilter: JwtAuthenticateFilter, /*exceptionHandlerFilter: ExceptionHandlerFilter*/): SecurityFilterChain =
        http.csrf().disable()
            .cors().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/account/**").permitAll()
            .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**").permitAll()
            .and()
            .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}