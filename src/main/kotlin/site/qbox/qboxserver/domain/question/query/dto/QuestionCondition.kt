package site.qbox.qboxserver.domain.question.query.dto

import com.querydsl.core.types.dsl.BooleanExpression
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.question.command.entity.QQuestion.question

data class QuestionCondition(
    val title: String = "",
    val body: String = "",
    val writerNickname: String = "",
    val lectureCode: String,
    val lectureDepart: Long,
) {

    fun toFilter(): BooleanExpression =
        question.lecture.eq(LectureId(lectureCode, lectureDepart))
            .and(question.title.contains(title))
            .and(question.body.contains(body))
            .and(member.nickname.contains(writerNickname))
}
