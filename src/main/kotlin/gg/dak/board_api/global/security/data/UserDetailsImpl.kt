package gg.dak.board_api.global.security.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: String,
    val encodedPassword: String,
    val roles: MutableList<String>
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }.toMutableList()

    @Deprecated("UserDetailsImpl.password 를 이용해주세요!", ReplaceWith("password"))
    override fun getPassword(): String = encodedPassword
    override fun getUsername(): String = id

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}