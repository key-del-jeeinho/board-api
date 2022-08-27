package gg.dak.board_api

import gg.dak.board_api.domain.account.config.JwtProperties
import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.post.config.PostProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties(LoginProperties::class, JwtProperties::class, PostProperties::class)
class BoardApiApplication

fun main(args: Array<String>) {
    runApplication<BoardApiApplication>(*args)
}
