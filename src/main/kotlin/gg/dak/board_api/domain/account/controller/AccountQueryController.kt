package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.response.AccountQueryResponse
import gg.dak.board_api.domain.account.data.response.PageableAccountQueryResponse
import gg.dak.board_api.domain.account.service.AccountQueryService
import gg.dak.board_api.domain.account.util.AccountQueryConverter
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["계정 조회 API"])
@RestController
@RequestMapping("/api/v1/account/query")
class AccountQueryController(
    private val accountQueryService: AccountQueryService,
    private val accountQueryConverter: AccountQueryConverter
) {
    @ApiOperation(value = "전체 계정목록 조회(with Pagination)", notes = "페이지네이션된 전체 계정목록을 조회합니다.")
    @GetMapping("/all")
    fun findAllAccountWithPagination(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PageableAccountQueryResponse> =
        accountQueryService.findAllAccount(PageRequest.of(page, size))
            .map { accountQueryConverter.toResponse(it) }
            .let { accountQueryConverter.toPageableResponse(it.toList()) }
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "인덱스로 계정 조회", notes = "계정의 인덱스로 계정정보를 조회합니다.")
    @GetMapping("/{idx}")
    fun findAccountByIndex(@PathVariable idx: Long): ResponseEntity<AccountQueryResponse> =
        accountQueryService.findAccountByIndex(idx)
            .let { accountQueryConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "아이디로 계정조회", notes = "계정의 아이디로 계정정보를 조회합니다.")
    @GetMapping("/id/{id}")
    fun findAccountById(@PathVariable id: String): ResponseEntity<AccountQueryResponse> =
        accountQueryService.findAccountById(id)
            .let { accountQueryConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }
}