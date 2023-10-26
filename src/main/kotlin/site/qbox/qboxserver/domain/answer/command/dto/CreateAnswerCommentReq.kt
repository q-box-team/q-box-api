package site.qbox.qboxserver.domain.answer.command.dto

import site.qbox.qboxserver.domain.answer.command.entity.AnswerId

data class CreateAnswerCommentReq (
    val answer: AnswerId,
    val content: String,
)
