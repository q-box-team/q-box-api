package site.qbox.qboxserver.domain.lecture.query

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes


@RestController
@RequestMapping("lectures")
class LectureQueryCtrl(
    private val lectureDao: LectureDao,
) {
    @GetMapping
    fun getAllByDepart(depart: Long,@PageableDefault(page = 10, sort = ["name"]) pageable: Pageable) : List<LectureRes> =
        lectureDao.findAllByDepartId(depart, pageable)
}