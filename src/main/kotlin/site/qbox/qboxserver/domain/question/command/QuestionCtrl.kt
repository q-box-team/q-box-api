package site.qbox.qboxserver.domain.question.command

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.question.command.dto.CreateQuestionReq
import site.qbox.qboxserver.global.dto.IdRes

@RestController
@RequestMapping("questions")
class QuestionCtrl(
    private val questionSvc: QuestionSvc
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createQuestion(
        @RequestBody @Valid req: CreateQuestionReq,
        auth: Authentication
    ): IdRes<Long> =
        questionSvc.create(req, auth.name)
}