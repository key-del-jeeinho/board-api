package gg.dak.board_api.test_utils

object TestUtil {
    fun data() = TestDataUtil
    fun convert() = TestConvertUtil
    fun generate() = TestGenerateUtil

    object TestDataUtil {
        fun account() = AccountDataUtil
        fun post() = PostDataUtil
    }
    object TestConvertUtil {
        fun post() = PostDataConvertUtil
    }

    object TestGenerateUtil {
        fun account() = AccountGenerateUtil
    }
}
