package site.qbox.qboxserver.domain.answer.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

data class AnswerRes @QueryProjection constructor(
    val content: String,
    val questionId: Long,
    val writer: MemberRes,
)