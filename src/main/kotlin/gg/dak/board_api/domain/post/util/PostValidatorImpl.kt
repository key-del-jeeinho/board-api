package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.global.common.exception.PolicyValidationException
import gg.dak.board_api.global.security.service.LoginAccountService
import org.springframework.stereotype.Component

@Component
class PostValidatorImpl(
    private val postProperties: PostProperties,
    private val postRepository: PostRepository,
    private val dailyPostCountRepository: DailyPostCountRepository,
    private val loginAccountService: LoginAccountService
): PostValidator {
    override fun validate(type: PostOperationType, dto: PostDto) =
        when(type) {
            PostOperationType.CREATE -> validateDailyPostLimit(dto)
            PostOperationType.DELETE -> validateIsOwner(dto)
            PostOperationType.UPDATE -> validateIsOwner(dto)
        }

    private fun validateIsOwner(dto: PostDto) =
        postRepository.findById(dto.idx)
            .let { if(it.isEmpty) throw PolicyValidationException("포스트 삭제 정책을 위반하였습니다!", "존재하지 않는 게시글입니다.") else it.get() }
            .let { it.writerIdx == loginAccountService.getLoginAccount().idx}
            .let { isOwner -> if(!isOwner) throw PolicyValidationException("포스트 삭제 정책을 위반하였습니다!", "포스트 작성자만 포스트를 삭제할 수 있습니다!") }

    private fun validateDailyPostLimit(dto: PostDto) =
        dailyPostCountRepository.findByAccountIdxAndBoard(dto.writerIdx, dto.board)
            .let { if(it.isEmpty) return else it.get()} //만약, 일일작성횟수 정보가 없다면, 검증중인 게시글을 오늘 첫 작성 게시글로 취급한다.
            .let { if(it.count >= postProperties.dailyPostLimit) throw PolicyValidationException("포스트 작성 정책을 위반하였습니다!", "일일 작성한도를 초과하였습니다.") }
}
