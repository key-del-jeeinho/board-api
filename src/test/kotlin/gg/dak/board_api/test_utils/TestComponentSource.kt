package gg.dak.board_api.test_utils

import com.fasterxml.jackson.databind.ObjectMapper
import gg.dak.board_api.domain.account.util.JwtTokenGenerator
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.global.account.repository.AccountRepository
import org.springframework.test.web.servlet.MockMvc

object TestComponentSource {
    private var mockMvc: MockMvc? = null
    private var objectMapper: ObjectMapper? = null
    private var dailyPostCountRepository: DailyPostCountRepository? = null
    private var accountRepository: AccountRepository? = null
    private var jwtTokenGenerator: JwtTokenGenerator? = null

    fun initializeMockMvc(mockMvc: MockMvc) {
        if(this.mockMvc == null) this.mockMvc = mockMvc
    }
    fun mockMvc(): MockMvc = mockMvc!!

    fun initializeObjectMapper(objectMapper: ObjectMapper) {
        if(this.objectMapper == null) this.objectMapper = objectMapper
    }
    fun objectMapper(): ObjectMapper = objectMapper!!

    fun initializeDailyPostCountRepository(dailyPostCountRepository: DailyPostCountRepository) {
        if(this.dailyPostCountRepository == null) this.dailyPostCountRepository = dailyPostCountRepository
    }
    fun dailyPostCountRepository(): DailyPostCountRepository = dailyPostCountRepository!!

    fun initializeAccountRepository(accountRepository: AccountRepository) {
        if(this.accountRepository == null) this.accountRepository = accountRepository
    }
    fun accountRepository(): AccountRepository = accountRepository!!

    fun initializeJwtTokenGenerator(jwtTokenGenerator: JwtTokenGenerator) {
        if(this.jwtTokenGenerator == null) this.jwtTokenGenerator = jwtTokenGenerator
    }
    fun jwtTokenGenerator(): JwtTokenGenerator = jwtTokenGenerator!!
}