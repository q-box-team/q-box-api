package site.qbox.qboxserver.domain.lecture.query

import org.springframework.data.domain.Pageable
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class LectureDao (
    private val lectureQueryRepo: LectureQueryRepo
){
    fun findAllByDepartId(depart: Long, pageable: Pageable) : List<LectureRes> =
        lectureQueryRepo.findAllById_DepartId(depart, pageable).map(::LectureRes)
}