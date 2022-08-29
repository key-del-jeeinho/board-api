package gg.dak.board_api.test_utils

object TestUtil {
    fun data() = TestDataUtil
    fun convert() = TestConvertUtil

    object TestDataUtil {
        fun account() = AccountDataUtil
        fun post() = PostDataUtil
    }
    object TestConvertUtil {
        fun post() = PostDataConvertUtil
    }
}
