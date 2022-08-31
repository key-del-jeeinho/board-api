package gg.dak.board_api.domain.post

import gg.dak.board_api.IntegrationTestBase
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.request.RefreshLoginRequest
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse
import gg.dak.board_api.domain.account.util.JwtTokenUtil
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.test_utils.TestComponentSource
import gg.dak.board_api.test_utils.TestEnvironment.createAccount
import gg.dak.board_api.test_utils.TestEnvironment.createRefreshToken
import gg.dak.board_api.test_utils.TestUtil
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountIntegrationTest: IntegrationTestBase() {
    @Autowired
    private lateinit var accountRepository: AccountRepository
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @BeforeEach
    fun setUp() {
        TestComponentSource.initializeMockMvc(mvc)
        TestComponentSource.initializeObjectMapper(objectMapper)
        TestComponentSource.initializeJwtTokenGenerator(jwtTokenUtil)
        TestComponentSource.initializeAccountRepository(accountRepository)
        accountRepository.deleteAll()
    }

    @Test @DisplayName("회원가입 통합테스트 - 회원가입 성공")
    fun testRegister() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        val request = RegisterRequest(nickname, id, password)


        val resultAction = mvc.perform(post("/api/v1/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo{ print(it.response.contentAsString) }

        resultAction
                .andExpect(status().isOk)
                .andExpect(jsonPath("accountIdx", `is`(notNullValue())))
    }

    @Test @DisplayName("로그인 통합테스트 - 로그인 성공")
    fun testLogin() {
        val account = createAccount()

        val request = LoginRequest(account.id, account.password)

        val resultAction = mvc.perform(post("/api/v1/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo{ print(it.response.contentAsString) }

        resultAction
                .andExpect(status().isOk)
                .andExpect(jsonPath("accessToken", `is`(notNullValue())))
                .andExpect(jsonPath("refreshToken", `is`(notNullValue())))
    }

    @Test @DisplayName("로그인 통합테스트 - 로그인 연장")
    fun testRefreshLogin() {
        val account = createAccount()
        val refreshToken = createRefreshToken(account)

        val request = RefreshLoginRequest(refreshToken)

        val resultAction = mvc.perform(post("/api/v1/account/login/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo{ print(it.response.contentAsString) }

        val result = resultAction
                .andExpect(status().isOk)
                .andExpect(jsonPath("accessToken", `is`(notNullValue())))
                .andExpect(jsonPath("refreshToken", `is`(notNullValue())))
                .andReturn().response.contentAsString
                .let { TestComponentSource.objectMapper().readValue(it, LoginResponse::class.java) }
                .let { LoginTokenDto(it.accessToken, it.refreshToken) }
        val id = TestUtil.TestQueryUtil.account().id(result.accessToken)

        assertNotEquals(refreshToken, result.refreshToken)
        assertEquals(account.id, id)
    }
}