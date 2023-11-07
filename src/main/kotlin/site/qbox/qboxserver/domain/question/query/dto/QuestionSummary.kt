package site.qbox.qboxserver.domain.question.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.member.query.dto.MemberSummary
import java.time.LocalDateTime

data class QuestionSummary @QueryProjection constructor (
    val id: Long,
    val title: String,
    val writer: MemberSummary,
    val createdAt: LocalDateTime,
)