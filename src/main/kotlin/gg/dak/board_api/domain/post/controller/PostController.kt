package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.request.UpdatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.data.response.DeletePostResponse
import gg.dak.board_api.domain.post.data.response.UpdatePostResponse
import gg.dak.board_api.domain.post.service.PostService
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.global.security.service.LoginAccountService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(tags = ["게시글 API"])
@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService,
    private val postConverter: PostConverter,
    private val loginAccountService: LoginAccountService
) {
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다. 게시글은 게시판별로 1일 3번만 작성 가능합니다.(삭제된 게시글 포함)")
    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<CreatePostResponse> =
        loginAccountService.getLoginAccount().idx
            .let { postConverter.toDto(request, it) }
            .let { postService.createPost(it) }
            .let { postConverter.toCreateResponse(it) }
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다. 게시글의 작성자만 삭제할 수 있습니다.")
    @DeleteMapping("/{idx}")
    fun deletePost(@PathVariable idx: Long): ResponseEntity<DeletePostResponse> =
        postConverter.toDto(idx)
            .let { postService.deletePost(it) }
            .let { postConverter.toDeleteResponse(it) }
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "게시글 수정", notes = "게시글의 내용을 수정합니다. 게시글 작성자만 수정할 수 있습니다.")
    @PutMapping("/{idx}")
    fun updatePost(@PathVariable idx: Long,
                   @RequestBody request: UpdatePostRequest
    ): ResponseEntity<UpdatePostResponse> =
        postConverter.toDto(idx, request)
            .let { postService.updatePost(it) }
            .let { postConverter.toUpdateResponse(it) }
            .let { ResponseEntity.ok(it) }
}