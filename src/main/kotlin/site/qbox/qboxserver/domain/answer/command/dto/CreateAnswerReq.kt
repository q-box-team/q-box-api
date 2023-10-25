package site.qbox.qboxserver.domain.answer.command.dto

data class CreateAnswerReq (
    val content: String,
    val question : Long
)