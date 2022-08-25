package gg.dak.board_api

import gg.dak.board_api.domain.account.config.JwtProperties
import gg.dak.board_api.domain.account.config.LoginProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(LoginProperties::class, JwtProperties::class)
class BoardApiApplication

fun main(args: Array<String>) {
    runApplication<BoardApiApplication>(*args)
}
