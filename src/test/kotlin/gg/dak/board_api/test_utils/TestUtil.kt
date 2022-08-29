package gg.dak.board_api.test_utils

object TestUtil {
    fun data() = TestDataUtil
    fun convert() = TestConvertUtil
    fun query() = TestGenerateUtil

    object TestDataUtil {
        fun account() = AccountDataUtil
        fun post() = PostDataUtil
    }
    object TestConvertUtil {
        fun post() = PostDataConvertUtil
    }

    object TestGenerateUtil {
        fun account() = AccountQueryUtil
    }
}
