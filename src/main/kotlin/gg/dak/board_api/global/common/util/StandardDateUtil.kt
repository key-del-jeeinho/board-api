package gg.dak.board_api.global.common.util

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class StandardDateUtil: DateUtil {
    override fun dateTimeNow(): LocalDateTime = LocalDateTime.now()
}