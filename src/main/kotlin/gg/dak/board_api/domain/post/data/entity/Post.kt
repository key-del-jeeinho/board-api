package gg.dak.board_api.domain.post.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long,
    val writerIdx: Long,
    val title: String,
    val content: String
)