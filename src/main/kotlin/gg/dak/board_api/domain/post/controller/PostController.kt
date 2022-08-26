package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.service.PostService
import gg.dak.board_api.domain.post.util.PostConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService,
    private val postConverter: PostConverter
) {
    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<CreatePostResponse> =
        postConverter.toDto(request)
            .let { postService.createPost(it) }
            .let { postConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: String) {

    }

    @PutMapping("/{id}")
    fun changePost(@PathVariable id: String) {

    }
}