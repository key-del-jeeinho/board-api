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
            .let { postConverter.toEntity(it) }
            .let { postRepository.save(it) }
            .let { postConverter.toDto(it) }
            .also {
                postConverter.toCreateEvent(it)
                .let { event -> applicationEventPublisher.publishEvent(event) }
            }

    override fun deletePost(dto: PostDto): PostDto =
        postValidator.validate(PostOperationType.DELETE, dto)
            .let { postProcessor.process(PostOperationType.DELETE, dto) }
            .also { postRepository.deleteById(dto.idx) }
            .also {
                postConverter.toDeleteEvent(it.idx)
                .let { event -> applicationEventPublisher.publishEvent(event) }
            }.let { dto }

    override fun updatePost(dto: PostDto): PostDto {
        TODO("Not yet implemented")
    }
}