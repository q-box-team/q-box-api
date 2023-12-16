package site.qbox.qboxserver.domain.lecturebookmark.command.dto

import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

data class CreateLectureBookmarkReq (
    val memberId : String,
    val lecture : LectureId,
)