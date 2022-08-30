package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

object PostCommandUtil {
    fun saveDailyCount(entity: DailyPostCount): Long =
            TestComponentSource.dailyPostCountRepository().save(entity).id

    fun create(request: CreatePostRequest, accessToken: String): Long =
            TestComponentSource.mockMvc().perform(MockMvcRequestBuilders.post("/api/v1/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestComponentSource.objectMapper().writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer $accessToken"))
                    .andReturn()
                    .response.contentAsString
                    .let { TestComponentSource.objectMapper().readValue(it, CreatePostResponse::class.java) }
                    .idx

}
