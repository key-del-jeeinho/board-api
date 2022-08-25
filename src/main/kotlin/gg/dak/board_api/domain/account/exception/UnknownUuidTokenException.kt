package gg.dak.board_api.domain.account.exception

class UnknownUuidTokenException(message: String, val token: String) : RuntimeException("$message - $token")