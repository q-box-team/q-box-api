package site.qbox.qboxserver.domain.member.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.member.command.entity.Member

interface MemberRepo : JpaRepository<Member, String>