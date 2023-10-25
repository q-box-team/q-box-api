package site.qbox.qboxserver.domain.lecture.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

interface LectureRepo : JpaRepository<Lecture, LectureId>