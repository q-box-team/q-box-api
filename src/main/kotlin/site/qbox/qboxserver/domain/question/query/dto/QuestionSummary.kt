package site.qbox.qboxserver.domain.question.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

data class QuestionSummary @QueryProjection constructor (
    val id: Long,
    val title: String,
    val writer: MemberRes,
)