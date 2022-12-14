package gg.dak.board_api.domain.account.util

interface JwtTokenUtil {
    fun generate(payload: Map<String, String>, expireSecond: Long): String
    fun decode(token: String): Map<String, String>
}
