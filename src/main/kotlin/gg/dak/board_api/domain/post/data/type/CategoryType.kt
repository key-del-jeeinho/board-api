package gg.dak.board_api.domain.post.data.type

enum class CategoryType(val displayName: String) {
    CHAT("잡담"), QUESTION("질문"), HUMOR("유머"), UNKNOWN("@UNKNOWN")
}