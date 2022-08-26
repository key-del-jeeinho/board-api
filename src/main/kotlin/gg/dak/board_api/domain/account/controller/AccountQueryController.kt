package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.response.AccountQueryResponse
import gg.dak.board_api.domain.account.data.response.PageableAccountQueryResponse
import gg.dak.board_api.domain.account.service.AccountQueryService
import gg.dak.board_api.domain.account.util.AccountQueryConverter
import io.swagger.annotations.Api
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
    @GetMapping("/all")
    fun findAllAccountWithPagination(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PageableAccountQueryResponse> =
        accountQueryService.findAllAccount(PageRequest.of(page, size))
            .map { accountQueryConverter.toResponse(it) }
            .let { accountQueryConverter.toPageabelResponse(it.toList()) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("/{idx}")
    fun findAccountByIndex(@PathVariable idx: String): ResponseEntity<AccountQueryResponse> {
        TODO()
    }

    @GetMapping("/id/{id}")
    fun findAccountById(@PathVariable id: String): ResponseEntity<AccountQueryResponse> {
        TODO()
    }
}