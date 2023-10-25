package site.qbox.qboxserver.domain.member.query

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.member.command.entity.Member

interface MemberQueryRepo : JpaRepository<Member, String> {
    fun findByEmail(email: String): Member
}