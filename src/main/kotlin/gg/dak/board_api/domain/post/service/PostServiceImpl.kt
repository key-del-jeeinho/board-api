package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.domain.post.util.PostProcessor
import gg.dak.board_api.domain.post.util.PostValidator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postConverter: PostConverter,
    private val postRepository: PostRepository,
    private val postValidator: PostValidator,
    private val postProcessor: PostProcessor,
    private val applicationEventPublisher: ApplicationEventPublisher
): PostService {
    override fun createPost(dto: PostDto): PostDto =
        postValidator.validate(PostOperationType.CREATE, dto)
            .let { postProcessor.process(PostOperationType.CREATE, dto) }
            .let(postConverter::toEntity)
            .let(postRepository::save)
            .let(postConverter::toDto)
            .also { postConverter.toCreateEvent(it).publishEvent() }

    override fun deletePost(dto: PostDto): PostDto =
        postValidator.validate(PostOperationType.DELETE, dto)
            .let { postProcessor.process(PostOperationType.DELETE, dto) }
            .also { postRepository.deleteById(dto.idx) }
            .also { postConverter.toDeleteEvent(it.idx).publishEvent() }
            .let { dto }

    override fun updatePost(dto: PostDto): PostDto =
        postValidator.validate(PostOperationType.UPDATE, dto)
            .let { postProcessor.process(PostOperationType.UPDATE, dto) }
            .let(postConverter::toEntity)
            .let(postRepository::save)
            .let(postConverter::toDto)
            .also { postConverter.toUpdateEvent(it).publishEvent() }

    fun <T: Any> T.publishEvent() = applicationEventPublisher.publishEvent(this)
}