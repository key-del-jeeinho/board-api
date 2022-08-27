package gg.dak.board_api.domain.post.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("post")
class PostProperties(
    val dailyPostLimit: Int
)