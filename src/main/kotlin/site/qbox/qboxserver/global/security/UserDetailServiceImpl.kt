package site.qbox.qboxserver.global.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import site.qbox.qboxserver.domain.member.query.MemberDao
import site.qbox.qboxserver.global.annotation.QueryService


@QueryService
class UserDetailServiceImpl(
    private val memberDao: MemberDao
) : UserDetailsService {
    override fun loadUserByUsername(username: String?) =
        MemberAuth(memberDao.getAuth(username!!))
}