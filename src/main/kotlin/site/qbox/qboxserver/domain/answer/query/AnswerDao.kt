package site.qbox.qboxserver.domain.answer.query

import com.querydsl.core.group.GroupBy
import com.querydsl.jpa.impl.JPAQueryFactory
import site.qbox.qboxserver.domain.answer.command.entity.QAnswer.answer
import site.qbox.qboxserver.domain.answer.command.entity.QAnswerComment.answerComment
import site.qbox.qboxserver.domain.answer.query.dto.AnswerRes
import site.qbox.qboxserver.domain.answer.query.dto.QAnswerCommentRes
import site.qbox.qboxserver.domain.answer.query.dto.QAnswerRes
import site.qbox.qboxserver.domain.member.command.entity.QMember
import site.qbox.qboxserver.domain.member.query.dto.QMemberRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class AnswerDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun findAllByQuestion(questionId: Long): List<AnswerRes> {
        val answerWriter = QMember.member
        val commentWriter = QMember.member
        return queryFactory
            .select(
                QAnswerRes(
                    answer.content,
                    answer.id.questionId,
                    QMemberRes(answerWriter.email, answerWriter.nickname),
                    GroupBy.list(
                        QAnswerCommentRes(
                            answerComment.id,
                            answerComment.content,
                            QMemberRes(commentWriter.email, commentWriter.nickname)
                        )
                    )
                )
            )
            .from(answer)
            .where(answer.id.questionId.eq(questionId))
            .leftJoin(answerComment).on(answerComment.answer.eq(answer.id))
            .join(answerWriter).on(answer.id.writerId.eq(answerWriter.email))
            .join(commentWriter).on(answerComment.commentWriterId.eq(commentWriter.email))
            .groupBy(answer.id)
            .fetch()
    }
}