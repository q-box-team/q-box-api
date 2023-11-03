package site.qbox.qboxserver.domain.question.query

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.lecture.command.entity.QLecture.lecture
import site.qbox.qboxserver.domain.lecture.query.dto.QLectureRes
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.MemberQuery
import site.qbox.qboxserver.domain.question.command.entity.QQuestion.question
import site.qbox.qboxserver.domain.question.query.dto.QQuestionRes
import site.qbox.qboxserver.domain.question.query.dto.QQuestionSummary
import site.qbox.qboxserver.domain.question.query.dto.QuestionRes
import site.qbox.qboxserver.domain.question.query.dto.QuestionSummary
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class QuestionDao (
    private val queryFactory: JPAQueryFactory,
){
    fun findAllByLecture(code: String, depart: Long, pageable: Pageable): List<QuestionSummary> {
        return queryFactory
            .select(QQuestionSummary(
                question.id,
                question.title,
                MemberQuery.summary
                ))
            .from(question)
            .where(question.lecture.eq(LectureId(code, depart)))
            .join(member).on(question.writerId.eq(MemberQuery.id))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    fun getBy(id: Long): QuestionRes {
        return queryFactory
            .select(QQuestionRes(
                question.id,
                question.title,
                question.body,
                QLectureRes(
                    lecture.name,
                    lecture.id.code,
                    lecture.id.departId),
                MemberQuery.summary
            ))
            .from(question)
            .where(question.id.eq(id))
            .join(member).on(question.writerId.eq(MemberQuery.id))
            .join(lecture).on(question.lecture.eq(lecture.id))
            .fetchFirst()
    }
}