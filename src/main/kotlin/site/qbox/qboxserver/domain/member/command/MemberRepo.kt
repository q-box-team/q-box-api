package site.qbox.qboxserver.domain.member.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.email.entity.Email

interface MemberRepo : JpaRepository<Member, Email>