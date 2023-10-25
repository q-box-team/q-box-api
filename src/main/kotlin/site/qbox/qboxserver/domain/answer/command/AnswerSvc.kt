package site.qbox.qboxserver.domain.answer.command

import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerReq
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class AnswerSvc (
    private val answerRepo: AnswerRepo,
){
    fun create(req: CreateAnswerReq, writer: String) {
        answerRepo.save(Answer(req.content, req.question, writer))
    }
}