package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.service.PostService
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.global.security.service.LoginAccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService,
    private val postConverter: PostConverter,
    private val loginAccountService: LoginAccountService
) {
    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<CreatePostResponse> =
        loginAccountService.getLoginAccount().idx
            .let { postConverter.toDto(request, it) }
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