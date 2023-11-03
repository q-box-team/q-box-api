package site.qbox.qboxserver.domain.member.query

import com.querydsl.core.types.dsl.StringPath
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.dto.QMemberSummary

class MemberQuery {
    companion object {
        val id: StringPath = member.email.email
        val summary = QMemberSummary(id, member.nickname)
    }
}