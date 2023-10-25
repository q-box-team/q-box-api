package site.qbox.qboxserver.domain.member.command.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpReq(
    @Email val email: String,
    @NotBlank val nickname: String,
)