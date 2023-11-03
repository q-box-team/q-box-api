package site.qbox.qboxserver.domain.member.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.depart.query.dto.DepartRes

data class MemberRes @QueryProjection constructor (
    val email: String,
    val nickname: String,
    val depart: DepartRes,
)