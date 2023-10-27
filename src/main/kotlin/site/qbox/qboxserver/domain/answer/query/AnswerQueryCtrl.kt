package site.qbox.qboxserver.domain.answer.query

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.answer.query.dto.AnswerRes

@RestController
@RequestMapping("answers")
class AnswerQueryCtrl(
    private val answerDao: AnswerDao,
) {
    @GetMapping
    fun getAllByQuestion(question: Long): List<AnswerRes> =
        answerDao.findAllByQuestion(question)

}