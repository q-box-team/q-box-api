package site.qbox.qboxserver.domain.lecture.command.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity
class Lecture(
    @EmbeddedId val id: LectureId,
    @Column(nullable = false) val name: String,
) {
    constructor(code: String, departId: Long, name: String) : this(LectureId(code, departId), name)
}