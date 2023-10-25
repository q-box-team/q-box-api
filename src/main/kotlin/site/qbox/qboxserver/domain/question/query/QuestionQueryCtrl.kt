package site.qbox.qboxserver.domain.question.query

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.question.query.dto.QuestionRes
import site.qbox.qboxserver.domain.question.query.dto.QuestionSummary

@RestController
@RequestMapping("questions")
class QuestionQueryCtrl (
    private val questionDao: QuestionDao,
){
    @GetMapping
    fun getAllByLecture(code: String, depart: Long, @PageableDefault(page = 10) pageable: Pageable) : List<QuestionSummary> =
        questionDao.findAllByLecture(code, depart, pageable)

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long) : QuestionRes =
        questionDao.getBy(id)
}