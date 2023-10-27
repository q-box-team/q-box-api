package site.qbox.qboxserver.domain.member.query.dto

import site.qbox.qboxserver.domain.member.command.entity.Member


class MemberAuthRes(
    val email: String,
    val password: String,
    val role: List<String>,
    val group: String,
) {
    constructor(member: Member) : this(member.email, member.password, member.role.map { it.name }, member.emailDomain)
}
