package site.qbox.qboxserver.domain.question.query

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import site.qbox.qboxserver.domain.lecture.command.entity.QLecture.lecture
import site.qbox.qboxserver.domain.lecture.query.dto.QLectureRes
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.MemberQuery
import site.qbox.qboxserver.domain.question.command.entity.QQuestion.question
import site.qbox.qboxserver.domain.question.query.dto.*
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class QuestionDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun findAllBy(condition: QuestionCondition, pageable: Pageable): List<QuestionSummary> {
        return queryFactory
            .select(questionSummary())
            .from(question)
            .where(condition.toFilter())
            .join(member).on(question.writerId.eq(MemberQuery.id))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(question.createdAt.desc())
            .fetch()
    }

    private fun questionSummary() = QQuestionSummary(
        question.id,
        question.title,
        MemberQuery.summary,
        question.createdAt,
    )

    fun getBy(id: Long): QuestionRes {
        return queryFactory
            .select(questionRes())
            .from(question)
            .where(question.id.eq(id))
            .join(member).on(question.writerId.eq(MemberQuery.id))
            .join(lecture).on(question.lecture.eq(lecture.id))
            .fetchFirst()
    }

    private fun questionRes() = QQuestionRes(
        question.id,
        question.title,
        question.body,
        QLectureRes(
            lecture.name,
            lecture.id.code,
            lecture.id.departId
        ),
        MemberQuery.summary,
        question.createdAt,
        question.updatedAt
    )
}