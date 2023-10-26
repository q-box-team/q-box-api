package site.qbox.qboxserver.domain.answer.command

import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerCommentReq
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerReq
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.domain.answer.command.entity.AnswerComment
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class AnswerSvc (
    private val answerRepo: AnswerRepo,
    private val answerCommentRepo: AnswerCommentRepo,
){
    fun create(req: CreateAnswerReq, writer: String) {
        answerRepo.save(Answer(req.content, req.question, writer))
    }

    fun addAnswer(req: CreateAnswerCommentReq, writer: String) {
        answerCommentRepo.save(AnswerComment(req.content, writer, req.answer))
    }
}