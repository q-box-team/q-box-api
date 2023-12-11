package site.qbox.qboxserver.domain.lecturebookmark.command

import site.qbox.qboxserver.domain.lecturebookmark.command.dto.CreateLectureBookmarkReq
import site.qbox.qboxserver.domain.lecturebookmark.command.entity.LectureBookmark
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class LectureBookmarkSvc(
    private val lectureBookmarkRepo: LectureBookmarkRepo
) {
    fun register(req: CreateLectureBookmarkReq) {
        lectureBookmarkRepo.save(LectureBookmark(req.memberId, req.lectureId))
    }
}