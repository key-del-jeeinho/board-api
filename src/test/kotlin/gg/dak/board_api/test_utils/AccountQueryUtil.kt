package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

object AccountQueryUtil {
    fun accessToken(id: String, password: String) = loginToken(id, password).accessToken
    fun refreshToken(id: String, password: String) = loginToken(id, password).refreshToken

    private fun loginToken(id: String, password: String): LoginTokenDto =
        TestComponentSource.objectMapper().writeValueAsString(LoginRequest(id, password))
            .let { request ->
                TestComponentSource.mockMvc().perform(
                    post("/api/v1/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn().response.contentAsString
            }.let { TestComponentSource.objectMapper().readValue(it, LoginResponse::class.java) }
            .let { LoginTokenDto(it.accessToken, it.refreshToken) }

    fun idx(accessToken: String): Long = id(accessToken).let { TestComponentSource.accountRepository().findById(it) }.get().idx

    fun id(accessToken: String): String = TestComponentSource.jwtTokenGenerator().decode(accessToken).let { it["id"]!! }
}