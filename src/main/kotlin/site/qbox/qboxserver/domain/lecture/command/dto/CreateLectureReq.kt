package site.qbox.qboxserver.domain.lecture.command.dto

import jakarta.validation.constraints.NotBlank

data class CreateLectureReq(
    @NotBlank val code: String,
    @NotBlank val name: String,
    @NotBlank val departId: Long,
)