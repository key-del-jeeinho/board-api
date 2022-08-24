package gg.dak.board_api.domain.account.util

interface JwtTokenGenerator {
    fun generate(payload: Map<String, String>, expireSecond: Long): String
}
