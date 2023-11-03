package site.qbox.qboxserver.domain.answer.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.query.dto.MemberSummary

data class AnswerCommentRes @QueryProjection constructor(
    val id: Long,
    val content: String,
    val writer: MemberSummary,
)