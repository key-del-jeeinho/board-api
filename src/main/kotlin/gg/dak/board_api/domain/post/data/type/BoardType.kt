package gg.dak.board_api.domain.post.data.type

/* Trade-off
아직 게시판의 동적 추가가 필요할 정도로 서비스의 복잡성이 증대하지않았으므로, Enum을 통해 게시판을 구현한다.
 */
enum class BoardType(val displayName: String) {
    NOTICE("공지사항"), FREE("자유 게시판"), ATTENDANCE("출석 게시판")
}