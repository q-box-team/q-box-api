package site.qbox.qboxserver.domain.question.command.dto

import jakarta.validation.constraints.NotBlank
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

data class CreateQuestionReq(
    @NotBlank val title: String,
    @NotBlank val content: String,
    @NotBlank val lecture: LectureId,
)
