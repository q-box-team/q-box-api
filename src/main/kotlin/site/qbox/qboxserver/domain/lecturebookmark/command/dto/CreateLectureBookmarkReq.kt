package site.qbox.qboxserver.domain.lecturebookmark.command.dto

import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

data class CreateLectureBookmarkReq (
    val lectureId : LectureId,
    val memberId : String,
)