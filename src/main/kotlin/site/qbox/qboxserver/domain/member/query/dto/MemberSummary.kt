package site.qbox.qboxserver.domain.member.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.command.entity.Member


data class MemberSummary @QueryProjection constructor (
    val email: String,
    val nickname: String,
) {
    constructor(member: Member) : this(member.id, member.nickname)
}