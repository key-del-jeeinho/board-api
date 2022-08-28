package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.response.PageablePostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.service.PostQueryService
import gg.dak.board_api.domain.post.util.PostQueryConverter
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(tags = ["게시글 조회 API"])
@RestController
@RequestMapping("/api/v1/post/query")
class PostQueryController(
    private val postQueryConverter: PostQueryConverter,
    private val postQueryService: PostQueryService,
) {
    @ApiOperation(value = "전체 게시글목록 조회(with Pagination)", notes = "페이지네이션된 전체 게시글목록을 조회합니다.")
    @GetMapping("/all")
    fun findAllPostWithPagination(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int): ResponseEntity<PageablePostQueryResponse> =
        postQueryService.findAllPost(PageRequest.of(page, size))
            .map { postQueryConverter.toResponse(it) }
            .let { postQueryConverter.toPageableResponse(it.toList()) }
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "인덱스로 게시글 조회", notes = "게시글의 인덱스로 게시글정보를 조회합니다.")
    @GetMapping("/{idx}")
    fun findPostByIndex(@PathVariable idx: Long): ResponseEntity<PostQueryResponse> =
        postQueryService.findPostByIndex(idx)
            .let { postQueryConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }
}