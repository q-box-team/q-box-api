package site.qbox.qboxserver.domain.lecture.command

import site.qbox.qboxserver.domain.lecture.command.dto.CreateLectureReq
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class LectureSvc(
    private val lectureRepo: LectureRepo,
) {
    fun create(req: CreateLectureReq) {
        lectureRepo.save(Lecture(req.code, req.departId, req.name))
    }
}