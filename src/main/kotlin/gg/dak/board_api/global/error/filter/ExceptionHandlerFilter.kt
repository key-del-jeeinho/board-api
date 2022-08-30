package gg.dak.board_api.global.error.filter

import com.fasterxml.jackson.databind.ObjectMapper
import gg.dak.board_api.global.account.exception.JwtException
import gg.dak.board_api.global.common.exception.PolicyValidationException
import gg.dak.board_api.global.error.data.response.ErrorResponse
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import gg.dak.board_api.global.error.exception.GlobalException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class ExceptionHandlerFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { throwable ->
            when(throwable) {
                is PolicyValidationException -> sendErrorResponse(HttpStatus.BAD_REQUEST, throwable, response, ErrorStatusType.POLICY_VIOLATION)
                is JwtException -> sendErrorResponse(HttpStatus.UNAUTHORIZED, throwable, response, ErrorStatusType.JWT_ERROR)

                else -> sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, object: GlobalException {
                    override fun getErrorMessage(): String = "알 수 없는 오류가 발생했습니다!"
                    override fun getErrorDetails(): String = throwable.message ?: throwable.localizedMessage
                }, response, ErrorStatusType.UNKNOWN_ERROR)
            } }
    }

    private fun sendErrorResponse(status: HttpStatus, exception: GlobalException, response: HttpServletResponse, errorStatusType: ErrorStatusType) {
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.status = status.value()
        response.contentType = "application/json"
        try {
            ErrorResponse(
                errorStatusType,
                exception.getErrorMessage(),
                exception.getErrorDetails()
            ).let { writeErrorResponse(it) }
            .let { response.writer.write(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun writeErrorResponse(errorResponse: ErrorResponse): String =
        runCatching { ObjectMapper().writeValueAsString(errorResponse) }
            .getOrNull() ?: "예상치 못한 오류가 발생하였습니다!"

}
