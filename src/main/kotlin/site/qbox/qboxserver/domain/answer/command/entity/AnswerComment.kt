package site.qbox.qboxserver.domain.answer.command.entity

import jakarta.persistence.*

@Entity
class AnswerComment(
    @Lob var content: String,
    var commentWriterId: String,
    @Embedded val answer: AnswerId,
    @Id @GeneratedValue val id: Long = 0L,
)