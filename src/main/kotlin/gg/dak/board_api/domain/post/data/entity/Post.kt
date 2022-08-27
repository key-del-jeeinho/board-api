package gg.dak.board_api.domain.post.data.entity

import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import javax.persistence.*

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long,
    val writerIdx: Long,
    val title: String,
    val content: String,
    @Enumerated(EnumType.STRING)
    val category: CategoryType,
    @Enumerated(EnumType.STRING)
    val board: BoardType
)