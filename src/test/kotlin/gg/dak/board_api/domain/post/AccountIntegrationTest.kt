package gg.dak.board_api.domain.post

import gg.dak.board_api.IntegrationTestBase
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.test_utils.TestComponentSource
import gg.dak.board_api.test_utils.TestUtil
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

    @BeforeEach
    fun setUp() {
        TestComponentSource.initializeMockMvc(mvc)
        TestComponentSource.initializeObjectMapper(objectMapper)
    }

    @Test @DisplayName("회원가입 통합테스트 - 회원가입 성공")
    fun testRegister() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        val request = RegisterRequest(nickname, id, password)

        accountRepository.deleteAll()

        val resultAction = mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo{ print(it.response.contentAsString) }

        resultAction
                .andExpect(status().isOk)
                .andExpect(jsonPath("accountIdx").value(nickname))
    }
}