package gg.dak.board_api.domain.account.controller

import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["계정 조회 API"])
@RestController
@RequestMapping("/api/v1/account/query")
class AccountQueryController {
    @GetMapping("/all")
    fun findAllAccountWithPagination(@RequestParam("page") page: Int, @RequestParam("size") size: Int): ResponseEntity<*> {
        TODO()
    }

    @GetMapping("/{idx}")
    fun findAccountByIndex(@PathVariable idx: String): ResponseEntity<*> {
        TODO()
    }

    @GetMapping("/id/{id}")
    fun findAccountById(@PathVariable id: String): ResponseEntity<*> {
        TODO()
    }
}