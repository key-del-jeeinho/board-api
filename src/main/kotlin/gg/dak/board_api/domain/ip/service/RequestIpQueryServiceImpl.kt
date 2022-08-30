package gg.dak.board_api.domain.ip.service

import gg.dak.board_api.global.ip.service.RequestIpQueryService
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

@Service
class RequestIpQueryServiceImpl: RequestIpQueryService {
    override fun getCurrentRequestIp(): String =
        RequestContextHolder.currentRequestAttributes()
            .let { it as ServletRequestAttributes }
            .request
            .let { getIp(it) }

    private fun getIp(request: HttpServletRequest): String {
        var ip =request.getHeader("X-Forwarded-For")
        if (ip.isNullOrBlank() || "unknown".equals(ip, true)) ip = request.getHeader("Proxy-Client-IP")
        if (ip.isNullOrBlank() || "unknown".equals(ip, true)) ip = request.getHeader("WL-Proxy-Client-IP")
        if (ip.isNullOrBlank() || "unknown".equals(ip, true)) ip = request.getHeader("HTTP_CLIENT_IP")
        if (ip.isNullOrBlank() || "unknown".equals(ip, true)) ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        if (ip.isNullOrBlank() || "unknown".equals(ip, true)) ip = request.remoteAddr
        return ip
    }
}