package gg.dak.board_api

import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.test_utils.AccountDataUtil
import gg.dak.board_api.test_utils.PostDataConvertUtil
import gg.dak.board_api.test_utils.PostDataUtil

@Deprecated("TestUtil을 사용해주세요!")
object TestDummyDataUtil {
    fun nickname(length: Int) = AccountDataUtil.nickname(length)
    fun id() = AccountDataUtil.id()
    fun password() = AccountDataUtil.password()
    fun encodedPassword() = AccountDataUtil.encodedPassword()
    fun token() = AccountDataUtil.token()
    fun accountDto(isPasswordEncoded: Boolean) = AccountDataUtil.dto(isPasswordEncoded)
    fun account() = AccountDataUtil.entity()

    fun post() = PostDataUtil.entity()
    fun title() = PostDataUtil.title()

    fun content() = PostDataUtil.content()
    fun updatePost(entity: Post, updateContent: String) = PostDataConvertUtil.updatePost(entity, updateContent)

    fun toDto(entity: Post) = PostDataConvertUtil.toDto(entity)

    fun postDto() = PostDataUtil.dto()
    fun postQueryDto() = PostDataUtil.queryDto()
    fun views() = PostDataUtil.views()
    fun ip() = PostDataUtil.ip()
}