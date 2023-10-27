package site.qbox.qboxserver.domain.member.query

import org.springframework.stereotype.Service
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.query.dto.MemberAuthRes
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

@Service
class MemberDao(private val memberQueryRepo: MemberQueryRepo) {
    fun getByEmail(email: String): MemberRes =
        MemberRes(getEntity(email))

    fun getAuth(email: String): MemberAuthRes =
        MemberAuthRes(getEntity(email))

    private fun getEntity(id: String): Member =
        memberQueryRepo.findByEmail(id)
}