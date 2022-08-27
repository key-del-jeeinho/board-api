package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.global.security.service.LoginAccountService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.random.Random

class PostValidatorTest {
    private lateinit var postProperties: PostProperties
    private lateinit var postRepository: PostRepository
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    private lateinit var loginAccountService: LoginAccountService
    private lateinit var target: PostValidator

    @BeforeEach
    fun setUp() {
        postProperties = mock()
        postRepository = mock()
        dailyPostCountRepository = mock()
        loginAccountService = mock()
        target = PostValidatorImpl(postProperties, postRepository, dailyPostCountRepository, loginAccountService)
    }

    /* PolicyValidator - 게시글 작성 로직 검증 성공테스트
    PolicyValidator.validate(PostOperationType.CREATE, ?: PostDto)
    작성자가 일일 작성 가능 횟수를 초과하였다면, 게시글을 작성할 수 없다.
     */
    @Test @DisplayName("PolicyValidator - 게시글 작성 로직 검증 성공테스트A")
    fun testValidateCreatePost_positive() {
        //given
        val board = BoardType.values().random()
        val dailyPostLimit = (1..1000).random()
        val count = (1..999).filter { dailyPostLimit > it }.random()
        val accountIdx = Random.nextLong()
        val dto = mock<PostDto>()
        val countEntity = mock<DailyPostCount>()
        val optional = Optional.of(countEntity)

        //when
        whenever(postProperties.dailyPostLimit).thenReturn(dailyPostLimit)
        whenever(dailyPostCountRepository.findByAccountIdxAndBoard(accountIdx, board)).thenReturn(optional)
        whenever(countEntity.count).thenReturn(count)
        whenever(countEntity.board).thenReturn(board)

        //then
        assertDoesNotThrow { target.validate(PostOperationType.CREATE, dto) }
    }
    //TODO 일일 작성 게시글 정보가 없을 경우(오늘 첫 작성일 경우) 성공테스트 작성

    /* PolicyValidator - 게시글 삭제 로직 검증 성공테스트
    PolicyValidator.validate(PostOperationType.DELETE, ?: PostDto)
    요청자가 작성자가 아니라면, 게시글을 삭제할 수 없다.
    TODO: 랭킹에 등록되어있는 게시글은 삭제할 수 없다.
     */
    @Test @DisplayName("PolicyValidator - 게시글 삭제 로직 검증 성공테스트")
    fun testValidateDeletePost_positive() {
        //given
        val dto = mock<PostDto>()
        val entity = mock<Post>()
        val ownerIdx = Random.nextLong()
        val optional = Optional.of(entity)
        val accountDto = mock<AccountDto>()

        //when
        whenever(postRepository.findById(dto.idx)).thenReturn(optional)
        whenever(entity.writerIdx).thenReturn(ownerIdx)
        whenever(loginAccountService.getLoginAccount()).thenReturn(accountDto)
        whenever(accountDto.idx).thenReturn(ownerIdx)

        //then
        assertDoesNotThrow { target.validate(PostOperationType.DELETE, dto) }
    }
}