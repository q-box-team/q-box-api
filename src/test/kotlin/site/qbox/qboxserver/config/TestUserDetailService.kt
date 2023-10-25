package site.qbox.qboxserver.config

import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import site.qbox.qboxserver.domain.member.query.dto.MemberAuthRes
import site.qbox.qboxserver.global.security.MemberAuth

class TestUserDetailService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return MemberAuth(MemberAuthRes(username, "pw", listOf("ROLE_USER"), "group"))
    }

}