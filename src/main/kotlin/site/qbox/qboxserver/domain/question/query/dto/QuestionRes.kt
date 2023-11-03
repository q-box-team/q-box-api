package site.qbox.qboxserver.domain.question.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes
import site.qbox.qboxserver.domain.member.query.dto.MemberSummary

data class QuestionRes @QueryProjection constructor (
    val id: Long,
    val title: String,
    val body: String,
    val lecture: LectureRes,
    val writer: MemberSummary,
)