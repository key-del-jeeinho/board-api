package gg.dak.board_api.global.account.util

interface UuidTokenGenerator {
    fun generate(payload: Map<String, String>, expireSecond: Long): String
    fun decode(token: String): Map<String, String>
}