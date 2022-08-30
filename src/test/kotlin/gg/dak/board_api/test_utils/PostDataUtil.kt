package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import kotlin.math.absoluteValue
import kotlin.random.Random

object PostDataUtil {
    fun title() = listOf("제목", "제목일지도", "제목일거야", "ㄹㅇㅋㅋ").random()
    fun content() = listOf("내용", "내용일지도", "내용일거야", "ㅈㄱㄴ").random()
    fun ip() = "${(1..255).random()}.${(1..255).random()}.${(1..255).random()}.${(1..255).random()}"
    fun views() = Random.nextInt().absoluteValue

    fun entity() = Post(
        idx = Random.nextLong(),
        writerIdx = Random.nextLong(),
        title = title(),
        content = content(),
        category = CategoryType.values().random(),
        board = BoardType.values().random()
    )

    fun dto() = PostDto(
        idx = Random.nextLong(),
        writerIdx = Random.nextLong(),
        title = title(),
        content = content(),
        category = CategoryType.values().random(),
        board = BoardType.values().random()
    )

    fun queryDto() = PostQueryDto(
        idx = Random.nextLong(),
        writerIdx = Random.nextLong(),
        title = title(),
        content = content(),
        category = CategoryType.values().random(),
        board = BoardType.values().random(),
        views = views()
    )
}