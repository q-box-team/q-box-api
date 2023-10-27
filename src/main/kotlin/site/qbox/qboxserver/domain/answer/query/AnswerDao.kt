package site.qbox.qboxserver.domain.answer.query

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.jpa.impl.JPAQueryFactory
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId
import site.qbox.qboxserver.domain.answer.command.entity.QAnswer.answer
import site.qbox.qboxserver.domain.answer.command.entity.QAnswerComment.answerComment
import site.qbox.qboxserver.domain.answer.query.dto.*
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.dto.QMemberRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class AnswerDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun findAllByQuestion(questionId: Long): List<AnswerRes> {
        val comments = getCommentsGroup(questionId)
        val summary = getSummary(questionId)

        return mapToRes(summary, comments)

    }

    private fun getCommentsGroup(questionId: Long): MutableMap<AnswerId, MutableList<AnswerCommentRes>> =
        queryFactory.from(answerComment)
            .where(answerComment.answer.questionId.eq(questionId))
            .join(member).on(answerComment.commentWriterId.eq(member.email))
            .transform(
                groupBy(answerComment.answer).`as`(
                    list(
                        QAnswerCommentRes(
                            answerComment.id,
                            answerComment.content,
                            QMemberRes(member.email, member.nickname)))))

    private fun getSummary(questionId: Long): MutableList<AnswerSummary> =
        queryFactory.select(
            QAnswerSummary(
                answer.content,
                answer.id.questionId,
                QMemberRes(member.email, member.nickname)
            ))
            .from(answer)
            .where(answer.id.questionId.eq(questionId))
            .join(member).on(answer.id.writerId.eq(member.email))
            .fetch()

    private fun mapToRes(
        summary: MutableList<AnswerSummary>,
        comments: MutableMap<AnswerId, MutableList<AnswerCommentRes>>
    ) = summary.map {
        AnswerRes(it, comments[getAnswerId(it)] ?: emptyList())
    }

    private fun getAnswerId(it: AnswerSummary) =
        AnswerId(it.questionId, it.writer.email)
}