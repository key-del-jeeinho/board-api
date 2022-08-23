package gg.dak.board_api.domain.account.data.enitty

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Account (
    @Id val idx: Long,
    val nickName: String,
    val id: String,
    val encodedPassword: String
)