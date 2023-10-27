package site.qbox.qboxserver.domain.member.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.command.entity.Member


data class MemberRes @QueryProjection constructor (
    val email: String,
    val nickname: String
) {
    constructor(member: Member) : this(member.email, member.nickname)
}