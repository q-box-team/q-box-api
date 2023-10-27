package site.qbox.qboxserver.domain.answer.command

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerCommentReq
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerReq

@RestController
@RequestMapping("answers")
class AnswerCtrl(
    private val answerSvc: AnswerSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAnswer(@RequestBody req: CreateAnswerReq, auth: Authentication) =
        answerSvc.create(req, auth.name)

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAnswerComment(@RequestBody req: CreateAnswerCommentReq, auth:Authentication) =
        answerSvc.addAnswer(req, auth.name)
}