package site.qbox.qboxserver.domain.question.command.entity

import jakarta.persistence.*
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.global.entity.BaseEntity

@Entity
class Question(
    @Column(nullable = false) var title: String,
    @Lob @Column(nullable = false) var body: String,
    @Embedded val lecture: LectureId,
    @Column(nullable = false) val writerId: String,
    @Id @GeneratedValue val id: Long = 0L,
) : BaseEntity() {
    fun changeContents(title: String, body: String) {
        this.title = title
        this.body = body
    }
}