package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.util.LoginTokenGenerator
import gg.dak.board_api.global.account.util.UuidTokenGenerator
import gg.dak.board_api.global.common.exception.PolicyValidationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class RefreshLoginServiceImpl(
    @Qualifier("volatility") private val uuidTokenGenerator: UuidTokenGenerator,
    private val loginTokenGenerator: LoginTokenGenerator
): RefreshLoginService {
    override fun refreshLogin(refreshToken: String): LoginTokenDto =
        uuidTokenGenerator.decode(refreshToken)
            .also { validatePayload(it) }
            .let { it["id"]!! }
            .let { loginTokenGenerator.generate(it) }

    private fun validatePayload(payload: Map<String, String>) {
        if(isFormatWrong(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "토큰이 담고있는 정보가 바르지 않습니다!")
        if(isNotRefreshToken(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "해당 토큰은 재발급토큰이 아닙니다!")
        if(isExpired(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "이미 만료된 재발급토큰입니다!")
    }

    //토큰의 페이로드 무결성 여부를 검사합니다.
    private fun isFormatWrong(payload: Map<String, String>): Boolean = listOf("type", "expiration", "id").stream().noneMatch { key -> payload.containsKey(key) }
    //토큰이 재발급토큰인지 검사합니다.
    private fun isNotRefreshToken(payload: Map<String, String>): Boolean = payload["type"] != TokenType.LOGIN_REFRESH.key
    //토큰이 만료되었는지 검사합니다.
    private fun isExpired(payload: Map<String, String>): Boolean = payload["expiration"].toBoolean()
}