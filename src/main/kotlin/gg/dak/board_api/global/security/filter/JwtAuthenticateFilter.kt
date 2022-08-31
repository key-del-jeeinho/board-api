package gg.dak.board_api.global.security.filter

import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.util.JwtTokenUtil
import gg.dak.board_api.global.common.exception.PolicyValidationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticateFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil
): OncePerRequestFilter() {
    companion object { private const val TOKEN_PREFIX = "Bearer " }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) = request.hasHeader("Authorization")
        .let { //만약 header에 토큰이 존재하지 않을경우, 인증/인가로직을 스킵한다.
            if(it) request.getHeader("Authorization")
            else return filterChain.doFilter(request, response)
        }.let { header -> //만약 header에 있는 토큰의 format이 올바르지 않을경우, 인증/인가로직을 스킵한다.
            if(header.startsWith(TOKEN_PREFIX)) header
            else return filterChain.doFilter(request, response)
        }.let { header -> getToken(header) }
        .let { token -> getId(token) }
        .let { id ->
            if(SecurityContextHolder.getContext().authentication == null) //Security Context 에 인증된 객체가 없을경우
                saveToContext(userDetailsService.loadUserByUsername(id), request) //인증객체를 생성하여 Context에 저장한다
            filterChain.doFilter(request, response)
        }

    private fun saveToContext(userDetails: UserDetails, request: HttpServletRequest) =
        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            .let {
                it.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = it
            }

    private fun getId(token: String): String =
        jwtTokenUtil.decode(token)
            .also { validatePayload(it) }
            .let { it["id"]!! }

    private fun getToken(header: String): String = header.substring(TOKEN_PREFIX.length)

    private fun validatePayload(payload: Map<String, String>) {
        if(isFormatWrong(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "토큰이 담고있는 정보가 바르지 않습니다!")
        if(isNotRefreshToken(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "해당 토큰은 액세스토큰이 아닙니다!")
        if(isExpired(payload)) throw PolicyValidationException("로그인 연장 정책을 위반하였습니다!", "이미 만료된 액세스토큰입니다!")
    }

    //토큰의 페이로드 무결성 여부를 검사합니다.
    private fun isFormatWrong(payload: Map<String, String>): Boolean = listOf("type", "expiration", "id").stream().noneMatch { key -> payload.containsKey(key) }
    //토큰이 재발급토큰인지 검사합니다.
    private fun isNotRefreshToken(payload: Map<String, String>): Boolean = payload["type"] != TokenType.LOGIN_ACCESS.key
    //토큰이 만료되었는지 검사합니다.
    private fun isExpired(payload: Map<String, String>): Boolean = payload["expiration"].toBoolean()

    private fun HttpServletRequest.hasHeader(s: String): Boolean = runCatching {
        if(getHeader(s).isNullOrEmpty()) throw Exception()
    }.isSuccess
}
