package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import gg.dak.board_api.global.account.data.dto.AccountDto

object TestEnvironment {
    fun createRefreshToken(account: AccountDto): String =
            TestUtil.query().account().refreshToken(account.id, account.password)
    fun createAccessToken(account: AccountDto): String =
            TestUtil.query().account().accessToken(account.id, account.password)

    fun createAccount(): AccountDto {
        val nickname = TestUtil.data().account().nickname()
        var id = TestUtil.data().account().id()
        while (TestComponentSource.accountRepository().existsById(id)) id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()
        val idx = TestUtil.command().account().create(RegisterRequest(nickname, id, password))
        return AccountDto(idx, nickname, id, password, false)
    }

    fun createPost(accessToken: String): PostDto {
        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)
        val idx = TestUtil.command().post().create(request, accessToken)
        val writerIdx = TestUtil.query().account().idx(accessToken)
        return PostDto(idx, writerIdx, title, content, category, board)
    }
}