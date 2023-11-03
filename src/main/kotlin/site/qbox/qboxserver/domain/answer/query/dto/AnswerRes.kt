package site.qbox.qboxserver.domain.answer.query.dto

import site.qbox.qboxserver.domain.member.query.dto.MemberSummary

data class AnswerRes(
    val content: String,
    val questionId: Long,
    val writer: MemberSummary,
    val comments: List<AnswerCommentRes>
) {
    constructor(res: AnswerSummary, comments: List<AnswerCommentRes>) :
            this(res.content, res.questionId, res.writer, comments)
}
