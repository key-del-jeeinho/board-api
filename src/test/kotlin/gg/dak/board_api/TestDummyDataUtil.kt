package gg.dak.board_api

object TestDummyDataUtil {
    fun nickname(length: Int) = listOf("닥지지", "라울", "아무닉", "가나다라마", "앰비션뮤직", "G", "스프링MVC").filter { it.length == length }.random()
}