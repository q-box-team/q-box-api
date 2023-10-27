package site.qbox.qboxserver.global.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import site.qbox.qboxserver.domain.member.query.dto.MemberAuthRes


class MemberAuth(
    private val auth: MemberAuthRes
) : UserDetails {
    override fun getAuthorities() =
        auth.role.map { SimpleGrantedAuthority(it) }.toMutableList()

    override fun getPassword() = auth.password
    override fun getUsername() = auth.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true

    val name: String
        get() = auth.email
    val group : String
        get() = auth.group
}