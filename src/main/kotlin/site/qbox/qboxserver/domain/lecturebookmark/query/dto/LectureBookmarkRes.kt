package site.qbox.qboxserver.domain.lecturebookmark.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes

data class LectureBookmarkRes @QueryProjection constructor (
    val memberId: String,
    val lectures: List<LectureRes>,
)