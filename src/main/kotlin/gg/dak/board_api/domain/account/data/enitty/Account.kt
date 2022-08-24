package gg.dak.board_api.domain.account.data.enitty

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long,
    val nickName: String,
    val id: String,
    val encodedPassword: String
)