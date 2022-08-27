package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.global.common.exception.PolicyValidationException
import org.springframework.stereotype.Component

@Component
class PostValidatorImpl(
    private val postProperties: PostProperties,
    private val dailyPostCountRepository: DailyPostCountRepository
): PostValidator {
    override fun validate(type: PostOperationType, dto: PostDto) {
        when(type) {
            PostOperationType.CREATE -> validateDailyPostLimit(dto)
        }
    }

    private fun validateDailyPostLimit(dto: PostDto) =
        dailyPostCountRepository.findByAccountIdx(dto.writerIdx)
            .let { if(it.isEmpty) return else it.get()} //만약, 일일작성횟수 정보가 없다면, 검증중인 게시글을 오늘 첫 작성 게시글로 취급한다.
            .let { if(it.count >= postProperties.dailyPostLimit) throw PolicyValidationException("포스트 작성 정책을 위반하였습니다!", "일일 작성한도를 초과하였습니다.") }
}
