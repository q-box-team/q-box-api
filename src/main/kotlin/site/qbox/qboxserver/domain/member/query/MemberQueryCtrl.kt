package site.qbox.qboxserver.domain.member.query

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

@RestController
@RequestMapping("members")
class MemberQueryCtrl(
    private val memberDao: MemberDao,
) {
    @GetMapping("me")
    fun getMe(auth: Authentication): MemberRes =
        memberDao.getByEmail(auth.name)
}