package site.qbox.qboxserver.domain.lecturebookmark.command.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

@Entity
class LectureBookmark (
   @EmbeddedId val id : LectureBookmarkId
){
    constructor(memberId: String, lectureId: LectureId) : this(LectureBookmarkId(memberId, lectureId))
}