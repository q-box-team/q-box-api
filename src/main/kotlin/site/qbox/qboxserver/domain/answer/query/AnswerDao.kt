package site.qbox.qboxserver.domain.answer.query

import com.querydsl.jpa.impl.JPAQueryFactory
import site.qbox.qboxserver.domain.answer.command.entity.QAnswer.answer
import site.qbox.qboxserver.domain.answer.query.dto.AnswerRes
import site.qbox.qboxserver.domain.answer.query.dto.QAnswerRes
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.dto.QMemberRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class AnswerDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun findAllByQuestion(questionId: Long): List<AnswerRes> {
        return queryFactory
            .select(
                QAnswerRes(
                    answer.content,
                    answer.id.questionId,
                    QMemberRes(member.email, member.nickname)
                )
            )
            .from(answer)
            .where(answer.id.questionId.eq(questionId))
            .join(member).on(answer.id.writerId.eq(member.email))
            .fetch()
    }
}