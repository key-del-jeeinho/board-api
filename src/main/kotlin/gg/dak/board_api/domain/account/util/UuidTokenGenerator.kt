package gg.dak.board_api.domain.account.util

interface UuidTokenGenerator {
    fun generate(payload: Map<String, String>, expireSecond: Long): String
    //TODO 나중에 페이로드를 반환하는 decode(token) 작성
}
