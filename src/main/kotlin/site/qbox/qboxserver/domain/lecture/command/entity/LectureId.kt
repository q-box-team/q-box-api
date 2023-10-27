package site.qbox.qboxserver.domain.lecture.command.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class LectureId(
    val code: String,
    val departId: Long
) : Serializable
