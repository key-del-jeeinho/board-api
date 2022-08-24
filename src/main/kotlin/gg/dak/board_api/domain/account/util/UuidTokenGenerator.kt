package gg.dak.board_api.domain.account.util

interface UuidTokenGenerator {
    fun generate(expireSecond: Long): String
}
