package gg.dak.board_api.domain.post.scheduler

import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DailyPostCountClearScheduler(
    private val dailyPostCountRepository: DailyPostCountRepository
) {
    /* Trade-off
    서비스 증대시, Scheduler로 Count가 초기화되기 이전에 게시글 작성 요청이 들어올 수 있다.
    이로 인한 오류를 방지하려면, DailyPostCount에 날짜정보를 포함시키고, Validation에서 당일에 대한 Count를 가져오는방식으로 변경해야한다.
    삭제 또한 전체삭제가아닌, 날짜기반 그룹 삭제 방식으로 변경해야한다.
     */
    @Scheduled(cron = "0 0 0 * * *")
    fun clearDailyPostCount() {
        dailyPostCountRepository.deleteAll()
    }
}