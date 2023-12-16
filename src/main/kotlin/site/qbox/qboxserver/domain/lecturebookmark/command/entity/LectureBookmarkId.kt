package site.qbox.qboxserver.domain.lecturebookmark.command.entity

import jakarta.persistence.Embeddable
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import java.io.Serializable

@Embeddable
data class LectureBookmarkId(
    val memberId: String,
    val lectureId: LectureId,
) : Serializable