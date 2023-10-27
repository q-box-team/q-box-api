package site.qbox.qboxserver.domain.lecture.query

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

interface LectureQueryRepo : JpaRepository<Lecture, LectureId> {
    fun findAllById_DepartId(departId: Long, pageable: Pageable) : List<Lecture>
}