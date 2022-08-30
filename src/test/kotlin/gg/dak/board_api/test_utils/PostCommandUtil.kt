package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.post.data.entity.DailyPostCount

object PostCommandUtil {
    fun saveDailyCount(entity: DailyPostCount): Long =
        TestComponentSource.dailyPostCountRepository().save(entity).id

}
