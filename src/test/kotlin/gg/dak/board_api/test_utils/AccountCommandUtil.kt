package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.RegisterResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

object AccountCommandUtil {
    fun create(request: RegisterRequest): Long =
        TestComponentSource.mockMvc().perform(post("/api/v1/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestComponentSource.objectMapper().writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo{ println() }
                .andReturn()
                .response.contentAsString
                .let { TestComponentSource.objectMapper().readValue(it, RegisterResponse::class.java) }
                .accountIdx
}
