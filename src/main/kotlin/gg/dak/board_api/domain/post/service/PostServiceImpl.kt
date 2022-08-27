package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.domain.post.util.PostProcessor
import gg.dak.board_api.domain.post.util.PostValidator

class PostServiceImpl(
    private val postConverter: PostConverter,
    private val postRepository: PostRepository,
    private val postValidator: PostValidator,
    private val postProcessor: PostProcessor
): PostService {
    override fun createPost(dto: PostDto): PostDto =
        postValidator.validate(PostOperationType.CREATE, dto)
            .let { postProcessor.process(PostOperationType.CREATE, dto) }
            .let { postConverter.toEntity(it) }
            .let { postRepository.save(it) }
            .let { postConverter.toDto(it) }
}