package site.qbox.qboxserver.domain.answer.command.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AnswerId(
    val questionId: Long,
    val writerId: String
) : Serializable