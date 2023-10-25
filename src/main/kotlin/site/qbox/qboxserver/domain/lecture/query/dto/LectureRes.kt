package site.qbox.qboxserver.domain.lecture.query.dto

import com.querydsl.core.annotations.QueryProjection
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture

data class LectureRes @QueryProjection constructor (
    val name: String,
    val code: String,
    val departId: Long,
) {
    constructor(lecture: Lecture) : this(lecture.name, lecture.id.code, lecture.id.departId)
}