package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.account.data.request.RegisterRequest

object TestUtil {
    fun data() = TestDataUtil
    fun convert() = TestConvertUtil
    fun query() = TestQueryUtil
    fun command() = TestCommandUtil

    object TestDataUtil {
        fun account() = AccountDataUtil
        fun post() = PostDataUtil
    }
    object TestConvertUtil {
        fun post() = PostDataConvertUtil
    }

    object TestQueryUtil {
        fun account() = AccountQueryUtil
    }
    object TestCommandUtil {
        fun register(request: RegisterRequest)
    }
}
