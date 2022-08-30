package gg.dak.board_api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Disabled
@Transactional
@AutoConfigureMockMvc
class IntegrationTestBase {
    @Autowired
    protected lateinit var mvc: MockMvc
    @Autowired
    protected lateinit var objectMapper: ObjectMapper
}