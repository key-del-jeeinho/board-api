package gg.dak.board_api

import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import kotlin.random.Random

object TestDummyDataUtil {
    fun nickname(length: Int) = listOf("닥지지", "라울", "아무닉", "가나다라마", "앰비션뮤직", "G", "스프링MVC", "스카니아").filter { it.length == length }.random()
    fun id() = listOf("abcd123", "develop_raul", "qwer1234").random()
    fun password() = listOf("helloworld0123!", "b0ard!ap1", "pa5sw0r|)").random()
    fun encodedPassword() = listOf("az+Dasc98As=8a", "s=1Scat1l2_", "s+lcjm4=32kco1p").random()
    fun token() = listOf("98A2+az+DSs=8sca1c2_", "cjStl2_m41lsca=1", "csjca12+at1llm4=3ascko1p").random()
    fun accountDto(isPasswordEncoded: Boolean) = AccountDto(
        idx = Random.nextLong(),
        nickname = nickname((2..5).random()),
        id = id(),
        password = if(isPasswordEncoded) encodedPassword() else password(),
        isPasswordEncoded = isPasswordEncoded
    )
    fun account() = Account(
        idx = Random.nextLong(),
        nickName = nickname((2..5).random()),
        id = id(),
        encodedPassword = encodedPassword())

    fun post() = Post(
        idx = Random.nextLong(),
        writerIdx = Random.nextLong(),
        title = "제목",
        content = content(),
        category = CategoryType.values().random(),
        board = BoardType.values().random()
    )

    fun content() = listOf("내용", "내용일지도", "내용일거야", "ㅈㄱㄴ").random()
    fun updatePost(entity: Post, updateContent: String) = Post(
        idx = entity.idx,
        writerIdx = entity.writerIdx,
        title = entity.title,
        content = updateContent,
        category = entity.category,
        board = entity.board,
    )

    fun toDto(entity: Post) = PostDto(
        idx = entity.idx,
        writerIdx = entity.writerIdx,
        title = entity.title,
        content = entity.content,
        category = entity.category,
        board = entity.board,
    )

    fun postDto() = toDto(post())
}