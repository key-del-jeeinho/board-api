package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.global.account.data.dto.AccountDto
import kotlin.random.Random

object AccountDataUtil {
    fun nickname(length: Int = (2..5).random()) = listOf("닥지지", "라울", "아무닉", "가나다라마", "앰비션뮤직", "G", "스프링MVC", "스카니아", "깡", "메이플스토리").filter { it.length == length }.random()
    fun id() = listOf("abcd123", "develop_raul", "qwer1234").random()
    fun password() = listOf("helloworld0123!", "b0ard!ap1", "pa5sw0r|)").random()
    fun encodedPassword() = listOf("az+Dasc98As=8a", "s=1Scat1l2_", "s+lcjm4=32kco1p").random()
    fun token() = listOf("98A2+az+DSs=8sca1c2_", "cjStl2_m41lsca=1", "csjca12+at1llm4=3ascko1p").random()

    fun dto(isPasswordEncoded: Boolean) = AccountDto(
        idx = Random.nextLong(),
        nickname = nickname((2..5).random()),
        id = id(),
        password = if(isPasswordEncoded) encodedPassword() else password(),
        isPasswordEncoded = isPasswordEncoded
    )
    fun entity() = Account(
        idx = Random.nextLong(),
        nickName = nickname((2..5).random()),
        id = id(),
        encodedPassword = encodedPassword())
}